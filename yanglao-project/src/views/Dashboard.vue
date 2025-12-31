<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6" v-for="stat in stats" :key="stat.title">
        <el-card class="stat-card">
          <div class="stat-content">
            <el-icon :size="40" :color="stat.color"><component :is="stat.icon" /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stat.value }}</div>
              <div class="stat-title">{{ stat.title }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最新公告</span>
              <el-button link type="primary" @click="$router.push('/layout/notice/list')">查看更多</el-button>
            </div>
          </template>
          <el-timeline v-if="notices.length > 0">
            <el-timeline-item
              v-for="notice in notices"
              :key="notice.id"
              :timestamp="formatTime(notice.createTime)"
              :type="getNoticeType(notice.type)"
            >
              {{ notice.title }}
            </el-timeline-item>
          </el-timeline>
          <el-empty v-else description="暂无公告" :image-size="60" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>最近活动</span>
          </template>
          <el-list>
            <el-list-item v-for="activity in activities" :key="activity.id">
              <span>{{ activity.name }}</span>
              <el-tag type="success" size="small">{{ activity.status }}</el-tag>
            </el-list-item>
          </el-list>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { User, UserFilled, Service, Trophy } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getNoticeList } from '@/api/notice'

const userStore = useUserStore()

const stats = ref([
  { title: '用户总数', value: '0', icon: User, color: '#409EFF' },
  { title: '家人总数', value: '0', icon: UserFilled, color: '#67C23A' },
  { title: '服务总数', value: '0', icon: Service, color: '#E6A23C' },
  { title: '活动总数', value: '0', icon: Trophy, color: '#F56C6C' }
])

const notices = ref([])

const activities = ref([
  { id: 1, name: '健康讲座', status: '进行中' },
  { id: 2, name: '社区活动', status: '已报名' }
])

const loadNotices = async () => {
  try {
    const res = await getNoticeList()
    // 只取最新的5条公告
    notices.value = res.slice(0, 5)
  } catch (error) {
    console.error('获取公告失败', error)
  }
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
  // 简单截取日期部分 yyyy-MM-dd
  return timeStr.split(' ')[0]
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
.dashboard {
  padding: 20px;
}

.stat-card {
  margin-bottom: 20px;
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #333;
}

.stat-title {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
