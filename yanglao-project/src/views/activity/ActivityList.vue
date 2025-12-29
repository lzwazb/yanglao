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
              <el-image :src="activity.image" fit="cover" style="width: 100%; height: 200px" />
            </div>
            <div class="activity-content">
              <h3>{{ activity.name }}</h3>
              <p class="activity-desc">{{ activity.description }}</p>
              <div class="activity-info">
                <span><el-icon><Calendar /></el-icon> {{ activity.date }}</span>
                <span><el-icon><Location /></el-icon> {{ activity.location }}</span>
              </div>
              <div class="activity-footer">
                <el-tag :type="activity.status === '进行中' ? 'success' : 'info'">{{ activity.status }}</el-tag>
                <el-button type="primary" size="small" @click="handleSignUp(activity)" v-if="activity.status === '报名中'">
                  立即报名
                </el-button>
                <el-button size="small" disabled v-else>已结束</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Calendar, Location } from '@element-plus/icons-vue'

const activities = ref([
  {
    id: 1,
    name: '健康讲座',
    description: '邀请专业医生讲解老年人健康知识',
    date: '2024-01-20',
    location: '社区活动中心',
    status: '报名中',
    image: 'https://via.placeholder.com/300x200'
  },
  {
    id: 2,
    name: '书法比赛',
    description: '社区书法比赛，展示才艺',
    date: '2024-01-25',
    location: '社区活动中心',
    status: '报名中',
    image: 'https://via.placeholder.com/300x200'
  },
  {
    id: 3,
    name: '太极拳教学',
    description: '专业教练指导太极拳',
    date: '2024-01-18',
    location: '社区广场',
    status: '进行中',
    image: 'https://via.placeholder.com/300x200'
  }
])

const handleSignUp = (activity) => {
  ElMessage.success('报名成功')
  // TODO: 调用后端接口
}

onMounted(() => {
  // TODO: 加载活动列表
})
</script>

<style scoped>
.activity-list {
  padding: 20px;
}

.activity-card {
  margin-bottom: 20px;
}

.activity-content {
  padding: 15px;
}

.activity-content h3 {
  margin: 0 0 10px 0;
  color: #333;
}

.activity-desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  min-height: 40px;
}

.activity-info {
  display: flex;
  gap: 15px;
  margin-bottom: 15px;
  font-size: 12px;
  color: #999;
}

.activity-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

