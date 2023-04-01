package WxmlDocHandler;

import Reflection.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionBuilder {

    public static Element buildElement(FieldObject fo, Document doc){
        List<FieldObject> list = new ArrayList<>();
        ((Collection<?>) fo.getFieldValue()).stream().toList().forEach(obj -> list.add(FieldObjectBuilder.build(obj)));
        return objectChecking(doc.createElement(fo.getFieldName()),list,doc);
    }
    private static Element objectChecking(Element mainElement,List<FieldObject> list,Document doc){
      for(FieldObject fo : list){
          mainElement.appendChild(buildSubElement(fo,doc));
      }
      return mainElement;
    }
    private static Element buildSubElement(FieldObject fo,Document doc){
        return Inspect.inspectFieldObject(fo,doc);
    }

}
