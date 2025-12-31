package com.yanglao.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanglao.business.entity.ServiceOrder;
import com.yanglao.business.service.ServiceOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/service/order")
@Tag(name = "服务订单管理")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Operation(summary = "提交预约订单")
    @PostMapping("/book")
    public boolean book(@RequestBody ServiceOrder order) {
        order.setStatus("PENDING");
        order.setCreateTime(LocalDateTime.now());
        return serviceOrderService.save(order);
    }

    @Operation(summary = "获取用户订单列表")
    @GetMapping("/list")
    public Page<ServiceOrder> list(@RequestParam Long userId,
                                   @RequestParam(defaultValue = "1") int pageNum,
                                   @RequestParam(defaultValue = "10") int pageSize,
                                   @RequestParam(required = false) String status) {
        LambdaQueryWrapper<ServiceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ServiceOrder::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq(ServiceOrder::getStatus, status);
        }
        wrapper.orderByDesc(ServiceOrder::getCreateTime);
        return serviceOrderService.page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Operation(summary = "取消订单")
    @PostMapping("/cancel/{id}")
    public boolean cancel(@PathVariable Long id) {
        ServiceOrder order = new ServiceOrder();
        order.setId(id);
        order.setStatus("CANCELLED");
        return serviceOrderService.updateById(order);
    }

    @Operation(summary = "管理员分页查询订单")
    @GetMapping("/admin/page")
    public Page<ServiceOrder> adminPage(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) String status) {
        LambdaQueryWrapper<ServiceOrder> wrapper = new LambdaQueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            wrapper.eq(ServiceOrder::getStatus, status);
        }
        wrapper.orderByDesc(ServiceOrder::getCreateTime);
        return serviceOrderService.page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Operation(summary = "管理员更新订单状态")
    @PostMapping("/admin/updateStatus")
    public boolean updateStatus(@RequestParam Long id, @RequestParam String status) {
        ServiceOrder order = new ServiceOrder();
        order.setId(id);
        order.setStatus(status);
        return serviceOrderService.updateById(order);
    }
}
