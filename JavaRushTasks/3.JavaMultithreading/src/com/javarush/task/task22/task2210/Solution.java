package com.javarush.task.task22.task2210;

import java.util.ArrayList;
import java.util.StringTokenizer;

/*
StringTokenizer
*/
public class Solution {
    public static void main(String[] args) {

    }
    public static String [] getTokens(String query, String delimiter) {
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(query, delimiter);
        while (stringTokenizer.hasMoreTokens())
        {
            tokens.add(stringTokenizer.nextToken());
        }
        String[] array = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++)
            array[i] = tokens.get(i);
        return array;
    }
}
