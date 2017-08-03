package com.javarush.task.task25.task2506;

/**
 * Created by FILIP on 01.04.2017.
 */
public class LoggingStateThread extends Thread {
    private Thread target;

    public LoggingStateThread(Thread thread) {
        this.target = thread;
        setDaemon(true);
    }

    public void run()
    {
        System.out.println(target.getState());
        State state = target.getState();
        while(state != State.TERMINATED)
        {
            if (state != target.getState())
            {
                System.out.println(target.getState());
                state = target.getState();
            }
        }
        interrupt();
    }
}
