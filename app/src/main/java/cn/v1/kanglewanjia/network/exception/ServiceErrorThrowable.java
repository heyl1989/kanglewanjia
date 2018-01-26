package cn.v1.kanglewanjia.network.exception;

/**
 * Created by migo on 16/1/30.
 */
public class ServiceErrorThrowable extends Throwable {
    private String code = "";
    public ServiceErrorThrowable(String detailMessage,String code) {
        super(detailMessage);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
