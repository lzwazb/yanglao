<template>
  <div class="activity-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>活动管理</span>
          <el-button type="primary" @click="handleAdd">发布活动</el-button>
        </div>
      </template>

      <div class="filter-container">
        <el-input
          v-model="searchTitle"
          placeholder="搜索活动标题"
          style="width: 200px; margin-right: 10px"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>

      <el-table :data="activityList" style="width: 100%; margin-top: 20px" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="活动标题" show-overflow-tooltip />
        <el-table-column prop="location" label="地点" show-overflow-tooltip />
        <el-table-column label="时间范围" width="300">
          <template #default="{ row }">
            {{ formatTime(row.startTime) }} 至 {{ formatTime(row.endTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="maxPeople" label="人数限制" width="100">
          <template #default="{ row }">
            {{ row.maxPeople > 0 ? row.maxPeople : '不限' }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top: 20px; text-align: right">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <!-- 编辑/新增对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="活动标题">
          <el-input v-model="form.title" placeholder="请输入活动标题" />
        </el-form-item>
        <el-form-item label="活动内容">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入活动内容" />
        </el-form-item>
        <el-form-item label="活动地点">
          <el-input v-model="form.location" placeholder="请输入活动地点" />
        </el-form-item>
        <el-form-item label="活动时间">
          <el-date-picker
            v-model="timeRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="最大人数">
          <el-input-number v-model="form.maxPeople" :min="0" :step="1" placeholder="0为不限" />
          <span style="margin-left: 10px; color: #999; font-size: 12px">0表示不限制人数</span>
        </el-form-item>
        <el-form-item label="状态" v-if="form.id">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="未开始" :value="0" />
            <el-option label="进行中" :value="1" />
            <el-option label="已结束" :value="2" />
            <el-option label="已取消" :value="-1" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getActivityPage, addActivity, updateActivity, deleteActivity } from '@/api/activity'

const activityList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const searchTitle = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('发布活动')
const timeRange = ref([])

const form = reactive({
  id: null,
  title: '',
  content: '',
  location: '',
  startTime: '',
  endTime: '',
  maxPeople: 0,
  status: 0
})

const loadActivities = async () => {
  loading.value = true
  try {
    const res = await getActivityPage({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      title: searchTitle.value || undefined
    })
    activityList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取活动列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNum.value = 1
  loadActivities()
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadActivities()
}

const handleAdd = () => {
  dialogTitle.value = '发布活动'
  Object.assign(form, {
    id: null,
    title: '',
    content: '',
    location: '',
    startTime: '',
    endTime: '',
    maxPeople: 0,
    status: 0
  })
  timeRange.value = []
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑活动'
  Object.assign(form, row)
  timeRange.value = [row.startTime, row.endTime]
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该活动吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteActivity(row.id)
      ElMessage.success('删除成功')
      loadActivities()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

const handleSubmit = async () => {
  if (!form.title || !form.location || !timeRange.value || timeRange.value.length < 2) {
    ElMessage.warning('请填写完整信息')
    return
  }

  form.startTime = timeRange.value[0]
  form.endTime = timeRange.value[1]

  try {
    if (form.id) {
      await updateActivity(form)
      ElMessage.success('更新成功')
    } else {
      await addActivity(form)
      ElMessage.success('发布成功')
    }
    dialogVisible.value = false
    loadActivities()
  } catch (error) {
    console.error('操作失败', error)
  }
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
  return timeStr.replace('T', ' ')
}

const getStatusType = (status) => {
  const map = {
    0: 'success',
    1: 'warning',
    2: 'info',
    '-1': 'danger'
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    0: '未开始',
    1: '进行中',
    2: '已结束',
    '-1': '已取消'
  }
  return map[status] || '未知'
}

onMounted(() => {
  loadActivities()
})
</script>

<style scoped>
.activity-manage {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
