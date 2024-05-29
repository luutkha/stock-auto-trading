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

    @Pointcut("within(trading.stock.stocktrading.facades.*)")
    public void pointCutCrossFacade() {
    }

    @Pointcut("within(trading.stock.stocktrading.services.*)")
    public void pointCutCrossService() {
    }
    
    @Before("pointCutCrossController() || pointCutCrossService() || pointCutCrossFacade()")
    public void startMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringTypeName());
        String methodName = signature.getMethod().getName();
        logHeaderConfig(logger);
        logger.info("START EXE METHOD: {}", methodName);
        removeHeaderConfigOfThreadContext();
    }

    @After("pointCutCrossController() || pointCutCrossService() || pointCutCrossFacade()")
    public void endMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        logHeaderConfig(log);

        log.info("END EXE METHOD: {}", methodName);

        removeHeaderConfigOfThreadContext();
    }

    @AfterThrowing(value = "pointCutCrossController()", throwing = "ex")
    public void throwException(JoinPoint joinPoint, Throwable ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringTypeName());
        String methodName = signature.getMethod().getName();

        logHeaderConfig(logger);

        logger.info("Exception: {} {}", methodName, ex);

        removeHeaderConfigOfThreadContext();
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

    private void logHeaderConfig(Logger logger) {
        ThreadContext.put("userId", request.getHeader("USER_ID"));
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("session", request.getSession().getId());
        logger.info("ipAddress  {}", ThreadContext.get("ipAddress"));
        logger.info("userId  {}", ThreadContext.get("userId"));
        logger.info("session  {}", ThreadContext.get("session"));
    }

    private static void removeHeaderConfigOfThreadContext() {
        ThreadContext.remove("userId");
        ThreadContext.remove("ipAddress");
        ThreadContext.remove("session");
    }

//    @Before("execution(* trading.stock.stocktrading.services.StockService.getStockDetailByCodeAndTime(String, Long, Long))")
//    public void logBefore() {
//        log.error("Executing log Before advice on getStockDetailByCodeAndTime()");
//    }
}
