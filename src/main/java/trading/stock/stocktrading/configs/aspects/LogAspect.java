package trading.stock.stocktrading.configs.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LogAspect {

    @Autowired
    HttpServletRequest request;

    @Pointcut("within(trading.stock.stocktrading.controllers.*)")
    public void pointCutCrossController() {
    }


    @Before("pointCutCrossController()")
    public void startMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringTypeName());
        String methodName = signature.getMethod().getName();
        ThreadContext.put("userId", request.getHeader("USER_ID"));
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("session", request.getSession().getId());
        logger.info("ipAddress  {}", ThreadContext.get("ipAddress"));
        logger.info("userId  {}", ThreadContext.get("userId"));
        logger.info("session  {}", ThreadContext.get("session"));
        logger.info("START EXE METHOD: {}", methodName);
        ThreadContext.remove("userId");
        ThreadContext.remove("ipAddress");
        ThreadContext.remove("session");
    }

    @After("pointCutCrossController()")
    public void endMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        ThreadContext.put("userId", request.getHeader("USER_ID"));
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("session", request.getSession().getId());
        log.info("ipAddress  {}", ThreadContext.get("ipAddress"));
        log.info("userId  {}", ThreadContext.get("userId"));
        log.info("session  {}", ThreadContext.get("session"));

        log.info("END EXE METHOD: {}", methodName);

        ThreadContext.remove("userId");
        ThreadContext.remove("ipAddress");
        ThreadContext.remove("session");
    }

    @AfterThrowing(value = "pointCutCrossController()", throwing = "ex")
    public void throwException(JoinPoint joinPoint, Throwable ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringTypeName());
        String methodName = signature.getMethod().getName();

        ThreadContext.put("userId", request.getHeader("USER_ID"));
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("session", request.getSession().getId());
        logger.info("ipAddress  {}", ThreadContext.get("ipAddress"));
        logger.info("userId  {}", ThreadContext.get("userId"));
        logger.info("session  {}", ThreadContext.get("session"));

        logger.info("Exception: {} {}", methodName, ex);

        ThreadContext.remove("userId");
        ThreadContext.remove("ipAddress");
        ThreadContext.remove("session");
    }

    @Around(value = "pointCutCrossController()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("{} executed in {} ms", joinPoint.getSignature().getName(), executionTime);

        return proceed;
    }
}
