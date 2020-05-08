package io.io;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("ALL")
public class ConcurrentCountDir4 {
    private ExecutorService service;
    private AtomicLong pendingFileVisits = new AtomicLong();
    private BlockingQueue<Long> fileSizes = new ArrayBlockingQueue<>(10000);

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        long[] count = new ConcurrentCountDir4().coutDir("C:\\Users\\dou\\Desktop", 5);
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB",
                count[0], (double) count[1] / (1024 * 1024 * 1024));
    }

    private void getCountDir(File file) {
        if (!file.isDirectory()) {
            try {
                fileSizes.put(file.length());
                pendingFileVisits.decrementAndGet();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        File[] children = file.listFiles();
        if (children == null) {
            System.out.println("without permission: " + file.getPath());
            pendingFileVisits.decrementAndGet();
            return;
        }
        for (File child : children) {
            if (child.isDirectory()) {
                pendingFileVisits.incrementAndGet();
                service.execute(() -> getCountDir(child));
            } else {
                try {
                    fileSizes.put(child.length());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        pendingFileVisits.decrementAndGet();
    }

    private long[] coutDir(String dir, int count) throws InterruptedException {
        int nfiles = 0;
        long nbytes = 0;
        service = Executors.newFixedThreadPool(count);
        try {
            pendingFileVisits.incrementAndGet();
            service.execute(() -> getCountDir(new File(dir)));
            while (pendingFileVisits.get() > 0 || fileSizes.size() > 0) {
                nbytes += fileSizes.take();
                nfiles++;
            }
            return new long[]{nfiles, nbytes};
        } finally {
            service.shutdown();
        }
    }

}

