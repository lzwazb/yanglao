<template>
  <div class="activity-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>活动管理</span>
          <el-button type="primary" @click="handleAdd">发布活动</el-button>
        </div>
      </template>
      <el-table :data="activityList" style="width: 100%">
        <el-table-column prop="name" label="活动名称" />
        <el-table-column prop="date" label="活动日期" />
        <el-table-column prop="location" label="活动地点" />
        <el-table-column prop="participants" label="报名人数" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === '进行中' ? 'success' : 'info'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="primary" @click="handleViewParticipants(row)">查看报名</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="活动名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="活动描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="活动日期">
          <el-date-picker v-model="form.date" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="活动地点">
          <el-input v-model="form.location" />
        </el-form-item>
        <el-form-item label="活动状态">
          <el-radio-group v-model="form.status">
            <el-radio label="报名中">报名中</el-radio>
            <el-radio label="进行中">进行中</el-radio>
            <el-radio label="已结束">已结束</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="participantsDialogVisible" title="报名人员" width="600px">
      <el-table :data="participantsList" style="width: 100%">
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="signupTime" label="报名时间" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const activityList = ref([
  { id: 1, name: '健康讲座', date: '2024-01-20', location: '社区活动中心', participants: 25, status: '报名中' }
])

const participantsList = ref([
  { name: '张爷爷', phone: '13800138000', signupTime: '2024-01-15 10:00' }
])

const dialogVisible = ref(false)
const participantsDialogVisible = ref(false)
const dialogTitle = ref('发布活动')

const form = reactive({
  id: null,
  name: '',
  description: '',
  date: null,
  location: '',
  status: '报名中'
})

const handleAdd = () => {
  dialogTitle.value = '发布活动'
  Object.assign(form, {
    id: null,
    name: '',
    description: '',
    date: null,
    location: '',
    status: '报名中'
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑活动'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该活动吗？', '提示', {
      type: 'warning'
    })
    ElMessage.success('删除成功')
    // TODO: 调用后端接口
  } catch (error) {
    // 取消操作
  }
}

const handleSubmit = () => {
  ElMessage.success('保存成功')
  dialogVisible.value = false
  // TODO: 调用后端接口
}

const handleViewParticipants = (row) => {
  participantsDialogVisible.value = true
  // TODO: 加载报名人员列表
}

onMounted(() => {
  // TODO: 加载活动列表
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

