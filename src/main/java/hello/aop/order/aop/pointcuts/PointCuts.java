package hello.aop.order.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;


public class PointCuts {

    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {}

    @Pointcut("execution(* hello.aop.order.service..*(..))")
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
