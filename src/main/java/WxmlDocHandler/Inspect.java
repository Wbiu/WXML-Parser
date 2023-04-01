package WxmlDocHandler;

import Reflection.FieldObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Inspect {

    public static Element inspectFieldObject(FieldObject fo, Document doc){
        return switch (fo.fieldType){
            case ARRAY -> ArrayBuilder.buildElement(fo,doc);
            case PRIMITIVE -> PrimitiveBuilder.buildElement(fo,doc);
            case COLLECTION -> CollectionBuilder.buildElement(fo, doc);
            case HASHMAP -> HashMapBuilder.buildElement(fo,doc);
            case USER_DEFINEDCLASS -> (Element) doc.importNode(ClassBuidler.buildElement(fo, doc),true);
            case USER_DEFINEDARRAY -> CustomArrayBuilder.buildElement(fo,doc);
            case GENERIC -> GenericBuilder.buildElement(fo,doc);
        };
    }
}
