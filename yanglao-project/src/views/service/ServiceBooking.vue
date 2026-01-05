<template>
  <div class="service-booking">
    <el-card>
      <template #header>
        <span>服务预约</span>
      </template>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="登记入住" name="CHECK_IN">
          <el-form :model="checkinForm" label-width="120px" style="max-width: 600px">
            <el-form-item label="服务项目">
              <el-select v-model="checkinForm.serviceId" placeholder="请选择服务" @change="handleServiceChange($event, 'checkin')">
                <el-option
                  v-for="item in serviceList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="房间号">
              <el-input v-model="checkinForm.roomNumber" placeholder="请输入房间号" />
            </el-form-item>
            <el-form-item label="床位号">
              <el-input v-model="checkinForm.bedNumber" placeholder="请输入床位号" />
            </el-form-item>
            <el-form-item label="入住日期">
              <el-date-picker
                v-model="checkinForm.checkinDate"
                type="datetime"
                placeholder="选择日期时间"
                value-format="YYYY-MM-DD HH:mm:ss"
              />
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="checkinForm.remark" type="textarea" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleCheckin">提交预约</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="上门服务" name="HOME">
          <el-form :model="homeServiceForm" label-width="120px" style="max-width: 600px">
            <el-form-item label="服务项目">
              <el-select v-model="homeServiceForm.serviceId" placeholder="请选择服务" @change="handleServiceChange($event, 'home')">
                <el-option
                  v-for="item in serviceList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="单元号">
              <el-input v-model="homeServiceForm.unitNumber" placeholder="请输入单元号" />
            </el-form-item>
            <el-form-item label="房间号">
              <el-input v-model="homeServiceForm.roomNumber" placeholder="请输入房间号" />
            </el-form-item>
            <el-form-item label="服务时间">
              <el-date-picker
                v-model="homeServiceForm.serviceDate"
                type="datetime"
                placeholder="选择日期时间"
                value-format="YYYY-MM-DD HH:mm:ss"
              />
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="homeServiceForm.remark" type="textarea" />
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
      <el-table :data="bookingList" style="width: 100%" v-loading="loading">
        <el-table-column prop="type" label="类型">
          <template #default="{ row }">
            {{ row.type === 'CHECK_IN' ? '登记入住' : '上门服务' }}
          </template>
        </el-table-column>
        <el-table-column prop="serviceName" label="服务名称" />
        <el-table-column prop="addressInfo" label="地址信息" />
        <el-table-column prop="bookTime" label="预约时间" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button
              size="small"
              type="danger"
              @click="handleCancelOrder(row.id)"
              v-if="row.status === 'PENDING' || row.status === 'CONFIRMED'"
            >取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px; text-align: right">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getServiceList, bookService, getUserOrders, cancelOrder } from '@/api/service'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()

// 使用 store 中的 userInfo 获取当前用户ID
// 注意：这里假设 userInfo 中包含 id 字段，如果字段名不同（如 userId），请相应调整
const currentUserId = computed(() => userStore.userInfo?.id)

const activeTab = ref('CHECK_IN')
const serviceList = ref([])
const bookingList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const checkinForm = reactive({
  serviceId: null,
  serviceName: '',
  roomNumber: '',
  bedNumber: '',
  checkinDate: null,
  remark: ''
})

const homeServiceForm = reactive({
  serviceId: null,
  serviceName: '',
  unitNumber: '',
  roomNumber: '',
  serviceDate: null,
  remark: ''
})

// 加载服务列表
const loadServices = async () => {
  try {
    const res = await getServiceList()
    serviceList.value = res

    // 如果路由参数中有 serviceId，自动填充
    if (route.query.serviceId) {
      const sId = Number(route.query.serviceId)
      const service = res.find(item => item.id === sId)
      if (service) {
        checkinForm.serviceId = sId
        checkinForm.serviceName = service.name
        homeServiceForm.serviceId = sId
        homeServiceForm.serviceName = service.name
      }
    }
  } catch (error) {
    console.error('加载服务列表失败', error)
  }
}

// 加载订单列表
const loadOrders = async () => {
  if (!currentUserId.value) return

  loading.value = true
  try {
    const res = await getUserOrders({
      userId: currentUserId.value,
      pageNum: pageNum.value,
      pageSize: pageSize.value
    })
    bookingList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('加载订单列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleServiceChange = (val, type) => {
  const service = serviceList.value.find(item => item.id === val)
  if (type === 'checkin') {
    checkinForm.serviceName = service ? service.name : ''
  } else {
    homeServiceForm.serviceName = service ? service.name : ''
  }
}

const handleCheckin = async () => {
  if (!currentUserId.value) {
    ElMessage.warning('请先登录')
    return
  }
  if (!checkinForm.serviceId || !checkinForm.checkinDate) {
    ElMessage.warning('请填写完整信息')
    return
  }

  const data = {
    userId: currentUserId.value,
    serviceId: checkinForm.serviceId,
    serviceName: checkinForm.serviceName,
    type: 'CHECK_IN',
    addressInfo: `房间:${checkinForm.roomNumber} 床位:${checkinForm.bedNumber}`,
    bookTime: checkinForm.checkinDate,
    remark: checkinForm.remark
  }

  try {
    await bookService(data)
    ElMessage.success('预约提交成功')
    loadOrders()
    // 重置表单
    checkinForm.roomNumber = ''
    checkinForm.bedNumber = ''
    checkinForm.checkinDate = null
    checkinForm.remark = ''
  } catch (error) {
    console.error('预约失败', error)
  }
}

const handleHomeService = async () => {
  if (!currentUserId.value) {
    ElMessage.warning('请先登录')
    return
  }
  if (!homeServiceForm.serviceId || !homeServiceForm.serviceDate) {
    ElMessage.warning('请填写完整信息')
    return
  }

  const data = {
    userId: currentUserId.value,
    serviceId: homeServiceForm.serviceId,
    serviceName: homeServiceForm.serviceName,
    type: 'HOME',
    addressInfo: `单元:${homeServiceForm.unitNumber} 房间:${homeServiceForm.roomNumber}`,
    bookTime: homeServiceForm.serviceDate,
    remark: homeServiceForm.remark
  }

  try {
    await bookService(data)
    ElMessage.success('预约提交成功')
    loadOrders()
    // 重置表单
    homeServiceForm.unitNumber = ''
    homeServiceForm.roomNumber = ''
    homeServiceForm.serviceDate = null
    homeServiceForm.remark = ''
  } catch (error) {
    console.error('预约失败', error)
  }
}

const handleCancelOrder = (id) => {
  ElMessageBox.confirm('确定要取消该预约吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await cancelOrder(id)
      ElMessage.success('取消成功')
      loadOrders()
    } catch (error) {
      console.error('取消失败', error)
    }
  })
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadOrders()
}

const getStatusType = (status) => {
  const map = {
    'PENDING': 'warning',
    'CONFIRMED': 'primary',
    'COMPLETED': 'success',
    'CANCELLED': 'info'
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    'PENDING': '待确认',
    'CONFIRMED': '已确认',
    'COMPLETED': '已完成',
    'CANCELLED': '已取消'
  }
  return map[status] || status
}

onMounted(() => {
  loadServices()
  loadOrders()
})
</script>

<style scoped>
.service-booking {
  padding: 20px;
}
</style>
