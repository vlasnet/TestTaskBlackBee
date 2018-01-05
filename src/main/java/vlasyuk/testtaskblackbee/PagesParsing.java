package vlasyuk.testtaskblackbee;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

/**
 *
 * @author Sergey
 */
public class PagesParsing {

    public static void doPagesParsing(String link, String keyword) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String source = "https://www.aboutyou.de";
        Set<String> productLinks = new LinkedHashSet<>();
        Set<String> thumbnailsLinks = new LinkedHashSet<>();
        String pageLink;
        int productCounter = 0;

        Document doc = Jsoup.connect(link).maxBodySize(0).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
        Element lastPage = doc.select("li[class^=pageNumbers_] > a").last();

        int pagesAmount = Integer.parseInt(lastPage.text());
        String linkCommonPart = lastPage.attr("href").substring(0, lastPage.attr("href").indexOf(lastPage.text()));

        System.out.println(pagesAmount + " markers found...");
                
        for (int i = 1; i <= pagesAmount; i++) {
            pageLink = linkCommonPart + i;
            Document document = Jsoup.connect(pageLink).maxBodySize(0).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
            Elements productsOnPage = document.select("div[class^=col-sm-6 col-md-4] a[class=anchor_wgmchy]");

            for (Element elem : productsOnPage) {
                productLinks.add(source + elem.attr("href"));
            }

            System.out.println(productLinks.size() + " products found on page №" + i);

            for (String productPageLink : productLinks) {
                Document pageDocument = Jsoup.connect(productPageLink).maxBodySize(0).timeout​(60000).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();

                Elements productThumbnails = pageDocument.select("div[class^=thumbsWrapper_]>a");
                
                if (productThumbnails.size() > 1) {                    
                    for (Element elem : productThumbnails) {
                        thumbnailsLinks.add(source + elem.attr("href"));
                    }
                    System.out.println(thumbnailsLinks.size() + " variants of same product was found on product page");
                    
                    for (String linkFromSet : thumbnailsLinks) {
                        XMLHandling.addDataToXml(keyword, ProductPageParsing.getProductData(linkFromSet));
                        productCounter++;
                    }
                    thumbnailsLinks.clear();
                } else {
                    XMLHandling.addDataToXml(keyword, ProductPageParsing.getProductData(productPageLink));
                    productCounter++;
                }
            }
            productLinks.clear();
        }
        System.out.println("Amount of extracted products: " + productCounter);
    }
}
