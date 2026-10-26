package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * spring.aop.proxy-target-class=true -> CGLIB 사용
 * spring.aop.proxy-target-class=false -> JDK 동적 프록시 사용
 */

@Slf4j
@Import({ThisTargetTest.ThisTargetAspect.class})
@SpringBootTest(properties = "spring.aop.proxy-target-class=true")
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");

        /**
         * === JDK 동적 프록시일 경우 (spring.aop.proxy-target-class=false) ===
         * 2026-10-26T15:31:51.663+09:00  INFO 59781 --- [aop] [    Test worker] hello.aop.pointcut.ThisTargetTest        : memberService Proxy=class jdk.proxy3.$Proxy69
         * 2026-10-26T15:31:51.665+09:00  INFO 59781 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [target-impl] String hello.aop.member.MemberService.hello(String)
         * 2026-10-26T15:31:51.665+09:00  INFO 59781 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [target-interface] String hello.aop.member.MemberService.hello(String)
         * 2026-10-26T15:31:51.665+09:00  INFO 59781 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [this-interface] String hello.aop.member.MemberService.hello(String)
         *
         * === CGLIB일 경우 (spring.aop.proxy-target-class=true) ===
         * 2026-10-26T15:33:39.774+09:00  INFO 60165 --- [aop] [    Test worker] hello.aop.pointcut.ThisTargetTest        : memberService Proxy=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
         * 2026-10-26T15:33:39.776+09:00  INFO 60165 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [target-impl] String hello.aop.member.MemberServiceImpl.hello(String)
         * 2026-10-26T15:33:39.776+09:00  INFO 60165 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [target-interface] String hello.aop.member.MemberServiceImpl.hello(String)
         * 2026-10-26T15:33:39.777+09:00  INFO 60165 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [this-impl] String hello.aop.member.MemberServiceImpl.hello(String)
         * 2026-10-26T15:33:39.777+09:00  INFO 60165 --- [aop] [    Test worker] h.a.p.ThisTargetTest$ThisTargetAspect    : [this-interface] String hello.aop.member.MemberServiceImpl.hello(String)
         */
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        // 부모 타입 허용
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입 허용
        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThisImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTargetImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
