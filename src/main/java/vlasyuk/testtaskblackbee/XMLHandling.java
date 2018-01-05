package vlasyuk.testtaskblackbee;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Sergey
 */
public class XMLHandling {

    public static void createXml(String file, Product product) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {

        String filename = file + ".xml";

        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();

        Element offers = document.createElement("offers");
        document.appendChild(offers);

        Element offer = document.createElement("offer");
        offers.appendChild(offer);
        XMLHandling.fillingOffer(document, offer, product);

        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(
                new File(System.getProperty("user.dir")
                        + File.separator + filename));

        transformer.transform(source, result);

        System.out.println("File " + filename + " was saved!");
    }

    public static void addDataToXml(String file, Product product) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        String filename = file + ".xml";
        if ((new File(filename)).exists()) {
            final String filepath = System.getProperty("user.dir")
                    + File.separator + filename;
            final File xmlFile = new File(filepath);
            DocumentBuilder db = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document document = db.parse(xmlFile);
            document.normalize();

            Node offers = document.getFirstChild();

            Element offer = document.createElement("offer");
            offers.appendChild(offer);
            XMLHandling.fillingOffer(document, offer, product);

            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

            System.out.println("New product added in file " + filename);
        } else {
            XMLHandling.createXml(file, product);
        }
    }

    private static void fillingOffer(Document document, Element offer, Product product) {
        Element name = document.createElement("name");
        name.setTextContent(product.getName());
        offer.appendChild(name);

        Element brand = document.createElement("brand");
        brand.setTextContent(product.getBrand());
        offer.appendChild(brand);

        Element color = document.createElement("color");
        color.setTextContent(product.getColor());
        offer.appendChild(color);

        Element price = document.createElement("price");
        price.setTextContent(product.getPrice());
        offer.appendChild(price);

        Element initialPrice = document.createElement("initialPrice");
        initialPrice.setTextContent(product.getInitialPrice());
        offer.appendChild(initialPrice);

        Element description = document.createElement("description");
        description.setTextContent(product.getDescription());
        offer.appendChild(description);

        Element articleID = document.createElement("articleID");
        articleID.setTextContent(product.getArticleID());
        offer.appendChild(articleID);
    }
}
