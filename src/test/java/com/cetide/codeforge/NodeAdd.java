package com.cetide.codeforge;

/**
 * 2. 两数相加
 */
public class NodeAdd {

    ListNode dummyHead = new ListNode();
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode current = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0){
            int sum = carry;
            if (l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }
        return dummyHead.next;
    }

    /**
     *         if (list1 == null){
     *             return list2;
     *         }else if (list2 == null){
     *             return list1;
     *         }else if (list1.val < list2.val){
     *             list1.next = mergeTwoLists(list1.next, list2);
     *             return list1;
     *         }else {
     *             list2.next = mergeTwoLists(list1, list2.next);
     *             return list2;
     *         }
     */


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}

