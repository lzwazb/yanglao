<template>
  <div class="message-board">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>留言板</span>
          </template>
          <div class="message-list">
            <div v-for="message in messages" :key="message.id" class="message-item">
              <div class="message-header">
                <span class="message-author">{{ message.author }}</span>
                <span class="message-time">{{ message.createTime }}</span>
              </div>
              <div class="message-content">{{ message.content }}</div>
              <div class="message-footer" v-if="message.reply">
                <el-divider />
                <div class="reply-content">
                  <span class="reply-label">管理员回复：</span>
                  <span>{{ message.reply }}</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <span>发布留言</span>
          </template>
          <el-form :model="messageForm" label-width="80px">
            <el-form-item label="留言内容">
              <el-input
                v-model="messageForm.content"
                type="textarea"
                :rows="8"
                placeholder="请输入您的留言或建议"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit" :loading="loading">提交</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const loading = ref(false)

const messages = ref([
  {
    id: 1,
    author: '张爷爷',
    content: '希望增加更多的健康检测项目',
    createTime: '2024-01-15 10:00:00',
    reply: '感谢您的建议，我们会考虑增加更多健康检测项目。'
  },
  {
    id: 2,
    author: '李奶奶',
    content: '活动时间能否调整到上午？',
    createTime: '2024-01-14 15:00:00',
    reply: null
  }
])

const messageForm = reactive({
  content: ''
})

const handleSubmit = async () => {
  if (!messageForm.content.trim()) {
    ElMessage.warning('请输入留言内容')
    return
  }
  loading.value = true
  try {
    // TODO: 调用后端接口
    ElMessage.success('留言提交成功')
    messageForm.content = ''
    // 刷新留言列表
  } catch (error) {
    ElMessage.error('提交失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  // TODO: 加载留言列表
})
</script>

<style scoped>
.message-board {
  padding: 20px;
}

.message-list {
  max-height: 600px;
  overflow-y: auto;
}

.message-item {
  padding: 15px;
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
}

.message-time {
  font-size: 12px;
  color: #999;
}

.message-content {
  color: #666;
  line-height: 1.6;
}

.message-footer {
  margin-top: 10px;
}

.reply-content {
  background: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
}

.reply-label {
  font-weight: bold;
  color: #409EFF;
}
</style>

