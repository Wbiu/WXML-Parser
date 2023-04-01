package Reflection;

import Exeption.WxmlExeption;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Reflection {
    private Object clazzObj = null;
    private List<FieldObject> listOfFiedObj;
    public String getclassName(){
        return this.clazzObj.getClass().getSimpleName();
    }

    public Reflection(Object clazzObj){
        this.clazzObj = clazzObj;
        reflect();
    }
    public Object getClazzObj() {
        return clazzObj;
    }

    public <T> Reflection(Class<T> clazzOfT) throws WxmlExeption {
        try {
            this.clazzObj = clazzOfT.getConstructor().newInstance();
            reflect();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |NoSuchMethodException e) {
            throw new WxmlExeption(e);
        }
    }
    public List getListofFields(){
        return  this.listOfFiedObj;
    }
    private void reflect(){
        fieldFilter(this.clazzObj.getClass().getDeclaredFields());
    }
    private void fieldFilter(Field[] fields){
        this.listOfFiedObj = FieldFilter.filterAsList(fields,this.clazzObj).stream().map(this::createFieldObj).toList();
    }
    private FieldObject createFieldObj(Field field) {
        try {
            field.setAccessible(true);
            return FieldObjectBuilder.build(field,this.clazzObj);
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
    }
}
