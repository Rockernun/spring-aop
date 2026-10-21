package hello.aop;

import hello.aop.order.aop.AspectV1;
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
@Import(AspectV1.class)
public class AopTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));

        /**
         * 2026-10-21T10:28:29.120+09:00  INFO 54356 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderService=true
         * 2026-10-21T10:28:29.120+09:00  INFO 54356 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderRepository=true
         */
    }

    @Test
    void success() {
        orderService.orderItem("itemA");

        /**
         * 2026-10-21T10:29:10.329+09:00  INFO 54508 --- [aop] [    Test worker] hello.aop.order.aop.AspectV1             : [log] void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T10:29:10.329+09:00  INFO 54508 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T10:29:10.329+09:00  INFO 54508 --- [aop] [    Test worker] hello.aop.order.aop.AspectV1             : [log] String hello.aop.order.repository.OrderRepository.save(String)
         * 2026-10-21T10:29:10.329+09:00  INFO 54508 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         */
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}
