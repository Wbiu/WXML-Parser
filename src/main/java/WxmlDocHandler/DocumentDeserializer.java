package WxmlDocHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentDeserializer {

    public static NodeList findeNodeByName(String nodeName, Element element){
        return element.getElementsByTagName(nodeName);
    }
    public static boolean isElementNode(Node node){
        return node.getNodeType() == Node.ELEMENT_NODE;
    }

}
