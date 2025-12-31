<template>
  <div class="notice-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>公告管理</span>
          <el-button type="primary" @click="handleAdd">发布公告</el-button>
        </div>
      </template>

      <div class="filter-container">
        <el-input
          v-model="searchTitle"
          placeholder="搜索公告标题"
          style="width: 200px; margin-right: 10px"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>

      <el-table :data="noticeList" style="width: 100%; margin-top: 20px" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" show-overflow-tooltip />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getNoticeType(row.type)">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="author" label="发布人" width="120" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '已发布' : '已撤回' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button
              size="small"
              :type="row.status === 1 ? 'warning' : 'success'"
              @click="handleToggleStatus(row)"
            >
              {{ row.status === 1 ? '撤回' : '发布' }}
            </el-button>
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
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '发布公告'" width="600px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入公告标题" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="通知" value="通知" />
            <el-option label="重要" value="重要" />
            <el-option label="活动" value="活动" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="6" placeholder="请输入公告内容" />
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
import { getNoticePage, addNotice, updateNotice, deleteNotice } from '@/api/notice'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const noticeList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const searchTitle = ref('')
const dialogVisible = ref(false)

const form = reactive({
  id: null,
  title: '',
  type: '通知',
  content: '',
  author: '',
  status: 1
})

const loadNotices = async () => {
  loading.value = true
  try {
    const res = await getNoticePage({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      title: searchTitle.value || undefined
    })
    noticeList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取公告列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNum.value = 1
  loadNotices()
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadNotices()
}

const handleAdd = () => {
  form.id = null
  form.title = ''
  form.type = '通知'
  form.content = ''
  form.author = userStore.userInfo?.username || '管理员'
  form.status = 1
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.title || !form.content) {
    ElMessage.warning('请填写完整信息')
    return
  }

  try {
    if (form.id) {
      await updateNotice(form)
      ElMessage.success('更新成功')
    } else {
      await addNotice(form)
      ElMessage.success('发布成功')
    }
    dialogVisible.value = false
    loadNotices()
  } catch (error) {
    console.error('操作失败', error)
  }
}

const handleToggleStatus = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  try {
    await updateNotice({
      ...row,
      status: newStatus
    })
    ElMessage.success(newStatus === 1 ? '已发布' : '已撤回')
    loadNotices()
  } catch (error) {
    console.error('状态更新失败', error)
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该公告吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteNotice(row.id)
      ElMessage.success('删除成功')
      loadNotices()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

const getNoticeType = (type) => {
  const map = {
    '重要': 'danger',
    '通知': 'primary',
    '活动': 'success'
  }
  return map[type] || 'info'
}

onMounted(() => {
  loadNotices()
})
</script>

<style scoped>
.notice-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
