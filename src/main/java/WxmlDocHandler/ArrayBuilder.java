package WxmlDocHandler;

import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class ArrayBuilder {

    public static Element buildElement(FieldObject fo, Document doc)  {
        List<?> list = ObjectValidator.convertObjectToList(fo.getFieldValue());
        Element mainElement = doc.createElement(fo.getFieldName());
        list.forEach(obj -> mainElement.appendChild(buildArrayElement(obj,doc)));
        return mainElement;
    }
    private static Element buildArrayElement(Object obj,Document doc) {
        Element subElement = doc.createElement(obj.getClass().getSimpleName());
        subElement.setTextContent(obj.toString());
        return subElement;
    }
}
