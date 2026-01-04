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
import java.util.List;

@RestController
@RequestMapping("/service/order")
@Tag(name = "服务订单接口")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Operation(summary = "预约服务")
    @PostMapping("/book")
    public boolean book(@RequestBody ServiceOrder serviceOrder) {
        serviceOrder.setStatus("PENDING");
        serviceOrder.setCreateTime(LocalDateTime.now());
        serviceOrder.setUpdateTime(LocalDateTime.now());
        return serviceOrderService.save(serviceOrder);
    }

    @Operation(summary = "查询用户订单列表")
    @GetMapping("/list")
    public List<ServiceOrder> getUserOrders(@RequestParam Long userId, @RequestParam(required = false) String status) {
        LambdaQueryWrapper<ServiceOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceOrder::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(ServiceOrder::getStatus, status);
        }
        queryWrapper.orderByDesc(ServiceOrder::getCreateTime);
        return serviceOrderService.list(queryWrapper);
    }

    @Operation(summary = "取消订单")
    @PostMapping("/cancel/{id}")
    public boolean cancelOrder(@PathVariable Long id) {
        ServiceOrder order = serviceOrderService.getById(id);
        if (order != null && "PENDING".equals(order.getStatus())) {
            order.setStatus("CANCELLED");
            order.setUpdateTime(LocalDateTime.now());
            return serviceOrderService.updateById(order);
        }
        return false;
    }

    // --- 管理员接口 ---

    @Operation(summary = "分页查询所有订单(管理员)")
    @GetMapping("/admin/page")
    public Page<ServiceOrder> getAdminOrders(@RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(required = false) String status) {
        LambdaQueryWrapper<ServiceOrder> queryWrapper = new LambdaQueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(ServiceOrder::getStatus, status);
        }
        queryWrapper.orderByDesc(ServiceOrder::getCreateTime);
        return serviceOrderService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Operation(summary = "更新订单状态(管理员)")
    @PostMapping("/admin/updateStatus")
    public boolean updateOrderStatus(@RequestParam Long id, @RequestParam String status) {
        ServiceOrder order = serviceOrderService.getById(id);
        if (order != null) {
            order.setStatus(status);
            order.setUpdateTime(LocalDateTime.now());
            return serviceOrderService.updateById(order);
        }
        return false;
    }

    @Operation(summary = "获取订单总数(内部调用)")
    @GetMapping("/count")
    public Long getServiceOrderCount() {
        return serviceOrderService.count();
    }
}
