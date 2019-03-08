package com.da.lect4.threadpools.exception.example2;

import java.util.Arrays;
import java.util.concurrent.*;

public class ThreadPoolHandlingException extends ThreadPoolExecutor {


    public ThreadPoolHandlingException(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                       TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


    @Override
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        // If submit() method is called instead of execute()
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException e) {
                t = e;
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            // Exception occurred
            System.out.println("Uncaught exception is detected! " + t + " st: " + Arrays.toString(t.getStackTrace()));
            // ... Handle the exception
            // Restart the runnable again
            //execute(r);
        }
        // ... Perform cleanup actions
    }
}
