<template>
  <div class="health-profile-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>健康档案管理</span>
        </div>
      </template>

      <el-table :data="profileList" style="width: 100%" v-loading="loading">
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="height" label="身高(cm)" width="100" />
        <el-table-column prop="weight" label="体重(kg)" width="100" />
        <el-table-column prop="bloodType" label="血型" width="100" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
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

    <el-dialog v-model="dialogVisible" title="编辑健康档案" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="用户ID">
          <el-input v-model="form.userId" disabled />
        </el-form-item>
        <el-form-item label="身高(cm)">
          <el-input-number v-model="form.height" :min="0" :max="300" />
        </el-form-item>
        <el-form-item label="体重(kg)">
          <el-input-number v-model="form.weight" :min="0" :max="300" />
        </el-form-item>
        <el-form-item label="血型">
          <el-select v-model="form.bloodType" placeholder="请选择">
            <el-option label="A型" value="A" />
            <el-option label="B型" value="B" />
            <el-option label="AB型" value="AB" />
            <el-option label="O型" value="O" />
            <el-option label="其他" value="Other" />
          </el-select>
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
import { ElMessage } from 'element-plus'
import { getAdminProfilePage, saveHealthProfile } from '@/api/health'

const profileList = ref([])
const loading = ref(false)
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)

const form = reactive({
  id: null,
  userId: null,
  height: null,
  weight: null,
  bloodType: ''
})

const loadProfiles = async () => {
  loading.value = true
  try {
    const res = await getAdminProfilePage({
      pageNum: pageNum.value,
      pageSize: pageSize.value
    })
    profileList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取档案列表失败', error)
  } finally {
    loading.value = false
  }
}

const handlePageChange = (val) => {
  pageNum.value = val
  loadProfiles()
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    await saveHealthProfile(form)
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadProfiles()
  } catch (error) {
    console.error('保存失败', error)
  }
}

onMounted(() => {
  loadProfiles()
})
</script>

<style scoped>
.health-profile-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
