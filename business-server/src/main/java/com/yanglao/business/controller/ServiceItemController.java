package com.yanglao.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanglao.business.entity.ServiceItem;
import com.yanglao.business.service.ServiceItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/item")
@Tag(name = "服务项目管理")
public class ServiceItemController {

    @Autowired
    private ServiceItemService serviceItemService;

    @Operation(summary = "获取所有上架服务")
    @GetMapping("/list")
    public List<ServiceItem> list() {
        return serviceItemService.list(new LambdaQueryWrapper<ServiceItem>()
                .eq(ServiceItem::getStatus, 1));
    }

    @Operation(summary = "分页查询服务")
    @GetMapping("/page")
    public Page<ServiceItem> page(@RequestParam(defaultValue = "1") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        return serviceItemService.page(new Page<>(pageNum, pageSize));
    }

    @Operation(summary = "添加服务")
    @PostMapping("/add")
    public boolean add(@RequestBody ServiceItem serviceItem) {
        return serviceItemService.save(serviceItem);
    }

    @Operation(summary = "修改服务")
    @PostMapping("/update")
    public boolean update(@RequestBody ServiceItem serviceItem) {
        return serviceItemService.updateById(serviceItem);
    }

    @Operation(summary = "删除服务")
    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return serviceItemService.removeById(id);
    }
}
