<template>
  <div class="service-order-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>服务订单管理</span>
          <div class="filter-container">
            <el-select v-model="filterStatus" placeholder="状态筛选" clearable @change="handleFilterChange" style="width: 150px">
              <el-option label="待确认" value="PENDING" />
              <el-option label="已确认" value="CONFIRMED" />
              <el-option label="已完成" value="COMPLETED" />
              <el-option label="已取消" value="CANCELLED" />
            </el-select>
          </div>
        </div>
      </template>

      <el-table :data="orderList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="订单ID" width="80" />
        <el-table-column prop="serviceName" label="服务名称" width="150" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            {{ row.type === 'CHECK_IN' ? '登记入住' : '上门服务' }}
          </template>
        </el-table-column>
        <el-table-column prop="addressInfo" label="地址信息" show-overflow-tooltip />
        <el-table-column prop="bookTime" label="预约时间" width="180" />
        <el-table-column prop="remark" label="备注" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              @click="handleUpdateStatus(row, 'CONFIRMED')"
              v-if="row.status === 'PENDING'"
            >确认接单</el-button>
            <el-button
              size="small"
              type="success"
              @click="handleUpdateStatus(row, 'COMPLETED')"
              v-if="row.status === 'CONFIRMED'"
            >完成服务</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleUpdateStatus(row, 'CANCELLED')"
              v-if="['PENDING', 'CONFIRMED'].includes(row.status)"
            >取消订单</el-button>
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
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAdminOrders, updateOrderStatus } from '@/api/service'

const orderList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const filterStatus = ref('')

const loadOrders = async () => {
  loading.value = true
  try {
    const res = await getAdminOrders({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      status: filterStatus.value || undefined
    })
    orderList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取订单列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleFilterChange = () => {
  pageNum.value = 1
  loadOrders()
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadOrders()
}

const handleUpdateStatus = (row, newStatus) => {
  const actionText = {
    'CONFIRMED': '确认接单',
    'COMPLETED': '完成服务',
    'CANCELLED': '取消订单'
  }[newStatus]

  ElMessageBox.confirm(`确定要${actionText}吗?`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: newStatus === 'CANCELLED' ? 'warning' : 'info'
  }).then(async () => {
    try {
      await updateOrderStatus(row.id, newStatus)
      ElMessage.success('操作成功')
      loadOrders()
    } catch (error) {
      console.error('操作失败', error)
    }
  })
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
  loadOrders()
})
</script>

<style scoped>
.service-order-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
