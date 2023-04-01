package WxmlDocHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RootElementBuilder {

    public static Element createRootElement(String rootName, Document doc){return  doc.createElement(rootName);}
}
