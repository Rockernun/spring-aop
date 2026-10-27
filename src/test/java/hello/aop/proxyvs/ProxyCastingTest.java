package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);  // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 가능
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // MemberServiceImpl로 캐스팅이 가능한가? 불가능하다...
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });

        /**
         * class jdk.proxy3.$Proxy29 cannot be cast to class hello.aop.member.MemberServiceImpl (jdk.proxy3.$Proxy29 is in module jdk.proxy3 of loader 'app'; hello.aop.member.MemberServiceImpl is in unnamed module of loader 'app')
         * java.lang.ClassCastException: class jdk.proxy3.$Proxy29 cannot be cast to class hello.aop.member.MemberServiceImpl (jdk.proxy3.$Proxy29 is in module jdk.proxy3 of loader 'app'; hello.aop.member.MemberServiceImpl is in unnamed module of loader 'app')
         * 	at hello.aop.proxyvs.ProxyCastingTest.jdkProxy(ProxyCastingTest.java:22)
         */
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);  // CGLIB 프록시

        // 프록시를 인터페이스로 캐스팅 가능
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // MemberServiceImpl로 캐스팅이 가능한가? 가능하다!
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
