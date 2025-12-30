package com.userserver.service;

import com.userserver.entity.User;

import java.util.List;

public interface FamilyElderlyService {

    /**
     * 根据家人ID查询关联的老人信息列表
     * @param familyId 家人ID
     * @return 老人信息列表
     */
    List<User> getElderlyByFamilyId(Integer familyId);

    /**
     * 根据家人ID和老人ID查询老人详细信息
     * @param familyId 家人ID
     * @param elderlyId 老人ID
     * @return 老人详细信息
     */
    User getElderlyDetail(Integer familyId, Integer elderlyId);
}


