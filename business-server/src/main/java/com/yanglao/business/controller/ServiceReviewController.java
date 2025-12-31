package com.yanglao.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanglao.business.entity.ServiceReview;
import com.yanglao.business.service.ServiceReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/service/review")
@Tag(name = "服务评价管理")
public class ServiceReviewController {

    @Autowired
    private ServiceReviewService serviceReviewService;

    @Operation(summary = "提交评价")
    @PostMapping("/add")
    public boolean add(@RequestBody ServiceReview review) {
        review.setCreateTime(LocalDateTime.now());
        return serviceReviewService.save(review);
    }

    @Operation(summary = "获取评价列表")
    @GetMapping("/list")
    public Page<ServiceReview> list(@RequestParam(defaultValue = "1") int pageNum,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(required = false) Long serviceId) {
        LambdaQueryWrapper<ServiceReview> wrapper = new LambdaQueryWrapper<>();
        // 如果需要根据服务ID筛选评价，可能需要关联查询，这里暂时只查所有或根据用户查
        // 实际业务中通常是查看某个服务的评价
        wrapper.orderByDesc(ServiceReview::getCreateTime);
        return serviceReviewService.page(new Page<>(pageNum, pageSize), wrapper);
    }
    
    @Operation(summary = "根据订单ID查询评价")
    @GetMapping("/getByOrder/{orderId}")
    public ServiceReview getByOrder(@PathVariable Long orderId) {
        return serviceReviewService.getOne(new LambdaQueryWrapper<ServiceReview>()
                .eq(ServiceReview::getOrderId, orderId));
    }
}
