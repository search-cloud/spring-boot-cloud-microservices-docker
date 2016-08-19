//package org.asion.sample.common;
//
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StopWatch;
//
///**
// * 方法拦截记录执行时间
// * @author Asion.
// * @since 16/6/1.
// */
//@Aspect
//public class MethodTimeAdvice {
//
//    public static Logger logger = LoggerFactory.getLogger(MethodTimeAdvice.class);
//
//    /**
//     * 拦截要执行的目标方法
//     */
//    @Around("execution(* com.ytx.seller.workspace.item.ItemImportController.*(..))")
//    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
//        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch
//        StopWatch clock = new StopWatch();
//        clock.start(); //计时开始
//        Object result = pjp.proceed();
//        clock.stop();  //计时结束
//
//        //方法参数类型，转换成简单类型
//        Object[] params = pjp.getArgs();
//        logger.info("Method cost: " + clock.getTotalTimeMillis() + " ms ["
//                + pjp.getSignature().getDeclaringType().getSimpleName() + "."
//                + pjp.getSignature().getName() + "("+ StringUtils.join(params,",")+")] ");
//        return result;
//    }
//}
