package io.io;

import java.io.File;

public class CountDir {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CountDir countDir = new CountDir();
        countDir.countDir(new File("C:\\Users\\dou"));
        long end = System.currentTimeMillis();
        System.out.println("total time: " + ((end - start) / 1000));
        System.out.printf("total:\t%d files\t%4.1f GB",
                countDir.nfiles, (double) countDir.nbytes / (1024 * 1024 * 1024));
    }

    private int nfiles = 0;
    private long nbytes = 0;

    public void countDir(File file) {
        if (!file.isDirectory()) {
            nfiles++;
            nbytes += file.length();
        } else {
            File[] children = file.listFiles();
            if (children == null) {
                System.out.println("without permission: " + file.getPath());
            } else {
                for (File child : children) {
                    countDir(child);
                }
            }
        }
    }
}
