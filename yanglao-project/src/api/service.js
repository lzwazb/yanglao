import request from '@/utils/request'

// --- 服务项目相关 ---

// 获取所有上架服务
export function getServiceList() {
  return request({
    url: '/service/item/list',
    method: 'get'
  })
}

// --- 订单相关 ---

// 提交预约订单
export function bookService(data) {
  return request({
    url: '/service/order/book',
    method: 'post',
    data
  })
}

// 获取用户订单列表
export function getUserOrders(params) {
  return request({
    url: '/service/order/list',
    method: 'get',
    params
  })
}

// 取消订单
export function cancelOrder(id) {
  return request({
    url: '/service/order/cancel/' + id,
    method: 'post'
  })
}

// 管理员获取所有订单（分页）
export function getAdminOrders(params) {
  return request({
    url: '/service/order/admin/page',
    method: 'get',
    params
  })
}

// 管理员更新订单状态
export function updateOrderStatus(id, status) {
  return request({
    url: '/service/order/admin/updateStatus',
    method: 'post',
    params: { id, status }
  })
}

// --- 评价相关 ---

// 提交评价
export function addReview(data) {
  return request({
    url: '/service/review/add',
    method: 'post',
    data
  })
}

// 获取评价列表
export function getReviewList(params) {
  return request({
    url: '/service/review/list',
    method: 'get',
    params
  })
}

// 根据订单ID查询评价
export function getReviewByOrder(orderId) {
  return request({
    url: '/service/review/getByOrder/' + orderId,
    method: 'get'
  })
}
