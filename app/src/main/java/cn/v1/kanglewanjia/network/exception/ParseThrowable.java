package cn.v1.kanglewanjia.network.exception;

/**
 * Created by lawrence on 15/2/6.
 * 数据请求，解析报错
 */
public class ParseThrowable extends Throwable {

    public ParseThrowable() {
    }

    public ParseThrowable(String detailMessage) {
        super(detailMessage);
    }
}
