package com.administrator.controller;

import com.administrator.entity.Administrator;
import com.administrator.entity.Employee;
import com.administrator.entity.Family;
import com.administrator.entity.User;
import com.administrator.service.AdministratorService;
import com.administrator.service.EmployeeService;
import com.administrator.service.FamilyService;
import com.administrator.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator")
@Tag(name = "管理员接口")
public class AdministratorController {

    @Autowired
    private UserService userService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private EmployeeService employeeService;

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
    public boolean deleteUser(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @Operation(summary = "修改用户")
    @PostMapping("/user/update")
    public boolean updateUser(@RequestBody User user) {
        return userService.updateById(user);
    }

    @Operation(summary = "查询用户详情")
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/user/page")
    public Page<User> getUserPage(@RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        return userService.page(new Page<>(pageNum, pageSize));
    }

    // --- Family Management ---
    @Operation(summary = "添加家属")
    @PostMapping("/family")
    public boolean addFamily(@RequestBody Family family) {
        return familyService.save(family);
    }

    @Operation(summary = "删除家属")
    @PostMapping("/family/delete/{id}")
    public boolean deleteFamily(@PathVariable Integer id) {
        return familyService.removeById(id);
    }

    @Operation(summary = "修改家属")
    @PostMapping("/family/update")
    public boolean updateFamily(@RequestBody Family family) {
        return familyService.updateById(family);
    }

    @Operation(summary = "查询家属详情")
    @GetMapping("/family/{id}")
    public Family getFamily(@PathVariable Integer id) {
        return familyService.getById(id);
    }

    @Operation(summary = "分页查询家属")
    @GetMapping("/family/page")
    public Page<Family> getFamilyPage(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return familyService.page(new Page<>(pageNum, pageSize));
    }

    // --- Employee Management ---
    @Operation(summary = "添加员工")
    @PostMapping("/employee")
    public boolean addEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @Operation(summary = "删除员工")
    @PostMapping("/employee/delete/{id}")
    public boolean deleteEmployee(@PathVariable String id) {
        return employeeService.removeById(id);
    }

    @Operation(summary = "修改员工")
    @PostMapping("/employee/update")
    public boolean updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateById(employee);
    }

    @Operation(summary = "查询员工详情")
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable String id) {
        return employeeService.getById(id);
    }

    @Operation(summary = "分页查询员工")
    @GetMapping("/employee/page")
    public Page<Employee> getEmployeePage(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        return employeeService.page(new Page<>(pageNum, pageSize));
    }

    // --- Administrator Management ---
    @Operation(summary = "添加管理员")
    @PostMapping("/admin")
    public boolean addAdministrator(@RequestBody Administrator administrator) {
        return administratorService.save(administrator);
    }

    @Operation(summary = "删除管理员")
    @PostMapping("/admin/delete/{id}")
    public boolean deleteAdministrator(@PathVariable Integer id) {
        return administratorService.removeById(id);
    }

    @Operation(summary = "修改管理员")
    @PostMapping("/admin/update")
    public boolean updateAdministrator(@RequestBody Administrator administrator) {
        return administratorService.updateById(administrator);
    }

    @Operation(summary = "查询管理员详情")
    @GetMapping("/admin/{id}")
    public Administrator getAdministrator(@PathVariable Integer id) {
        return administratorService.getById(id);
    }

    @Operation(summary = "分页查询管理员")
    @GetMapping("/admin/page")
    public Page<Administrator> getAdministratorPage(@RequestParam(defaultValue = "1") int pageNum,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        return administratorService.page(new Page<>(pageNum, pageSize));
    }
}
