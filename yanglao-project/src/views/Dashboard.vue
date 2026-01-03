<template>
  <div class="dashboard">
    <!-- 欢迎语 -->
    <div class="welcome-section">
      <h2>{{ greeting }}，{{ userStore.userInfo?.username || '用户' }}</h2>
      <p class="welcome-subtitle">欢迎回到养老服务平台，祝您今天心情愉快！</p>
    </div>

    <!-- 快捷操作入口 -->
    <el-card class="quick-actions-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span class="header-title"><el-icon><Menu /></el-icon> 快捷操作</span>
        </div>
      </template>
      <div class="quick-actions">
        <!-- 管理员快捷操作 -->
        <template v-if="userStore.userType === 'admin'">
          <div class="action-item" @click="$router.push('/layout/admin/notice')">
            <el-icon class="action-icon" style="background-color: #e6f7ff; color: #1890ff"><Bell /></el-icon>
            <span>发布公告</span>
          </div>
          <div class="action-item" @click="$router.push('/layout/activity/manage')">
            <el-icon class="action-icon" style="background-color: #fff7e6; color: #fa8c16"><Flag /></el-icon>
            <span>发布活动</span>
          </div>
          <div class="action-item" @click="$router.push('/layout/admin/service/order')">
            <el-icon class="action-icon" style="background-color: #f6ffed; color: #52c41a"><Tickets /></el-icon>
            <span>审核订单</span>
          </div>
           <div class="action-item" @click="$router.push('/layout/admin/user')">
            <el-icon class="action-icon" style="background-color: #fff0f6; color: #eb2f96"><User /></el-icon>
            <span>用户管理</span>
          </div>
        </template>

        <!-- 用户/家属快捷操作 -->
        <template v-else>
          <div class="action-item" @click="$router.push('/layout/service/booking')">
            <el-icon class="action-icon" style="background-color: #e6f7ff; color: #1890ff"><Calendar /></el-icon>
            <span>预约服务</span>
          </div>
          <div class="action-item" @click="$router.push('/layout/health/management')">
            <el-icon class="action-icon" style="background-color: #f6ffed; color: #52c41a"><TrendCharts /></el-icon>
            <span>健康数据</span>
          </div>
          <div class="action-item" @click="$router.push('/layout/activity/list')">
            <el-icon class="action-icon" style="background-color: #fff7e6; color: #fa8c16"><Flag /></el-icon>
            <span>查看活动</span>
          </div>
           <div class="action-item" @click="$router.push('/layout/message/board')">
            <el-icon class="action-icon" style="background-color: #fff0f6; color: #eb2f96"><ChatDotRound /></el-icon>
            <span>留言反馈</span>
          </div>
        </template>
      </div>
    </el-card>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6" v-for="stat in stats" :key="stat.title">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-icon-wrapper" :style="{ backgroundColor: stat.bgColor }">
              <el-icon :size="24" :color="stat.color"><component :is="stat.icon" /></el-icon>
            </div>
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
        <el-card class="info-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="header-title"><el-icon><Bell /></el-icon> 最新公告</span>
              <el-button link type="primary" @click="$router.push('/layout/notice/list')">查看更多</el-button>
            </div>
          </template>
          <el-timeline v-if="notices.length > 0" style="padding-left: 10px;">
            <el-timeline-item
              v-for="notice in notices"
              :key="notice.id"
              :timestamp="formatTime(notice.createTime)"
              :type="getNoticeType(notice.type)"
              :hollow="true"
            >
              {{ notice.title }}
            </el-timeline-item>
          </el-timeline>
          <el-empty v-else description="暂无公告" :image-size="60" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="info-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="header-title"><el-icon><Flag /></el-icon> 最近活动</span>
              <el-button link type="primary" @click="$router.push('/layout/activity/list')">查看更多</el-button>
            </div>
          </template>
          <div v-if="activities.length > 0" class="activity-list">
            <div v-for="activity in activities" :key="activity.id" class="activity-item" @click="$router.push('/layout/activity/list')">
              <div class="activity-info">
                <div class="activity-title">{{ activity.title }}</div>
                <div class="activity-time">{{ formatTime(activity.startTime) }}</div>
              </div>
              <el-tag size="small" :type="getStatusType(activity.status)" effect="light" round>{{ getStatusText(activity.status) }}</el-tag>
            </div>
          </div>
          <el-empty v-else description="暂无活动" :image-size="60" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { User, UserFilled, Service, Trophy, Bell, Flag, Tickets, Calendar, TrendCharts, ChatDotRound, Menu } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getNoticeList } from '@/api/notice'
import { getActivityList } from '@/api/activity'

const userStore = useUserStore()

const stats = ref([
  { title: '用户总数', value: '0', icon: User, color: '#409EFF', bgColor: '#ecf5ff' },
  { title: '家人总数', value: '0', icon: UserFilled, color: '#67C23A', bgColor: '#f0f9eb' },
  { title: '服务总数', value: '0', icon: Service, color: '#E6A23C', bgColor: '#fdf6ec' },
  { title: '活动总数', value: '0', icon: Trophy, color: '#F56C6C', bgColor: '#fef0f0' }
])

const notices = ref([])
const activities = ref([])

const greeting = computed(() => {
  const hour = new Date().getHours()
  if (hour < 6) return '凌晨好'
  if (hour < 9) return '早上好'
  if (hour < 12) return '上午好'
  if (hour < 14) return '中午好'
  if (hour < 17) return '下午好'
  if (hour < 19) return '傍晚好'
  return '晚上好'
})

const loadData = async () => {
  try {
    const noticeRes = await getNoticeList()
    notices.value = noticeRes.slice(0, 5)

    const activityRes = await getActivityList()
    activities.value = activityRes.slice(0, 5)

    // 模拟统计数据 (实际项目中应该调用后端接口获取)
    // 这里简单模拟一下，如果有真实接口可以替换
    stats.value[0].value = '128'
    stats.value[1].value = '45'
    stats.value[2].value = '32'
    stats.value[3].value = '15'

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
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px); /* 减去header高度 */
}

.welcome-section {
  margin-bottom: 20px;
}

.welcome-section h2 {
  margin: 0;
  font-size: 24px;
  color: #303133;
  font-weight: 600;
}

.welcome-subtitle {
  margin: 8px 0 0;
  color: #909399;
  font-size: 14px;
}

.quick-actions-card {
  margin-bottom: 20px;
  border-radius: 8px;
  border: none;
}

.quick-actions {
  display: flex;
  justify-content: space-around;
  padding: 10px 0;
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s;
}

.action-item:hover {
  transform: translateY(-5px);
}

.action-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  margin-bottom: 8px;
}

.action-item span {
  font-size: 14px;
  color: #606266;
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  border: none;
  border-radius: 8px;
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 20px rgba(0, 0, 0, 0.1);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon-wrapper {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  line-height: 1.2;
}

.stat-title {
  font-size: 13px;
  color: #909399;
  margin-top: 4px;
}

.info-card {
  border: none;
  border-radius: 8px;
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-title {
  font-size: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
}

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.activity-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
  border-radius: 6px;
  background-color: #f9fafc;
  cursor: pointer;
  transition: all 0.3s;
}

.activity-item:hover {
  background-color: #f0f2f5;
}

.activity-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.activity-title {
  font-size: 14px;
  color: #303133;
  font-weight: 500;
}

.activity-time {
  font-size: 12px;
  color: #909399;
}
</style>
