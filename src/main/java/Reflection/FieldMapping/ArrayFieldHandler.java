package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ArrayFieldHandler extends DocumentDeserializer {

    public static <T> Object map(FieldObject fo,Object classObj ,Element element) throws WxmlExeption {

        List<T> list =  new ArrayList<>();
        NodeList arrayNode = findeNodeByName(fo.getFieldName(), element);
        String type = arrType(fo.getFieldValue());

        if(arrayNode.getLength()>=1){
            NodeList childNodesOfArrayNode = arrayNode.item(0).getChildNodes();
            for (int i = 0; i < childNodesOfArrayNode.getLength(); i++) {
                Node childNode = childNodesOfArrayNode.item(i);
                if(isElementNode(childNode)){
                    list.add((T)childNode.getTextContent());
                }
            }
        }
        Object obj = ObjectValidator.ListToArray(list,type);
        fo.replaceNewValueToClassObj(obj,classObj);
        return classObj;
    }

    protected static <T> Object map(Node node,Class<?> classType) {
        List<T> list =  new ArrayList<>();
        NodeList arrayNode = node.getChildNodes();
        String type = classType.getSimpleName();

        if(arrayNode.getLength()>=1){
            NodeList childNodesOfArrayNode = arrayNode.item(0).getChildNodes();
            for (int i = 0; i < childNodesOfArrayNode.getLength(); i++) {
                Node childNode = childNodesOfArrayNode.item(i);
                if(isElementNode(childNode)){
                    list.add((T)childNode.getTextContent());
                }
            }
        }
        return ObjectValidator.ListToArray(list,type);
    }
    private static String arrType(Object value){
       return value.getClass().getComponentType().getSimpleName();
    }
}
