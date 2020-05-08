package io.io;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("all")
public class ConcurrentCountDir6 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ConcurrentCountDir6 countDir = new ConcurrentCountDir6();
        countDir.countDir("F:\\", 1000);
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB\r\n",
                countDir.nfiles.get(), (double) countDir.nbytes.get() / (1024 * 1024 * 1024));
    }

    private AtomicInteger count = new AtomicInteger();
    private AtomicInteger nfiles = new AtomicInteger();
    private AtomicLong nbytes = new AtomicLong();
    private ExecutorService service;
    private CountDownLatch cdl = new CountDownLatch(1);

    private void coutDir(File file) {
        if (!file.isDirectory()) {
            nfiles.incrementAndGet();
            nbytes.addAndGet(file.length());
            count.decrementAndGet();
            return;
        }
        File[] children = file.listFiles();
        if (children == null) {
            System.out.println("without permission: " + file.getPath());
            count.decrementAndGet();
            return;
        }
        for (File child : children) {
            if (child.isDirectory()) {
                count.incrementAndGet();
                service.execute(() -> coutDir(child));
            } else {
                nfiles.incrementAndGet();
                nbytes.addAndGet(child.length());
            }
        }
        if (count.decrementAndGet() == 0) cdl.countDown();
    }

    public void countDir(String dir, int nThreads) throws InterruptedException {
        service = Executors.newFixedThreadPool(nThreads);
        try {
            count.incrementAndGet();
            coutDir(new File(dir));
            service.execute(() -> {
                while (count.get() != 0) {
                    System.out.printf("%d files\t%4.1f GB\r\n",
                            nfiles.get(), (double) nbytes.get() / (1024 * 1024 * 1024));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            cdl.await();
        } finally {
            service.shutdown();
        }
    }
}
