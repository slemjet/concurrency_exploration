package com.slemjet.simple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFutureSampler {

    public void runExample() {
        CompletableFuture<String> completableFuture = new CompletableFuture<String>();

        CompletableFuture<Void> completableFutureAsync = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Sleeping " + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Awaken " + Thread.currentThread().getName());
            }
        });

        CompletableFuture<String> completableFutureString = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    System.out.println("Sleeping " + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Awaken " + Thread.currentThread().getName());
                return "Some result";
            }
        });

        try {
            completableFuture.complete("I Am manual");
            String result = completableFuture.get();
            System.out.println(result);
            System.out.println(completableFutureAsync.get());
            System.out.println(completableFutureString.get());

            completableFutureString.thenApply(s -> {
                System.out.println("Converting " + s + " to 1");
                return 1;
            }).thenAccept(integer -> System.out.println("Doing something with " + integer));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
