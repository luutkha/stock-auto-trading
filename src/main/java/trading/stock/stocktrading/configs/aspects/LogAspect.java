package trading.stock.stocktrading.configs.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@AllArgsConstructor
public class LogAspect {

    private final HttpServletRequest request;

    private static void removeHeaderConfigOfThreadContext() {
        ThreadContext.remove("userId");
        ThreadContext.remove("ipAddress");
        ThreadContext.remove("session");
    }

    @Pointcut("within(trading.stock.stocktrading.controllers.*)")
    public void pointCutCrossController() {
    }

    @Pointcut("within(trading.stock.stocktrading.facades.*)")
    public void pointCutCrossFacade() {
    }

    @Pointcut("within(trading.stock.stocktrading.services.impl.*)")
    public void pointCutCrossService() {
    }

    @Pointcut("within(trading.stock.stocktrading.repositories.UserRepository)")
    public void pointCutCrossRepository() {
    }

    @Pointcut("within(trading.stock.stocktrading.utils.*)")
    public void pointCutCrossUtils() {
    }

    @Before("pointCutCrossController() || pointCutCrossService() || pointCutCrossFacade() || pointCutCrossUtils()")
    public void startMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringTypeName());
        String methodName = signature.getMethod().getName();
        logHeaderConfig(logger);
        logger.info("START EXE METHOD: {}", methodName);
        removeHeaderConfigOfThreadContext();
    }

    @After("pointCutCrossController() || pointCutCrossService() || pointCutCrossFacade() || pointCutCrossUtils()")
    public void endMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        logHeaderConfig(log);

        log.info("END EXE METHOD: {}", methodName);

        removeHeaderConfigOfThreadContext();
    }

    @AfterThrowing(value = "pointCutCrossController() || pointCutCrossService() || pointCutCrossFacade() || pointCutCrossUtils()", throwing = "ex")
    public void throwException(JoinPoint joinPoint, Throwable ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringTypeName());
        String methodName = signature.getMethod().getName();

        logHeaderConfig(logger);

        logger.error("Exception: {} {}", methodName, ex);

        removeHeaderConfigOfThreadContext();
    }

    @Around(value = "pointCutCrossController() || pointCutCrossService() || pointCutCrossFacade()||pointCutCrossRepository()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.debug("{} executed in {} ms", joinPoint.getSignature().getName(), executionTime);

        return proceed;
    }

    private void logHeaderConfig(Logger logger) {
//        ThreadContext.put("userId", request.getHeader("USER_ID"));
//        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("session", request.getSession().getId());
//        logger.info("ipAddress  {}", ThreadContext.get("ipAddress"));
//        logger.info("userId  {}", ThreadContext.get("userId"));
        logger.info("session  {}", () -> ThreadContext.get("session"));
    }

    @Before("execution(* trading.stock.stocktrading.services.StockService.getStockDetailByCodeAndTime(String, Long, Long))")
    public void logBefore() {
        log.debug("Executing log Before advice on getStockDetailByCodeAndTime()");
    }

}
