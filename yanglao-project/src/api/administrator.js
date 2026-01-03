import request from '@/utils/request'

// 用户管理
export const userApi = {
  // 添加用户
  addUser(data) {
    return request({
      url: '/administrator/user',
      method: 'post',
      data
    })
  },
  // 删除用户
  deleteUser(id) {
    return request({
      url: `/administrator/user/delete/${id}`,
      method: 'post'
    })
  },
  // 更新用户
  updateUser(data) {
    return request({
      url: '/administrator/user/update',
      method: 'post',
      data
    })
  },
  // 获取用户详情
  getUser(id) {
    return request({
      url: `/administrator/user/${id}`,
      method: 'get'
    })
  },
  // 分页查询用户
  getUserPage(pageNum = 1, pageSize = 10) {
    return request({
      url: '/administrator/user/page',
      method: 'get',
      params: { pageNum, pageSize }
    })
  }
}

// 家人管理
export const familyApi = {
  addFamily(data) {
    return request({
      url: '/administrator/family',
      method: 'post',
      data
    })
  },
  deleteFamily(id) {
    return request({
      url: `/administrator/family/delete/${id}`,
      method: 'post'
    })
  },
  updateFamily(data) {
    return request({
      url: '/administrator/family/update',
      method: 'post',
      data
    })
  },
  getFamily(id) {
    return request({
      url: `/administrator/family/${id}`,
      method: 'get'
    })
  },
  getFamilyPage(pageNum = 1, pageSize = 10) {
    return request({
      url: '/administrator/family/page',
      method: 'get',
      params: { pageNum, pageSize }
    })
  },
  // 获取所有家属列表（用于下拉选择）
  getAllFamilies() {
    return request({
      url: '/administrator/family/list',
      method: 'get'
    })
  }
}

// 员工管理
export const employeeApi = {
  addEmployee(data) {
    return request({
      url: '/administrator/employee',
      method: 'post',
      data
    })
  },
  deleteEmployee(id) {
    return request({
      url: `/administrator/employee/delete/${id}`,
      method: 'post'
    })
  },
  updateEmployee(data) {
    return request({
      url: '/administrator/employee/update',
      method: 'post',
      data
    })
  },
  getEmployee(id) {
    return request({
      url: `/administrator/employee/${id}`,
      method: 'get'
    })
  },
  getEmployeePage(pageNum = 1, pageSize = 10) {
    return request({
      url: '/administrator/employee/page',
      method: 'get',
      params: { pageNum, pageSize }
    })
  }
}

// 管理员管理
export const administratorApi = {
  addAdministrator(data) {
    return request({
      url: '/administrator/admin',
      method: 'post',
      data
    })
  },
  deleteAdministrator(id) {
    return request({
      url: `/administrator/admin/delete/${id}`,
      method: 'post'
    })
  },
  updateAdministrator(data) {
    return request({
      url: '/administrator/admin/update',
      method: 'post',
      data
    })
  },
  getAdministrator(id) {
    return request({
      url: `/administrator/admin/${id}`,
      method: 'get'
    })
  },
  getAdministratorPage(pageNum = 1, pageSize = 10) {
    return request({
      url: '/administrator/admin/page',
      method: 'get',
      params: { pageNum, pageSize }
    })
  }
}
