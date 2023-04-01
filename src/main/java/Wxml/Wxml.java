package Wxml;

import Exeption.WxmlExeption;

/**
 * XML Parser based on java reflection.
 * Fields are retrieve using java reflection.
 * No getter and setter is needed.
 * @param <T>
 * @Author Sitthichai Wilet
 */
public class Wxml<T>{

    /**
     * Takes any reflectable class object and parse it to a XML pretty string.
     * Fields can exclude by using the transient modifier. Any field that´s modified with transient won´t be taken as
     * a reflectable field.
     * @param clazzObj
     * @return String
     * @throws WxmlExeption
     */
    public String toXML(Object clazzObj) throws WxmlExeption {
        ControllerToXML cnT_XML = new ControllerToXML(clazzObj);
        return cnT_XML.getXMLAsPretteyString();
    }

    /**
     * Takes any reflectable class object and the related file.
     * The file will be mapped to the given class.
     * Fields can exclude by using the transient modifier.
     * Any field that´s modified with transient won´t be taken as a reflectable field.
     * @param clazzOfT
     * @param filepath
     * @return T
     * @throws WxmlExeption
     */
    public T fromXML(Class<T> clazzOfT,String filepath) throws WxmlExeption {
        ControllerFromXML cnF_XML = new ControllerFromXML(clazzOfT,filepath);
        return (T) cnF_XML.getMappedClass();
    }

}
