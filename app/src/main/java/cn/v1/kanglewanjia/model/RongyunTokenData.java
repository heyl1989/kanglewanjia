package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2017/11/16.
 */

public class RongyunTokenData {


    /**
     * code : 200
     * token : FL7LpcUgxeSzFrnPDxSIzJMWpaP1CLFUd/h5wsoZ+ppac7/j9z+4VL/w5uRuV/RvXSxzF9HgchwITCSniplkemnhooSeTdHH
     * userId : request
     */

    @SerializedName("code")
    private int code;
    @SerializedName("token")
    private String token;
    @SerializedName("userId")
    private String userId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
