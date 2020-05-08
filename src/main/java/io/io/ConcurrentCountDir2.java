package io.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SuppressWarnings("ALL")
public class ConcurrentCountDir2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        long[] count = new ConcurrentCountDir2().coutDir("C:\\Users\\dou");
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB",
                count[0], (double) count[1] / (1024 * 1024 * 1024));
    }


    private long[] getCountDir(ExecutorService service, File file) throws ExecutionException, InterruptedException, TimeoutException {
        if (!file.isDirectory()) {
            return new long[]{1, file.length()};
        }
        int nfiles = 0;
        long nbytes = 0;
        File[] children = file.listFiles();
        if (children != null) {
            List<Future<long[]>> futures = new ArrayList<>();
            for (File child : children) {
                if (child.isDirectory())
                    //线程池太小的话，会阻塞
                    futures.add(service.submit(() -> getCountDir(service, child)));
                else {
                    nfiles++;
                    nbytes += child.length();
                }
            }
            for (Future<long[]> future : futures) {
                //future的返回依赖于后面的线程，但是后面的线程可能会因为线程池满了而阻塞，导致形成死锁
                long[] partialRes = future.get();
                nfiles += partialRes[0];
                nbytes += partialRes[1];
            }
        } else {
            System.out.println("without permission: " + file.getPath());
        }
        return new long[]{nfiles, nbytes};
    }

    private long[] coutDir(String dir) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService service = Executors.newFixedThreadPool(100000);
        try {
            return getCountDir(service, new File(dir));
        } finally {
            service.shutdown();
        }
    }

}

