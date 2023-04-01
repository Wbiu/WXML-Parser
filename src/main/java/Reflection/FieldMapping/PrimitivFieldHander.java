package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import ObjectViladator.ObjectValidator;
import Reflection.FieldObject;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Arrays;

public class PrimitivFieldHander extends DocumentDeserializer {

    public static Object map(FieldObject fo,Object classObj ,Element rootElement) throws WxmlExeption {
        NodeList nl = findeNodeByName(fo.getFieldName(),rootElement);
        if(nl.getLength()>=1){
            for (int i = 0; i < nl.getLength(); i++) {
                if(isElementNode(nl.item(i))){
                    Object newValue = nl.item(i).getTextContent();
                    classObj = fo.replaceNewValueToClassObj(newValue,classObj);
                }
            }
        }
        return classObj;
    }

    protected static Object map(Node node,Class<?> classType) {
        NodeList nl = node.getChildNodes();
        Object obj = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            String t= n.getNodeName();
            if(isElementNode(n)){
                obj = ObjectValidator.toGivenDataType(classType.getSimpleName(),n.getTextContent(),true);
            }
        }
        return obj;
    }
}
