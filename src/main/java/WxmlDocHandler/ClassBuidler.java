package WxmlDocHandler;

import Exeption.WxmlExeption;
import Reflection.FieldObject;
import Reflection.Reflection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class ClassBuidler {
    public static Element buildElement(FieldObject fo, Document doc){
        try {
            List<Object> list = reflectClassObj(fo.getFieldValue());
            WxmlBuilder builder = new WxmlBuilder(fo.getFieldName(),list);
            return builder.getRootElement();
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
    }
    private static List reflectClassObj(Object clazzObj) {
        Reflection ref = new Reflection(clazzObj);
        return ref.getListofFields();
    }

}
