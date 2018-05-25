package com.sppan.base.common.log;

import com.sppan.base.common.annotation.LogAnnotation;
import com.sppan.base.entity.Log;
import com.sppan.base.entity.User;
import com.sppan.base.service.ILogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class LogAopAction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.sppan.base.common.annotation.LogAnnotation)")
    private void pointCutMethod(){}

    /**
     * 记录操作日志
     */
    @After("pointCutMethod()")  // 使用上面定义的切入点
    public void recordLog(JoinPoint joinPoint){
        Long start = System.currentTimeMillis();
        Log log = new Log();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.warn("user 信息为空");
        }else{
            log.setUser(user);
        }

        //下面开始获取 ip，targetType，remark，action
        try {
            Map<String,String> map = getLogMark(joinPoint);
            log.setAction(map.get(LoggerUtil.LOG_ACTION));
            log.setTargetType(map.get(LoggerUtil.LOG_TARGET_TYPE));
            log.setRemark(map.get(LoggerUtil.LOG_REMARK));
            log.setIp(LoggerUtil.getCliectIp(request));

            //删除，修改，关联，下载会用到id
            String id = request.getParameter("id");

            log.setCreateTime(new Date());
            logService.save(log);
        }catch (ClassNotFoundException c){
            logger.error(c.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("插入日志异常",e.getMessage());
        }
        Long end = System.currentTimeMillis();
        logger.info("记录日志消耗时间:"+ (end - start) / 1000);
    }

    private Map<String,String> getLogMark(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String,String> map = new HashMap<>();
        String methodName = joinPoint.getSignature().getName();
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods){
            if(method.getName().equals(methodName)){
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                map.put(LoggerUtil.LOG_TARGET_TYPE,logAnnotation.targetType());
                map.put(LoggerUtil.LOG_ACTION,logAnnotation.action());
                map.put(LoggerUtil.LOG_REMARK,logAnnotation.remark());
            }
        }
        return map;
    }
}
