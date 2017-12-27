package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.service.ISystemLogService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class SystemLogUtil {
    public SystemLogUtil(){
    }
    @Autowired
    ISystemLogService logService;
    public void writeLog(JoinPoint joinPoint){
        if(joinPoint.getTarget() instanceof ISystemLogService){
            return;
        }
        SystemLog log=new SystemLog();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        /*设置记录时间*/
        log.setOpTime(new Date());
        //设置Ip地址
        log.setOpIp(request.getRemoteAddr());
        //设置操作用户
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal!=null){
            log.setOpUser((Employee)principal);
        }
        String className=joinPoint.getTarget().getClass().getName();
        String methodName=joinPoint.getSignature().getName();
        //设置目标执行的方法
        log.setFunction(className+":"+methodName);
        log.setParams(JSON.toJSONString(joinPoint.getArgs()));
        logService.insert(log);

    }
}
