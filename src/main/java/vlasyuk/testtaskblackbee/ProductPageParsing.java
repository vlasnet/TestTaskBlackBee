package vlasyuk.testtaskblackbee;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sergey
 */
public class ProductPageParsing {
    
    public static Product getProductData(String link) throws IOException {
        Product product = new Product();
        
        Document doc = Jsoup.connect(link).maxBodySize(0).timeout​(60000).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();

        Element productBrandAndName = doc.selectFirst("h1[class^=productName_]");
        String productBrand;
        String productName;
        if (productBrandAndName == null) {
            productBrand = "N/A";
            productName = "N/A";
            System.out.println("Product on link: " + link + " hasn't name and brand tag");
        } else {
            productBrand = productBrandAndName.text().substring(0, productBrandAndName.text().indexOf("|")).trim();
            productName = productBrandAndName.text().substring(productBrandAndName.text().indexOf("|") + 1).trim();
        }

        Element description = doc.selectFirst("meta[name=description]");
        String productDescription;
        String productColor;
        if (description == null) {
            productDescription = "N/A";
            productColor = "N/A";
            System.out.println("Product on link: " + link + " hasn't description tags");
        } else {
            productDescription = description.attr("content");
            productColor = productDescription.substring(productDescription.lastIndexOf(" in ") + 3, productDescription.indexOf("bei ABOUT YOU")).trim();
        }
        
        /*Another variant for product description that consist data from Produktdetails fields*/
        
//        Elements descriptionHeaders = doc.select("div[class^=outerWrapper_] p[class^=subline_]");
//        String productDescription = "";
//        if (descriptionHeaders.isEmpty()) {
//            productDescription = "N/A";
//            System.out.println("Product on link: " + link + " hasn't description tags");
//        } else {
//            for (Element elem : descriptionHeaders) {
//                Elements description = doc.select(("p:containsOwn(" + elem.text() + ") + div ul[class^=orderedList_] li"));
//                productDescription += "\n" + elem.text() + ":\n";
//                for (Element descriptionElem : description) {
//                    productDescription += descriptionElem.text() + ", ";
//                }
//            }
//        }

        Element price = doc.selectFirst("span[class^=finalPrice_]");
        String productPrice;
        if (price == null) {
            productPrice = "N/A";
            System.out.println("Product on link: " + link + " hasn't price tag");
        } else {
            productPrice = price.text();
        }

        Element initialPrice = doc.selectFirst("span[class^=originalPrice_]");
        String productInitialPrice;
        if (initialPrice == null) {
            productInitialPrice = productPrice;
        } else {
            productInitialPrice = initialPrice.text();
        }

        Element articleID = doc.selectFirst(":containsOwn(Artikel-Nr:)");
        String productArticleID;
        if (articleID == null) {
            productArticleID = "N/A";
            System.out.println("Product on link: " + link + " hasn't articleID tag");
        } else {
            productArticleID = articleID.text();
            productArticleID = productArticleID.substring(productArticleID.indexOf("Nr:") + 4);
        }  
        
        product.setBrand(productBrand);
        product.setName(productName);
        product.setColor(productColor);
        product.setPrice(productPrice);
        product.setInitialPrice(productInitialPrice);
        product.setArticleID(productArticleID);
        product.setDescription(productDescription);

        return product;
    }
}
