package com.sober.mall.auth.service.impl;

import com.sober.common.constant.AuthConstant;
import com.sober.common.domain.UserDto;
import com.sober.mall.auth.constant.MessageConstant;
import com.sober.mall.auth.domain.SecurityUser;
import com.sober.mall.auth.service.UmsAdminService;
import com.sober.mall.auth.service.UmsMemberService;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by sober on 2020/10/14
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private UmsAdminService adminService;
    @Resource
    private UmsMemberService memberService;
    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        UserDto userDto;
        if (AuthConstant.ADMIN_CLIENT_ID.equals(clientId)) {
            userDto = adminService.loadUserByUsername(username);
        } else {
            userDto = memberService.loadUserByUsername(username);
        }
        if (userDto == null) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        userDto.setClientId(clientId);
        SecurityUser securityUser = new SecurityUser(userDto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
