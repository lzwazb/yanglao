<template>
  <div class="health-data-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>健康数据监控</span>
        </div>
      </template>

      <div class="filter-container">
        <el-input
          v-model="searchUserId"
          placeholder="按用户ID搜索"
          style="width: 200px; margin-right: 10px"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>

      <el-table :data="dataList" style="width: 100%; margin-top: 20px" v-loading="loading">
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="measureTime" label="测量时间" width="180" />
        <el-table-column label="血压(mmHg)" width="150">
          <template #default="{ row }">
            {{ row.bloodPressureHigh }}/{{ row.bloodPressureLow }}
          </template>
        </el-table-column>
        <el-table-column prop="heartRate" label="心率(次/分)" width="120" />
        <el-table-column prop="bloodSugar" label="血糖(mmol/L)" width="120" />
        <el-table-column prop="temperature" label="体温(℃)" width="120" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
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
import { getAdminDataPage, deleteHealthData } from '@/api/health'

const dataList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const searchUserId = ref('')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAdminDataPage({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      userId: searchUserId.value || undefined
    })
    dataList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取数据列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNum.value = 1
  loadData()
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadData()
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这条数据吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    try {
      await deleteHealthData(id)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.health-data-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.filter-container {
  margin-bottom: 20px;
}
</style>
