package ru.vsu.cs.gelem.task4;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.swap;

public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        int[] nums = new int[]{3, 5, 2, 6, 1, 7, 3, 9, 13, 88, 12, 14, -1, 34, 22, 0, 400};
        for (int i : nums) {
            list.add(i);
        }
        quickSort(list, Integer::compareTo);
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    public static <T> void quickSort(LinkedList<T> list, Comparator<T> c) {
        Stack<Integer[]> stack = new Stack<>();
        int pivot = (list.size() - 1) / 2;
        int l = 0;
        int r = list.size() - 1;
        Integer[] partition = new Integer[]{l, r, pivot};
        stack.push(partition);
        while (!stack.isEmpty()) {
            Integer[] part = stack.pop();
            if (part[0] >= part[1]) {
                continue;
            }
            int lNext = part[0];
            int rNext = part[1] - 1;
            int pivotNext = part[1];
            swap(list, part[2], part[1]);
            while (lNext <= rNext) {
                if (c.compare(list.get(lNext), list.get(pivotNext)) < 0) {
                    lNext++;
                } else {
                    if (c.compare(list.get(rNext), list.get(pivotNext)) >= 0) {
                        rNext--;
                    } else {
                        if (rNext > lNext) {
                            swap(list, lNext, rNext);
                        }
                    }
                }
            }
            swap(list, lNext, pivotNext);
            Integer[] partitionNext = new Integer[]{part[0], lNext - 1, part[0] + (lNext - 1 - part[0]) / 2};
            Integer[] partitionNext2 = new Integer[]{lNext + 1, part[1], lNext + 1 + (part[1] - lNext + 1) / 2};
            stack.push(partitionNext2);
            stack.push(partitionNext);
        }
    }
}
