package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
    }
        public static Integer[] sort (Integer[]array){
            Arrays.sort(array);
            final double median;
            if (array.length % 2 == 0)
                median = ((double) array[array.length / 2 - 1] + (double) array[array.length / 2]) / 2;
            else
                median = array[array.length / 2];
            Comparator<Integer> compareByMedian = new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    double value = Math.abs(o1 - median) - Math.abs(o2 - median);
                    if (value == 0)
                        value = o1 - o2;
                    return (int) value;
                }
            };
            Arrays.sort(array, compareByMedian);
            return array;
        }
    }
