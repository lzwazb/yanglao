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
            <p>{{ userInfo.phone }}</p>
            <p>{{ userInfo.email }}</p>
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
                <el-form-item label="邮箱" v-if="userType === 'user' || userType === 'admin'">
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
              <!-- 后端缺少活动接口，暂时为静态数据 -->
              <el-table :data="myActivities" style="width: 100%">
                <el-table-column prop="name" label="活动名称" />
                <el-table-column prop="date" label="活动日期" />
                <el-table-column prop="status" label="状态" />
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="健康数据" name="health">
              <!-- 后端缺少健康数据接口，暂时为静态数据 -->
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
// import { userApi, familyApi, employeeApi, administratorApi } from '@/api/administrator'
import { updateUserProfile, updateFamilyProfile, updateUserPassword, updateFamilyPassword } from '@/api/profile'
import { userApi, familyApi, employeeApi, administratorApi } from '@/api/administrator';
import { id } from 'element-plus/es/locale/index.mjs';


const userStore = useUserStore()
const activeTab = ref('info')
const passwordFormRef = ref()

const userInfo = computed(() => userStore.userInfo || {})
const userType = computed(() => userStore.userType || '')

const editForm = reactive({
  realName: '',
  phone: '',
  email: ''
})

// 组件加载时，用store中的信息填充表单
onMounted(() => {
  if (userInfo.value) {
    editForm.realName = userInfo.value.realName || userInfo.value.name || ''
    editForm.phone = userInfo.value.phone || ''
    editForm.email = userInfo.value.email || ''
  }
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


// 
const handleUpdateInfo = async () => {
  if (!userInfo.value || !userInfo.value.id) {
    ElMessage.error('无法获取用户信息，请重新登录')
    return
  }

  try {
    let response;
    // 根据用户类型调用不同的更新接口
    if (userType.value === 'user') {
      const payload = {
        id: userInfo.value.id,
        realName: editForm.realName,
        phone: editForm.phone,
        email: editForm.email
      }
      response = await updateUserProfile(payload);
    } else if (userType.value === 'family') {
       const payload = {
        id: userInfo.value.id,
        name: editForm.realName, // 家人实体类中是name
        phone: editForm.phone
      }
      response = await updateFamilyProfile(payload);
    }
    // employee 和 admin 的个人信息修改在 administrator-server 中
    else if (userType.value === 'employee') {
      const payload = { ...userInfo.value, name: editForm.realName, phone: editForm.phone };
      response = await employeeApi.updateEmployee(payload);
    } else if (userType.value === 'admin') {
      const payload = { ...userInfo.value, realName: editForm.realName, phone: editForm.phone, email: editForm.email };
      response = await administratorApi.updateAdministrator(payload);
    } else {
      ElMessage.error('未知的用户类型，无法更新');
      return;
    }
     if (response) {
      ElMessage.success('信息更新成功！');
      const newInfo = { 
        ...userInfo.value,
        realName: editForm.realName,
        name: editForm.realName, // 同时更新name和realName，兼容不同用户类型
        phone: editForm.phone,
        email: editForm.email
      };
      userStore.setUserInfo(newInfo);
    } else {
      ElMessage.error('信息更新失败，请稍后再试。');
    }
  } catch (error) {
    console.error('更新用户信息时出错:', error);
    // ElMessage.error('请求失败，请检查网络或联系管理员。');
  }
}

const handleUpdatePassword = async () => {
  if (!passwordFormRef.value) return
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        let response;
        const userId = userInfo.value.id
        if (!userId) {
          ElMessage.error('无法获取用户ID，请重新登录')
          return
        }

        const { oldPassword, newPassword } = passwordForm

        if (userType.value === 'user') {
          response = await updateUserPassword({id: userId,oldPassword, newPassword })
        } else if (userType.value === 'family') {
          // updateFamilyPassword 需要 id 作为 URL 参数
          response = await updateFamilyPassword({ familyId: userId, oldPassword, newPassword })
        } else {
          ElMessage.warning('管理员和员工的密码修改功能暂未开放。')
          return
        }

        if (response) {
          ElMessage.success('密码修改成功！')
          passwordForm.oldPassword = ''
          passwordForm.newPassword = ''
          passwordForm.confirmPassword = ''
        }
        // 错误情况已由axios拦截器处理
      } catch (error) {
        console.error('修改密码时出错:', error)
        // ElMessage.error('修改密码失败，请稍后再试。'); // 可以省略，拦截器会显示更具体的信息
      }
    } else {
      console.log('密码表单校验失败')
      return false
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

