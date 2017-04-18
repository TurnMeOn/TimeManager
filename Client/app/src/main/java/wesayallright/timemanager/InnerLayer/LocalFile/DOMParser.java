package wesayallright.timemanager.InnerLayer.LocalFile;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 用于解析xml文件
 */
public class DOMParser {
    private DocumentBuilderFactory BuildFactory = DocumentBuilderFactory.newInstance();

    //Load and parse XML file into DOM
    public Document parse(File f) {
        Document document = null;
        try {
            //DOM parser instance
            DocumentBuilder builder = BuildFactory.newDocumentBuilder();
            //parse an XML file into a DOM tree
            document = builder.parse(f);
        } catch (ParserConfigurationException|SAXException|IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}