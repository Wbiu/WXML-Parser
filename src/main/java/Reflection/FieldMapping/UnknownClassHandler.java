package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import Reflection.FieldObject;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.InvocationTargetException;

public class UnknownClassHandler extends DocumentDeserializer {

    public static <T> Object map(FieldObject fo,Object classObj ,Element element){
        try {
            Mapping mapping = new Mapping();
            NodeList nodeList = findeNodeByName(fo.getFieldName(), element);
            if (nodeList.getLength() > 1) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (isElementNode(nodeList.item(i))) {
                        T tObj = (T) mapping.mapToClassOfT(fo.getFieldValue().getClass(), (Element) nodeList.item(i));
                        fo.replaceNewValueToClassObj(tObj, classObj);
                    }
                }
            }

        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
        return classObj;
    }

    protected static<T> Object map(Node node, Class<?> classType) {
        T obj = null;
        //NodeList nl = findeNodeByName(classType.getSimpleName(),(Element) node);
        //NodeList nl = node.getChildNodes();
        Mapping mapping = new Mapping();
        try {
            obj = (T) mapping.mapToClassOfT(classType, (Element) node);
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
        /*
        if (nl.getLength() >= 1) {
            for (int i = 0; i < nl.getLength(); i++) {
                String nm1 = nl.item(i).getNodeName();
                if (isElementNode(nl.item(i))) {
                    try {
                        obj = (T) mapping.mapToClassOfT(classType, (Element) nl.item(i));
                        System.out.println();
                    } catch (WxmlExeption e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
         */
        return obj;
    }
    public static <T> Object mapFromCollection(Class<T> classObj ,Element element){
         T created = null;
        NodeList nl = element.getChildNodes();
        Mapping mapping = new Mapping();
        if(nl.getLength() > 1){
            try {
                created = mapping.mapToClassOfT(classObj,element);
            } catch (WxmlExeption e) {
                throw new RuntimeException(e);
            }
        }
        return  created == null ?  (created = (T)createClassOfT(classObj)): created;
    }
    private static<T> Object createClassOfT(Class<T> classObj ){
        try {
            return classObj.getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
