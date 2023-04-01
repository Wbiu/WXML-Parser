package Reflection;

import ObjectViladator.ObjectValidator;

/**
 * Categorize the given value to different FieldType
 */
public class EvaluateFieldType {
    public static  FieldType determineFieldType(Object value){
            if(ObjectValidator.isPrimitiveTypeKEY_ByObj(value)){return FieldType.PRIMITIVE;}

            if(value.getClass() == Class.class){
                if(ObjectValidator.isPrimitiveTypeKEY_ByClass((Class<?>) value)){
                    return FieldType.PRIMITIVE;
                }
            }
            if(ObjectValidator.isCollection(value)){return FieldType.COLLECTION;}
            if(ObjectValidator.isHashMap(value)){return FieldType.HASHMAP;}
            if(value.getClass().isArray()){
                if(ObjectValidator.isPrimitiveTypeKEY_ByObj_IsArray(value)){
                    return FieldType.ARRAY;
                }else {
                    return FieldType.USER_DEFINEDARRAY;
                }
            }
            if(ObjectValidator.isClassObj(value)){
                return FieldType.GENERIC;
            }
            return FieldType.USER_DEFINEDCLASS;
    }
}
