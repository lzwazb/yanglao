<template>
  <div class="profile">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header>
            <span>个人信息</span>
          </template>
          <div class="avatar-section">
            <el-avatar :size="100" :src="userInfo.avatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" />
            <h3>{{ userInfo.realName || userInfo.name || userInfo.username }}</h3>
            <p>{{ userInfo.email || userInfo.phone }}</p>
          </div>
          <el-divider />
          <el-descriptions :column="1" border>
            <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
            <el-descriptions-item label="真实姓名">{{ userInfo.realName || userInfo.name || '-' }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ userInfo.phone || '-' }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ userInfo.email || '-' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>修改信息</span>
          </template>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本信息" name="info">
              <el-form :model="editForm" label-width="100px" style="max-width: 600px">
                <el-form-item label="真实姓名">
                  <el-input v-model="editForm.realName" />
                </el-form-item>
                <el-form-item label="手机号">
                  <el-input v-model="editForm.phone" />
                </el-form-item>
                <el-form-item label="邮箱">
                  <el-input v-model="editForm.email" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleUpdateInfo">保存</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="password">
              <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px" style="max-width: 600px">
                <el-form-item label="原密码" prop="oldPassword">
                  <el-input v-model="passwordForm.oldPassword" type="password" show-password />
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                  <el-input v-model="passwordForm.newPassword" type="password" show-password />
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                  <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleUpdatePassword">保存</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="我的活动" name="activity">
              <el-table :data="myActivities" style="width: 100%">
                <el-table-column prop="name" label="活动名称" />
                <el-table-column prop="date" label="活动日期" />
                <el-table-column prop="status" label="状态" />
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="健康数据" name="health">
              <el-table :data="healthData" style="width: 100%">
                <el-table-column prop="date" label="日期" />
                <el-table-column prop="bloodPressure" label="血压" />
                <el-table-column prop="heartRate" label="心率" />
                <el-table-column prop="temperature" label="体温" />
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const activeTab = ref('info')
const passwordFormRef = ref()

const userInfo = computed(() => userStore.userInfo || {})

const editForm = reactive({
  realName: userInfo.value.realName || userInfo.value.name || '',
  phone: userInfo.value.phone || '',
  email: userInfo.value.email || ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const myActivities = ref([
  { name: '健康讲座', date: '2024-01-20', status: '已报名' }
])

const healthData = ref([
  { date: '2024-01-15', bloodPressure: '120/80', heartRate: '72', temperature: '36.5' }
])

const handleUpdateInfo = () => {
  ElMessage.success('更新成功')
  // TODO: 调用后端接口
}

const handleUpdatePassword = () => {
  passwordFormRef.value.validate((valid) => {
    if (valid) {
      ElMessage.success('密码修改成功')
      // TODO: 调用后端接口
    }
  })
}
</script>

<style scoped>
.profile {
  padding: 20px;
}

.avatar-section {
  text-align: center;
  padding: 20px 0;
}

.avatar-section h3 {
  margin: 15px 0 5px;
  color: #333;
}

.avatar-section p {
  color: #666;
  font-size: 14px;
}
</style>

