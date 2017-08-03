package ru.ncedu.Shabatin.ArchiveComparator;

import ru.ncedu.Shabatin.utils.ConsoleHelper;

import java.io.IOException;

/**
 * Class, which is responsible for starting this util.
 *
 * @author SHABATIN FILIP
 */
class Executor {

    static void execute(String path1, String path2) {
        startComparing(path1, path2);
    }

    static void execute() {
        String path1 = GUIHelper.getFilePath();
        String path2 = GUIHelper.getFilePath();
        startComparing(path1, path2);
    }


    private static void startComparing(String path1, String path2) {
        try {
            ArchiveComparator comparator = new ArchiveComparator(path1, path2);
            comparator.compare();
        }
        //If paths is incorrect, util closes.
        catch (IOException e) {
            ConsoleHelper.println("Incorrect paths");
            System.exit(1);
        }
    }
}
