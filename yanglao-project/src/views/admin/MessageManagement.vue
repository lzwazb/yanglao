<template>
  <div class="message-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>留言管理</span>
          <div class="filter-container">
            <el-select v-model="filterStatus" placeholder="状态筛选" clearable @change="handleFilterChange" style="width: 150px">
              <el-option label="未回复" :value="0" />
              <el-option label="已回复" :value="1" />
            </el-select>
          </div>
        </div>
      </template>

      <el-table :data="messageList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="留言用户" width="120" />
        <el-table-column prop="content" label="留言内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="留言时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">
              {{ row.status === 1 ? '已回复' : '未回复' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="replyContent" label="回复内容" show-overflow-tooltip />
        <el-table-column prop="replyUser" label="回复人" width="120" />
        <el-table-column prop="replyTime" label="回复时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleReply(row)">回复</el-button>
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

    <!-- 回复对话框 -->
    <el-dialog v-model="dialogVisible" title="回复留言" width="500px">
      <el-form :model="replyForm" label-width="80px">
        <el-form-item label="留言内容">
          <div style="padding-top: 6px; color: #666;">{{ currentMessage?.content }}</div>
        </el-form-item>
        <el-form-item label="回复内容">
          <el-input
            v-model="replyForm.replyContent"
            type="textarea"
            :rows="4"
            placeholder="请输入回复内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitReply" :loading="submitting">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMessagePage, replyMessage, deleteMessage } from '@/api/message'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const messageList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const filterStatus = ref('')

const dialogVisible = ref(false)
const submitting = ref(false)
const currentMessage = ref(null)
const replyForm = reactive({
  replyContent: ''
})

const loadMessages = async () => {
  loading.value = true
  try {
    const res = await getMessagePage({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      status: filterStatus.value === '' ? undefined : filterStatus.value
    })
    messageList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取留言列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleFilterChange = () => {
  pageNum.value = 1
  loadMessages()
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadMessages()
}

const handleReply = (row) => {
  currentMessage.value = row
  replyForm.replyContent = row.replyContent || ''
  dialogVisible.value = true
}

const handleSubmitReply = async () => {
  if (!replyForm.replyContent.trim()) {
    ElMessage.warning('请输入回复内容')
    return
  }

  submitting.value = true
  try {
    await replyMessage({
      id: currentMessage.value.id,
      replyContent: replyForm.replyContent,
      replyUser: userStore.userInfo?.username || '管理员'
    })
    ElMessage.success('回复成功')
    dialogVisible.value = false
    loadMessages()
  } catch (error) {
    console.error('回复失败', error)
  } finally {
    submitting.value = false
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该留言吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteMessage(row.id)
      ElMessage.success('删除成功')
      loadMessages()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

onMounted(() => {
  loadMessages()
})
</script>

<style scoped>
.message-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
