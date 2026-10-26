package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.pointcut.AtAnnotationTest.AtAnnotationAspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AtAnnotationTest.AtAnnotationAspect.class})
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");

        /**
         * 2026-10-26T12:53:11.173+09:00  INFO 41229 --- [aop] [    Test worker] hello.aop.pointcut.AtAnnotationTest      : memberService Proxy=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
         * 2026-10-26T12:53:11.175+09:00  INFO 41229 --- [aop] [    Test worker] .a.p.AtAnnotationTest$AtAnnotationAspect : [@annotation] String hello.aop.member.MemberServiceImpl.hello(String)
         */
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {
        @Around("@annotation(hello.aop.member.annotation.MethodAop)")
        public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
