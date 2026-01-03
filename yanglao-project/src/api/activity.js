import request from '@/utils/request'

// --- 用户端接口 ---

// 获取可报名的活动列表
export function getActivityList() {
  return request({
    url: '/activity/list',
    method: 'get'
  })
}

// 活动报名
// params: { activityId, userId, userName }
export function registerActivity(params) {
  return request({
    url: '/activity/register',
    method: 'post',
    params
  })
}

// 取消报名
// params: { activityId, userId }
export function cancelRegistration(params) {
  return request({
    url: '/activity/cancel',
    method: 'post',
    params
  })
}

// 获取我的报名记录
export function getMyRegistrations(userId) {
  return request({
    url: '/activity/my',
    method: 'get',
    params: { userId }
  })
}

// 获取活动详情
export function getActivityDetail(id) {
  return request({
    url: `/activity/${id}`,
    method: 'get'
  })
}

// --- 管理员接口 ---

// 分页获取活动列表
// params: { pageNum, pageSize, title }
export function getActivityPage(params) {
  return request({
    url: '/activity/page',
    method: 'get',
    params
  })
}

// 发布活动
export function addActivity(data) {
  return request({
    url: '/activity/add',
    method: 'post',
    data
  })
}

// 更新活动
export function updateActivity(data) {
  return request({
    url: '/activity/update',
    method: 'post',
    data
  })
}

// 删除活动
export function deleteActivity(id) {
  return request({
    url: `/activity/delete/${id}`,
    method: 'post'
  })
}
