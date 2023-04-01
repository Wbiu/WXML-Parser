package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class CollectionFieldHandler extends DocumentDeserializer{

    public static <E> Object map(FieldObject fo, Object classOfT, Element element) {
        List<E> list = new ArrayList<>();
        ParameterizedType pType = (ParameterizedType) fo.getField().getGenericType();
        Class<?> parameterClass = (Class<?>) pType.getActualTypeArguments()[0];

        Node node = findeNodeByName(fo.getFieldName(), element).item(0);
        NodeList nl = node.getChildNodes();

        if (nl.getLength() > 1) {
            if (ObjectValidator.isPrimitiveTypeKEY_ByObj(parameterClass)) {
                list = isPrimitiveCollection(parameterClass, nl);
            } else {
                list = nonePrimitiveCollection(parameterClass, node);
            }
        }

        try {
            classOfT = fo.replaceNewValueToClassObj(list, classOfT);
        } catch (WxmlExeption ex) {
            throw new RuntimeException(ex);
        }
        return classOfT;
    }

    protected static <E> Object map(Node node,Class<?> classType) {
        //Node node = findeNodeByName(fo.getFieldName(), element).item(0);
        NodeList nl = node.getChildNodes();
        List<E> list = new ArrayList<>();
        if (nl.getLength() > 1) {
            if (ObjectValidator.isPrimitiveTypeKEY_ByClass(classType)) {
                list = isPrimitiveCollection(classType, nl);
            } else {
                list = nonePrimitiveCollection(classType, node);
            }
        }
        return list;
    }

    private static <E> List isPrimitiveCollection(Class<?> parameterClass, NodeList nl){
        List<E> list = new ArrayList<>();
        switch (parameterClass.getSimpleName()){
            case "Double" :
                for (int i = 0; i <nl.getLength(); i++) {
                    if(isElementNode(nl.item(i))){
                        list.add((E)Double.valueOf(nl.item(i).getTextContent()));
                    }
                }
                break;
            case "Integer":
                for (int i = 0; i <nl.getLength(); i++) {
                    if(isElementNode(nl.item(i))){
                        list.add((E)Integer.valueOf(nl.item(i).getTextContent()));
                    }
                }
                break;

            default:
                for (int i = 0; i <nl.getLength(); i++) {
                    if(isElementNode(nl.item(i))){
                        list.add((E)nl.item(i).getTextContent());
                    }
                }
                break;
        }
        return list;
    }
    private static <E> List nonePrimitiveCollection(Class<?> parameterClass,Node node){
        List<E> list = new ArrayList<>();
        NodeList nl = node.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (isElementNode(nl.item(i))) {
                Object obj = UnknownClassHandler.mapFromCollection(parameterClass, (Element) nl.item(i));
                list.add((E) obj);
            }
        }
        return list;
    }
}
