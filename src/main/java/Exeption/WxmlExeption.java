package Exeption;

public class WxmlExeption extends Exception {

    public WxmlExeption() {
    }

    public WxmlExeption(String message) {
        super(message);
    }

    public WxmlExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public WxmlExeption(Throwable cause) {
        super(cause);
    }

    public WxmlExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
