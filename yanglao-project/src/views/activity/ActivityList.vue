<template>
  <div class="activity-list">
    <el-card>
      <template #header>
        <span>活动列表</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="8" v-for="activity in activities" :key="activity.id">
          <el-card class="activity-card" shadow="hover">
            <div class="activity-image">
              <!-- 使用随机图片或默认图片 -->
              <el-image :src="'https://picsum.photos/300/200?random=' + activity.id" fit="cover" style="width: 100%; height: 200px" />
            </div>
            <div class="activity-content">
              <h3>{{ activity.title }}</h3>
              <p class="activity-desc">{{ activity.content }}</p>
              <div class="activity-info">
                <div class="info-item"><el-icon><Calendar /></el-icon> {{ formatTime(activity.startTime) }}</div>
                <div class="info-item"><el-icon><Location /></el-icon> {{ activity.location }}</div>
                <div class="info-item"><el-icon><User /></el-icon> 限 {{ activity.maxPeople > 0 ? activity.maxPeople : '不限' }} 人</div>
              </div>
              <div class="activity-footer">
                <el-tag :type="getStatusType(activity.status)">{{ getStatusText(activity.status) }}</el-tag>
                <el-button
                  type="primary"
                  size="small"
                  @click="handleSignUp(activity)"
                  v-if="activity.status === 0"
                  :loading="loadingMap[activity.id]"
                >
                  立即报名
                </el-button>
                <el-button size="small" disabled v-else-if="activity.status === 1">进行中</el-button>
                <el-button size="small" disabled v-else>已结束</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-empty v-if="activities.length === 0" description="暂无活动" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Calendar, Location, User } from '@element-plus/icons-vue'
import { getActivityList, registerActivity } from '@/api/activity'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const activities = ref([])
const loadingMap = reactive({})

const loadActivities = async () => {
  try {
    const res = await getActivityList()
    activities.value = res || []
  } catch (error) {
    console.error('获取活动列表失败', error)
  }
}

const handleSignUp = (activity) => {
  if (!userStore.userInfo) {
    ElMessage.warning('请先登录')
    return
  }

  ElMessageBox.confirm(`确定要报名参加"${activity.title}"吗？`, '报名确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info'
  }).then(async () => {
    loadingMap[activity.id] = true
    try {
      await registerActivity({
        activityId: activity.id,
        userId: userStore.userInfo.id,
        userName: userStore.userInfo.realName || userStore.userInfo.name || userStore.userInfo.username
      })
      ElMessage.success('报名成功！请在个人中心查看')
    } catch (error) {
      // 错误信息已由拦截器处理，这里可以补充特定逻辑
    } finally {
      loadingMap[activity.id] = false
    }
  })
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
  return timeStr.replace('T', ' ')
}

const getStatusType = (status) => {
  const map = {
    0: 'success', // 未开始
    1: 'warning', // 进行中
    2: 'info',    // 已结束
    '-1': 'danger' // 已取消
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
  loadActivities()
})
</script>

<style scoped>
.activity-list {
  padding: 20px;
}

.activity-card {
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.activity-card:hover {
  transform: translateY(-5px);
}

.activity-content {
  padding: 15px;
}

.activity-content h3 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 18px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.activity-desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  height: 40px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.activity-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-bottom: 15px;
  font-size: 13px;
  color: #999;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.activity-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}
</style>
