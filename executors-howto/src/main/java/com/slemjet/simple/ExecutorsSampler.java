package com.slemjet.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorsSampler {

    public static void runExecutors() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable runnableTask = () -> {
            long start = System.currentTimeMillis();
            try {
                System.out.println("Sleeping " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep((long) (Math.random() * 3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println("Awaken " + Thread.currentThread().getName() + " after " + (end - start) / 1000 + " sec.");
        };

        Callable<String> callableTask = () -> {
            long start = System.currentTimeMillis();
            TimeUnit.SECONDS.sleep(5);
            long end = System.currentTimeMillis();
            return "I've slept well " + Thread.currentThread().getName() + " after " + (end - start) / 1000 + " sec.";
        };

        List<Runnable> tasks = new ArrayList<Runnable>();
        tasks.add(runnableTask);
        tasks.add(runnableTask);
        tasks.add(runnableTask);
        tasks.add(runnableTask);
        tasks.add(runnableTask);
        tasks.add(runnableTask);
        for (Runnable task : tasks)
            executorService.submit(task);

        try {
            Future<String> future1 = executorService.submit(callableTask);
            Future<String> future2 = executorService.submit(callableTask);
            Future<String> future3 = executorService.submit(callableTask);
            System.out.println(future3.get());
            System.out.println(future1.get());
            System.out.println(future2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Executing scheduled");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.schedule(runnableTask, 5, TimeUnit.SECONDS);


        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
