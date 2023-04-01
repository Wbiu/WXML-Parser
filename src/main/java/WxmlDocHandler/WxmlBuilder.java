package WxmlDocHandler;

import Exeption.WxmlExeption;
import Reflection.FieldObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

public class WxmlBuilder {
    private Document doc;
    private Element rootElement = null;
    public WxmlBuilder(String rootElementName,List<Object> list) throws WxmlExeption {
        docBuilder(rootElementName);
        buildXML(list);
    }
    private void buildXML(List list) {
        List<FieldObject> fieldList = new ArrayList<>(list);
        fieldList.forEach(this::buildElement);
        addRootElementToDoc();
    }
    private void addRootElementToDoc(){this.doc.appendChild(this.rootElement);}
    private void buildElement(FieldObject fo){
        this.rootElement.appendChild(Inspect.inspectFieldObject(fo,this.doc));
    }
    private void docBuilder(String rootElementName) throws WxmlExeption {
        DocBuilder docBuilder = new DocBuilder();
        this.doc = docBuilder.getDoc();
        createRootElement(rootElementName);

    }
    public String getXmlString() throws WxmlExeption {
        try {
            return XmlTransform.xmlToPrettyString(this.doc);
        } catch (TransformerException e) {
            throw new WxmlExeption(e);
        }
    }
    private void createRootElement(String rootElementName){this.rootElement = RootElementBuilder.createRootElement(rootElementName,this.doc);}
    public Element getRootElement(){return this.rootElement;}
}
