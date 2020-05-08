package algorithm.algs4.tree.rbTree;


import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RBTSerialization {
    public static void main(String[] args) {

    }

    @Test
    public void createRandomTxt() throws IOException {
        Random random = new Random(1024);
        File file = new File("random.txt");
        File delete = new File("deleteKey.txt");
        FileOutputStream fosPut = null;
        FileOutputStream fosDel = null;
        PrintWriter pwDel = null;
        PrintWriter pwPut = null;
        try {
            fosPut = new FileOutputStream(file);
            pwPut = new PrintWriter(fosPut);
            fosDel = new FileOutputStream(delete);
            pwDel = new PrintWriter(fosDel);
            StringBuilder put = new StringBuilder();
            StringBuilder del = new StringBuilder();
            for (int i = 1, j = 10; i < 1000; i++) {
                int r = random.nextInt(1000);
                put.append(r).append(" ");
                if (i == j) {
                    del.append(r).append(" ");
                    j += 10;
                }
            }
            pwPut.write(put.toString().toCharArray());
            pwPut.flush();

            pwDel.write(del.toString().toCharArray());
            pwDel.flush();
        } finally {
            if (fosPut != null) fosPut.close();
            if (pwPut != null) pwPut.close();
            if (fosDel != null) fosDel.close();
            if (pwDel != null) pwDel.close();
        }
    }

    @Test
    public void createRBTree() throws IOException {
        File file = new File("random.txt");
        PrintWriter printWriter = null;
        Scanner sc = null;
        FileOutputStream fos = null;
        try (FileInputStream fin = new FileInputStream(file)) {
            sc = new Scanner(fin);
            List<Integer> list = new ArrayList<>();
            RBTressAlgs4 algs4 = new RBTressAlgs4();
            while (sc.hasNextInt()) {
                int key = sc.nextInt();
                list.add(key);
                algs4.put(key);
            }
            List<RBTressAlgs4.Node> nodes = algs4.levelOrder();
            File rbtFile = new File("rbt_java.txt");
            fos = new FileOutputStream(rbtFile);
            printWriter = new PrintWriter(fos);
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (RBTressAlgs4.Node node : nodes) {
                sb.append(node.toString()).append("\t\t");
                i++;
                if (i == 15) {
                    i = 0;
                    sb.append("\n");
                }
            }
            printWriter.write(sb.toString().toCharArray());
            printWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) printWriter.close();
            if (fos != null) fos.close();
            if (sc != null) sc.close();
        }
    }

    @Test
    public void TestDelete() throws IOException {
        File file = new File("random.txt");
        PrintWriter printWriter = null;
        Scanner sc = null;
        FileOutputStream fos = null;
        FileInputStream fsDel = null;
        Scanner scDel = null;
        try (FileInputStream fin = new FileInputStream(file)) {
            sc = new Scanner(fin);
            RBTressAlgs4 algs4 = new RBTressAlgs4();
            while (sc.hasNextInt()) {
                int key = sc.nextInt();
                algs4.put(key);
            }
            List<RBTressAlgs4.Node> nodes = null;
            File deleteFile = new File("rbt_delete_java.txt");
            fos = new FileOutputStream(deleteFile);
            printWriter = new PrintWriter(fos);


            File deleteKey = new File("deleteKey.txt");
            fsDel = new FileInputStream(deleteKey);
            scDel = new Scanner(fsDel);
            for (; scDel.hasNextInt(); ) {
                int key = scDel.nextInt();
                StringBuilder sb = new StringBuilder();
                algs4.delete(key);
                nodes = algs4.levelOrder();
                for (RBTressAlgs4.Node node : nodes) {
                    sb.append(node.toString()).append("\t\t\t");
                }
                sb.append("\n\n\n\n");
                printWriter.append(sb.toString());
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) printWriter.close();
            if (fos != null) fos.close();
            if (sc != null) sc.close();
            if (fsDel != null) fsDel.close();
            if (scDel != null) scDel.close();
        }
    }
}
