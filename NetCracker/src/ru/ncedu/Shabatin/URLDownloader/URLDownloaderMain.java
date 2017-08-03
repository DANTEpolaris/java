package ru.ncedu.Shabatin.URLDownloader;

import java.io.IOException;

/**
 * Main of the ArchiveComparator.
 *
 * @author SHABATIN FILIP
 */
public class URLDownloaderMain {
    public static void main(String[] args) throws IOException {
        if (args.length == 1)
            Executor.execute(args[0], null, false);
        else if (args.length == 2)
            Executor.execute(args[0], args[1], false);
        else if (args.length == 3)
            Executor.execute(args[0], args[1], true);
        else
            System.exit(1);
    }
}
