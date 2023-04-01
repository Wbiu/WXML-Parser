package WxmlDocHandler;

import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import Reflection.FieldObjectBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * If an Array is a CustomClass Array
 * This Class will deserialize the Custom Array
 */
public class CustomArrayBuilder {
    public static Element buildElement(FieldObject fo, Document doc) {
        Element mainElement = doc.createElement(fo.getFieldName());
        for(Object obj : (Object[])fo.getFieldValue()){
            if(obj != null){
                Class<?> cls = obj.getClass();
                if(ObjectValidator.isPrimitiveTypeKEY_ByObj(cls)){
                    mainElement.appendChild(PrimitiveBuilder.buildElement(FieldObjectBuilder.build(obj),doc));
                }else{
                    mainElement.appendChild(doc.importNode(ClassBuidler.buildElement(FieldObjectBuilder.build(obj),doc),true));
                }
            }
        }
        return  mainElement;
    }
}
