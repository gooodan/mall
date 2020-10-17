package com.sober.mall.user.controller;


import com.sober.common.api.CommonResult;
import com.sober.mall.user.dto.UmsAdminLoginParam;
import com.sober.mall.user.dto.UmsAdminParam;
import com.sober.mall.user.model.UmsAdmin;
import com.sober.mall.user.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Resource
    private UmsAdminService umsAdminService;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        return umsAdminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
    }
}
