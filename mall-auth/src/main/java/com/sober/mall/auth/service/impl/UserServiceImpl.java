package com.sober.mall.auth.service.impl;

import com.sober.mall.auth.service.UmsAdminService;
import com.sober.mall.auth.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
