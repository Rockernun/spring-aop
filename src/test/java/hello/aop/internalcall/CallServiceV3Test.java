package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({CallLogAspect.class})
@Slf4j
@SpringBootTest
class CallServiceV3Test {

    @Autowired
    CallServiceV3 callServiceV3;

    @Test
    void external() {
        callServiceV3.external();

        /**
         * 2026-10-27T17:01:03.185+09:00  INFO 15210 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV3.external()
         * 2026-10-27T17:01:03.185+09:00  INFO 15210 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV3     : call external method
         * 2026-10-27T17:01:03.185+09:00  INFO 15210 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.InternalService.internal()
         * 2026-10-27T17:01:03.185+09:00  INFO 15210 --- [aop] [    Test worker] hello.aop.internalcall.InternalService   : call internal method
         */
    }
}