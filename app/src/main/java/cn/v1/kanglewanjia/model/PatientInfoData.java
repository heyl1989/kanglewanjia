package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/5.
 */

public class PatientInfoData extends BaseData {


    /**
     * data : {"id":68328,"idCardNo":"110101200001012913","relationShip":"奶奶","idCardUrl":"http://file.yihu365.com/onlinetHospital//15151236553943599.jpeg","status":"1","securityUrl ":"http://file.yihu365.comhttp://file.yihu365.com/onlinetHospital//15151322663502663.jpeg","userId":1,"securityNo":"110101200001012913","realName":"嗷嗷","district":0,"age ":18}
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
         * id : 68328
         * idCardNo : 110101200001012913
         * relationShip : 奶奶
         * idCardUrl : http://file.yihu365.com/onlinetHospital//15151236553943599.jpeg
         * status : 1
         * securityUrl  : http://file.yihu365.comhttp://file.yihu365.com/onlinetHospital//15151322663502663.jpeg
         * userId : 1
         * securityNo : 110101200001012913
         * realName : 嗷嗷
         * district : 0
         * age  : 18
         */

        @SerializedName("id")
        private int id;
        @SerializedName("idCardNo")
        private String idCardNo;
        @SerializedName("relationShip")
        private String relationShip;
        @SerializedName("idCardUrl")
        private String idCardUrl;
        @SerializedName("status")
        private String status;
        @SerializedName("securityUrl")
        private String securityUrl;
        @SerializedName("userId")
        private int userId;
        @SerializedName("securityNo")
        private String securityNo;
        @SerializedName("realName")
        private String realName;
        @SerializedName("district")
        private int district;
        @SerializedName("age")
        private int age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public String getRelationShip() {
            return relationShip;
        }

        public void setRelationShip(String relationShip) {
            this.relationShip = relationShip;
        }

        public String getIdCardUrl() {
            return idCardUrl;
        }

        public void setIdCardUrl(String idCardUrl) {
            this.idCardUrl = idCardUrl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSecurityUrl() {
            return securityUrl;
        }

        public void setSecurityUrl(String securityUrl) {
            this.securityUrl = securityUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getSecurityNo() {
            return securityNo;
        }

        public void setSecurityNo(String securityNo) {
            this.securityNo = securityNo;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
