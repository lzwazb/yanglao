import request from '@/utils/request'

// 获取留言分页列表 (管理端)
export const getMessagePage = (params) => {
  return request({
    url: '/message/page',
    method: 'get',
    params
  })
}

// 发布留言 (用户端)
export const addMessage = (data) => {
  return request({
    url: '/message/add',
    method: 'post',
    data
  })
}

// 回复留言 (管理端)
export const replyMessage = (data) => {
  return request({
    url: '/message/reply',
    method: 'post',
    data
  })
}

// 用户回复/追问留言
export const userReplyMessage = (data) => {
  return request({
    url: '/message/user/reply',
    method: 'post',
    data
  })
}

// 删除留言
export const deleteMessage = (id) => {
  return request({
    url: `/message/delete/${id}`,
    method: 'post'
  })
}
