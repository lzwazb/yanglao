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
            <span>最新公告</span>
          </template>
          <el-timeline>
            <el-timeline-item v-for="notice in notices" :key="notice.id" :timestamp="notice.time">
              {{ notice.content }}
            </el-timeline-item>
          </el-timeline>
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
import { ref } from 'vue'
import { User, UserFilled, Service, Trophy } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const stats = ref([
  { title: '用户总数', value: '0', icon: User, color: '#409EFF' },
  { title: '家人总数', value: '0', icon: UserFilled, color: '#67C23A' },
  { title: '服务总数', value: '0', icon: Service, color: '#E6A23C' },
  { title: '活动总数', value: '0', icon: Trophy, color: '#F56C6C' }
])

const notices = ref([
  { id: 1, content: '欢迎使用养老服务平台', time: '2024-01-01' },
  { id: 2, content: '系统维护通知', time: '2024-01-02' }
])

const activities = ref([
  { id: 1, name: '健康讲座', status: '进行中' },
  { id: 2, name: '社区活动', status: '已报名' }
])
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
</style>

