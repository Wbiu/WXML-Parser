package Wxml;

import Exeption.WxmlExeption;
import Reflection.Reflection;
import WxmlDocHandler.WxmlBuilder;

import java.util.List;

public class ControllerToXML {
    private String output;

    public ControllerToXML(Object classObj) throws WxmlExeption {
        reflect(classObj);
    }

    private void reflect(Object clazzObj) throws WxmlExeption {
            Reflection ref=  new Reflection(clazzObj);
            buildXMLDoc(ref.getclassName(), ref.getListofFields());
    }
    private void buildXMLDoc(String rootElementName,List<Object> list) throws WxmlExeption {
        WxmlBuilder wxmlBuilder = new WxmlBuilder(rootElementName,list);
        this.output = wxmlBuilder.getXmlString();
    }
    protected String getXMLAsPretteyString(){
        return this.output;
    }
}
