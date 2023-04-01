package Reflection.FieldMapping;
import Exeption.WxmlExeption;
import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomArray extends DocumentDeserializer {

    public static <T> Object map(FieldObject fo, Object classOfT, Element element){
        //T[] customArray = (T[]) new Object[Array.getLength(fo.getFieldValue())];
        T[] customArray = Arrays.copyOf((T[])fo.getFieldValue(),Array.getLength(fo.getFieldValue()));
        for (int i = 0; i < customArray.length; i++) {
            customArray[i] = null;
        }
        NodeList nodeList = findeNodeByName(fo.getFieldName(), element);
        int insertIndex = 0;
        if(nodeList.getLength()>=1){
            NodeList childNodes = nodeList.item(0).getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if(isElementNode(node)){
                    if(ObjectValidator.isKnownClass(node.getNodeName())){
                        customArray[insertIndex]=(T) ObjectValidator.toGivenDataType(node.getNodeName(),node.getTextContent(),false);
                    }else{
                        Class<?> componentType = fo.getField().getType().componentType();
                        customArray[insertIndex] =(T)UnknownClassHandler.map(node,componentType);
                    }
                    insertIndex++;
                }
            }
        }
        try {
            classOfT = fo.replaceNewValueToClassObj(customArray,classOfT);
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
        return  classOfT;
    }
    protected static <E,T> Object map(Node node,Class<?> classType) {
        NodeList nodeList = node.getChildNodes();
        List<E> list = new ArrayList<>();
        if(nodeList.getLength()>=1){
            NodeList childNodes = nodeList.item(0).getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node n = childNodes.item(i);
                if(isElementNode(n)){
                    if(ObjectValidator.isKnownClass(n.getNodeName())){
                        list.add((E)ObjectValidator.toGivenDataType(n.getNodeName(),n.getTextContent(),false));
                    }else{
                        list.add((E)UnknownClassHandler.map(n,classType));
                    }
                }
            }
        }
        T[] arr = (T[]) list.toArray();
        return arr;
    }



}
