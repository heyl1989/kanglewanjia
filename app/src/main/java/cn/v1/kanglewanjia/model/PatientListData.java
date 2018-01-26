package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qy on 2018/1/4.
 */

public class PatientListData extends BaseData {

    @SerializedName("data")
    private List<DataData> data;

    public List<DataData> getData() {
        return data;
    }

    public void setData(List<DataData> data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * district : 0
         * id : 68386
         * idCardNo : 110101195001011773
         * idCardUrl : http://file.yihu365.com/onlinetHospital//15157405677173276.jpeg
         * realName : 爱新觉罗·玄烨
         * relationShip : 爷爷
         * securityNo : 11111111111111133X
         * securityUrl : http://file.yihu365.com/onlinetHospital//15157412232935396.jpeg
         * status : 1
         * userId : 1
         */

        @SerializedName("district")
        private int district;
        @SerializedName("id")
        private int id;
        @SerializedName("idCardNo")
        private String idCardNo;
        @SerializedName("idCardUrl")
        private String idCardUrl;
        @SerializedName("realName")
        private String realName;
        @SerializedName("relationShip")
        private String relationShip;
        @SerializedName("securityNo")
        private String securityNo;
        @SerializedName("securityUrl")
        private String securityUrl;
        @SerializedName("status")
        private String status;
        @SerializedName("userId")
        private int userId;

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

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

        public String getIdCardUrl() {
            return idCardUrl;
        }

        public void setIdCardUrl(String idCardUrl) {
            this.idCardUrl = idCardUrl;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRelationShip() {
            return relationShip;
        }

        public void setRelationShip(String relationShip) {
            this.relationShip = relationShip;
        }

        public String getSecurityNo() {
            return securityNo;
        }

        public void setSecurityNo(String securityNo) {
            this.securityNo = securityNo;
        }

        public String getSecurityUrl() {
            return securityUrl;
        }

        public void setSecurityUrl(String securityUrl) {
            this.securityUrl = securityUrl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
