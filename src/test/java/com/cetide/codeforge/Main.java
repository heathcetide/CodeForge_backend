package com.cetide.codeforge;


import java.util.*;

public class Main{

    public static void main(String[] args){
        System.out.println("Hello World");
    }

    public List<List<Integer>> threeSum(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            }else map.put(nums[i], 1);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {

            }
        }

        return null;
    }
}