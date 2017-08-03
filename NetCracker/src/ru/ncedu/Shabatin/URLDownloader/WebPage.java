package ru.ncedu.Shabatin.URLDownloader;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a web page as its url, html code and links to resources.
 *
 * @author SHABATIN FILIP
 */
class WebPage {
    private URL url;
    private String html;
    private final List<URL> IMAGES;
    private final List<URL> LINKS;

    WebPage(String URLStr) throws IOException {
        setUrl(new URL(URLStr));
        setHtml(initHtml(getUrl(), getCharSet()));
        IMAGES = initRes("img", "src");
        LINKS = initRes("link", "href");
    }

    URL getUrl() {
        return url;
    }

    private void setUrl(URL url) {
        this.url = url;
    }

    List<URL> getImg() {
        return IMAGES;
    }

    List<URL> getLinks() {
        return LINKS;
    }

    String getHtml() {
        return html;
    }

    void setHtml(String html) {
        this.html = html;
    }


    /**
     * @param cssQuery
     * @param attributeKey
     * @return List with URLs of resources.
     * @throws IOException
     */
    private List<URL> initRes(String cssQuery, String attributeKey) throws IOException {
        Document localHtml = Jsoup.parse(getHtml());
        localHtml.setBaseUri(getUrl().getProtocol() + "://" + getUrl().getAuthority());
        Elements localElements = localHtml.select(cssQuery);
        List<URL> res = new ArrayList<>();
        for (int i = 0; i < localElements.size(); i++) {
            URL resURL;
            String resStr = localElements.get(i).attr(attributeKey);
            //Some of resources's local URLs start with "..", it disturbs a normal reading of resources, so it should be removed.
            if (resStr.startsWith("..")) {
                resStr = resStr.substring(2);
                localElements.get(i).attr(attributeKey, resStr);
            }
            try {
                //Make resources's URL absolute and replacing its in html-string for the possibility of further comparison in updateHtml().
                resURL = new URL(localElements.get(i).attr("abs:" + attributeKey));
                localElements.get(i).attr(attributeKey, resURL.toString());
            } catch (MalformedURLException e) {
                continue;
            }
            if (!resURL.toString().equals(""))
                res.add(resURL);
        }
        setHtml(localHtml.outerHtml());
        return res;
    }

    /**
     * @param url
     * @param charset
     * @return String of Html-code.
     */
    static String initHtml(URL url, String charset) {
        StringBuilder resStr = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), charset))) {
            int c;
            while ((c = reader.read()) != -1)
                resStr.append((char) c);
        } catch (IOException e) {
            return "";
        }
        return resStr.toString();
    }

    String getCharSet() throws IOException {
        Document page = Jsoup.connect(getUrl().toString()).get();
        return page.charset().toString();
    }

    boolean isHTML() throws IOException {
        return html.toLowerCase().contains("<!doctype html");
    }
}
