package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 Setter: {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;

        // 2026-10-27T16:44:01.987+09:00  INFO 10871 --- [aop] [    Test worker] hello.aop.internalcall.CallServiceV1     : callServiceV1 Setter: class hello.aop.internalcall.CallServiceV1$$SpringCGLIB$$0
    }

    public void external() {
        log.info("call external method");
        callServiceV1.internal();
    }

    public void internal() {
        log.info("call internal method");
    }
}
