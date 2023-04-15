package org.statter.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class SocketAspect {

    @Before("execution(* java.net.Socket+.new(..))")
    public void logSocketCreation(JoinPoint joinPoint) {
        System.out.println("Socket created in " + joinPoint.getSignature().getDeclaringTypeName());
    }

    @After("execution(* java.io.Printstream.println(..))")
    public void catchPrintln(JoinPoint joinPoint) {
        System.out.println("got sout");
    }

}
