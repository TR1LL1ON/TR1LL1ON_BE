package com.ybe.tr1ll1on.global.aop;

import static com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode.PRODUCT_SOLD_OUT;

import com.ybe.tr1ll1on.domain.order.exception.OrderException;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import java.lang.reflect.Method;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class RedissonLockAspect {

    private final RedissonClient redissonClient;
    private final RedissonCallTransaction redissonCallTransaction;

    @Around("@annotation(com.ybe.tr1ll1on.global.aop.RedissonLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        /* create key */
        String key = this.createKey(signature.getParameterNames(), joinPoint.getArgs(), redissonLock.key());

        /* get rLock 객체 */
        RLock rLock = redissonClient.getLock(key);

        try {
            /* get lock */
            boolean isPossible = rLock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit());
            if (!isPossible) {
                return false;
            }

            log.info("Redisson Lock Key : {}", key);

            /* service call */
            return redissonCallTransaction.proceed(joinPoint);
        } catch (OrderException e) {
            throw new OrderException(e.getErrorCode());
        } catch (Exception e) {
            throw new InterruptedException();
        } finally {
            rLock.unlock();
        }
    }

    /**
     * Redisson Key Create
     * @param parameterNames
     * @param args
     * @param key
     * @return
     */
    private String createKey(String[] parameterNames, Object[] args, String key) {
        String resultKey = key;

        /* key = parameterName */
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(key)) {
                resultKey += args[i];
                break;
            }
        }

        return resultKey;
    }
}
