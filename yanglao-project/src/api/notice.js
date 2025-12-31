import request from '@/utils/request'

// 获取所有已发布公告(用户端)
export function getNoticeList() {
  return request({
    url: '/notice/list',
    method: 'get'
  })
}

// 分页获取公告(管理端)
export function getNoticePage(params) {
  return request({
    url: '/notice/page',
    method: 'get',
    params
  })
}

// 发布公告
export function addNotice(data) {
  return request({
    url: '/notice/add',
    method: 'post',
    data
  })
}

// 修改公告
export function updateNotice(data) {
  return request({
    url: '/notice/update',
    method: 'post',
    data
  })
}

// 删除公告
export function deleteNotice(id) {
  return request({
    url: '/notice/delete/' + id,
    method: 'post'
  })
}
