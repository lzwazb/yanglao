<template>
  <div class="message-board">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>留言板</span>
          </template>
          <div class="message-list" v-loading="loadingList">
            <div v-for="message in messages" :key="message.id" class="message-item">
              <div class="message-header">
                <span class="message-author">{{ message.username }}</span>
                <span class="message-time">{{ message.createTime }}</span>
              </div>
              <div class="message-content">{{ message.content }}</div>

              <!-- 管理员回复区域 -->
              <div class="message-footer" v-if="message.status === 1">
                <el-divider />
                <div class="reply-content">
                  <div class="reply-header">
                    <span class="reply-label">管理员回复 ({{ message.replyUser }})：</span>
                    <span class="reply-time">{{ message.replyTime }}</span>
                  </div>
                  <div class="reply-text">{{ message.replyContent }}</div>
                </div>
              </div>

              <!-- 用户追问按钮 -->
              <div class="action-bar" v-if="canReply(message)">
                 <el-button type="text" size="small" @click="handleUserReply(message)">追问/回复</el-button>
              </div>
            </div>
            <el-empty v-if="messages.length === 0" description="暂无留言" />
          </div>

          <div style="margin-top: 20px; text-align: right" v-if="total > 0">
            <el-pagination
              background
              layout="prev, pager, next"
              :total="total"
              :page-size="pageSize"
              @current-change="handlePageChange"
            />
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header>
            <span>发布留言</span>
          </template>
          <el-form :model="messageForm" label-width="0">
            <el-form-item>
              <el-input
                v-model="messageForm.content"
                type="textarea"
                :rows="8"
                placeholder="请输入您的留言或建议"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit" :loading="submitting" style="width: 100%">提交留言</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <!-- 用户追问对话框 -->
    <el-dialog v-model="replyDialogVisible" title="追问/回复" width="500px">
      <el-form :model="userReplyForm">
        <el-form-item>
          <el-input
            v-model="userReplyForm.content"
            type="textarea"
            :rows="4"
            placeholder="请输入追问内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUserReply" :loading="replySubmitting">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMessagePage, addMessage, userReplyMessage } from '@/api/message'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const loadingList = ref(false)
const submitting = ref(false)
const messages = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const replyDialogVisible = ref(false)
const replySubmitting = ref(false)
const currentReplyMessage = ref(null)
const userReplyForm = reactive({
  content: ''
})

const messageForm = reactive({
  content: ''
})

const loadMessages = async () => {
  loadingList.value = true
  try {
    // 这里复用管理端的查询接口，实际场景可能需要区分用户端接口（例如只看自己的，或者公开的）
    // 假设留言板是公开的
    const res = await getMessagePage({
      pageNum: pageNum.value,
      pageSize: pageSize.value
    })
    messages.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取留言列表失败', error)
  } finally {
    loadingList.value = false
  }
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadMessages()
}

const handleSubmit = async () => {
  if (!userStore.userInfo) {
    ElMessage.warning('请先登录')
    return
  }
  if (!messageForm.content.trim()) {
    ElMessage.warning('请输入留言内容')
    return
  }

  submitting.value = true
  try {
    await addMessage({
      userId: userStore.userInfo.id,
      username: userStore.userInfo.username,
      content: messageForm.content
    })
    ElMessage.success('留言提交成功')
    messageForm.content = ''
    pageNum.value = 1
    loadMessages()
  } catch (error) {
    console.error('提交失败', error)
  } finally {
    submitting.value = false
  }
}

// 判断是否可以追问：当前用户是留言者本人
const canReply = (message) => {
  return userStore.userInfo && String(userStore.userInfo.id) === String(message.userId)
}

const handleUserReply = (message) => {
  currentReplyMessage.value = message
  userReplyForm.content = ''
  replyDialogVisible.value = true
}

const submitUserReply = async () => {
  if (!userReplyForm.content.trim()) {
    ElMessage.warning('请输入内容')
    return
  }

  replySubmitting.value = true
  try {
    await userReplyMessage({
      id: currentReplyMessage.value.id,
      content: userReplyForm.content,
      username: userStore.userInfo.username
    })
    ElMessage.success('提交成功')
    replyDialogVisible.value = false
    loadMessages()
  } catch (error) {
    console.error('追问失败', error)
  } finally {
    replySubmitting.value = false
  }
}

onMounted(() => {
  loadMessages()
})
</script>

<style scoped>
.message-board {
  padding: 20px;
}

.message-list {
  min-height: 400px;
}

.message-item {
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.message-item:last-child {
  border-bottom: none;
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.message-author {
  font-weight: bold;
  color: #333;
  font-size: 16px;
}

.message-time {
  font-size: 12px;
  color: #999;
}

.message-content {
  color: #666;
  line-height: 1.6;
  font-size: 14px;
  white-space: pre-wrap; /* 保留换行 */
}

.message-footer {
  margin-top: 15px;
}

.reply-content {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  border-left: 4px solid #409EFF;
}

.reply-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
  font-size: 12px;
}

.reply-label {
  font-weight: bold;
  color: #409EFF;
}

.reply-time {
  color: #999;
}

.reply-text {
  color: #606266;
  font-size: 14px;
  line-height: 1.5;
}

.action-bar {
  margin-top: 10px;
  text-align: right;
}
</style>
