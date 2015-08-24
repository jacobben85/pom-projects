package com.jbjohn.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jbjohn on 8/21/15.
 */
public class AsyncProcess {

    ExecutorService executor;

    public AsyncProcess() {
        System.out.println(this.getClass().getName());
        executor = Executors.newFixedThreadPool(10);
    }
}