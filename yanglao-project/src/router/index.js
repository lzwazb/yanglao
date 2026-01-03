import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/auth/Register.vue'),
    meta: { title: '注册' }
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    meta: { title: '首页' }
  },
  {
    path: '/layout',
    component: () => import('@/layout/MainLayout.vue'),
    redirect: '/layout/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '工作台' }
      },
      // 信息管理
      {
        path: 'admin/user',
        name: 'UserManagement',
        component: () => import('@/views/admin/UserManagement.vue'),
        meta: { title: '用户管理', requiresAuth: true, role: 'admin' }
      },
      {
        path: 'admin/family',
        name: 'FamilyManagement',
        component: () => import('@/views/admin/FamilyManagement.vue'),
        meta: { title: '家人管理', requiresAuth: true, role: 'admin' }
      },
      {
        path: 'admin/administrator',
        name: 'AdministratorManagement',
        component: () => import('@/views/admin/AdministratorManagement.vue'),
        meta: { title: '管理员管理', requiresAuth: true, role: 'admin' }
      },
      // 服务管理 (管理员)
      {
        path: 'admin/service',
        name: 'ServiceManagement',
        component: () => import('@/views/admin/ServiceManagement.vue'),
        meta: { title: '服务项目管理', requiresAuth: true, role: 'admin' }
      },
      {
        path: 'admin/service/order',
        name: 'ServiceOrderManagement',
        component: () => import('@/views/admin/ServiceOrderManagement.vue'),
        meta: { title: '服务订单管理', requiresAuth: true, role: 'admin' }
      },
      // 公告管理 (管理员)
      {
        path: 'admin/notice',
        name: 'NoticeManagement',
        component: () => import('@/views/admin/NoticeManagement.vue'),
        meta: { title: '公告管理', requiresAuth: true, role: 'admin' }
      },
      // 留言管理 (管理员)
      {
        path: 'admin/message',
        name: 'MessageManagement',
        component: () => import('@/views/admin/MessageManagement.vue'),
        meta: { title: '留言管理', requiresAuth: true, role: 'admin' }
      },
      // 服务预约
      {
        path: 'service/booking',
        name: 'ServiceBooking',
        component: () => import('@/views/service/ServiceBooking.vue'),
        meta: { title: '服务预约', requiresAuth: true }
      },
      {
        path: 'service/life',
        name: 'LifeService',
        component: () => import('@/views/service/LifeService.vue'),
        meta: { title: '生活服务', requiresAuth: true }
      },
      // 服务评价
      {
        path: 'service/review',
        name: 'ServiceReview',
        component: () => import('@/views/service/ServiceReview.vue'),
        meta: { title: '服务评价', requiresAuth: true }
      },
      // 健康管理
      {
        path: 'health/management',
        name: 'HealthManagement',
        component: () => import('@/views/health/HealthManagement.vue'),
        meta: { title: '健康管理', requiresAuth: true }
      },
      // 活动管理
      {
        path: 'activity/list',
        name: 'ActivityList',
        component: () => import('@/views/activity/ActivityList.vue'),
        meta: { title: '活动列表', requiresAuth: true }
      },
      {
        path: 'activity/manage',
        name: 'ActivityManage',
        component: () => import('@/views/activity/ActivityManage.vue'),
        meta: { title: '活动管理', requiresAuth: true, role: 'admin' }
      },
      // 公告栏
      {
        path: 'notice/list',
        name: 'NoticeList',
        component: () => import('@/views/notice/NoticeList.vue'),
        meta: { title: '公告栏' }
      },
      // 留言板
      {
        path: 'message/board',
        name: 'MessageBoard',
        component: () => import('@/views/message/MessageBoard.vue'),
        meta: { title: '留言板' }
      },
      // 个人中心
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/profile/Profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      },
      // 家人查看老人信息
      {
        path: 'family/elderly',
        name: 'FamilyElderly',
        component: () => import('@/views/family/FamilyElderly.vue'),
        meta: { title: '我的长辈', requiresAuth: true, role: 'family' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  // 检查是否需要登录（使用 userInfo 而不是 token，因为后端可能没有返回 token）
  if (to.meta.requiresAuth && !userStore.userInfo) {
    next({ path: '/login', query: { redirect: to.fullPath } })
  } else if (to.meta.role && userStore.userType !== to.meta.role) {
    // 如果路由需要特定角色，但当前用户角色不匹配，跳转到工作台
    next({ path: '/layout/dashboard' })
  } else {
    next()
  }
})

export default router
