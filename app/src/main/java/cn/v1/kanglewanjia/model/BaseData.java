package cn.v1.kanglewanjia.model;

/**
 * Created by dell on 2016/1/27.
 */
public class BaseData {

    /**
     * code : 0000
     * message : 请求成功
     */

    public String code;
    public String message;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
