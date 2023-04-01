package Reflection.FieldMapping;

import Exeption.WxmlExeption;
import Reflection.*;
import WxmlDocHandler.DocumentDeserializer;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Mapping {
    private Object classOfT;
    public  <T> T mapToClassOfT(Class<T> classObj,Element element) throws WxmlExeption {
        mapToFieldObj(reflect(classObj),element);
        return (T)this.classOfT;
    }

    private  <T> List<FieldObject> reflect(Class<T> classOfT) throws WxmlExeption {
        Reflection rf = new Reflection(classOfT);
        this.classOfT = rf.getClazzObj();
        return rf.getListofFields();
    }

    private  void mapToFieldObj(List<FieldObject> fo,Element element) throws WxmlExeption {

        for(FieldObject f : fo){
            this.classOfT = switch (f.fieldType){
                case ARRAY -> ArrayFieldHandler.map(f,this.classOfT,element);
                case PRIMITIVE -> PrimitivFieldHander.map(f,this.classOfT,element);
                case COLLECTION -> CollectionFieldHandler.map(f,this.classOfT,element);
                case HASHMAP -> HashMapFieldHandler.map(f,this.classOfT,element);
                case USER_DEFINEDARRAY -> CustomArray.map(f,this.classOfT,element);
                case USER_DEFINEDCLASS -> UnknownClassHandler.map(f,this.classOfT,element);
                case GENERIC -> GenericFieldHandler.map(f,this.classOfT,element);
            };
        }
    }
}
