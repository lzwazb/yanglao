<template>
  <div class="notice-list">
    <el-card>
      <template #header>
        <span>公告栏</span>
      </template>
      <el-scrollbar height="600px">
        <el-timeline>
          <el-timeline-item
            v-for="notice in notices"
            :key="notice.id"
            :timestamp="notice.createTime"
            placement="top"
          >
            <el-card>
              <h4>{{ notice.title }}</h4>
              <p>{{ notice.content }}</p>
              <div class="notice-footer">
                <el-tag size="small" :type="notice.type === '重要' ? 'danger' : 'info'">{{ notice.type }}</el-tag>
                <span class="notice-author">发布人：{{ notice.author }}</span>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-scrollbar>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const notices = ref([
  {
    id: 1,
    title: '欢迎使用养老服务平台',
    content: '平台正式上线，为老年人提供全方位的养老服务，包括生活服务、健康管理、活动管理等。',
    type: '重要',
    author: '管理员',
    createTime: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    title: '系统维护通知',
    content: '系统将于2024年1月20日进行维护，维护期间可能无法正常使用，请提前做好准备。',
    type: '通知',
    author: '管理员',
    createTime: '2024-01-15 14:00:00'
  },
  {
    id: 3,
    title: '健康讲座活动通知',
    content: '本周六下午2点在社区活动中心举办健康讲座，欢迎各位老人参加。',
    type: '活动',
    author: '管理员',
    createTime: '2024-01-16 09:00:00'
  }
])

onMounted(() => {
  // TODO: 加载公告列表
})
</script>

<style scoped>
.notice-list {
  padding: 20px;
}

.notice-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.notice-author {
  font-size: 12px;
  color: #999;
}
</style>

