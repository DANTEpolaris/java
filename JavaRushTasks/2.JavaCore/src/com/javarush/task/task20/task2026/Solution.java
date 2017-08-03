package com.javarush.task.task20.task2026;

import java.util.ArrayList;

/*
Алгоритмы-прямоугольники
*/
public class Solution {
    public static void main(String[] args) {
        byte[][] a = new byte[][]{
                {1, 1, 0, 0},
                {1, 1, 0, 1},
                {1, 1, 0, 0},
                {1, 1, 0, 0}
        };
        int count = getRectangleCount(a);
        System.out.println("count = " + count + ". Должно быть 2");
    }

    public static int buffCount(ArrayList<Integer> array)
    {
        int result = array.contains(1) ? 1 : 0;
        for (int i = 1; i < array.size(); i++)
        {
            if (array.get(i) != array.get(i - 1) + 1)
                result += 1;
        }
        return result;
    }

    public static int getRectangleCount(byte[][] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a.length; j++)
            {
                if (i == 0){        //первый ряд
                    if (j != a.length - 1){      //проверяем чтоб было не последнее число в ряду
                        if (a[i][j] == 1 && a[i][j+1] == 0) {
                            count ++;
                        }
                    }
                    else if (a[i][j] == 1 ) count ++;
                }
                else              //не первый ряд
                {
                    if (j != a.length - 1){     // проверяем чтоб было не последнее число в ряду
                        if (a[i][j] == 1 && a[i][j+1] == 0 && a[i-1][j] == 0) count ++;
                    }
                    else if (a[i][j] == 1 && a[i-1][j] == 0) count ++;
                }
            }
        }
        return count;
    }
}
