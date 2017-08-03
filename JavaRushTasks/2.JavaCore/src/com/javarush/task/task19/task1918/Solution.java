package com.javarush.task.task19.task1918;

/* 
Знакомство с тегами
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        FileReader fileReader = new FileReader(bufferedReader.readLine());
        bufferedReader.close();
        bufferedReader = new BufferedReader(fileReader);
        String arg = args[0];
        String str;
        StringBuilder file = new StringBuilder();
        int count = 0;
        TreeMap<Integer, String> indexTags = new TreeMap<>();

        while ((str = bufferedReader.readLine()) != null)
            file.append(str);
        bufferedReader.close();

        mapCreate(indexTags, file, "<", arg);
        mapCreate(indexTags, file, "</", arg);

        while (!indexTags.isEmpty()) {
            int openIndex = indexTags.firstKey();
            int closeIndex = 0;
            for (Map.Entry e : indexTags.entrySet()) {
                if (e.getValue().equals("<"))
                    ++count;
                else {
                    --count;
                    closeIndex = (int) e.getKey();
                }
                if (count == 0) {
                    System.out.println(file.toString().substring(openIndex, closeIndex + 3 + arg.length()));
                    indexTags.remove(openIndex);
                    indexTags.remove(closeIndex);
                    break;
                }
            }
        }

    }


    public static void mapCreate (TreeMap<Integer, String> indexTags, StringBuilder file, String s, String arg)
    {
        String str = file.toString();
        int sum = 0;

        while (str.contains(s + arg))
        {
            indexTags.put(str.indexOf(s + arg) + sum, s);
            sum += str.indexOf(s + arg) + 1;
            str = str.substring(str.indexOf(s + arg) + 1);
        }

    }
}
