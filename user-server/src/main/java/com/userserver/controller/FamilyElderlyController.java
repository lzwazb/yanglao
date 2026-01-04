package com.userserver.controller;

import com.userserver.entity.User;
import com.userserver.service.FamilyElderlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/family/elderly")
@Tag(name = "家属查看老人接口")
public class FamilyElderlyController {

    @Autowired
    private FamilyElderlyService familyElderlyService;

    @Operation(summary = "查询关联的老人列表", description = "根据家人ID查询其关联的所有老人信息")
    @GetMapping("/list/{familyId}")
    public List<User> getElderlyList(@PathVariable Long familyId) {
        return familyElderlyService.getElderlyByFamilyId(familyId);
    }

    @Operation(summary = "查询老人详细信息", description = "根据家人ID和老人ID查询老人的详细信息")
    @GetMapping("/detail/{familyId}/{elderlyId}")
    public User getElderlyDetail(@PathVariable Long familyId,
                                 @PathVariable Long elderlyId) {
        return familyElderlyService.getElderlyDetail(familyId, elderlyId);
    }
}
