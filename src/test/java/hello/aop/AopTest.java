package hello.aop;

import hello.aop.order.aop.AspectV5Order;
import hello.aop.order.repository.OrderRepository;
import hello.aop.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class})
public class AopTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));

        /**
         * 2026-10-21T11:06:37.195+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderService=true
         * 2026-10-21T11:06:37.195+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderRepository=true
         */
    }

    @Test
    void success() {
        orderService.orderItem("itemA");

        /**
         * 2026-10-21T11:06:37.186+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [트랜잭션 시작], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T11:06:37.186+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [log] void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T11:06:37.186+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T11:06:37.187+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [log] String hello.aop.order.repository.OrderRepository.save(String)
         * 2026-10-21T11:06:37.187+09:00  INFO 64045 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         * 2026-10-21T11:06:37.187+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [트랜잭션 커밋], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T11:06:37.187+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [리소스 릴리즈], void hello.aop.order.service.OrderService.orderItem(String)
         */
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);

        /**
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [트랜잭션 시작], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [log] void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [log] String hello.aop.order.repository.OrderRepository.save(String)
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [트랜잭션 롤백], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T11:06:37.210+09:00  INFO 64045 --- [aop] [    Test worker] hello.aop.order.aop.AspectV5Order        : [리소스 릴리즈], void hello.aop.order.service.OrderService.orderItem(String)
         */
    }
}
