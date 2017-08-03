package ru.ncedu.Shabatin.URLDownloader;

import java.io.File;

/**
 * Enumeration of possible states of the transmitted filePath.
 *
 * @author SHABATIN FILIP
 */
enum PathState {
    NULL,
    DIRECTORY,
    FILE,
    NONEXISTENT;

    static PathState checkPath(File path) {
        if (path == null)
            return NULL;
        if (path.exists())
            if (path.isDirectory())
                return DIRECTORY;
            else if (path.isFile())
                return FILE;
        return NONEXISTENT;
    }
}
