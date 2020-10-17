package com.sober.mall.user.service;

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

    UmsAdmin register()

}
