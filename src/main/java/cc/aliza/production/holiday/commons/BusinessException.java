package cc.aliza.production.holiday.commons;

/**
 * Created by Jing on 14-1-27.
 */
public class BusinessException extends Exception {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static String unknown_code = "99999";
    private static String unknown_message = "未知的错误";

    public BusinessException() {
        this(unknown_code, unknown_message);
    }

    public BusinessException(String message) {
        this(unknown_code, message);
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(Exception e) {
        this.code = unknown_code;
        if (e.getCause() == null) {
            this.message = e.getMessage();
        } else {
            this.message = e.getCause().getMessage();
        }
    }

    public static BusinessException parser(Exception e) {
        return new BusinessException(e);
    }
}
