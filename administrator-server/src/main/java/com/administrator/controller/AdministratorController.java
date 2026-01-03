package com.administrator.controller;

import com.administrator.entity.Administrator;
import com.administrator.entity.Family;
import com.administrator.entity.User;
import com.administrator.service.AdministratorService;
import com.administrator.service.FamilyService;
import com.administrator.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")
@Tag(name = "管理员接口")
public class AdministratorController {

    @Autowired
    private UserService userService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private AdministratorService administratorService;

    // --- User Management ---
    @Operation(summary = "添加用户")
    @PostMapping("/user")
    public boolean addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @Operation(summary = "删除用户")
    @PostMapping("/user/delete/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.removeById(id);
    }

    @Operation(summary = "修改用户")
    @PostMapping("/user/update")
    public boolean updateUser(@RequestBody User user) {
        System.out.println(user);
        return userService.updateById(user);
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/user/page")
    public Page<User> getUserPage(@RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        return userService.page(new Page<>(pageNum, pageSize));
    }

    @Operation(summary = "查询用户详情")
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    // --- Family Management ---
    @Operation(summary = "添加家属")
    @PostMapping("/family")
    public boolean addFamily(@RequestBody Family family) {
        return familyService.save(family);
    }

    @Operation(summary = "删除家属")
    @PostMapping("/family/delete/{id}")
    public boolean deleteFamily(@PathVariable Long id) {
        return familyService.removeById(id);
    }

    @Operation(summary = "修改家属")
    @PostMapping("/family/update")
    public boolean updateFamily(@RequestBody Family family) {
        return familyService.updateById(family);
    }

    @Operation(summary = "获取所有家属列表")
    @GetMapping("/family/list")
    public List<Family> getAllFamilies() {
        return familyService.list();
    }

    @Operation(summary = "查询家属详情")
    @GetMapping("/family/{id}")
    public Family getFamily(@PathVariable Long id) {
        return familyService.getById(id);
    }

    @Operation(summary = "分页查询家属")
    @GetMapping("/family/page")
    public Page<Family> getFamilyPage(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return familyService.page(new Page<>(pageNum, pageSize));
    }

    // --- Administrator Management ---
    @Operation(summary = "添加管理员")
    @PostMapping("/admin")
    public boolean addAdministrator(@RequestBody Administrator administrator) {
        return administratorService.save(administrator);
    }

    @Operation(summary = "删除管理员")
    @PostMapping("/admin/delete/{id}")
    public boolean deleteAdministrator(@PathVariable Long id) {
        return administratorService.removeById(id);
    }

    @Operation(summary = "修改管理员")
    @PostMapping("/admin/update")
    public boolean updateAdministrator(@RequestBody Administrator administrator) {
        return administratorService.updateById(administrator);
    }

    @Operation(summary = "查询管理员详情")
    @GetMapping("/admin/{id}")
    public Administrator getAdministrator(@PathVariable Long id) {
        return administratorService.getById(id);
    }

    @Operation(summary = "分页查询管理员")
    @GetMapping("/admin/page")
    public Page<Administrator> getAdministratorPage(@RequestParam(defaultValue = "1") int pageNum,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        return administratorService.page(new Page<>(pageNum, pageSize));
    }
}
