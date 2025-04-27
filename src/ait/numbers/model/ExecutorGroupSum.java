package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends GroupSum{
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() throws InterruptedException {
        OneGroupSum[] tasks = new OneGroupSum[numberGroups.length];
        Thread[] threads = new Thread[numberGroups.length];

        for (int i = 0; i < numberGroups.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threads.length; i++) {
            executorService.execute(tasks[i]);
        }

        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);


        int res = Arrays.stream(tasks).mapToInt(OneGroupSum::getSum).sum();
        return res;
    }
}
