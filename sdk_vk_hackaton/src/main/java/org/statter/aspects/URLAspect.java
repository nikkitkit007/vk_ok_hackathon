package org.statter.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.SourceLocation;
import org.statter.aspects.dataSending.DataSender;
import org.statter.aspects.dataSending.NetworkData;

import javax.print.DocFlavor;
import java.net.URL;
import java.sql.Timestamp;

@Aspect
public class URLAspect {

    private Thread thread;

    private Timestamp socketCreateStart;
    private Timestamp socketCreateEnd;

    @Pointcut("call(java.net.URL.new(..))")
    public void urlConstructor() {}

    @Before("call(java.net.URL.new(..))")
    public void beforeSocketCreation(JoinPoint joinPoint) {
        socketCreateStart = new Timestamp(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "urlConstructor()", returning = "url")
    public void afterSocketCreation(JoinPoint joinPoint, URL url) {
        socketCreateEnd = new Timestamp(System.currentTimeMillis());

        thread = Thread.currentThread();
        StackTraceElement [] stackTrace = thread.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element : stackTrace) {

            if(element.getClassName().equals("org.statter.aspects.dataSending.DataSender")){
                return;
            }

            stringBuilder.append(element.toString());
            stringBuilder.append("\n");
        }

        NetworkData networkData = new NetworkData();

        networkData.setStackTrace(stringBuilder.toString());
        networkData.setPackageSize(url.toString().getBytes().length);
        networkData.setStartTime(socketCreateStart.toString());
        networkData.setFinishTime(socketCreateEnd.toString());

        long durationInMillis = socketCreateEnd.getTime() - socketCreateStart.getTime();

        long millis = durationInMillis % 1000 + 1;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);

        networkData.setRunTime(time);

        DataSender sender = DataSender.getInstance();

        sender.SendData(networkData);

        System.out.println("Time start: " + socketCreateStart + " || Time end: " + socketCreateEnd);
        System.out.println("StackTrace: " + stringBuilder.toString());

        System.out.println("Object parameters: " + joinPoint.getStaticPart().toString());

        //System.out.println("Socket created in " + joinPoint.getSignature().getDeclaringTypeName());
    }

}
