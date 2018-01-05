package vlasyuk.testtaskblackbee;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Sergey
 */
public class ListLinks {

    public static List<String> getListLinks(String keyword) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        String source = "https://www.aboutyou.de";
        List<String> listLinks = new LinkedList<>();
        String url;
        print("Fetching %s...", source);

        Document doc = Jsoup.connect(source).maxBodySize(0).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            if (link.text().toLowerCase().equals(keyword)) {
                listLinks.add(link.attr("abs:href"));
            }
        }
        return listLinks;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    } 
}
