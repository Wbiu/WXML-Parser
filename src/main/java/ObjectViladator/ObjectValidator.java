/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectViladator;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Is used for value checking purposes
 * @author Sitthichai Wilet
 */
public class ObjectValidator {

    private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
    static {
        WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>(16);
        WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        WRAPPER_TYPE_MAP.put(Character.class, char.class);
        WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TYPE_MAP.put(Double.class, double.class);
        WRAPPER_TYPE_MAP.put(Float.class, float.class);
        WRAPPER_TYPE_MAP.put(Long.class, long.class);
        WRAPPER_TYPE_MAP.put(Short.class, short.class);
        WRAPPER_TYPE_MAP.put(Void.class, void.class);
        WRAPPER_TYPE_MAP.put(String.class, String.class);
        WRAPPER_TYPE_MAP.put(Enum.class, Enum.class);
    }
    /**
     * Checking if the given object is a primitive type
     * true if primitive type
     * else false
     * @param source
     * @return boolean
     */
    public static boolean isPrimitiveTypeKEY_ByClass(Class<?> source) {
        Class<?> cls  = source;
        return WRAPPER_TYPE_MAP.containsKey(source)|| WRAPPER_TYPE_MAP.containsValue(source);
    }
    
    /**
     * Checking if giving class is a primitive type class
     * If you are using this method please consider using
     * .getClass().componentType()
     * true if primitive type
     * else false
     * @param source
     * @return boolean
     */
    public static boolean isPrimitiveTypeKEY_ByObj(Object source) {
        return WRAPPER_TYPE_MAP.containsKey(source.getClass()) || WRAPPER_TYPE_MAP.containsValue(source.getClass());

    }

    /**
     * Check if the given array is a primitive type array
     * @param source
     * @return boolean
     */
    public static boolean isPrimitiveTypeKEY_ByObj_IsArray(Object source) {
        if(source.getClass().isArray()){
            source = source.getClass().getComponentType();
            return WRAPPER_TYPE_MAP.containsKey(source) || WRAPPER_TYPE_MAP.containsValue(source);
        }
        return WRAPPER_TYPE_MAP.containsKey(source.getClass()) || WRAPPER_TYPE_MAP.containsValue(source.getClass());
    }
    /**
     * Converts the giving  primitive class as String 
     * to there Class
     * Only takes String , Double , Integer as string parameter
     * If parameter does not match null will be returned
     * @param <T>
     * @param className
     * @return Class<?>
     */
    public static <T> Class<?> toClass(String className) {

        Class<?> cls = null;

        switch (className) {
            case "String":
                cls = String.class;
                break;
            case "Double":
                cls = Double.class;
                break;
            case "Integer":
                cls = Integer.class;
                break;
        }
        return cls;
    }
    /**
     * Takes the giving String value and parse to the giving primitive class
     * @param <T>
     * @param value
     * @param primitiveDataType
     * @return Object
     */
    public static <T> Object toObject(String value, Class<T> primitiveDataType) {

        if (Boolean.class == primitiveDataType) {
            return Boolean.parseBoolean(value);
        }
        if (Byte.class == primitiveDataType) {
            return Byte.parseByte(value);
        }
        if (Short.class == primitiveDataType) {
            return Short.parseShort(value);
        }
        if (Integer.class == primitiveDataType) {
            return Integer.parseInt(value);
        }
        if (Long.class == primitiveDataType) {
            return Long.parseLong(value);
        }
        if (Float.class == primitiveDataType) {
            return Float.parseFloat(value);
        }
        if (Double.class == primitiveDataType) {
            return Double.parseDouble(value);
        }
        return value;
    }

    public static boolean isKnownClass(String clazz){
        boolean b = false;
        if(clazz.equals("double")|| clazz.equals("int")|| clazz.equals("String")||clazz.equals("Double")||
                clazz.equals("Integer")){ b = true;}
        return b;

    }

