package Wxml;

import Exeption.WxmlExeption;
import Reflection.FieldMapping.Mapping;
import Reflection.*;
import WxmlDocHandler.DocBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public  class  ControllerFromXML <T> {
    private T mappedClassOft;
    public ControllerFromXML(Class<T> classOfT,String fileName) throws WxmlExeption {
        Element element = getFile(fileName).getDocumentElement();
        this.mappedClassOft = mapping(classOfT,element);
    }
    private Document getFile(String fileName) throws WxmlExeption {
        DocBuilder docBuilder = new DocBuilder();
        return docBuilder.parseDoc(fileName);
    }
    private <T> T mapping(Class<T> classOfT, Element element) throws WxmlExeption {
        Mapping mapping = new Mapping();
        return mapping.mapToClassOfT(classOfT,element);
    }
    protected  T getMappedClass(){
        return this.mappedClassOft;
    }
}
