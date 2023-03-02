package org.sky.framework.security.handle;

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
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //step 1. 根据token获取user

        //step 2. 清除用户登陆

        //返回数据
        //代码示例
        //response.getWriter().println("{\"code\" : 200}");
    }
}
