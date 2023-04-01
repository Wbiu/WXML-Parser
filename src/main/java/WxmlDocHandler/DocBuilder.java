package WxmlDocHandler;

import Exeption.WxmlExeption;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DocBuilder {
    private Document doc = null;
    public DocBuilder() throws WxmlExeption {}
    private void createDoc() throws WxmlExeption {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            docBuilder = docFactory.newDocumentBuilder();
            this.doc = docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new WxmlExeption(e);
        }
    }
    public Document getDoc() throws WxmlExeption {
        createDoc();
        return this.doc;
    }
    public Document parseDoc(String fileName) throws WxmlExeption {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(new File(fileName));
        } catch (ParserConfigurationException e) {
            throw new WxmlExeption(e);
        } catch (IOException e) {
            throw new WxmlExeption(e);
        } catch (SAXException e) {
            throw new WxmlExeption(e);
        }

    }

}
