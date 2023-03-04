package org.sky.framework.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sky.framework.api.common.result.AjaxResult;
import org.sky.framework.config.jwt.JwtConfig;
import org.sky.framework.config.web.properties.TokenProperties;
import org.sky.framework.security.service.SysUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangcong
 *
 * 退出登陆处理类
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //step 1. 根据token获取user
        String token = request.getHeader(this.tokenProperties.getHeader());

        //step 2. 清除用户登陆
        jwtConfig.clearToken(token);

        //step 3. 清除redis
        this.redisTemplate.delete(token);

        //返回数据
        //代码示例
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        new AjaxResult(200, null, "登出成功")
                )
        );
    }
}
