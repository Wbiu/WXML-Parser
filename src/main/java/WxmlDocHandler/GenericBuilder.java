package WxmlDocHandler;

import Reflection.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class GenericBuilder {
    public static Element buildElement(FieldObject fo, Document doc) {
        fo.fieldType = EvaluateFieldType.determineFieldType(fo.getFieldValue());
        return Inspect.inspectFieldObject(fo,doc);
    }
}
