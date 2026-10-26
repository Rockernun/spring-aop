package hello.aop.pointcut;

import hello.aop.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({BeanTest.BeanAspect.class})
@SpringBootTest
public class BeanTest {

    @Autowired
    OrderService orderService;

    @Test
    void success() {
        orderService.orderItem("itemA");

        /**
         * 2026-10-26T13:00:06.526+09:00  INFO 42971 --- [aop] [    Test worker] hello.aop.pointcut.BeanTest              : [bean] void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-26T13:00:06.527+09:00  INFO 42971 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-26T13:00:06.527+09:00  INFO 42971 --- [aop] [    Test worker] hello.aop.pointcut.BeanTest              : [bean] String hello.aop.order.repository.OrderRepository.save(String)
         * 2026-10-26T13:00:06.527+09:00  INFO 42971 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         */
    }

    @Aspect
    static class BeanAspect {
        @Around("bean(orderService) || bean(*Repository)")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[bean] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
