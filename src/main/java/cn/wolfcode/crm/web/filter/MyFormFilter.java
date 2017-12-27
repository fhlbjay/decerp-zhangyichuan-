package cn.wolfcode.crm.web.filter;

import cn.wolfcode.crm.util.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class MyFormFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse)response;
        //返回JsonResult
        resp.getWriter().print(new ObjectMapper().writeValueAsString(new JsonResult()));
        //不再实行后面的过滤器,所以也不会来到我们得/login.do
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse)response;
        String errorMsg = "登录失败,请联系管理员!";
        if(e instanceof UnknownAccountException){
            errorMsg =  "用户名不存在!";
        }else if(e instanceof IncorrectCredentialsException){
            errorMsg =  "密码错误!";
        }
        //返回JsonResult数据
        try {
            resp.getWriter().print(new ObjectMapper().writeValueAsString(new JsonResult(errorMsg)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        //把异常信息输出
        e.printStackTrace();
        //不再实行后面的过滤器,所以也不会来到我们得/login.do
        return false;
    }
    /**
     * 解决不能重复登录的问题
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //该方法是所有url路径都经过的
        //如果是登录请求/login.do,才注销之前登录过的subject
        if(this.isLoginRequest(request, response)) {
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                subject.logout();
            }
        }
        return super.isAccessAllowed(request,response,mappedValue);
    }
}
