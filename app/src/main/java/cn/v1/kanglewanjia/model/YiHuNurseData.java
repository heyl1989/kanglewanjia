package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qy on 2018/1/12.
 */

public class YiHuNurseData extends  BaseData{


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
         * code : 001
         * descrip : 肌肉注射，皮下注射
         * experience :
         * firSvCode : 001
         * h5_introduction : http://m.yihu365.com/hzb/project-dz.shtml
         * insuranceTips : 1.平台将为患者提供人保意外综合险
         * name : 打针
         * pic : http://m.yihu365.com/images/hs-dz.png
         * roleCode : 002
         * scdSvCode :
         * scope :
         * serviceTime :
         * shortDiscrip : 肌肉或皮下注射
         * singlePrice : 139.00元/次
         */

        @SerializedName("code")
        private String codeX;
        @SerializedName("descrip")
        private String descrip;
        @SerializedName("experience")
        private String experience;
        @SerializedName("firSvCode")
        private String firSvCode;
        @SerializedName("h5_introduction")
        private String h5Introduction;
        @SerializedName("insuranceTips")
        private String insuranceTips;
        @SerializedName("name")
        private String name;
        @SerializedName("pic")
        private String pic;
        @SerializedName("roleCode")
        private String roleCode;
        @SerializedName("scdSvCode")
        private String scdSvCode;
        @SerializedName("scope")
        private String scope;
        @SerializedName("serviceTime")
        private String serviceTime;
        @SerializedName("shortDiscrip")
        private String shortDiscrip;
        @SerializedName("singlePrice")
        private String singlePrice;

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public String getDescrip() {
            return descrip;
        }

        public void setDescrip(String descrip) {
            this.descrip = descrip;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getFirSvCode() {
            return firSvCode;
        }

        public void setFirSvCode(String firSvCode) {
            this.firSvCode = firSvCode;
        }

        public String getH5Introduction() {
            return h5Introduction;
        }

        public void setH5Introduction(String h5Introduction) {
            this.h5Introduction = h5Introduction;
        }

        public String getInsuranceTips() {
            return insuranceTips;
        }

        public void setInsuranceTips(String insuranceTips) {
            this.insuranceTips = insuranceTips;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getScdSvCode() {
            return scdSvCode;
        }

        public void setScdSvCode(String scdSvCode) {
            this.scdSvCode = scdSvCode;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getShortDiscrip() {
            return shortDiscrip;
        }

        public void setShortDiscrip(String shortDiscrip) {
            this.shortDiscrip = shortDiscrip;
        }

        public String getSinglePrice() {
            return singlePrice;
        }

        public void setSinglePrice(String singlePrice) {
            this.singlePrice = singlePrice;
        }
    }
}
