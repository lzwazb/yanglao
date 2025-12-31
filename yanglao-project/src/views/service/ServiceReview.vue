<template>
  <div class="service-review">
    <el-card>
      <template #header>
        <span>服务评价</span>
      </template>
      <el-table :data="orderList" style="width: 100%" v-loading="loading">
        <el-table-column prop="serviceName" label="服务名称" />
        <el-table-column prop="bookTime" label="服务时间" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag type="success">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleReview(row)" v-if="!row.hasReview">评价</el-button>
            <el-button size="small" @click="handleViewReview(row)" v-else>查看评价</el-button>
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

    <el-dialog v-model="reviewDialogVisible" :title="isViewMode ? '查看评价' : '服务评价'" width="600px">
      <el-form :model="reviewForm" label-width="100px">
        <el-form-item label="服务名称">
          <el-input v-model="reviewForm.serviceName" disabled />
        </el-form-item>
        <el-form-item label="评分">
          <el-rate v-model="reviewForm.rating" :max="5" :disabled="isViewMode" />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input v-model="reviewForm.content" type="textarea" :rows="5" :disabled="isViewMode" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewDialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="handleSubmitReview" v-if="!isViewMode">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserOrders, addReview, getReviewByOrder } from '@/api/service'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
// 使用 store 中的 userInfo 获取当前用户ID
const currentUserId = computed(() => userStore.userInfo?.id)

const orderList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const reviewDialogVisible = ref(false)
const isViewMode = ref(false)
const currentOrderId = ref(null)

const reviewForm = reactive({
  serviceName: '',
  rating: 5,
  content: ''
})

// 加载已完成的订单
const loadOrders = async () => {
  if (!currentUserId.value) return

  loading.value = true
  try {
    const res = await getUserOrders({
      userId: currentUserId.value,
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      status: 'COMPLETED' // 只查询已完成的订单
    })

    // 这里需要额外查询每个订单是否已评价，或者后端在返回订单列表时带上评价状态
    // 假设后端目前没带，我们前端暂时先通过尝试获取评价来判断（实际优化应由后端提供字段）
    // 为了演示，这里先简单处理，实际项目中建议后端 ServiceOrderDTO 增加 hasReview 字段

    const orders = res.records
    for (let order of orders) {
      try {
        const review = await getReviewByOrder(order.id)
        order.hasReview = !!review
      } catch (e) {
        order.hasReview = false
      }
    }

    orderList.value = orders
    total.value = res.total
  } catch (error) {
    console.error('加载订单列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleReview = (row) => {
  isViewMode.value = false
  currentOrderId.value = row.id
  reviewForm.serviceName = row.serviceName
  reviewForm.rating = 5
  reviewForm.content = ''
  reviewDialogVisible.value = true
}

const handleViewReview = async (row) => {
  try {
    const review = await getReviewByOrder(row.id)
    if (review) {
      reviewForm.serviceName = row.serviceName
      reviewForm.rating = review.rating
      reviewForm.content = review.content
      isViewMode.value = true
      reviewDialogVisible.value = true
    }
  } catch (error) {
    ElMessage.error('获取评价详情失败')
  }
}

const handleSubmitReview = async () => {
  if (!currentUserId.value) {
    ElMessage.warning('请先登录')
    return
  }
  if (!reviewForm.content) {
    ElMessage.warning('请输入评价内容')
    return
  }

  try {
    await addReview({
      orderId: currentOrderId.value,
      userId: currentUserId.value,
      rating: reviewForm.rating,
      content: reviewForm.content
    })
    ElMessage.success('评价提交成功')
    reviewDialogVisible.value = false
    loadOrders() // 刷新列表状态
  } catch (error) {
    console.error('提交评价失败', error)
  }
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadOrders()
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.service-review {
  padding: 20px;
}
</style>
