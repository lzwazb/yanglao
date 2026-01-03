<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="title">养老服务平台</h2>
      <el-tabs v-model="activeTab" class="login-tabs">
        <el-tab-pane label="用户登录" name="user">
          <el-form :model="userForm" :rules="rules" ref="userFormRef" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="userForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="userForm.password" type="password" placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUserLogin" :loading="loading" style="width: 100%">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="家人登录" name="family">
          <el-form :model="familyForm" :rules="rules" ref="familyFormRef" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="familyForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="familyForm.password" type="password" placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleFamilyLogin" :loading="loading" style="width: 100%">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="管理员登录" name="admin">
          <el-form :model="adminForm" :rules="rules" ref="adminFormRef" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="adminForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="adminForm.password" type="password" placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleAdminLogin" :loading="loading" style="width: 100%">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <div class="register-link">
        <span>还没有账号？</span>
        <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { loginApi } from '@/api/auth'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const activeTab = ref('user')
const loading = ref(false)

const userForm = reactive({
  username: '',
  password: ''
})

const familyForm = reactive({
  username: '',
  password: ''
})

const adminForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const userFormRef = ref()
const familyFormRef = ref()
const adminFormRef = ref()

const handleUserLogin = async () => {
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await loginApi.userLogin(userForm)
        userStore.setUserInfo(res)
        userStore.setUserType('user')
        userStore.setToken(res.id ? `user_${res.id}` : `user_${Date.now()}`)
        ElMessage.success('登录成功')
        router.push('/layout/dashboard')
      } catch (error) {
        ElMessage.error(error.message || '登录失败')
      } finally {
        loading.value = false
      }
    }
  })
}

const handleFamilyLogin = async () => {
  await familyFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await loginApi.familyLogin(familyForm)
        userStore.setUserInfo(res)
        userStore.setUserType('family')
        userStore.setToken(res.id ? `family_${res.id}` : `family_${Date.now()}`)
        ElMessage.success('登录成功')
        router.push('/layout/dashboard')
      } catch (error) {
        ElMessage.error(error.message || '登录失败')
      } finally {
        loading.value = false
      }
    }
  })
}

const handleAdminLogin = async () => {
  await adminFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await loginApi.adminLogin(adminForm)
        userStore.setUserInfo(res)
        userStore.setUserType('admin')
        // 设置一个简单的 token（如果后端没有返回 token，使用用户 ID 作为标识）
        userStore.setToken(res.id ? `admin_${res.id}` : `admin_${Date.now()}`)
        ElMessage.success('登录成功')
        router.push('/layout/dashboard')
      } catch (error) {
        ElMessage.error(error.message || '登录失败')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 450px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 28px;
}

.login-tabs {
  margin-bottom: 20px;
}

.register-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
}
</style>
