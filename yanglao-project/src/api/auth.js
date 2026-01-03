import request from '@/utils/request'

// 登录接口
export const loginApi = {
  // 用户登录
  userLogin(data) {
    return request({
      url: '/login/user',
      method: 'post',
      data
    })
  },
  // 管理员登录
  adminLogin(data) {
    return request({
      url: '/login/admin',
      method: 'post',
      data
    })
  },
  // 家人登录
  familyLogin(data) {
    return request({
      url: '/login/family',
      method: 'post',
      data
    })
  }
}

// 注册接口
export const registerApi = {
  // 用户注册
  userRegister(data) {
    return request({
      url: '/register/user',
      method: 'post',
      data
    })
  },
  // 管理员注册
  adminRegister(data) {
    return request({
      url: '/register/admin',
      method: 'post',
      data
    })
  },
  // 家人注册
  familyRegister(data) {
    return request({
      url: '/register/family',
      method: 'post',
      data
    })
  },
  // 根据姓名查找家属ID
  findFamilyIdByName(name) {
    return request({
      url: '/register/findFamilyId',
      method: 'get',
      params: { name }
    })
  }
}
