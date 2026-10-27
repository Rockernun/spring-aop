package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDiAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({ProxyDiAspect.class})
// @SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})
public class ProxyDiTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");

        /**
         * 2026-10-27T17:48:45.256+09:00  INFO 26045 --- [aop] [    Test worker] hello.aop.proxyvs.ProxyDiTest            : memberService class=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
         * 2026-10-27T17:48:45.256+09:00  INFO 26045 --- [aop] [    Test worker] hello.aop.proxyvs.ProxyDiTest            : memberServiceImpl class=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
         * 2026-10-27T17:48:45.257+09:00  INFO 26045 --- [aop] [    Test worker] hello.aop.proxyvs.code.ProxyDiAspect     : [proxyDIAdvice] String hello.aop.member.MemberServiceImpl.hello(String)
         */
    }
}
