package cn.v1.kanglewanjia.network.exception;

/**
 * Created by lawrence on 15/5/21.
 */
public class NetErrorThrowable extends Throwable {
    public NetErrorThrowable() {
    }

    public NetErrorThrowable(String detailMessage) {
        super(detailMessage);
    }
}
