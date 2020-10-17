package com.sober.mall.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sober.common.api.CommonResult;
import com.sober.common.api.ResultCode;
import com.sober.common.constant.AuthConstant;
import com.sober.common.exception.Asserts;
import com.sober.mall.user.dto.UmsAdminParam;
import com.sober.mall.user.model.UmsAdmin;
import com.sober.mall.user.mapper.UmsAdminMapper;
import com.sober.mall.user.model.UmsAdminLoginLog;
import com.sober.mall.user.service.AuthService;
import com.sober.mall.user.service.UmsAdminLoginLogService;
import com.sober.mall.user.service.UmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author sober
 * @since 2020-10-16
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Resource
    private UmsAdminMapper umsAdminMapper;
    @Resource
    private AuthService authService;
    @Resource
    private UmsAdminLoginLogService umsAdminLoginLogService;

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {

        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(LocalDateTime.now());
        umsAdmin.setStatus(1);

        // 查询是否有相同用户名的用户
        QueryWrapper<UmsAdmin> condition = new QueryWrapper<>();
        condition.eq("username", umsAdmin.getUsername());
        if (umsAdminMapper.selectCount(condition) > 0) {
            return null;
        }

        //将密码进行加密操作
        String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public CommonResult login(String username, String password) {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            Asserts.fail("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", "123456");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        CommonResult restResult = authService.getAccessToken(params);
        if (ResultCode.SUCCESS.getCode() == restResult.getCode() && restResult.getData() != null) {
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        }
        return restResult;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (ObjectUtil.isNull(admin)) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNotNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            loginLog.setIp(request.getRemoteAddr());
            umsAdminLoginLogService.save(loginLog);
        }
    }

    private UmsAdmin getAdminByUsername(String username) {

        // 查询是否有相同用户名的用户
        QueryWrapper<UmsAdmin> condition = new QueryWrapper<>();
        condition.eq("username", username);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectList(condition);
        if (CollectionUtil.isNotEmpty(umsAdminList)) {
            return umsAdminList.get(0);
        }
        return null;
    }


//    public static void main(String[] args) {
//        System.out.println(LocalDateTime.now());
//    }

}
