import request from '@/utils/request'

// 获取关联的老人列表
export function getElderlyList(familyId) {
  return request({
    url: `/user/family/elderly/list/${familyId}`,
    method: 'get'
  })
}

// 获取老人详细信息
export function getElderlyDetail(familyId, elderlyId) {
  return request({
    url: `/user/family/elderly/detail/${familyId}/${elderlyId}`,
    method: 'get'
  })
}
