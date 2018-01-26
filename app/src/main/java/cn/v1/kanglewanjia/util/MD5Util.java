package cn.v1.kanglewanjia.util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by migo on 16/3/25.
 */
public class MD5Util {
    public static byte[] encode(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[]result = digest.digest(source.getBytes("utf-8"));
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
