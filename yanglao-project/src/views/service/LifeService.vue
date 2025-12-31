<template>
  <div class="life-service">
    <el-card>
      <template #header>
        <span>生活服务</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="8" v-for="service in services" :key="service.id">
          <el-card class="service-card" shadow="hover">
            <div class="service-icon">
              <!-- 动态图标处理，如果后端返回的icon是字符串，这里需要映射或者直接显示图片 -->
              <!-- 暂时使用默认图标，或者根据名称匹配 -->
              <el-icon :size="50" color="#409EFF"><component :is="getIcon(service.icon)" /></el-icon>
            </div>
            <h3>{{ service.name }}</h3>
            <p>{{ service.description }}</p>
            <div class="price">参考价格: ¥{{ service.price }}</div>
            <el-button type="primary" @click="handleBook(service)">立即预约</el-button>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Tools, ShoppingBag, Coffee, HomeFilled, Service } from '@element-plus/icons-vue'
import { getServiceList } from '@/api/service'

const router = useRouter()
const services = ref([])

// 图标映射
const iconMap = {
  'Tools': Tools,
  'ShoppingBag': ShoppingBag,
  'Coffee': Coffee,
  'HomeFilled': HomeFilled
}

const getIcon = (iconName) => {
  return iconMap[iconName] || Service
}

const loadServices = async () => {
  try {
    const res = await getServiceList()
    services.value = res
  } catch (error) {
    console.error('获取服务列表失败', error)
  }
}

const handleBook = (service) => {
  // 跳转到预约页面，并传递服务ID和服务名称
  // 注意：这里需要使用完整的路由路径，包括 /layout 前缀
  router.push({
    path: '/layout/service/booking',
    query: {
      serviceId: service.id,
      serviceName: service.name
    }
  })
}

onMounted(() => {
  loadServices()
})
</script>

<style scoped>
.life-service {
  padding: 20px;
}

.service-card {
  text-align: center;
  margin-bottom: 20px;
  height: 300px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.service-icon {
  margin-bottom: 15px;
}

.service-card h3 {
  margin: 10px 0;
  color: #333;
}

.service-card p {
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
  flex-grow: 1;
}

.price {
  color: #f56c6c;
  font-weight: bold;
  margin-bottom: 15px;
}
</style>
