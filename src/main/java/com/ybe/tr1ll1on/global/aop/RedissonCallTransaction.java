package com.ybe.tr1ll1on.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RedissonCallTransaction {
    /**
     * 부모트랜잭션의 유무와 관계없이 동시성에 대한 처리는 별도의 트랜잭션으로 동작하기 위함
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    /*
    여기서 Propagation.REQUIRES_NEW는 트랜잭션 전파(propagation) 옵션 중 하나.
    메서드가 항상 새로운 트랜잭션 내에서 실행되어야 함을 의미한다.
    이 어노테이션이 적용된 메서드가 호출될 때마다 새로운 트랜잭션이 시작된다.
    이미 진행 중인 다른 트랜잭션이 있더라도 무시하고 새로운 트랜잭션을 시작한다.

    이것은 메서드를 호출할 때마다 독립적인 트랜잭션을 보장하고,
    내부적으로 이전 트랜잭션과는 독립적으로 실행되어야 할 때 유용하다.
    이를 통해 각 메서드가 독립적인 트랜잭션 처리를 보장하며,
    하나의 트랜잭션에 대한 영향을 받지 않고 실행될 수 있다.
     */
}
