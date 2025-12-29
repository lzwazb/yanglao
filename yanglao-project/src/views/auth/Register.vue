<template>
  <div class="register-container">
    <div class="register-box">
      <h2 class="title">用户注册</h2>
      <el-tabs v-model="activeTab" class="register-tabs">
        <el-tab-pane label="老人注册" name="user">
          <el-form :model="userForm" :rules="userRules" ref="userFormRef" label-width="100px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="userForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="userForm.password" type="password" placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="userForm.realName" placeholder="请输入真实姓名" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userForm.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="家庭ID" prop="familyId">
              <el-input-number v-model="userForm.familyId" :min="1" placeholder="请输入家庭ID" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUserRegister" :loading="loading" style="width: 100%">注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="家人注册" name="family">
          <el-form :model="familyForm" :rules="familyRules" ref="familyFormRef" label-width="100px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="familyForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="familyForm.password" type="password" placeholder="请输入密码" show-password />
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="familyForm.name" placeholder="请输入姓名" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="familyForm.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="familyForm.address" placeholder="请输入地址" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleFamilyRegister" :loading="loading" style="width: 100%">注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <div class="login-link">
        <span>已有账号？</span>
        <el-link type="primary" @click="$router.push('/login')">立即登录</el-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { registerApi } from '@/api/auth'

const router = useRouter()

const activeTab = ref('user')
const loading = ref(false)

const userForm = reactive({
  username: '',
  password: '',
  realName: '',
  email: '',
  phone: '',
  familyId: null
})

const familyForm = reactive({
  username: '',
  password: '',
  name: '',
  phone: '',
  address: ''
})

const userRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const familyRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const userFormRef = ref()
const familyFormRef = ref()

const handleUserRegister = async () => {
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await registerApi.userRegister(userForm)
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (error) {
        ElMessage.error(error.message || '注册失败')
      } finally {
        loading.value = false
      }
    }
  })
}

const handleFamilyRegister = async () => {
  await familyFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await registerApi.familyRegister(familyForm)
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (error) {
        ElMessage.error(error.message || '注册失败')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-box {
  width: 600px;
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

.register-tabs {
  margin-bottom: 20px;
}

.login-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
}
</style>

