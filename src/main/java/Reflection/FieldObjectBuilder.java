package Reflection;

import Exeption.WxmlExeption;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Categorize the giving Field to FieldObject
 */
public class FieldObjectBuilder {
    /**
     * Builds a FieldObject by the giving field.
     * @param field
     * @param clazzObj
     * @return FieldObject
     * @throws WxmlExeption
     */
    public static FieldObject build(Field field,Object clazzObj) throws WxmlExeption {

        //Extracting data from the giving field to build the FieldObject.
        FieldObject fieldObject = new FieldObject();
        try {
            fieldObject.setField(field);
            fieldObject.setFieldName(field.getName());
            fieldObject.setFieldValue(field.get(clazzObj));
            fieldObject.fieldType = EvaluateFieldType.determineFieldType(field.get(clazzObj));
            fieldObject.fieldType = isGeneric(fieldObject,field);
        } catch (IllegalAccessException e) {
            throw new WxmlExeption(e);
        }
        return fieldObject;
    }

    /**
     * Checking if Field is generic, which means if Field is an Object or a Collection with an Object as a parameter.
     * @param fieldObject
     * @param field
     * @return FieldType
     */
    private static FieldType isGeneric(FieldObject fieldObject,Field field){
        if(field.getType() == Object.class){
              return  fieldObject.fieldType = FieldType.GENERIC;
        }
        if(fieldObject.fieldType == FieldType.COLLECTION){
            try {
                ParameterizedType pType = (ParameterizedType) field.getGenericType();
                Class<?> cls = (Class<?>) pType.getActualTypeArguments()[0];
                if(cls == Object.class){
                    throw new Exception();
                }
            }catch (Exception e){
                fieldObject.fieldType = FieldType.GENERIC;
            }
        }
        return fieldObject.fieldType;
    }

    /**
     * Builds a FieldObject out of Object
     * @param value
     * @return FieldObject
     */
    public static FieldObject build(Object value){
        FieldObject fieldObject = new FieldObject();
        fieldObject.setFieldName(ifArray(value));
        fieldObject.setFieldValue(value);
        fieldObject.fieldType = EvaluateFieldType.determineFieldType(value);
        return fieldObject;
    }
    private static String ifArray(Object value){
        String fieldName;
        if(value.getClass().isArray()){
            fieldName = value.getClass().getComponentType().getSimpleName();
        }else{
            fieldName = value.getClass().getSimpleName();
        }
        return fieldName;
    }
}