package WxmlDocHandler;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Transform the Document into pretty string
 */
public class XmlTransform {

    public static String xmlToPrettyString(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource documentObjectModelSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();

        //Output Option
//        StreamResult streamresult = new StreamResult(System.out);
//        StreamResult streamresult = new StreamResult(new File("test.xml"));
        StreamResult streamresult = new StreamResult(writer);
        transformer.transform(documentObjectModelSource, streamresult);
        String outputString = writer.toString();

        return outputString;
    }
}
