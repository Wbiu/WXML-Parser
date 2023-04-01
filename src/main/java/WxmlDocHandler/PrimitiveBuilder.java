package WxmlDocHandler;

import Reflection.FieldObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PrimitiveBuilder {

    public static Element buildElement(FieldObject fo, Document doc){
        Element mainElement = doc.createElement(fo.getFieldName());
        mainElement.setTextContent(fo.getFieldValue().toString());
        return mainElement;
    }
}
