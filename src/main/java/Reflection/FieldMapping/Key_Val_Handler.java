package Reflection.FieldMapping;

import Reflection.FieldType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Key_Val_Handler {

    public static Object handle(Node node, FieldType kFieldType,Object classType){
        return switch (kFieldType) {
            case PRIMITIVE ->  PrimitivFieldHander.map(node,(Class<?>)classType);
            case USER_DEFINEDCLASS -> UnknownClassHandler.map(node,(Class<?>)classType);
            case COLLECTION -> CollectionFieldHandler.map(node,(Class<?>)classType);
            case ARRAY -> ArrayFieldHandler.map(node,(Class<?>)classType);
            case GENERIC -> GenericFieldHandler.map(node,(Class<?>)classType);
            case USER_DEFINEDARRAY -> CustomArray.map(node,(Class<?>)classType);
            case HASHMAP -> HashMapFieldHandler.map(null,classType,(Element)node);
        };
    }
}
