package org.statter.aspects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.sql.Timestamp;

@Aspect
public class HttpURLConnectionAspect {

    private Thread thread;

    private Timestamp socketCreateStart;
    private Timestamp socketCreateEnd;

    @Before("call(java.net.HttpURLConnection.new(..))")
    public void beforeSocketCreation(JoinPoint joinPoint) {
        socketCreateStart = new Timestamp(System.currentTimeMillis());
    }

    @After("call(java.net.HttpURLConnection.new(..))")
    public void afterSocketCreation(JoinPoint joinPoint) {
        socketCreateEnd = new Timestamp(System.currentTimeMillis());

        thread = Thread.currentThread();
        StackTraceElement [] stackTrace = thread.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            stringBuilder.append(element.toString());
            stringBuilder.append("\n");
        }
        System.out.println("Time start: " + socketCreateStart + " || Time end: " + socketCreateEnd);
        System.out.println("StackTrace: " + stringBuilder.toString());

        System.out.println("Object parameters: " + joinPoint.getStaticPart().toString());

        //System.out.println("Socket created in " + joinPoint.getSignature().getDeclaringTypeName());
    }
}
