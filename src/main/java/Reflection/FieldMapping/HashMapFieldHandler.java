package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import Reflection.EvaluateFieldType;
import Reflection.FieldObject;
import Reflection.FieldType;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public class HashMapFieldHandler extends DocumentDeserializer {

    private final static String ENTRY = "entry";
    private final static String KEY = "key";
    private final static String VAL = "val";

    public static <K,V> Object map(FieldObject fo, Object clazzOfT, Element element) {

        NodeList nl;
        String fieldVar_Name;
        ParameterizedType pType;

        if(fo == null){
            nl = element.getChildNodes();
            fieldVar_Name = "HashMap";
            pType =  (ParameterizedType) clazzOfT;
        }else{
            nl = findeNodeByName(fo.getFieldName(), element);
            fieldVar_Name = fo.getFieldName();
            pType = (ParameterizedType) fo.getField().getGenericType();
        }

        K kType;
        V vType;

        HashMap <K,V> map = new HashMap<>();


        Type[] types = pType.getActualTypeArguments();
        kType =(K) types[0];
        vType =(V) types[1];

        FieldType k_fieldType = EvaluateFieldType.determineFieldType(kType);
        FieldType v_fieldType = EvaluateFieldType.determineFieldType(vType);


        if(nl.getLength()>=1){

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if(node.getNodeName().equals(fieldVar_Name)){
                    NodeList mapChildNodes = node.getChildNodes();

                    for (int j = 0; j < mapChildNodes.getLength(); j++) {
                        Node entryNode = mapChildNodes.item(j);
                        if(entryNode.getNodeName().equals(ENTRY)){
                            NodeList entryChildNode = entryNode.getChildNodes();

                            Object prmKey = null;
                            for (int k = 0; k < entryChildNode.getLength(); k++) {

                                Node key_Val_Node = entryChildNode.item(k);

                                if(key_Val_Node.getNodeName().equals(KEY)){
                                    Object obj = Key_Val_Handler.handle(key_Val_Node,k_fieldType,kType);
                                    prmKey = obj;
                                    map.put((K)obj,null);
                                }
                                if (key_Val_Node.getNodeName().equals(VAL)) {
                                    Object obj = Key_Val_Handler.handle(key_Val_Node, v_fieldType, vType);
                                    map.put((K) prmKey, (V) obj);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(fo != null){
            try {
                clazzOfT =  fo.replaceNewValueToClassObj(map,clazzOfT);
            } catch (WxmlExeption e) {
                throw new RuntimeException(e);
            }
        }else{
            return map;
        }

        return clazzOfT;
    }

    /*
    // to do !!
    /// test method use the method above and use null for  FieldObject parameter
    // and chech whatever if FieldObject parameter is null !!
    protected static <K,V> Object map(Node node,Object classType) {
        NodeList nl = node.getChildNodes();
        ParameterizedType pType =  (ParameterizedType) classType;

        K kType = (K) pType.getActualTypeArguments()[0];
        V vType = (V) pType.getActualTypeArguments()[1];


        HashMap<K,V> map = new HashMap<>();

        FieldType k_fieldType = EvaluateFieldType.determineFieldType(kType);
        FieldType v_fieldType = EvaluateFieldType.determineFieldType(vType);


        String fieldVar_Name = "HashMap";


        if(nl.getLength()>=1){

            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                String nm = n.getNodeName();
                if(n.getNodeName().equals(fieldVar_Name)){
                    NodeList mapChildNodes = n.getChildNodes();

                    for (int j = 0; j < mapChildNodes.getLength(); j++) {
                        Node entryNode = mapChildNodes.item(j);
                        String subName = entryNode.getNodeName();
                        if(entryNode.getNodeName().equals(ENTRY)){
                            NodeList entryChildNode = entryNode.getChildNodes();

                            Object prmKey = null;
                            for (int k = 0; k < entryChildNode.getLength(); k++) {

                                Node key_Val_Node = entryChildNode.item(k);
                                String kayValStg = key_Val_Node.getNodeName();

                                //if node == key or val
                                // if(key_Val_Node.getNodeName().equals(KEY) || key_Val_Node.getNodeName().equals(VAL)){}
                                if(key_Val_Node.getNodeName().equals(KEY)){

                                    if(k_fieldType ==  FieldType.HASHMAP){
                                        Object obj = Key_Val_Handler.handle(key_Val_Node,v_fieldType,vType);
                                        prmKey = obj;
                                        map.put((K)obj,null);
                                    }else{
                                        Object obj = Key_Val_Handler.handle(key_Val_Node,k_fieldType, kType);
                                        prmKey = obj;
                                        map.put((K)obj,null);
                                    }
                                }
                                if(key_Val_Node.getNodeName().equals(VAL)){

                                    if(k_fieldType ==  FieldType.HASHMAP){
                                        Object obj =  Key_Val_Handler.handle(key_Val_Node,v_fieldType,vType);
                                        map.put((K)prmKey,(V)obj);
                                    }else{
                                        Object obj = Key_Val_Handler.handle(key_Val_Node,v_fieldType,vType);
                                        map.put((K)prmKey,(V)obj);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return map;
    }
     */

}
