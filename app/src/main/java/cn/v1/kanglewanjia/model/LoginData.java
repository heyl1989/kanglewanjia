package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/2.
 */

public class LoginData extends BaseData {


    /**
     * data : {"token":"bN4uOxMkJ3EWGnDyxzGu1g=="}
     */

    @SerializedName("data")
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * token : bN4uOxMkJ3EWGnDyxzGu1g==
         */

        @SerializedName("token")
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
