package io.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentCountDir {

    public static void main(String[] args)
            throws ExecutionException,
            InterruptedException {
        long start = System.currentTimeMillis();
        SubDirAndSize sds = new ConcurrentCountDir().getTotalOfDir(new File("C:\\Users\\dou"));
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB",
                sds.nfiles, (double) sds.total / (1024 * 1024 * 1024));
    }

    private class SubDirAndSize {
        final int nfiles;
        final long total;
        final List<File> subDir;

        SubDirAndSize(int nfiles, long total, ArrayList<File> subDir) {
            this.nfiles = nfiles;
            this.total = total;
            this.subDir = subDir;
        }
    }

    private SubDirAndSize getSubDirAndSize(File file) {
        long total = 0;
        int nfiles = 0;
        ArrayList<File> subDir = new ArrayList<>();
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    subDir.add(child);
                } else {
                    total += child.length();
                    nfiles++;
                }
            }
        } else {
            System.out.println("without permission: " + file.getPath());
        }
        return new SubDirAndSize(nfiles, total, subDir);
    }

    private SubDirAndSize getTotalOfDir(final File file) throws ExecutionException, InterruptedException {
        ExecutorService services = Executors.newFixedThreadPool(4);
        int nfiles = 0;
        long total = 0;
        try {
            final List<File> dirs = new ArrayList<>();
            dirs.add(file);
            while (!dirs.isEmpty()) {
                List<Future<SubDirAndSize>> futures = new ArrayList<>();
                for (File dir : dirs) {
                    futures.add(services.submit(() -> getSubDirAndSize(dir)));
                }
                dirs.clear();
                for (Future<SubDirAndSize> future : futures) {
                    SubDirAndSize subDirAndSize = future.get();
                    dirs.addAll(subDirAndSize.subDir);
                    total += subDirAndSize.total;
                    nfiles += subDirAndSize.nfiles;
                }
            }
            return new SubDirAndSize(nfiles, total, null);
        } finally {
            services.shutdown();
        }
    }

}

