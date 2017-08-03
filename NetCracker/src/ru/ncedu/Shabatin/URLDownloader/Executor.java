package ru.ncedu.Shabatin.URLDownloader;

import ru.ncedu.Shabatin.utils.FileManager;

import java.io.IOException;

/**
 * Class, which is responsible for starting this util.
 *
 * @author SHABATIN FILIP
 */
class Executor {
    static void execute(String url, String path, boolean open) throws IOException {
        URLDownloader urlDownloader = new URLDownloader(url, path);
        if (urlDownloader.getWebPage().isHTML()) {
            String dirStr = urlDownloader.getPagePath() + "_files";
            FileManager.cleanDir(dirStr);
            urlDownloader.writeImg(dirStr);
            urlDownloader.writeLinks(dirStr);
        }
        urlDownloader.writePage();
        if (open)
            urlDownloader.openPage(urlDownloader.getPagePath());
    }
}
