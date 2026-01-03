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
            <div class="card-header">
              <span>最近活动</span>
              <el-button link type="primary" @click="$router.push('/layout/activity/list')">查看更多</el-button>
            </div>
          </template>
          <div v-if="activities.length > 0">
            <div v-for="activity in activities" :key="activity.id" class="activity-item" @click="$router.push('/layout/activity/list')">
              <div class="activity-title">{{ activity.title }}</div>
              <el-tag size="small" :type="getStatusType(activity.status)">{{ getStatusText(activity.status) }}</el-tag>
            </div>
          </div>
          <el-empty v-else description="暂无活动" :image-size="60" />
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
import { getActivityList } from '@/api/activity'

const userStore = useUserStore()

const stats = ref([
  { title: '用户总数', value: '0', icon: User, color: '#409EFF' },
  { title: '家人总数', value: '0', icon: UserFilled, color: '#67C23A' },
  { title: '服务总数', value: '0', icon: Service, color: '#E6A23C' },
  { title: '活动总数', value: '0', icon: Trophy, color: '#F56C6C' }
])

const notices = ref([])
const activities = ref([])

const loadData = async () => {
  try {
    const noticeRes = await getNoticeList()
    notices.value = noticeRes.slice(0, 5)

    const activityRes = await getActivityList()
    activities.value = activityRes.slice(0, 5)
  } catch (error) {
    console.error('获取数据失败', error)
  }
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
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
    0: '报名中',
    1: '进行中',
    2: '已结束',
    '-1': '已取消'
  }
  return map[status] || '未知'
}

onMounted(() => {
  loadData()
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

.activity-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-item:hover {
  background-color: #f9f9f9;
}

.activity-title {
  font-size: 14px;
  color: #333;
}
</style>
