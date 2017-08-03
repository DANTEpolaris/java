package com.javarush.task.task25.task2512;

import java.util.ArrayList;
import java.util.List;

/*
Живем своим умом
*/
public class Solution implements Thread.UncaughtExceptionHandler {

    public void uncaughtException(Thread t, Throwable e)
    {
        t.interrupt();
        List<Throwable> exceptionList = new ArrayList<>();
        exceptionList.add(e);
        Throwable temporaryExceptionVariable = e.getCause();
        while (temporaryExceptionVariable != null)
        {
            exceptionList.add(temporaryExceptionVariable);
            temporaryExceptionVariable = temporaryExceptionVariable.getCause();
        }

        for (int i = exceptionList.size()-1; i >= 0; i--)
        {
            System.out.println(exceptionList.get(i).getClass().getName() + ": " + exceptionList.get(i).getMessage());
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(){
            public void run(){
                try{
                    throw new Exception("ABC", new RuntimeException("DEF", new IllegalAccessException("GHI")));
                } catch (Exception e){
                    getUncaughtExceptionHandler().uncaughtException(currentThread(), e);
                }
            }
        };
        thread.setUncaughtExceptionHandler(new Solution());
        thread.start();
    }
}
