package cn.v1.kanglewanjia.util;

/**
 *
 * @author lihongbo
 */
public interface Encryption {
    /**
     *
     * @param src
     * @return
     * @throws Exception
     */
    public String Encrypt(String src) throws Exception;

    /**
     *
     * @param src
     * @return
     * @throws Exception
     */
    public String Decrypt(String src) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    public String getSignKey() throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    public String getMyName();

}
