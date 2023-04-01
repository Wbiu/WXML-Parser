package WxmlDocHandler;

import Reflection.FieldObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.HashMap;
import java.util.Map;

public class HashMapBuilder extends KeyValBuilder{

    public static Element buildElement(FieldObject fo, Document doc) {
        HashMap<Object,Object> hashMap = (HashMap) fo.getFieldValue();
        Element mainElement = doc.createElement(fo.getFieldName());

        for(Map.Entry<Object,Object> entry : hashMap.entrySet()){
            Element entryElement  = doc.createElement("entry");
            entryElement.appendChild(keyBuilder(entry.getKey(),doc));
            entryElement.appendChild(valBuilder(entry.getValue(),doc));
            mainElement.appendChild(entryElement);
        }
        return  mainElement;
    }

}
