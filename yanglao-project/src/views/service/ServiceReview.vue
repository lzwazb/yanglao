<template>
  <div class="service-review">
    <el-card>
      <template #header>
        <span>服务评价</span>
      </template>
      <el-table :data="serviceList" style="width: 100%">
        <el-table-column prop="serviceName" label="服务名称" />
        <el-table-column prop="date" label="服务日期" />
        <el-table-column prop="status" label="状态" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleReview(row)" v-if="!row.reviewed">评价</el-button>
            <el-button size="small" @click="handleViewReview(row)" v-else>查看评价</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="reviewDialogVisible" :title="reviewForm.id ? '查看评价' : '服务评价'" width="600px">
      <el-form :model="reviewForm" label-width="100px">
        <el-form-item label="服务名称">
          <el-input v-model="reviewForm.serviceName" disabled />
        </el-form-item>
        <el-form-item label="评分">
          <el-rate v-model="reviewForm.rating" :max="5" />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input v-model="reviewForm.content" type="textarea" :rows="5" :disabled="!!reviewForm.id" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewDialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="handleSubmitReview" v-if="!reviewForm.id">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const serviceList = ref([
  { id: 1, serviceName: '家政保洁', date: '2024-01-10', status: '已完成', reviewed: false },
  { id: 2, serviceName: '助餐服务', date: '2024-01-08', status: '已完成', reviewed: true }
])

const reviewDialogVisible = ref(false)
const reviewForm = reactive({
  id: null,
  serviceName: '',
  rating: 5,
  content: ''
})

const handleReview = (row) => {
  reviewForm.id = null
  reviewForm.serviceName = row.serviceName
  reviewForm.rating = 5
  reviewForm.content = ''
  reviewDialogVisible.value = true
}

const handleViewReview = (row) => {
  reviewForm.id = row.id
  reviewForm.serviceName = row.serviceName
  reviewForm.rating = 4
  reviewForm.content = '服务很好，很满意'
  reviewDialogVisible.value = true
}

const handleSubmitReview = () => {
  ElMessage.success('评价提交成功')
  reviewDialogVisible.value = false
  // TODO: 调用后端接口
}

onMounted(() => {
  // TODO: 加载服务列表
})
</script>

<style scoped>
.service-review {
  padding: 20px;
}
</style>

