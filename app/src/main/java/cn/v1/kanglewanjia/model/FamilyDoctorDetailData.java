package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/10.
 */

public class FamilyDoctorDetailData extends BaseData {


    /**
     * data : {"departName":"呼吸科","doctorDesc":"介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊","doctorHeadPic":"https://tva4.sinaimg.cn/crop.0.0.180.180.180/7b9aed1ajw1e8qgp5bmzyj2050050aa8.jpg","doctorId":21,"doctorLevel":1,"doctorName":"盖伟伟","fdsDesc":"1.家庭医生会为您的一家人免费提供医疗咨询服务。\n2.家庭医生会定期为您发送一些阶段性总结，您可以在\u2018我的\u2019里查看。\n3.根据病情需求，家庭医生也可以为您开具处方和病历。\n4.如果您对购买的家庭医生有任何意见和建议，可致电4000-123-789，我们将竭诚为您服务。","hospitalDepartId":"1","hospitalName":"泾川县医院","lineUpCount":0,"onlineStatus":1,"signDate":"2018年01月01日 ","signEnableDays":355,"signUserName":"何永亮"}
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
         * departName : 呼吸科
         * doctorDesc : 介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊介绍介绍啊
         * doctorHeadPic : https://tva4.sinaimg.cn/crop.0.0.180.180.180/7b9aed1ajw1e8qgp5bmzyj2050050aa8.jpg
         * doctorId : 21
         * doctorLevel : 1
         * doctorName : 盖伟伟
         * fdsDesc : 1.家庭医生会为您的一家人免费提供医疗咨询服务。
         2.家庭医生会定期为您发送一些阶段性总结，您可以在‘我的’里查看。
         3.根据病情需求，家庭医生也可以为您开具处方和病历。
         4.如果您对购买的家庭医生有任何意见和建议，可致电4000-123-789，我们将竭诚为您服务。
         * hospitalDepartId : 1
         * hospitalName : 泾川县医院
         * lineUpCount : 0
         * onlineStatus : 1
         * signDate : 2018年01月01日
         * signEnableDays : 355
         * signUserName : 何永亮
         */

        @SerializedName("departName")
        private String departName;
        @SerializedName("doctorDesc")
        private String doctorDesc;
        @SerializedName("doctorHeadPic")
        private String doctorHeadPic;
        @SerializedName("doctorId")
        private int doctorId;
        @SerializedName("doctorLevel")
        private int doctorLevel;
        @SerializedName("doctorName")
        private String doctorName;
        @SerializedName("fdsDesc")
        private String fdsDesc;
        @SerializedName("hospitalDepartId")
        private String hospitalDepartId;
        @SerializedName("hospitalName")
        private String hospitalName;
        @SerializedName("lineUpCount")
        private int lineUpCount;
        @SerializedName("onlineStatus")
        private int onlineStatus;
        @SerializedName("signDate")
        private String signDate;
        @SerializedName("signEnableDays")
        private int signEnableDays;
        @SerializedName("signUserName")
        private String signUserName;

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public String getDoctorDesc() {
            return doctorDesc;
        }

        public void setDoctorDesc(String doctorDesc) {
            this.doctorDesc = doctorDesc;
        }

        public String getDoctorHeadPic() {
            return doctorHeadPic;
        }

        public void setDoctorHeadPic(String doctorHeadPic) {
            this.doctorHeadPic = doctorHeadPic;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public int getDoctorLevel() {
            return doctorLevel;
        }

        public void setDoctorLevel(int doctorLevel) {
            this.doctorLevel = doctorLevel;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getFdsDesc() {
            return fdsDesc;
        }

        public void setFdsDesc(String fdsDesc) {
            this.fdsDesc = fdsDesc;
        }

        public String getHospitalDepartId() {
            return hospitalDepartId;
        }

        public void setHospitalDepartId(String hospitalDepartId) {
            this.hospitalDepartId = hospitalDepartId;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public int getLineUpCount() {
            return lineUpCount;
        }

        public void setLineUpCount(int lineUpCount) {
            this.lineUpCount = lineUpCount;
        }

        public int getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(int onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public int getSignEnableDays() {
            return signEnableDays;
        }

        public void setSignEnableDays(int signEnableDays) {
            this.signEnableDays = signEnableDays;
        }

        public String getSignUserName() {
            return signUserName;
        }

        public void setSignUserName(String signUserName) {
            this.signUserName = signUserName;
        }
    }
}
