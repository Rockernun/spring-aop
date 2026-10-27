package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({CallLogAspect.class})
@Slf4j
@SpringBootTest
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    @Test
    void external() {
        callServiceV2.external();

        /**
         * 2026-10-27T16:52:27.853+09:00  INFO 13206 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV2.external()
         * 2026-10-27T16:52:27.853+09:00  INFO 13206 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV2     : call external method
         * 2026-10-27T16:52:27.853+09:00  INFO 13206 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV2.internal()
         * 2026-10-27T16:52:27.853+09:00  INFO 13206 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV2     : call internal method
         */
    }
}