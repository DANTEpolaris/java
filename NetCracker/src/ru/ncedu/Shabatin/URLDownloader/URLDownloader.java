package ru.ncedu.Shabatin.URLDownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.ncedu.Shabatin.utils.ConsoleHelper;
import ru.ncedu.Shabatin.utils.FileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.ncedu.Shabatin.URLDownloader.URLDownloader.URLparser.getExt;
import static ru.ncedu.Shabatin.URLDownloader.URLDownloader.UserInterviewer.confirmRewriting;
import static ru.ncedu.Shabatin.URLDownloader.URLDownloader.UserInterviewer.pathRequest;

/**
 * Class, which download data from the website, presented as an instance of WebPage.
 *
 * @see WebPage
 * @see PathState
 */
class URLDownloader {
    private static final String DEFAULT_NAME = "index.html";
    private static final String DEFAULT_PATH = System.getProperty("user.dir");
    private WebPage webPage;
    private File pagePath;

    URLDownloader(String url, String pathStr) throws IOException {
        try {
            setWebPage(new WebPage(url));
        } catch (IOException e) {
            System.out.println("Incorrect URL or access denied");
            System.exit(1);
        }
        if (pathStr == null)
            detPath(PathState.checkPath(null));
        else {
            File path = new File(pathStr);
            setPagePath(path);
            detPath(PathState.checkPath(path));
        }
    }

    WebPage getWebPage() {
        return webPage;
    }

    private void setWebPage(WebPage webPage) {
        this.webPage = webPage;
    }

    File getPagePath() {
        return pagePath;
    }

    private void setPagePath(File pagePath) {
        this.pagePath = pagePath;
    }

    /**
     * Write an html-code in the file.
     *
     * @throws IOException
     */
    void writePage() throws IOException {
        File file = new File(getPagePath().toString());
        FileManager.writeFile(getWebPage().getHtml(), file, getWebPage().getCharSet());
    }

    /**
     * Determine a path to file and its resources.
     *
     * @param pathState
     * @throws IOException
     * @see PathState
     */
    private void detPath(PathState pathState) throws IOException {
        URL url = webPage.getUrl();
        String fileName = URLparser.getName(url);
        switch (pathState) {
            case NULL:
                setPagePath(new File(DEFAULT_PATH, fileName));
                break;
            case NONEXISTENT:
                setPagePath(new File(getPagePath().toString()));
                break;
            case FILE:
                // if file is already exist, detPath confirmRewriting and rewrites or recurses with a new path.
                if (confirmRewriting())
                    setPagePath(new File(getPagePath().toString()));
                else {
                    setPagePath(pathRequest());
                    detPath(PathState.checkPath(getPagePath()));
                }
                break;
            case DIRECTORY:
                setPagePath(new File(getPagePath().toString(), fileName));
                break;
        }
    }

    void openPage(File file) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }

    /**
     * @param dirStr - directory for resources of page.
     * @throws IOException
     */
    void writeImg(String dirStr) throws IOException {
        List<URL> imgList = getWebPage().getImg();
        if (imgList != null) {
            for (int i = 0; i < imgList.size(); i++) {
                URL imageURL = imgList.get(i);
                //If it is impossible to know the extension from the url, uses defaultExt.
                String ext = getExt(imageURL.toString(), "jpg");
                File file = new File(dirStr, "img" + i + ".jpg");
                file.createNewFile();
                BufferedImage image;
                try {
                    image = ImageIO.read(imageURL);// 404
                    ImageIO.write(image, ext, file);
                    // Names of the type img.txt are used because of the complexity of parsing some URLs.
                    updateHtml("img", "src", imageURL.toString(), getPagePath() + "_files\\img" + i + ".jpg");
                } catch (IllegalArgumentException | IOException ignored) {
                    //Some of images's URL can be unreadable, its just skipped.
                }
            }
        }
    }

    //Comments on writeImg also apply to writeLinks
    void writeLinks(String dirStr) throws IOException {
        List<URL> resList = getWebPage().getLinks();
        if (resList != null) {
            for (int i = 0; i < resList.size(); i++) {
                URL resURL = resList.get(i);
                File file = new File(dirStr, "res" + i + ".txt");
                file.createNewFile();
                FileManager.writeFile(WebPage.initHtml(resURL, "UTF-8"), file, "UTF-8");
                updateHtml("link", "href", resURL.toString(), getPagePath() + "_files\\res" + i + ".txt");
            }
        }
    }

    /**
     * Replaces resources's oldLinks in html-string with new local paths.
     *
     * @param cssQuery
     * @param attributeKey
     * @param oldURL
     * @param newPath
     */
    private void updateHtml(String cssQuery, String attributeKey, String oldURL, String newPath) {
        Document page = Jsoup.parse(getWebPage().getHtml());
        Elements resElements = page.select(cssQuery);
        /*
        We must compare each of the URLs from the HTML string with the replaced,
        because of the possible presence of unreadable URLs.
        */
        for (Element res : resElements) {
            if (res.attr(attributeKey).equals(oldURL)) {
                res.attr(attributeKey, newPath);
            }
        }
        getWebPage().setHtml(page.outerHtml());
    }

    //Class to communicate with the user.
    static class UserInterviewer {
        static boolean confirmRewriting() {
            ConsoleHelper.println("Rewrite a file?");
            String answer = ConsoleHelper.stringRequest();
            if (answer.matches("Да|Yes|Y|y"))
                return true;
            else if (answer.matches("Нет|No|N|n")) {
                return false;
            } else {
                ConsoleHelper.println("Incorrect answer, please, try again");
                return confirmRewriting();
            }
        }

        static File pathRequest() {
            ConsoleHelper.println("Enter a new path");
            File path = new File(ConsoleHelper.stringRequest());
            if (PathState.checkPath(path) == PathState.FILE && !confirmRewriting()) {
                return pathRequest();
            }
            return path;
        }
    }

    static class URLparser {
        /**
         * @param url
         * @return A name of Name of the future html-file
         */
        static String getName(URL url) {
            String path = url.getPath();
            if (path.endsWith("/"))
                path = path.substring(0, path.length() - 1);
            if (path.equals(""))
                return DEFAULT_NAME;
            String result = path.substring(path.lastIndexOf("/") + 1);
            if (!result.endsWith(".html"))
                result += ".html";
            return result;
        }

        /**
         * @param url
         * @param defaultExt
         * @return an extension of resources, needed for ImageIO, so the pattern is optimized for images.
         */
        static String getExt(String url, String defaultExt) {
            Pattern extPattern = Pattern.compile("(.*)\\.((.){2,5})$");
            Matcher extMatcher = extPattern.matcher(url);
            return extMatcher.find() ? extMatcher.group(2) : defaultExt;
        }
    }
}

