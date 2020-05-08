package io.io;

import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("ALL")
public class ConcurrentCountDir3 {
    private ExecutorService service = Executors.newFixedThreadPool(4);
    private AtomicLong pendingFileVisits = new AtomicLong(); //计数线程
    private AtomicLong fileSize = new AtomicLong();
    private AtomicInteger files = new AtomicInteger();
    private CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        long[] count = new ConcurrentCountDir3().coutDir("H:\\");
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB",
                count[0], (double) count[1] / (1024 * 1024 * 1024));
    }


    private void getCountDir(File file) {
        int nfiles = 0;
        long nbytes = 0;
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (!child.isDirectory()) {
                        nfiles++;
                        nbytes += child.length();
                    } else {
                        //用一个新的线程去处理新的文件夹
                        pendingFileVisits.incrementAndGet();
                        service.execute(() -> getCountDir(child));
                    }
                }
            } else {
                System.out.println("without permission: " + file.getPath());
            }
        } else {
            nfiles = 1;
            nbytes = file.length();
        }
        fileSize.addAndGet(nbytes);
        files.addAndGet(nfiles);
        if (pendingFileVisits.decrementAndGet() == 0) cdl.countDown();
    }

    private long[] coutDir(String dir) throws InterruptedException {
        try {
            pendingFileVisits.incrementAndGet();
            service.execute(() -> getCountDir(new File(dir)));
            cdl.await();
            return new long[]{files.longValue(), fileSize.longValue()};
        } finally {
            service.shutdown();
        }
    }

}

