import request from '@/utils/request'

// 获取健康档案
export function getHealthProfile(userId) {
  return request({
    url: `/health/profile/${userId}`,
    method: 'get'
  })
}

// 保存/更新健康档案
export function saveHealthProfile(data) {
  return request({
    url: '/health/profile/save',
    method: 'post',
    data
  })
}

// 获取健康数据列表
export function getHealthDataList(userId) {
  return request({
    url: '/health/data/list',
    method: 'get',
    params: { userId }
  })
}

// 添加健康数据
export function addHealthData(data) {
  return request({
    url: '/health/data/add',
    method: 'post',
    data
  })
}

// 删除健康数据
export function deleteHealthData(id) {
  return request({
    url: `/health/data/delete/${id}`,
    method: 'post'
  })
}

// --- 管理员接口 ---

// 分页获取健康档案
export function getAdminProfilePage(params) {
  return request({
    url: '/health/admin/profile/page',
    method: 'get',
    params
  })
}

// 分页获取健康数据
export function getAdminDataPage(params) {
  return request({
    url: '/health/admin/data/page',
    method: 'get',
    params
  })
}
