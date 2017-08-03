package ru.ncedu.Shabatin.utils;

import java.io.*;

/**
 * Helper class for working with files.
 */
public class FileManager {

    public static void writeToFile(File file, String str) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.append(str + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static void cleanDir(String dirStr) throws IOException {
        File dir = new File(dirStr);
        if (!dir.mkdir())
            if (dir.isDirectory())
                for (File file : dir.listFiles()) {
                    file.delete();
                }
    }

    public static void writeFile(String str, File file, String charset) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), charset)) {
            file.createNewFile();
            writer.append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

