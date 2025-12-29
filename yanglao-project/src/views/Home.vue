<template>
  <div class="home-container">
    <div class="hero-section">
      <h1>欢迎来到养老服务平台</h1>
      <p>为您提供全方位的养老服务</p>
      <div class="action-buttons">
        <el-button type="primary" size="large" @click="$router.push('/login')">立即登录</el-button>
        <el-button size="large" @click="$router.push('/register')">免费注册</el-button>
      </div>
    </div>
    <div class="features-section">
      <el-row :gutter="20">
        <el-col :span="6" v-for="feature in features" :key="feature.title">
          <el-card class="feature-card">
            <el-icon :size="40" :color="feature.color"><component :is="feature.icon" /></el-icon>
            <h3>{{ feature.title }}</h3>
            <p>{{ feature.desc }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { Service, FirstAidKit, Trophy, ChatLineRound } from '@element-plus/icons-vue'

const features = [
  { title: '生活服务', desc: '家政保洁、助餐助浴等服务', icon: Service, color: '#409EFF' },
  { title: '健康管理', desc: '定期健康检测，关注您的健康', icon: FirstAidKit, color: '#67C23A' },
  { title: '活动管理', desc: '丰富的社区活动，精彩生活', icon: Trophy, color: '#E6A23C' },
  { title: '留言板', desc: '日常交流与社区建议', icon: ChatLineRound, color: '#F56C6C' }
]
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
}

.hero-section {
  text-align: center;
  color: white;
  padding: 80px 0;
}

.hero-section h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.hero-section p {
  font-size: 20px;
  margin-bottom: 40px;
}

.action-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.features-section {
  max-width: 1200px;
  margin: 0 auto;
}

.feature-card {
  text-align: center;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.feature-card h3 {
  margin: 15px 0 10px;
  color: #333;
}

.feature-card p {
  color: #666;
  font-size: 14px;
}
</style>

