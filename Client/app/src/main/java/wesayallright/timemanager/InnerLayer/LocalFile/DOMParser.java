package wesayallright.timemanager.InnerLayer.LocalFile;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
            Log.i("ParseXML", f.getAbsolutePath());
            document = builder.parse(f);
        } catch (ParserConfigurationException|SAXException|IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 输出完整xml文件
     * @param e
     */
    public static void printXML(Document e) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            t.transform(new DOMSource(e), new StreamResult(bos));
            System.out.println(bos.toString());
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
    }
    public static void printXML(Element e) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            t.transform(new DOMSource(e), new StreamResult(bos));
            System.out.println(bos.toString());
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
    }
}