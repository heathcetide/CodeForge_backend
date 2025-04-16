package com.cetide.codeforge;
import java.util.HashSet;
import java.util.Set;

/**
 * 141. 环形链表
 */
public class SolutionTest {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while( head != null) {
            if(set.contains(head)) {
                return true;
            } else {
                set.add(head);
            }
            head = head.next;
        }
        return false;
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}

