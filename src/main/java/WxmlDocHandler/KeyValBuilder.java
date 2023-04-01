package WxmlDocHandler;

import Reflection.FieldObject;
import Reflection.FieldObjectBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Build the Key and Val of each HashMap
 */
public class KeyValBuilder {

    protected static Element keyBuilder(Object keyObj,Document doc){
        Element key = doc.createElement("key");
        FieldObject fo  = FieldObjectBuilder.build(keyObj);
        Element subElement = objChecking(fo,doc);
        key.appendChild(subElement);
        return key;
    }

    protected static Element valBuilder(Object valObj,Document doc){
        Element val = doc.createElement("val");
        FieldObject fo  = FieldObjectBuilder.build(valObj);
        Element subElement = objChecking(fo,doc);
        val.appendChild(subElement);
        return  val;
    }

    private static Element objChecking(FieldObject fo,Document doc){
            return Inspect.inspectFieldObject(fo,doc);
    }
}
