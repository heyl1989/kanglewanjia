package cn.v1.kanglewanjia.network.exception;

/**
 * Created by lawrence on 15/5/21.
 */
public class ServerErrorThrowable extends Throwable {
    public ServerErrorThrowable() {
    }

    public ServerErrorThrowable(String detailMessage) {
        super(detailMessage);
    }
}
