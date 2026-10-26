package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({ParameterTest.ParameterAspect.class})
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");

        /**
         * 2026-10-26T13:39:01.988+09:00  INFO 51924 --- [aop] [    Test worker] hello.aop.pointcut.ParameterTest         : memberService Proxy=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
         * 2026-10-26T13:39:01.989+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [logArgs1]String hello.aop.member.MemberServiceImpl.hello(String) arg=helloA
         * 2026-10-26T13:39:01.990+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [logArgs2]String hello.aop.member.MemberServiceImpl.hello(String) arg=helloA
         * 2026-10-26T13:39:01.990+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [@annotation]String hello.aop.member.MemberServiceImpl.hello(String) annotationValue=test value
         * 2026-10-26T13:39:01.991+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [@target]String hello.aop.member.MemberServiceImpl.hello(String) obj=class jdk.proxy3.$Proxy58
         * 2026-10-26T13:39:01.991+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [@within]String hello.aop.member.MemberServiceImpl.hello(String) obj=class jdk.proxy3.$Proxy58
         * 2026-10-26T13:39:01.992+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [logArgs3] arg=helloA
         * 2026-10-26T13:39:01.992+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [target]String hello.aop.member.MemberServiceImpl.hello(String) obj=class hello.aop.member.MemberServiceImpl
         * 2026-10-26T13:39:01.992+09:00  INFO 51924 --- [aop] [    Test worker] h.a.p.ParameterTest$ParameterAspect      : [this]String hello.aop.member.MemberServiceImpl.hello(String) obj=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
         */
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{} arg={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{} arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{} obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{} obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{} obj={}", joinPoint.getSignature(), annotation.getClass());
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{} obj={}", joinPoint.getSignature(), annotation.getClass());
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{} annotationValue={}", joinPoint.getSignature(), annotation.value());
        }
    }
}
