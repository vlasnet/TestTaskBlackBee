package vlasyuk.testtaskblackbee;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.jsoup.helper.Validate;
import org.xml.sax.SAXException;

/**
 *
 * @author Sergey
 */
public class Main {

    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        /*Two commented strings below need for starting parser as mentioned in test assignment 
        "~$ java -jar yourProgram.jar <keyword>" but now I don't know how to made that jar file*/
        
//        Validate.isTrue(args.length == 1, "usage: supply keyword to fetch");
//        String keyword = args[0]; 

        /*I use this variable to work test of the program*/
        String keyword = "bikini";
        
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        
        PagesParsing.doPagesParsing(ListLinks.getListLinks(keyword).get(0), keyword);
        
        long lEndTime = System.nanoTime();
        long duration = lEndTime - startTime;
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        
        System.out.println("Run-time in seconds: " + duration / 1000000/1000);
        System.out.println("Memory Footprint: " + actualMemUsed/1024/1024 + "Mb");
    }
}