    /**
     * Check if the given Object is a collection
     * @param clazz
     * @return boolean
     */
    public static boolean isCollection(Object clazz) {
        return clazz instanceof List<?> || clazz instanceof Collection<?>;
        //return  !clazz.isArray();

    }

    /**
     * Check if the given Object is a Hashmap
     * @param obj
     * @return boolean
     */
    public static boolean isHashMap(Object obj){

        if(obj instanceof  ParameterizedType){
            ParameterizedType pType = (ParameterizedType)obj;
            Class<?> cls  =(Class<?>) pType.getRawType();
            if(cls == Map.class || cls == HashMap.class){
                return true;
            }
        }
        return  obj instanceof Map<?,?> || obj.getClass() == Map.class || obj.getClass() == HashMap.class;
    }
    public static boolean isClassObj(Object cls){
        return  cls == Object.class;
    }
    /**
     * Converts an Object to List if Object is a Type of a Collection or an
     * Array
     *
     * @param obj
     * @return List<?>
     */
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = null;
        if (obj.getClass().isArray()) {
//            list = Arrays.asList((Object[]) obj);
            List<?> list2 = new ArrayList<>(Arrays.asList(obj));
            String cls = list2.get(0).getClass().componentType().getSimpleName();
            if (isKnownClass(cls)) {
                switch (cls) {
                    case "double":
                        double[] arrD = (double[]) list2.get(0);
                        Double[] arrD2 = arrD.length > 0 ? Arrays.stream(arrD).boxed().toArray(Double[]::new) : null;
                        for (int i = 0; i < arrD2.length; i++) {
                            list = Arrays.asList(arrD2);
                        }
                        break;
                    case "int":
                        int[] arrI = (int[]) list2.get(0);
                        Integer[] arrI2 = arrI.length > 0 ? Arrays.stream(arrI).boxed().toArray(Integer[]::new) : null;
                        for (int i = 0; i < arrI2.length; i++) {
                            list = Arrays.asList(arrI2);
                        }
                        break;
                    case "String":
                        list = Arrays.asList((Object[]) obj);
                        break;
                    case "Double":
                        Double[] arrDb = (Double[]) list2.get(0);
                        list = Arrays.asList(arrDb);
                        break;
                    case "Integer":
                        Integer[] arrInt = (Integer[]) list2.get(0);
                        list = Arrays.asList(arrInt);
                        break;
                }
            }else{
                list = Arrays.asList((Object[]) obj);
            }
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);
        }
        //Creating a LinkedList because List and ArrayList does not support remove methods
        List<Object> listTemp = new LinkedList<>(list);
        //Arrays.stream(new Object[]{obj}).forEach(o -> listTemp.add(o));
        listTemp.removeIf(Objects::isNull);
        list = listTemp;
        return list;
    }
     /**
     * Takes a primitive type list and convert it to an array of primitive
     * type by giving a datatype such as : 
     * ArrayList<String> listStr = new ArrayList<>();
     * to
     * String[] arrStr = new String[listStr.size];
     * @param collection
     * @param dataType
     * @return Object
     */
    public static Object ListToArray(List collection,String dataType){
        return switch(dataType){
            //case"String" -> (String[]) collection.toArray(String[]::new);
            case"double" -> collection.stream().mapToDouble(item -> Double.parseDouble((String)item)).toArray();
            case"int"-> collection.stream().mapToInt(item -> Integer.parseInt((String)item)).toArray();
            default ->  (String[]) collection.toArray(String[]::new);
        };
    }
    public static Object toGivenDataType(String dataType,String value,boolean toWrapperClass){
        Object obj;

        if(toWrapperClass){
            obj =  switch (dataType){
                case "Integer" -> Integer.valueOf(value);
                case "Double" -> Double.valueOf(value);
                default -> value;
            };
        }else{
            obj =  switch (dataType){
                case "Integer" -> Integer.parseInt(value);
                case "Double" -> Double.parseDouble(value);
                default -> value;
            };
        }



        return obj;
    }

    public static void parseValue() {
    }
}
