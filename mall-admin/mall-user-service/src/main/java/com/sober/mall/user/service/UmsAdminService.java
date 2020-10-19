package com.sober.mall.user.service;

import com.sober.common.api.CommonResult;
import com.sober.common.domain.UserDto;
import com.sober.mall.user.dto.UmsAdminParam;
import com.sober.mall.user.model.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author sober
 * @since 2020-10-16
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 调用认证中心返回结果
     */
    CommonResult login(String username, String password);

    UserDto loadUserByUsername(String username);

}
