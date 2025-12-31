<template>
  <div class="service-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>服务项目管理</span>
          <el-button type="primary" @click="handleAdd">发布新服务</el-button>
        </div>
      </template>

      <el-table :data="serviceList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="服务名称" width="150" />
        <el-table-column prop="icon" label="图标" width="100">
          <template #default="{ row }">
            <el-icon :size="20"><component :is="getIcon(row.icon)" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="参考价格" width="120">
          <template #default="{ row }">¥{{ row.price }}</template>
        </el-table-column>
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button
              size="small"
              :type="row.status === 1 ? 'warning' : 'success'"
              @click="handleToggleStatus(row)"
            >
              {{ row.status === 1 ? '下架' : '上架' }}
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
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

    <!-- 编辑/新增对话框 -->
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑服务' : '发布新服务'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="服务名称">
          <el-input v-model="form.name" placeholder="请输入服务名称" />
        </el-form-item>
        <el-form-item label="图标">
          <el-select v-model="form.icon" placeholder="请选择图标">
            <el-option
              v-for="(icon, name) in iconMap"
              :key="name"
              :label="name"
              :value="name"
            >
              <span style="float: left">{{ name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">
                <el-icon><component :is="icon" /></el-icon>
              </span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参考价格">
          <el-input-number v-model="form.price" :precision="2" :step="10" :min="0" />
        </el-form-item>
        <el-form-item label="服务描述">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入服务描述" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">上架</el-radio>
            <el-radio :label="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Tools, ShoppingBag, Coffee, HomeFilled, Service } from '@element-plus/icons-vue'
import request from '@/utils/request'

// 图标映射
const iconMap = {
  'Tools': Tools,
  'ShoppingBag': ShoppingBag,
  'Coffee': Coffee,
  'HomeFilled': HomeFilled,
  'Service': Service
}

const getIcon = (iconName) => {
  return iconMap[iconName] || Service
}

const serviceList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)

const form = reactive({
  id: null,
  name: '',
  icon: 'Service',
  price: 0,
  description: '',
  status: 1
})

// 获取服务列表（分页）
const loadServices = async () => {
  loading.value = true
  try {
    const res = await request.get('/service/item/page', {
      params: {
        pageNum: pageNum.value,
        pageSize: pageSize.value
      }
    })
    serviceList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取服务列表失败', error)
  } finally {
    loading.value = false
  }
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadServices()
}

const handleAdd = () => {
  form.id = null
  form.name = ''
  form.icon = 'Service'
  form.price = 0
  form.description = ''
  form.status = 1
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.name) {
    ElMessage.warning('请输入服务名称')
    return
  }

  try {
    const url = form.id ? '/service/item/update' : '/service/item/add'
    await request.post(url, form)
    ElMessage.success(form.id ? '更新成功' : '发布成功')
    dialogVisible.value = false
    loadServices()
  } catch (error) {
    console.error('操作失败', error)
  }
}

const handleToggleStatus = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  try {
    await request.post('/service/item/update', {
      ...row,
      status: newStatus
    })
    ElMessage.success(newStatus === 1 ? '已上架' : '已下架')
    loadServices()
  } catch (error) {
    console.error('状态更新失败', error)
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该服务吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.post(`/service/item/delete/${row.id}`)
      ElMessage.success('删除成功')
      loadServices()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

onMounted(() => {
  loadServices()
})
</script>

<style scoped>
.service-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
