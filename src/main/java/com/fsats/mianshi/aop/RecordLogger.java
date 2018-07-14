package com.fsats.mianshi.aop;


import com.fsats.mianshi.annotation.LoggsType;
import com.fsats.mianshi.entity.RecordLog;
import com.fsats.mianshi.entity.Users;
import com.fsats.mianshi.service.RecordLogService;
import org.apache.log4j.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 切面类，记录CURD操作日志
 */
@Aspect
@Component
public class RecordLogger {
    private static final Logger logger = Logger.getLogger(RecordLogger.class);

    @Autowired
    private RecordLogService recordLogService;

    /**
     * 织入点，不包含RecordService的其他方法
     */
    @Pointcut("execution(* com.fsats.mianshi.controller.*.*(..))")
    public void pointuct(){}



    @Around("pointuct()")
    private Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        RecordLog recordLog = new RecordLog();

        //获得操作者id
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Users users = (Users)request.getSession().getAttribute("user");
        if (null==users){
            recordLog.setUserId(null);
        }else{
            recordLog.setUserId(users.getId());
        }
        //获得执行方法名称
        String methodName=joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获得注解值
        Class[] parameterType = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(),parameterType);
        if(null!=method){
            if (method.isAnnotationPresent(LoggsType.class)){
                String typename = method.getAnnotation(LoggsType.class).type().getName();
                /*判断是否为OTHER的方法，则不进行添加日志*/
                if(typename.equals("OTHER")){
                    result = joinPoint.proceed();
                    return result;
                }else{
                    recordLog.setType(method.getAnnotation(LoggsType.class).type().getName());
                }
            }else{
                recordLog.setType(null);
            }
        }else{
            recordLog.setType(null);
        }
        /*if(null!=method){
            if (method.isAnnotationPresent(LoggsType.class)){
                recordLog.setType(method.getAnnotation(LoggsType.class).type().getName());
            }else{
                recordLog.setType("");
            }
        }else{
            recordLog.setType("");
        }*/

        recordLog.setRecordDate(new Date());
        recordLog.setIPAddress(request.getRemoteAddr());
        recordLog.setMethodName(methodName);

        try {
            logger.info(joinPoint.getSignature().getName() + "方法前置通知执行");
            result = joinPoint.proceed();
            logger.info(joinPoint.getSignature().getName() + "方法后置通知执行");

        } catch (Throwable throwable) {
            recordLog.setErrorCode(System.currentTimeMillis()+"");
            recordLog.setErrorMessage(throwable.getMessage());

            logger.info(joinPoint.getSignature().getName() + "方法异常通知执行");
        }finally {
            logger.info(joinPoint.getSignature().getName()+"结束执行");
            recordLogService.addLogger(recordLog);

        }

        return result;
    }

}
