package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class GenericFieldHandler <K, V> extends DocumentDeserializer {

    public static <E,K, V> Object map(FieldObject fo, Object classObj, Element element) {
        List<E> list = new ArrayList<>();
        NodeList nodeList = findeNodeByName(fo.getFieldName(), element);
        Object objToReplace = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (isElementNode(node)) {
                GenericFieldHandler gf = new GenericFieldHandler();
                Object ob = gf.inToMap(node, fo.getFieldName());
                list.add((E) ob);
            }
        }
        try {
            if(list.size() == 1){
                objToReplace = list.get(0);
                if(ObjectValidator.isCollection(fo.getFieldValue())){
                    objToReplace = unpack(list);
                }
            }else{
                objToReplace = list;
            }
            classObj =  fo.replaceNewValueToClassObj(objToReplace,classObj);
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
        return classObj;
    }
    protected static <E> Object map(Node node,Class<?> classType) {
        List<E> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        Object objToReplace = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (isElementNode(n)) {
                GenericFieldHandler gf = new GenericFieldHandler();
                Object ob = gf.inToMap(n, classType.getSimpleName());
                list.add((E) ob);
            }
        }
        if(list.size() == 1){
            objToReplace = list.get(0);
            if(classType == Collection.class ||classType == List.class || classType == ArrayList.class ){
                objToReplace = unpack(list);
            }
        }else{
            objToReplace = list;
        }
        return objToReplace;
    }

    private static <E,K,V> List unpack(List<E> list){
        HashMap<K,V> map = (HashMap)list.get(0);
        V val = null;
        for(Map.Entry<K,V> set : map.entrySet()){
            val = set.getValue();
        }
        list = (List) val;
        return list;
    }
    private <E> HashMap<K, V> inToMap(Node rootNode, String nodeName) {
        HashMap<K, V> rootMap = new HashMap<>();
        NodeList nodeList = rootNode.getChildNodes();
        List<E> vList = new ArrayList<>();
        rootMap.put((K) nodeName, null);
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node subNode = nodeList.item(j);
            String subNm = subNode.getNodeName();
            if (isElementNode(subNode)) {
                NodeList subNl = subNode.getChildNodes();
                if (subNl.getLength() == 1) {
                    String textContent = subNode.getTextContent();
                    HashMap<K, V> subMap = new HashMap<>();
                    subMap.put((K) subNm, (V) textContent);
                    vList.add((E) subMap);
                } else {
                    GenericFieldHandler gf = new GenericFieldHandler();
                    HashMap<K, V> subMap = gf.inToMap(subNode, subNm);
                    vList.add((E) subMap);
                }
            }
        }
        rootMap.put((K) nodeName, (V) vList);
        return rootMap;
    }
}
