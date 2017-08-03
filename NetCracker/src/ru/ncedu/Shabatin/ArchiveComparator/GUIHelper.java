package ru.ncedu.Shabatin.ArchiveComparator;

import ru.ncedu.Shabatin.utils.FileManager;

import javax.swing.*;
import java.io.File;

/**
 * Helper class for working with the GUI.
 *
 * @author SHABATIN FILIP
 */
class GUIHelper {
    static String getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter());
        int ret = fileChooser.showDialog(null, "Выберите архив");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        } else
            //If user don't choose an archive, util closes.
            System.exit(1);
        return null;
    }

    /**
     * Filter for fileChooser, accept only .zip and .jar.
     */
    static class FileFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String ext = FileManager.getExtension(f);
            return ext != null && (ext.equals("jar") || ext.equals("zip"));
        }

        @Override
        public String getDescription() {
            return null;
        }
    }
}
