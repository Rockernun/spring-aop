package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({CallLogAspect.class})
@Slf4j
@SpringBootTest
class CallServiceV1Test {

    @Autowired
    CallServiceV1 callServiceV1;

    @Test
    void external() {
        callServiceV1.external();

        /**
         * 2026-10-27T16:44:02.316+09:00  INFO 10871 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV1.external()
         * 2026-10-27T16:44:02.317+09:00  INFO 10871 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV1     : call external method
         * 2026-10-27T16:44:02.317+09:00  INFO 10871 --- [aop] [    Test worker] hello.aop.internalcall.CallLogAspect     : aop=void hello.aop.internalcall.CallServiceV1.internal()
         * 2026-10-27T16:44:02.317+09:00  INFO 10871 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV1     : call internal method
         */
    }
}