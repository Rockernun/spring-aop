package hello.aop;

import hello.aop.order.aop.AspectV6Advice;
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
@Import(AspectV6Advice.class)
public class AopTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));

        /**
         * 2026-10-21T13:29:31.060+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderService=true
         * 2026-10-21T13:29:31.060+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.AopTest                        : isAopProxy, orderRepository=false
         */
    }

    @Test
    void success() {
        orderService.orderItem("itemA");

        /**
         * 2026-10-21T13:29:31.055+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.aop.AspectV6Advice       : [before], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T13:29:31.055+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T13:29:31.055+09:00  INFO 79637 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         * 2026-10-21T13:29:31.055+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.aop.AspectV6Advice       : [return] void hello.aop.order.service.OrderService.orderItem(String) return=null
         * 2026-10-21T13:29:31.055+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.aop.AspectV6Advice       : [after] void hello.aop.order.service.OrderService.orderItem(String)
         */
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);

        /**
         * 2026-10-21T13:29:31.074+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.aop.AspectV6Advice       : [before], void hello.aop.order.service.OrderService.orderItem(String)
         * 2026-10-21T13:29:31.074+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.service.OrderService     : [orderService] 실행
         * 2026-10-21T13:29:31.075+09:00  INFO 79637 --- [aop] [    Test worker] h.aop.order.repository.OrderRepository   : [orderRepository] 실행
         * 2026-10-21T13:29:31.075+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.aop.AspectV6Advice       : [ex] {} message={}
         *
         * java.lang.IllegalStateException: 예외 발생!
         * 	at hello.aop.order.repository.OrderRepository.save(OrderRepository.java:15) ~[main/:na]
         * 	at hello.aop.order.service.OrderService.orderItem(OrderService.java:18) ~[main/:na]
         * 	...
         * 2026-10-21T13:29:31.080+09:00  INFO 79637 --- [aop] [    Test worker] hello.aop.order.aop.AspectV6Advice       : [after] void hello.aop.order.service.OrderService.orderItem(String)
         */
    }
}
