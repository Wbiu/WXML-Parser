# WXML-Parser
## Description
This library was made to create XML Docs easier just like the GSON lib.<br/>
Since reflection is used, getters and setters are not required.

## Usage example
The **.jar** can be found under out>artifacts>WxmlMaster_V3 <br/>
Simply add this lib to your project then you should be good to go.<br/>

> **note** : 
<font size = 3>Fields can exclude by using the **transient** modifier. Some field might have to be excluded if subclasses are not reflectable. </font>


To xml example :<br/>

```java
public class TestClass{

    public static void main(String args[]){
        try {
            Wxml wxml = new Wxml();
            String xmlDoc = wxml.toXML(new DataClass());
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }    
    }
}
```
<br/>
From xml example :<br/>

```java
public class TestClass{

    public static void main(String args[]){
        try {
            Wxml wxml = new Wxml();
            DataClass xmlDoc = (DataClass) wxml.from(DataClass.class,"filepath");
        } catch (WxmlExeption e) {
            throw new RuntimeException(e);
        }
    }
}
```

