package trading.stock.stocktrading.configs.aspects;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
@Log4j2
@AllArgsConstructor
public class CacheAspect {
    private final RedisTemplate<String, String> redisTemplate;

    @Around("execution(* trading.stock.stocktrading.repositories.redis.*.*(..))")
    public Object logBeforeCallCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();

        // Extract and print the return type
        Class<?> returnType = signature.getDeclaringType().getMethod(signature.getName(),
                ((MethodSignature) signature).getParameterTypes()).getReturnType();
        System.out.println("Return type of method " + signature.getName() + ": " + returnType);

        try {
            // Check if Redis is reachable
            redisTemplate.opsForValue().get("ping");
            return joinPoint.proceed();
        } catch (Exception e) {
            if (List.class.isAssignableFrom(returnType)) {
                System.out.println("Method " + signature.getName() + " returns a List");
                return new ArrayList<>();
            }
            if (returnType.equals(Boolean.class) || returnType.equals(boolean.class)) {
                System.out.println("Method " + signature.getName() + " returns a Boolean");
                return false;
            }
            if (returnType.equals(Optional.class)) {
                System.out.println("Method " + signature.getName() + " returns a Optional");
                return Optional.empty();
            }
            log.error("[REDIS SERVER DOWN] return null for all redis method");
            return null;
        }

    }
}
