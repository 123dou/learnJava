package io.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("ALL")
public class ConcurrentCountDir5 {
    private final static ForkJoinPool fjp = new ForkJoinPool();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long[] count = fjp.invoke(new countDir(
                new File("C:\\Users\\dou")));
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB",
                count[0], (double) count[1] / (1024 * 1024 * 1024));
    }

    private static class countDir extends RecursiveTask<long[]> {
        final File file;

        countDir(File f) {
            file = f;
        }

        @Override
        protected long[] compute() {
            int files = 0;
            long nbytes = 0;
            if (!file.isDirectory()) {
                files = 1;
                nbytes = file.length();
            } else {
                File[] children = file.listFiles();
                if (children == null) {
                    System.out.println("without permission: " + file.getPath());
                } else {
                    List<ForkJoinTask<long[]>> tasks = new ArrayList<>();
                    for (File child : children) {
                        if (child.isDirectory()) tasks.add(new countDir(child));
                        else {
                            files++;
                            nbytes += child.length();
                        }
                    }
                    for (ForkJoinTask<long[]> task : invokeAll(tasks)) {
                        files += task.join()[0];
                        nbytes += task.join()[1];
                    }
                }
            }
            return new long[]{files, nbytes};
        }
    }
}

