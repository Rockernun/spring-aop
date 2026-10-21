package hello.aop;

import hello.aop.order.aop.AspectV3;
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
@Import(AspectV3.class)
public class AopTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));

        /**
         * 2026-10-21T10:45:46.118+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderService=true
         * 2026-10-21T10:45:46.119+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderRepository=true
         */
    }

    @Test
    void success() {
        orderService.orderItem("itemA");

        /**
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [log] void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [트랜잭션 시작], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [log] String hello.aop.order.repository.OrderRepository.save(String)
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [트랜잭션 커밋], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:45:46.112+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [리소스 릴리즈], void hello.aop.order.service.OrderService.orderItem(String)
         */
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);

        /**
         * 2026-10-21T10:45:46.132+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [log] void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:45:46.132+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [트랜잭션 시작], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:45:46.133+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T10:45:46.133+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [log] String hello.aop.order.repository.OrderRepository.save(String)
         * 2026-10-21T10:45:46.133+09:00  INFO 59073 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         * 2026-10-21T10:45:46.133+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [트랜잭션 롤백], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:45:46.133+09:00  INFO 59073 --- [aop] [    Test worker] hello.aop.order.aop.AspectV3             : [리소스 릴리즈], void hello.aop.order.service.OrderService.orderItem(String)
         */
    }
}
