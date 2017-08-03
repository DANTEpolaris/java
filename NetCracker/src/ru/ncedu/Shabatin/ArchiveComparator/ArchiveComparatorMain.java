package ru.ncedu.Shabatin.ArchiveComparator;

import java.io.IOException;

/**
 * Main of the ArchiveComparator.
 *
 * @author SHABATIN FILIP
 */
public class ArchiveComparatorMain {
    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            Executor.execute();
        if (args.length == 2)
            Executor.execute(args[0], args[1]);
        else
            System.exit(1);
    }

}
