package org.sky.framework.security.service;

import org.sky.framework.api.common.domain.SysUser;
import org.sky.framework.config.jwt.JwtConfig;
import org.sky.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     *
     * @param username
     * @param password
     * @return token
     */
    public String login(String username, String password) {
        Authentication authentication = null;

        try {
            //构建用户密码token凭证
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            AuthenticationContextHolder.setContext(usernamePasswordAuthenticationToken);
            //登陆(会去调用UserDetailsServiceImpl的loadUserByUsername方法)
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e){
            throw e;
        }finally {
            AuthenticationContextHolder.clearContext();
        }

        //拿到登陆后的用户
        SysUser loginUser = (SysUser) authentication.getPrincipal();

        //生成token
        return jwtConfig.createToken(loginUser.getUsername());
    }
}
