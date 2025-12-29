<template>
  <div class="service-booking">
    <el-card>
      <template #header>
        <span>服务预约</span>
      </template>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="登记入住" name="checkin">
          <el-form :model="checkinForm" label-width="120px" style="max-width: 600px">
            <el-form-item label="房间号">
              <el-input v-model="checkinForm.roomNumber" placeholder="请输入房间号" />
            </el-form-item>
            <el-form-item label="床位号">
              <el-input v-model="checkinForm.bedNumber" placeholder="请输入床位号" />
            </el-form-item>
            <el-form-item label="服务编号">
              <el-input v-model="checkinForm.serviceNumber" placeholder="请输入服务编号" />
            </el-form-item>
            <el-form-item label="入住日期">
              <el-date-picker v-model="checkinForm.checkinDate" type="date" placeholder="选择日期" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleCheckin">提交预约</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="上门服务" name="home">
          <el-form :model="homeServiceForm" label-width="120px" style="max-width: 600px">
            <el-form-item label="单元号">
              <el-input v-model="homeServiceForm.unitNumber" placeholder="请输入单元号" />
            </el-form-item>
            <el-form-item label="房间号">
              <el-input v-model="homeServiceForm.roomNumber" placeholder="请输入房间号" />
            </el-form-item>
            <el-form-item label="服务编号">
              <el-input v-model="homeServiceForm.serviceNumber" placeholder="请输入服务编号" />
            </el-form-item>
            <el-form-item label="服务日期">
              <el-date-picker v-model="homeServiceForm.serviceDate" type="date" placeholder="选择日期" />
            </el-form-item>
            <el-form-item label="服务时间">
              <el-time-picker v-model="homeServiceForm.serviceTime" placeholder="选择时间" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleHomeService">提交预约</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <span>我的预约</span>
      </template>
      <el-table :data="bookingList" style="width: 100%">
        <el-table-column prop="type" label="类型" />
        <el-table-column prop="roomNumber" label="房间号" />
        <el-table-column prop="serviceNumber" label="服务编号" />
        <el-table-column prop="date" label="日期" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === '已确认' ? 'success' : 'info'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="small" type="danger" @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const activeTab = ref('checkin')

const checkinForm = reactive({
  roomNumber: '',
  bedNumber: '',
  serviceNumber: '',
  checkinDate: null
})

const homeServiceForm = reactive({
  unitNumber: '',
  roomNumber: '',
  serviceNumber: '',
  serviceDate: null,
  serviceTime: null
})

const bookingList = ref([
  { id: 1, type: '登记入住', roomNumber: '101', serviceNumber: 'S001', date: '2024-01-15', status: '已确认' }
])

const handleCheckin = () => {
  ElMessage.success('预约提交成功，等待确认')
  // TODO: 调用后端接口
}

const handleHomeService = () => {
  ElMessage.success('预约提交成功，等待确认')
  // TODO: 调用后端接口
}

const handleCancel = (id) => {
  ElMessage.success('已取消预约')
  // TODO: 调用后端接口
}

onMounted(() => {
  // TODO: 加载预约列表
})
</script>

<style scoped>
.service-booking {
  padding: 20px;
}
</style>

