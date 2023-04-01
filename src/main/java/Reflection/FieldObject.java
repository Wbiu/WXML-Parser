package Reflection;

import Exeption.WxmlExeption;
import java.lang.reflect.Field;

/**
 * Withhold the data from the FieldObjectBuild
 */

public class FieldObject{
    private String fieldName = "";
    private Object fieldValue;
    private Field field;
    public FieldType fieldType;
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public Object getFieldValue() {
        return fieldValue;
    }
    FieldObject(){}
    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setField(Field field){this.field = field;}

    public Field getField(){return this.field;}

    public Object replaceNewValueToClassObj(Object newValue,Object classObj) throws WxmlExeption {
        try {
            this.field.set(classObj,replacePreCkeck(newValue));
        } catch (IllegalAccessException e) {
            throw new WxmlExeption(e);
        }
        return classObj;
    }
    private Object replacePreCkeck(Object newValue){

        if(fieldType == FieldType.PRIMITIVE){

            if(this.field.getType() == int.class){
                newValue = Integer.parseInt((String) newValue);
            }
            if(this.field.getType() == double.class){
                newValue = Double.parseDouble((String) newValue);
            }
        }
        return newValue;
    }
}
