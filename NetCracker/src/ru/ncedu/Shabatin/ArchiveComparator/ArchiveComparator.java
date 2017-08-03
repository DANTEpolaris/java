package ru.ncedu.Shabatin.ArchiveComparator;

import ru.ncedu.Shabatin.utils.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static ru.ncedu.Shabatin.ArchiveComparator.ArchiveComparator.reportHelper.writeStrToReport;

/**
 * Class, which is responsible for obtaining and comparing files from archives.
 *
 * @author SHABATIN FILIP
 */
class ArchiveComparator {
    /**
     * @see FileInfo
     */
    private List<FileInfo> archive1;
    private List<FileInfo> archive2;
    //Each new program report is re-stored in the working directory as a report.txt.
    private static final File report = new File(System.getProperty("user.dir"), "report.txt");


    ArchiveComparator(String path1, String path2) throws IOException {
        setArchive1(getArchivedFiles(path1));
        setArchive2(getArchivedFiles(path2));
        reportHelper.prepReport(path1, path2);
    }

    private List<FileInfo> getArchive1() {
        return archive1;
    }

    private void setArchive1(List<FileInfo> archive1) {
        this.archive1 = archive1;
    }

    private List<FileInfo> getArchive2() {
        return archive2;
    }

    private void setArchive2(List<FileInfo> archive2) {
        this.archive2 = archive2;
    }

    private static File getReport() {
        return report;
    }

    /**
     * @param path to the archive.
     * @return list of the files, contained in archive.
     * @throws IOException
     * @see FileInfo
     */
    private List<FileInfo> getArchivedFiles(String path) throws IOException {
        List<FileInfo> archivedFiles = new ArrayList<>();
        try (ZipInputStream stream = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {
                archivedFiles.add(new FileInfo(entry.getName(), entry.hashCode(), entry.getSize()));
            }
            return archivedFiles;
        }
    }

    void compare() {
        /*  Every file of the second archive,
            founded(in original or changed state)
            in first archive, is added to found files.
            It's needed to detecting deleted or added files.
        */
        List<FileInfo> foundFiles = new ArrayList<>();
        for (FileInfo file1 : getArchive1()) {
            int size = foundFiles.size();
            for (FileInfo file2 : getArchive2()) {
                boolean equalsHash = file1.getHash() == file2.getHash();
                boolean equalsLength = file1.getLength() == file2.getLength();
                boolean equalsName = file1.getName().equals(file2.getName());

                /*
                Files are compared by their main properties.
                In case of ambiguity, for example the file of the first archive can be an updated version of one file
                or a renamed version of another file of the second archive,
                the information will be recorded for each possible case.
                However, the file will be considered deleted or added
                only if there are no potentially modified versions of it in another archive.
                */
                if (equalsLength || equalsName) {
                    foundFiles.add(file2);
                    if (!(equalsLength && equalsHash) && equalsName) {
                        writeStrToReport(" * updated " + file1.getName(), " * updated " + file2.getName());
                    } else if (!equalsName) {
                        writeStrToReport(" ? renamed " + file1.getName(), " ? renamed " + file2.getName());
                    }
                }

            }
            //If foundFiles.size didn't change after a full iteration of the first archive file, then it was deleted.
            if (size == foundFiles.size())
                writeStrToReport(" - deleted " + file1.getName(), "");
        }
        //All files of the second archive not found after all iterations are considered added.
        getArchive2().removeAll(foundFiles);
        for (FileInfo file2 : getArchive2())
            writeStrToReport("", " + added " + file2.getName());

    }

    /**
     * Static nested class, needed to work with the report.
     */
    static class reportHelper {

        /**
         * Recreate the report and write a head to it.
         *
         * @param path1
         * @param path2
         * @throws IOException
         */
        private static void prepReport(String path1, String path2) throws IOException {
            if (getReport().delete())
                getReport().createNewFile();
            writeStrToReport(path1.substring(path1.lastIndexOf("\\") + 1), path2.substring(path2.lastIndexOf("\\") + 1));
            FileManager.writeToFile(getReport(), stretchStr("", "-") + "+" + stretchStr("", "-"));
        }

        /**
         * Create an entire string of two parts and write it to report.
         *
         * @param leftPart
         * @param rightPart
         */
        static void writeStrToReport(String leftPart, String rightPart) {
            FileManager.writeToFile(getReport(), stretchStr(leftPart, " ") + "|" + stretchStr(rightPart, " "));
        }

        /**
         * Stretches a string up to 30 characters using a symbol.
         *
         * @param str
         * @param symbol
         * @return A stretched string.
         */
        private static String stretchStr(String str, String symbol) {
            StringBuilder strBuilder = new StringBuilder(str);
            while (strBuilder.length() < 30)
                strBuilder.append(symbol);
            return strBuilder.toString();
        }
    }
}
