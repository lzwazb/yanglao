<template>
  <div class="family-elderly">
    <el-card>
      <template #header>
        <span>我的长辈</span>
      </template>
      <el-table :data="elderlyList" style="width: 100%" v-loading="loading">
        <el-table-column prop="realName" label="姓名">
          <template #default="{ row }">
            {{ row.realName || row.name || row.username }}
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleViewDetail(row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin-top: 20px" v-if="selectedElderly" v-loading="detailLoading">
      <template #header>
        <span>{{ selectedElderly.realName || selectedElderly.name || selectedElderly.username }} - 详细信息</span>
      </template>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="个人信息" name="info">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="用户名">{{ selectedElderly.username }}</el-descriptions-item>
            <el-descriptions-item label="真实姓名">{{ selectedElderly.realName || '-' }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ selectedElderly.phone || '-' }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ selectedElderly.email || '-' }}</el-descriptions-item>
            <el-descriptions-item label="注册时间">{{ formatTime(selectedElderly.createTime) }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="健康信息" name="health">
          <el-table :data="healthData" style="width: 100%" v-loading="healthLoading">
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
            <el-table-column prop="heartRate" label="心率" width="100" />
            <el-table-column prop="bloodSugar" label="血糖" width="100" />
            <el-table-column prop="temperature" label="体温" width="100" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="服务记录" name="service">
          <el-table :data="serviceRecords" style="width: 100%" v-loading="serviceLoading">
            <el-table-column prop="serviceName" label="服务名称" />
            <el-table-column prop="bookTime" label="预约时间">
              <template #default="{ row }">
                {{ formatTime(row.bookTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
              <template #default="{ row }">
                <el-tag :type="getServiceStatusType(row.status)">{{ getServiceStatusText(row.status) }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="活动记录" name="activity">
          <el-table :data="activityRecords" style="width: 100%" v-loading="activityLoading">
            <el-table-column label="活动名称">
              <template #default="{ row }">
                {{ getActivityName(row.activityId) }}
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="报名时间">
              <template #default="{ row }">
                {{ formatTime(row.createTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
              <template #default="{ row }">
                <el-tag :type="row.status === 1 ? 'success' : 'info'">
                  {{ row.status === 1 ? '已报名' : '已取消' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useUserStore } from '@/stores/user'
import { getElderlyList } from '@/api/family'
import { getHealthDataList } from '@/api/health'
import { getUserOrders } from '@/api/service'
import { getMyRegistrations, getActivityDetail } from '@/api/activity'

const userStore = useUserStore()
const activeTab = ref('info')
const selectedElderly = ref(null)
const elderlyList = ref([])
const loading = ref(false)
const detailLoading = ref(false)

// 详情数据
const healthData = ref([])
const healthLoading = ref(false)
const serviceRecords = ref([])
const serviceLoading = ref(false)
const activityRecords = ref([])
const activityLoading = ref(false)
const activityCache = reactive({})

const loadElderlyList = async () => {
  if (!userStore.userInfo?.id) return
  loading.value = true
  try {
    const res = await getElderlyList(userStore.userInfo.id)
    elderlyList.value = res || []
  } catch (error) {
    console.error('获取老人列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleViewDetail = (row) => {
  selectedElderly.value = row
  activeTab.value = 'info'
  // 切换老人时，清空旧数据
  healthData.value = []
  serviceRecords.value = []
  activityRecords.value = []
}

// 加载健康数据
const loadHealthData = async () => {
  if (!selectedElderly.value) return
  healthLoading.value = true
  try {
    const res = await getHealthDataList(selectedElderly.value.id)
    healthData.value = res || []
  } catch (error) {
    console.error('获取健康数据失败', error)
  } finally {
    healthLoading.value = false
  }
}

// 加载服务记录
const loadServiceRecords = async () => {
  if (!selectedElderly.value) return
  serviceLoading.value = true
  try {
    const res = await getUserOrders({ userId: selectedElderly.value.id })
    serviceRecords.value = res || []
  } catch (error) {
    console.error('获取服务记录失败', error)
  } finally {
    serviceLoading.value = false
  }
}

// 加载活动记录
const loadActivityRecords = async () => {
  if (!selectedElderly.value) return
  activityLoading.value = true
  try {
    const res = await getMyRegistrations(selectedElderly.value.id)
    activityRecords.value = res || []
    // 异步加载活动详情
    res.forEach(item => {
      if (!activityCache[item.activityId]) {
        fetchActivityDetail(item.activityId)
      }
    })
  } catch (error) {
    console.error('获取活动记录失败', error)
  } finally {
    activityLoading.value = false
  }
}

const fetchActivityDetail = async (id) => {
  try {
    const res = await getActivityDetail(id)
    if (res) {
      activityCache[id] = res
    }
  } catch (error) {
    console.error(`获取活动${id}详情失败`, error)
  }
}

const getActivityName = (id) => {
  return activityCache[id] ? activityCache[id].title : `活动ID:${id}`
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
  return timeStr.replace('T', ' ')
}

const getServiceStatusType = (status) => {
  const map = {
    'PENDING': 'warning',
    'CONFIRMED': 'primary',
    'COMPLETED': 'success',
    'CANCELLED': 'info'
  }
  return map[status] || 'info'
}

const getServiceStatusText = (status) => {
  const map = {
    'PENDING': '待确认',
    'CONFIRMED': '已确认',
    'COMPLETED': '已完成',
    'CANCELLED': '已取消'
  }
  return map[status] || status
}

// 监听Tab切换
watch(activeTab, (newVal) => {
  if (!selectedElderly.value) return

  if (newVal === 'health') {
    loadHealthData()
  } else if (newVal === 'service') {
    loadServiceRecords()
  } else if (newVal === 'activity') {
    loadActivityRecords()
  }
})

onMounted(() => {
  loadElderlyList()
})
</script>

<style scoped>
.family-elderly {
  padding: 20px;
}
</style>
