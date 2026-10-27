package hello.aop.internalcall;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({CallLogAspect.class})
@Slf4j
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void external() {
        log.info("target={}", callServiceV0.getClass());  // callServiceV0는 프록시
        callServiceV0.external();

        /**
         * 2026-10-27T16:12:35.154+09:00  INFO 3819 --- [aop] [    Test worker] h.aop.internalcall.CallServiceV0Test     : target=class hello.aop.internalcall.CallServiceV0$$SpringCGLIB$$0
         * 2026-10-27T16:12:35.155+09:00  INFO 3819 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV0.external()
         * 2026-10-27T16:12:35.155+09:00  INFO 3819 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV0     : call external method
         * 2026-10-27T16:12:35.155+09:00  INFO 3819 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV0     : call internal method
         */
    }

    @Test
    void internal() {
        callServiceV0.internal();

        /**
         * 2026-10-27T16:12:35.160+09:00  INFO 3819 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV0.internal()
         * 2026-10-27T16:12:35.160+09:00  INFO 3819 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV0     : call internal method
         */
    }
}