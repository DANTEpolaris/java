package com.javarush.task.task22.task2208;

import java.util.HashMap;
import java.util.Map;

/* 
Формируем WHERE
*/
public class Solution {
    public static void main(String[] args) {


    }
    public static String getQuery(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> e : params.entrySet())
        {
            if (!(e.getValue() == null))
            {
                stringBuilder.append(e.getKey()).append(" = ").append("'").append(e.getValue()).append("' and ");
            }
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf("'") + 1);
    }
}
