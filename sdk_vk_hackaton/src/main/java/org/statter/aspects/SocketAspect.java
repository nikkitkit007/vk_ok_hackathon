package org.statter.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.SourceLocation;
import org.statter.aspects.dataSending.DataSender;
import org.statter.aspects.dataSending.NetworkData;

import java.sql.Timestamp;

@Aspect
public class SocketAspect {
    private Thread thread;

    private Timestamp socketCreateStart;
    private Timestamp socketCreateEnd;

    @Before("call(java.net.Socket.new(..))")
    public void beforeSocketCreation(JoinPoint joinPoint) {
        socketCreateStart = new Timestamp(System.currentTimeMillis());
    }

    @After("call(java.net.Socket.new(..))")
    public void afterSocketCreation(JoinPoint joinPoint) {
        socketCreateEnd = new Timestamp(System.currentTimeMillis());

        thread = Thread.currentThread();
        StackTraceElement [] stackTrace = thread.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element : stackTrace) {

            System.out.println("class name: " + element.getClassName());

            if(element.getClassName().equals("org.statter.aspects.dataSending.DataSender")){
                return;
            }

            stringBuilder.append(element.toString());
            stringBuilder.append("\n");
        }
        System.out.println("Time start: " + socketCreateStart + " || Time end: " + socketCreateEnd);
        System.out.println("StackTrace: " + stringBuilder.toString());

        System.out.println("Object parameters: " + joinPoint.getStaticPart().toString());

        NetworkData data = new NetworkData();

        data.setStackTrace(stringBuilder.toString());

        data.setStartTime(socketCreateStart.toString());
        data.setFinishTime(socketCreateEnd.toString());

        long durationInMillis = socketCreateEnd.getTime() - socketCreateStart.getTime();

        long millis = durationInMillis % 1000 + 1;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);

        data.setRunTime(time);

        DataSender sender = DataSender.getInstance();

        sender.SendData(data);
        //System.out.println("Socket created in " + joinPoint.getSignature().getDeclaringTypeName());
    }

    /*@After("call(java.net.Socket.new(..))")
    public void afterSocketCreation(JoinPoint joinPoint) {
        //System.out.println(Arrays.toString(thread.getStackTrace()));

        System.out.println("Socket was created in " + joinPoint.getSignature().getDeclaringTypeName());
    }*/

    /*@After("call(* println(..))")
    public void catchPrintln(JoinPoint joinPoint) {
        System.out.print("1 sout");
    }*/

}
