package com.javarush.task.task22.task2211;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/* 
Смена кодировки
*/
public class Solution {
    static String win1251TestString = "РќР°СЂСѓС€РµРЅРёРµ РєРѕРґРёСЂРѕРІРєРё РєРѕРЅСЃРѕР»Рё?"; //only for your testing
    public static void main(String[] args) throws IOException {
        String in = args[0];
        String out = args[1];
        FileInputStream fin = new FileInputStream(in);
        FileOutputStream fout = new FileOutputStream(out);
        Charset Win1251 = Charset.forName("Windows-1251");
        Charset UTF = Charset.forName("UTF-8");
        byte[] buffer = new byte[fin.available()];
        fin.read(buffer);
        String s = new String(buffer, UTF);
        buffer = s.getBytes(Win1251);
        fout.write(buffer);
        fin.close();
        fout.close();
    }
}