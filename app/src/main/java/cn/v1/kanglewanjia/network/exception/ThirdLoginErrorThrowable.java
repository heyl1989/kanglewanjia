package cn.v1.kanglewanjia.network.exception;

/**
 * Created by migo on 16/1/30.
 */
public class ThirdLoginErrorThrowable extends Throwable {
    public ThirdLoginErrorThrowable(String responseStr) {
        super(responseStr);
    }
}
