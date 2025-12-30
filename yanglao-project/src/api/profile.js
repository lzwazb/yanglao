import request from '@/utils/request'

/**
 * 修改当前登录用户的密码
 * @param {object} data - 包含 oldPassword 和 newPassword
 */
export function updateUserPassword(data) {
  return request({
    url: '/user/profile/password',
    method: 'post',
    data
  })
}

/**
 * 修改当前登录家人的密码
 * @param {object} params - 包含 familyId, oldPassword, newPassword
 */
export function updateFamilyPassword(params) {
  return request({
    url: '/user/profile/family/password',
    method: 'post',
    params // 使用params将数据作为URL查询参数发送
  })
}

/**
 * 更新用户个人基本信息
 * @param {object} data
 */
export function updateUserProfile(data) {
    return request({
        url: '/user/profile/update',
        method: 'post',
        data
    })
}

/**
 * 更新家人个人基本信息
 * @param {object} data
 */
export function updateFamilyProfile(data) {
    return request({
        url: '/user/profile/family/update',
        method: 'post',
        data
    })
}
