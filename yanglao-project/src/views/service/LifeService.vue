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
              <el-icon :size="50" :color="service.color"><component :is="service.icon" /></el-icon>
            </div>
            <h3>{{ service.name }}</h3>
            <p>{{ service.description }}</p>
            <el-button type="primary" @click="handleBook(service)">立即预约</el-button>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog v-model="bookingDialogVisible" title="服务预约" width="500px">
      <el-form :model="bookingForm" label-width="100px">
        <el-form-item label="服务名称">
          <el-input v-model="bookingForm.serviceName" disabled />
        </el-form-item>
        <el-form-item label="预约日期">
          <el-date-picker v-model="bookingForm.date" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="预约时间">
          <el-time-picker v-model="bookingForm.time" placeholder="选择时间" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="bookingForm.remark" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="bookingDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitBooking">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { Tools, ShoppingBag, Coffee, HomeFilled } from '@element-plus/icons-vue'

const services = ref([
  { id: 1, name: '家政保洁', description: '专业保洁服务，让您的家更整洁', icon: Tools, color: '#409EFF' },
  { id: 2, name: '助餐服务', description: '营养配餐，送餐上门', icon: ShoppingBag, color: '#67C23A' },
  { id: 3, name: '助浴服务', description: '专业助浴，安全舒适', icon: Coffee, color: '#E6A23C' },
  { id: 4, name: '上门维修', description: '家电维修，快速响应', icon: HomeFilled, color: '#F56C6C' }
])

const bookingDialogVisible = ref(false)
const bookingForm = reactive({
  serviceName: '',
  date: null,
  time: null,
  remark: ''
})

const handleBook = (service) => {
  bookingForm.serviceName = service.name
  bookingDialogVisible.value = true
}

const handleSubmitBooking = () => {
  ElMessage.success('预约成功')
  bookingDialogVisible.value = false
  // TODO: 调用后端接口
}
</script>

<style scoped>
.life-service {
  padding: 20px;
}

.service-card {
  text-align: center;
  margin-bottom: 20px;
  height: 250px;
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
  margin-bottom: 15px;
}
</style>

