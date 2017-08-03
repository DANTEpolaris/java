package ru.ncedu.Shabatin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helper class for working with the console.
 *
 * @author SHABATIN FILIP
 */
public class ConsoleHelper {
    public static void println(String string) {
        System.out.println(string);
    }

    public static String stringRequest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
