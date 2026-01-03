<template>
  <div class="health-management">
    <el-tabs v-model="activeTab" class="health-tabs">
      <el-tab-pane label="健康档案" name="profile">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>基本健康档案</span>
              <el-button type="primary" size="small" @click="handleEditProfile" v-if="!isEditingProfile">编辑档案</el-button>
              <div v-else>
                <el-button size="small" @click="cancelEditProfile">取消</el-button>
                <el-button type="primary" size="small" @click="saveProfile">保存</el-button>
              </div>
            </div>
          </template>
          <el-form :model="profileForm" label-width="100px" :disabled="!isEditingProfile">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="身高(cm)">
                  <el-input-number v-model="profileForm.height" :min="0" :max="300" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="体重(kg)">
                  <el-input-number v-model="profileForm.weight" :min="0" :max="300" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="血型">
                  <el-select v-model="profileForm.bloodType" placeholder="请选择">
                    <el-option label="A型" value="A" />
                    <el-option label="B型" value="B" />
                    <el-option label="AB型" value="AB" />
                    <el-option label="O型" value="O" />
                    <el-option label="其他" value="Other" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="健康数据" name="data">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>健康数据记录</span>
              <el-button type="primary" size="small" @click="handleAddData">录入数据</el-button>
            </div>
          </template>

          <el-table :data="healthDataList" style="width: 100%" v-loading="loadingData">
            <el-table-column prop="measureTime" label="测量时间" width="180">
              <template #default="{ row }">
                {{ formatTime(row.measureTime) }}
              </template>
            </el-table-column>
            <el-table-column label="血压(mmHg)" width="150">
              <template #default="{ row }">
                {{ row.bloodPressureHigh }}/{{ row.bloodPressureLow }}
              </template>
            </el-table-column>
            <el-table-column prop="heartRate" label="心率(次/分)" width="120" />
            <el-table-column prop="bloodSugar" label="血糖(mmol/L)" width="120" />
            <el-table-column prop="temperature" label="体温(℃)" width="120" />
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button size="small" type="danger" @click="handleDeleteData(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>

    <!-- 录入数据弹窗 -->
    <el-dialog v-model="dataDialogVisible" title="录入健康数据" width="500px">
      <el-form :model="dataForm" label-width="120px">
        <el-form-item label="测量时间">
          <el-date-picker v-model="dataForm.measureTime" type="datetime" placeholder="选择时间" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="收缩压(高压)">
          <el-input-number v-model="dataForm.bloodPressureHigh" :min="0" :max="300" />
        </el-form-item>
        <el-form-item label="舒张压(低压)">
          <el-input-number v-model="dataForm.bloodPressureLow" :min="0" :max="200" />
        </el-form-item>
        <el-form-item label="心率">
          <el-input-number v-model="dataForm.heartRate" :min="0" :max="200" />
        </el-form-item>
        <el-form-item label="血糖">
          <el-input-number v-model="dataForm.bloodSugar" :min="0" :max="50" :precision="1" :step="0.1" />
        </el-form-item>
        <el-form-item label="体温">
          <el-input-number v-model="dataForm.temperature" :min="30" :max="45" :precision="1" :step="0.1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dataDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitData">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { getHealthProfile, saveHealthProfile, getHealthDataList, addHealthData, deleteHealthData } from '@/api/health'

const userStore = useUserStore()
const activeTab = ref('profile')
const isEditingProfile = ref(false)
const loadingData = ref(false)
const dataDialogVisible = ref(false)
const healthDataList = ref([])

const profileForm = reactive({
  id: null,
  userId: null,
  height: null,
  weight: null,
  bloodType: ''
})

const dataForm = reactive({
  measureTime: '',
  bloodPressureHigh: null,
  bloodPressureLow: null,
  heartRate: null,
  bloodSugar: null,
  temperature: null
})

// 加载健康档案
const loadProfile = async () => {
  if (!userStore.userInfo?.id) return
  try {
    const res = await getHealthProfile(userStore.userInfo.id)
    if (res) {
      Object.assign(profileForm, res)
    } else {
      // 如果没有档案，初始化userId
      profileForm.userId = userStore.userInfo.id
    }
  } catch (error) {
    console.error('获取健康档案失败', error)
  }
}

// 加载健康数据列表
const loadDataList = async () => {
  if (!userStore.userInfo?.id) return
  loadingData.value = true
  try {
    const res = await getHealthDataList(userStore.userInfo.id)
    healthDataList.value = res || []
  } catch (error) {
    console.error('获取健康数据失败', error)
  } finally {
    loadingData.value = false
  }
}

// 档案操作
const handleEditProfile = () => {
  isEditingProfile.value = true
}

const cancelEditProfile = () => {
  isEditingProfile.value = false
  loadProfile() // 恢复原数据
}

const saveProfile = async () => {
  try {
    // 确保userId存在
    if (!profileForm.userId) {
      profileForm.userId = userStore.userInfo.id
    }
    await saveHealthProfile(profileForm)
    ElMessage.success('档案保存成功')
    isEditingProfile.value = false
    loadProfile()
  } catch (error) {
    console.error('保存档案失败', error)
  }
}

// 数据操作
const handleAddData = () => {
  // 重置表单
  Object.assign(dataForm, {
    measureTime: new Date().toISOString().replace('T', ' ').substring(0, 19), // 默认当前时间
    bloodPressureHigh: null,
    bloodPressureLow: null,
    heartRate: null,
    bloodSugar: null,
    temperature: null
  })
  dataDialogVisible.value = true
}

const handleSubmitData = async () => {
  try {
    await addHealthData({
      ...dataForm,
      userId: userStore.userInfo.id
    })
    ElMessage.success('数据录入成功')
    dataDialogVisible.value = false
    loadDataList()
  } catch (error) {
    console.error('录入数据失败', error)
  }
}

const handleDeleteData = (id) => {
  ElMessageBox.confirm('确定要删除这条记录吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    try {
      await deleteHealthData(id)
      ElMessage.success('删除成功')
      loadDataList()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
  return timeStr.replace('T', ' ')
}

onMounted(() => {
  loadProfile()
  loadDataList()
})
</script>

<style scoped>
.health-management {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
