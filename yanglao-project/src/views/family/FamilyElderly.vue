<template>
  <div class="family-elderly">
    <el-card>
      <template #header>
        <span>我的长辈</span>
      </template>
      <el-table :data="elderlyList" style="width: 100%">
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="roomNumber" label="房间号" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleViewDetail(row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin-top: 20px" v-if="selectedElderly">
      <template #header>
        <span>{{ selectedElderly.name }} - 详细信息</span>
      </template>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="个人信息" name="info">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="姓名">{{ selectedElderly.name }}</el-descriptions-item>
            <el-descriptions-item label="年龄">{{ selectedElderly.age }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ selectedElderly.phone }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ selectedElderly.email }}</el-descriptions-item>
            <el-descriptions-item label="房间号">{{ selectedElderly.roomNumber }}</el-descriptions-item>
            <el-descriptions-item label="床位号">{{ selectedElderly.bedNumber }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="健康信息" name="health">
          <el-table :data="healthData" style="width: 100%">
            <el-table-column prop="date" label="日期" />
            <el-table-column prop="bloodPressure" label="血压" />
            <el-table-column prop="heartRate" label="心率" />
            <el-table-column prop="temperature" label="体温" />
            <el-table-column prop="note" label="备注" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="服务记录" name="service">
          <el-table :data="serviceRecords" style="width: 100%">
            <el-table-column prop="serviceName" label="服务名称" />
            <el-table-column prop="date" label="日期" />
            <el-table-column prop="status" label="状态" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="活动记录" name="activity">
          <el-table :data="activityRecords" style="width: 100%">
            <el-table-column prop="activityName" label="活动名称" />
            <el-table-column prop="date" label="日期" />
            <el-table-column prop="status" label="状态" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const activeTab = ref('info')
const selectedElderly = ref(null)

const elderlyList = ref([
  { id: 1, name: '张爷爷', age: 75, phone: '13800138000', roomNumber: '101' }
])

const healthData = ref([
  { date: '2024-01-15', bloodPressure: '120/80', heartRate: '72', temperature: '36.5', note: '正常' }
])

const serviceRecords = ref([
  { serviceName: '家政保洁', date: '2024-01-10', status: '已完成' }
])

const activityRecords = ref([
  { activityName: '健康讲座', date: '2024-01-12', status: '已参加' }
])

const handleViewDetail = (row) => {
  selectedElderly.value = row
  // TODO: 加载详细信息
}

onMounted(() => {
  // TODO: 根据 familyId 加载关联的老人信息
})
</script>

<style scoped>
.family-elderly {
  padding: 20px;
}
</style>

