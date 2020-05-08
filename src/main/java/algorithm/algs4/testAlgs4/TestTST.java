package algorithm.algs4.testAlgs4;


import algorithm.algs4.string.wordSearchTree.TST;

public class TestTST {
    public static void main(String[] args) {
        TST<String> tst = new TST<>();
        tst.put("she", "she");
        tst.put("sells", "sells");
        tst.put("sea", "sea");
        tst.put("shells", "shells");
        tst.put("by", "by");
        tst.put("the", "the");
        tst.put("sea", "sea");
        tst.put("shore", "shore");
        Iterable<String> sh = tst.keys();
        for (String s : sh) System.out.print(s + " ");
        System.out.println(tst.longestPrefixOf("sh"));
    }
}
