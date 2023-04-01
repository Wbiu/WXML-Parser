package Reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Filters each field so fields that is modified with transient or is null won´t be taken
 * for further usage.
 */
public class FieldFilter {

    private static final String KEY_FILTER = "transient";

    public static List<Field> filterAsList(Field[] fields,Object classObj){
        return Arrays.stream(fields).filter(field -> FieldFilter.fieldChecking(field,classObj)).toList();
    }

    /**
     * Check if field contains or is modified with TRANSIENT
     * @param field
     * @param classObj
     * @return boolean
     */
    private static boolean fieldChecking(Field field, Object classObj){
        try {
            field.setAccessible(true);
            String fieldString = field.toString().toLowerCase();

            //issue !!
            //if (field.getModifiers() !=  Modifier.TRANSIENT){}
            //field.getModifiers() will sum all the field modifier;
            //int value for TRANSIENT  = 128;
            //int value for PRIVATE  = 2;
            //int value for PUBLIC  = 1;

            //then the returned int value from field.getModifiers() will compare to Modifier.TRANSIENT -> [128]
            //so it means that if a field is modified with [ private transient var ] then the sum will be 130
            //which is not equal to 128.
            //so by checking with if (field.getModifiers() !=  Modifier.TRANSIENT){}
            //then the only field that will be true is those fields which is ONLY modified with TRANSIENT
            //therefor !fieldString.contains(KEY_FILTER) is used.


            //boolean b = field.get(classObj) != null && field.getModifiers() != Modifier.TRANSIENT;
            return field.get(classObj) != null && !fieldString.contains(KEY_FILTER);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
