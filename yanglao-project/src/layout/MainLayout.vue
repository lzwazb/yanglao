<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="header-left">
        <h2>养老服务平台</h2>
      </div>
      <div class="header-right">
        <el-scrollbar>
          <div class="notice-bar">
            <el-icon><Bell /></el-icon>
            <span>欢迎使用养老服务平台</span>
          </div>
        </el-scrollbar>
        <el-dropdown @command="handleCommand">
          <span class="user-info">
            <el-icon><User /></el-icon>
            {{ userStore.userInfo?.username || '未登录' }}
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu
          :default-active="activeMenu"
          router
          class="sidebar-menu"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
        >
          <el-menu-item index="/layout/dashboard">
            <el-icon><House /></el-icon>
            <span>工作台</span>
          </el-menu-item>
          
          <el-sub-menu index="admin" v-if="userStore.userType === 'admin'">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/layout/admin/user">用户管理</el-menu-item>
            <el-menu-item index="/layout/admin/family">家人管理</el-menu-item>
            <el-menu-item index="/layout/admin/employee">员工管理</el-menu-item>
            <el-menu-item index="/layout/admin/administrator">管理员管理</el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/layout/service/booking" v-if="['user', 'family'].includes(userStore.userType)">
            <el-icon><Calendar /></el-icon>
            <span>服务预约</span>
          </el-menu-item>

          <el-menu-item index="/layout/service/life" v-if="['user', 'family'].includes(userStore.userType)">
            <el-icon><Service /></el-icon>
            <span>生活服务</span>
          </el-menu-item>

          <el-menu-item index="/layout/family/elderly" v-if="userStore.userType === 'family'">
            <el-icon><UserFilled /></el-icon>
            <span>我的长辈</span>
          </el-menu-item>

          <el-menu-item index="/layout/service/review" v-if="['user', 'family'].includes(userStore.userType)">
            <el-icon><Star /></el-icon>
            <span>服务评价</span>
          </el-menu-item>

          <el-menu-item index="/layout/health/management">
            <el-icon><FirstAidKit /></el-icon>
            <span>健康管理</span>
          </el-menu-item>

          <el-menu-item index="/layout/activity/list">
            <el-icon><Trophy /></el-icon>
            <span>活动列表</span>
          </el-menu-item>

          <el-menu-item index="/layout/activity/manage" v-if="userStore.userType === 'admin'">
            <el-icon><Document /></el-icon>
            <span>活动管理</span>
          </el-menu-item>

          <el-menu-item index="/layout/notice/list">
            <el-icon><Bell /></el-icon>
            <span>公告栏</span>
          </el-menu-item>

          <el-menu-item index="/layout/message/board">
            <el-icon><ChatLineRound /></el-icon>
            <span>留言板</span>
          </el-menu-item>

          <el-menu-item index="/layout/profile">
            <el-icon><User /></el-icon>
            <span>个人中心</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import {
  House,
  Setting,
  Calendar,
  Service,
  UserFilled,
  Star,
  FirstAidKit,
  Trophy,
  Document,
  Bell,
  ChatLineRound,
  User,
  ArrowDown
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/layout/profile')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.header {
  background: #409EFF;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-left h2 {
  margin: 0;
  font-size: 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.notice-bar {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background 0.3s;
}

.user-info:hover {
  background: rgba(255, 255, 255, 0.1);
}

.aside {
  background: #304156;
}

.sidebar-menu {
  border-right: none;
  height: 100%;
}

.main-content {
  background: #f0f2f5;
  padding: 20px;
}
</style>

