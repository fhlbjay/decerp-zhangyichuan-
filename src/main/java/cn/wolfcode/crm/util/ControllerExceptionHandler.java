package cn.wolfcode.crm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/20.
 */

/**
 * ControllerAdvice:该类是对Controller的增强
 * 所有Controller都是有效的
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 出现没有权限的异常,就做以下的处理
     */
    @ExceptionHandler(UnauthorizedException.class)
    public void exceptionHandler(HttpServletResponse response, HandlerMethod handlerMethod) throws IOException {
        ResponseBody annotation = handlerMethod.getMethod().getAnnotation(ResponseBody.class);
        if(annotation!=null){
            //是ajax请求,就应该返回jsonresult
            response.getWriter().print(new ObjectMapper()
                    .writeValueAsString(new JsonResult("没有权限操作!")));
        }else {
            // 否则就重定向到没有权限的页面
            response.sendRedirect("/nopermission.jsp");
        }
    }
}
