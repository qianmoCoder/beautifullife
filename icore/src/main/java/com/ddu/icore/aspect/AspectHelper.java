package com.ddu.icore.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

/**
 * Created by yzbzz on 2018/1/10.
 */

public class AspectHelper {

    public static void sendEventForData(JoinPoint joinPoint) {

        String fileName = joinPoint.getSourceLocation().getFileName();
        Object target = joinPoint.getTarget();

        Object joinPointThis = joinPoint.getThis();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        String king = joinPoint.getKind();
        Signature signature = joinPoint.getSignature();


    }
}
