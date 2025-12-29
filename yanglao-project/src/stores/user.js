import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  // 安全地从 localStorage 读取数据
  const getLocalStorage = (key, defaultValue) => {
    try {
      const value = localStorage.getItem(key)
      return value ? (key === 'userInfo' ? JSON.parse(value) : value) : defaultValue
    } catch (e) {
      console.error(`读取 localStorage ${key} 失败:`, e)
      return defaultValue
    }
  }

  const token = ref(getLocalStorage('token', ''))
  const userInfo = ref(getLocalStorage('userInfo', null))
  const userType = ref(getLocalStorage('userType', '')) // user, admin, family, employee

  const setToken = (newToken) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const setUserInfo = (info) => {
    userInfo.value = info
    localStorage.setItem('userInfo', JSON.stringify(info))
  }

  const setUserType = (type) => {
    userType.value = type
    localStorage.setItem('userType', type)
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    userType.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('userType')
  }

  return {
    token,
    userInfo,
    userType,
    setToken,
    setUserInfo,
    setUserType,
    logout
  }
})

