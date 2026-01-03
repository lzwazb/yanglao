<template>
  <div class="health-management">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>健康数据记录</span>
            <el-button
              v-if="isAdmin"
              type="primary"
              size="small"
              style="float: right"
              @click="handleAddRecord"
            >添加记录</el-button>
          </template>
          <el-table :data="healthRecords" style="width: 100%">
            <el-table-column prop="date" label="日期" />
            <el-table-column prop="bloodPressure" label="血压" />
            <el-table-column prop="heartRate" label="心率" />
            <el-table-column prop="temperature" label="体温" />
            <el-table-column prop="weight" label="体重(kg)" />
            <el-table-column label="操作" width="150" v-if="isAdmin">
              <template #default="{ row }">
                <el-button size="small" @click="handleEdit(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>健康预约</span>
            <el-button
              v-if="canBook"
              type="primary"
              size="small"
              style="float: right"
              @click="handleBookHealth"
            >预约检测</el-button>
          </template>
          <el-table :data="healthBookings" style="width: 100%">
            <el-table-column prop="date" label="预约日期" />
            <el-table-column prop="time" label="预约时间" />
            <el-table-column prop="employee" label="健康管家" />
            <el-table-column prop="status" label="状态">
              <template #default="{ row }">
                <el-tag :type="row.status === '已确认' ? 'success' : 'info'">{{ row.status }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100" v-if="canBook">
              <template #default="{ row }">
                <el-button size="small" type="danger" @click="handleCancelBooking(row.id)">取消</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="recordDialogVisible" :title="recordForm.id ? '编辑记录' : '添加记录'" width="500px">
      <el-form :model="recordForm" label-width="100px">
        <el-form-item label="日期">
          <el-date-picker v-model="recordForm.date" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="血压">
          <el-input v-model="recordForm.bloodPressure" placeholder="如：120/80" />
        </el-form-item>
        <el-form-item label="心率">
          <el-input v-model="recordForm.heartRate" placeholder="次/分钟" />
        </el-form-item>
        <el-form-item label="体温">
          <el-input v-model="recordForm.temperature" placeholder="℃" />
        </el-form-item>
        <el-form-item label="体重">
          <el-input v-model="recordForm.weight" placeholder="kg" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="recordForm.note" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="recordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitRecord">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="bookingDialogVisible" title="预约健康检测" width="500px">
      <el-form :model="bookingForm" label-width="100px">
        <el-form-item label="预约日期">
          <el-date-picker v-model="bookingForm.date" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="预约时间">
          <el-time-picker v-model="bookingForm.time" placeholder="选择时间" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="bookingForm.note" type="textarea" :rows="3" />
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
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.userType === 'admin' || userStore.userType === 'employee')
const canBook = computed(() => ['user', 'family'].includes(userStore.userType))

const healthRecords = ref([
  { id: 1, date: '2024-01-15', bloodPressure: '120/80', heartRate: '72', temperature: '36.5', weight: '65' }
])

const healthBookings = ref([
  { id: 1, date: '2024-01-20', time: '10:00', employee: '张医生', status: '已确认' }
])

const recordDialogVisible = ref(false)
const bookingDialogVisible = ref(false)

const recordForm = reactive({
  id: null,
  date: null,
  bloodPressure: '',
  heartRate: '',
  temperature: '',
  weight: '',
  note: ''
})

const bookingForm = reactive({
  date: null,
  time: null,
  note: ''
})

const handleAddRecord = () => {
  Object.assign(recordForm, {
    id: null,
    date: null,
    bloodPressure: '',
    heartRate: '',
    temperature: '',
    weight: '',
    note: ''
  })
  recordDialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(recordForm, row)
  recordDialogVisible.value = true
}

const handleDelete = (id) => {
  ElMessage.success('删除成功')
  // TODO: 调用后端接口
}

const handleSubmitRecord = () => {
  ElMessage.success('保存成功')
  recordDialogVisible.value = false
  // TODO: 调用后端接口
}

const handleBookHealth = () => {
  Object.assign(bookingForm, {
    date: null,
    time: null,
    note: ''
  })
  bookingDialogVisible.value = true
}

const handleSubmitBooking = () => {
  ElMessage.success('预约成功')
  bookingDialogVisible.value = false
  // TODO: 调用后端接口
}

const handleCancelBooking = (id) => {
  ElMessage.success('已取消预约')
  // TODO: 调用后端接口
}

onMounted(() => {
  // TODO: 加载数据
})
</script>

<style scoped>
.health-management {
  padding: 20px;
}
</style>
