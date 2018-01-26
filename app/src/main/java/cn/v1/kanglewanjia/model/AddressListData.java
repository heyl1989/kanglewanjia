package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qy on 2018/1/8.
 */

public class AddressListData extends BaseData {


    @SerializedName("data")
    private List<DataData> data;

    public List<DataData> getData() {
        return data;
    }

    public void setData(List<DataData> data) {
        this.data = data;
    }

    public static class DataData implements Serializable {

        public DataData() {
        }

        /**
         * caId : 10004
         * userId : 1
         * mobile : 11111111111
         * userRealName : q
         * detailAddress : qqqqqqqqqqqqqqqqq
         * status : 1
         * createTime : Jan 8, 2018 8:49:28 PM
         * modifyTime : Jan 8, 2018 8:49:28 PM
         * longitude : 116.492932
         * latitude : 40.01507
         * isDefault : 0
         */

        @SerializedName("caId")
        private int caId;
        @SerializedName("userId")
        private String userId;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("userRealName")
        private String userRealName;
        @SerializedName("detailAddress")
        private String detailAddress;
        @SerializedName("status")
        private int status;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("modifyTime")
        private String modifyTime;
        @SerializedName("longitude")
        private String longitude;
        @SerializedName("latitude")
        private String latitude;
        @SerializedName("isDefault")
        private int isDefault;

        public int getCaId() {
            return caId;
        }

        public void setCaId(int caId) {
            this.caId = caId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }
}
