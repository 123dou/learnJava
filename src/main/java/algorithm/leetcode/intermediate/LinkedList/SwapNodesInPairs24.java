package algorithm.leetcode.intermediate.LinkedList;

import algorithm.leetcode.TreeAndList.ListNode;

/**
 * ### [24\. 两两交换链表中的节点](https://leetcode-cn.com/problems/swap-nodes-in-pairs/)
 * <p>
 * 难度 **中等**
 * <p>
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * <p>
 * *示例:**
 * <p>
 * *说明:**
 * <p>
 * ```
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.```
 * <p>
 * #### Solution
 * <p>
 * Language: **Java**
 * <p>
 * ```java
 **/


public class SwapNodesInPairs24 {
    public ListNode swapPairs(ListNode head) {
        if (head == null) return null;
        ListNode nei = head.next;
        if (nei == null) return null;
        swapPairs(head, null);
        return nei;
    }

    public void swapPairs(ListNode curr, ListNode pre) {
        if (curr == null) return;
        ListNode nei = curr.next;
        if (nei == null) return;
        if (pre != null) pre.next = nei;
        ListNode nnei = nei.next;
        curr.next = nnei;
        nei.next = curr;
        swapPairs(nnei, curr);
    }
}
