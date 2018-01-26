package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/11.
 */

public class InquiryOrderDetailData extends BaseData {


    /**
     * data : {"createTime":"2018-01-10 19:25:20","departId":10000,"departName":"呼吸科","doctorId":21,"doctorName":"盖伟伟","doctorPosition":1,"hospitalId":1000000,"hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":0,"orderId":10040,"orderStatus":3,"orderType":1,"patientAge":18,"patientArchivesId":68338,"patientIdcard":"110101200001012913","patientIdcardUrl":"/onlinetHospital//15151464242336307.jpeg","patientRealName":"十三岁","patientSecurityNo":"110101200001012913","patientSecurityUrl":"/onlinetHospital//15151463739284633.jpeg","patientSex":0,"payStatus":0,"payTime":"","price":0.01,"realPrice":0.01,"userId":1,"userMobile":"13810205545"}
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
         * createTime : 2018-01-10 19:25:20
         * departId : 10000
         * departName : 呼吸科
         * doctorId : 21
         * doctorName : 盖伟伟
         * doctorPosition : 1
         * hospitalId : 1000000
         * hospitalName : 泾川县医院
         * medicalInsuranceDeductibleAmount : 0.0
         * orderId : 10040
         * orderStatus : 3
         * orderType : 1
         * patientAge : 18
         * patientArchivesId : 68338
         * patientIdcard : 110101200001012913
         * patientIdcardUrl : /onlinetHospital//15151464242336307.jpeg
         * patientRealName : 十三岁
         * patientSecurityNo : 110101200001012913
         * patientSecurityUrl : /onlinetHospital//15151463739284633.jpeg
         * patientSex : 0
         * payStatus : 0
         * payTime :
         * price : 0.01
         * realPrice : 0.01
         * userId : 1
         * userMobile : 13810205545
         */

        @SerializedName("createTime")
        private String createTime;
        @SerializedName("departId")
        private int departId;
        @SerializedName("departName")
        private String departName;
        @SerializedName("doctorId")
        private int doctorId;
        @SerializedName("doctorName")
        private String doctorName;
        @SerializedName("doctorPosition")
        private int doctorPosition;
        @SerializedName("hospitalId")
        private int hospitalId;
        @SerializedName("hospitalName")
        private String hospitalName;
        @SerializedName("medicalInsuranceDeductibleAmount")
        private double medicalInsuranceDeductibleAmount;
        @SerializedName("orderId")
        private int orderId;
        @SerializedName("orderStatus")
        private int orderStatus;
        @SerializedName("orderType")
        private int orderType;
        @SerializedName("patientAge")
        private int patientAge;
        @SerializedName("patientArchivesId")
        private int patientArchivesId;
        @SerializedName("patientIdcard")
        private String patientIdcard;
        @SerializedName("patientIdcardUrl")
        private String patientIdcardUrl;
        @SerializedName("patientRealName")
        private String patientRealName;
        @SerializedName("patientSecurityNo")
        private String patientSecurityNo;
        @SerializedName("patientSecurityUrl")
        private String patientSecurityUrl;
        @SerializedName("patientSex")
        private int patientSex;
        @SerializedName("payStatus")
        private int payStatus;
        @SerializedName("payTime")
        private String payTime;
        @SerializedName("price")
        private double price;
        @SerializedName("realPrice")
        private double realPrice;
        @SerializedName("userId")
        private int userId;
        @SerializedName("userMobile")
        private String userMobile;
        @SerializedName("payType")
        private String payType;
        @SerializedName("tradeNo")
        private String tradeNo;


        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDepartId() {
            return departId;
        }

        public void setDepartId(int departId) {
            this.departId = departId;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public int getDoctorPosition() {
            return doctorPosition;
        }

        public void setDoctorPosition(int doctorPosition) {
            this.doctorPosition = doctorPosition;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public double getMedicalInsuranceDeductibleAmount() {
            return medicalInsuranceDeductibleAmount;
        }

        public void setMedicalInsuranceDeductibleAmount(double medicalInsuranceDeductibleAmount) {
            this.medicalInsuranceDeductibleAmount = medicalInsuranceDeductibleAmount;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getPatientAge() {
            return patientAge;
        }

        public void setPatientAge(int patientAge) {
            this.patientAge = patientAge;
        }

        public int getPatientArchivesId() {
            return patientArchivesId;
        }

        public void setPatientArchivesId(int patientArchivesId) {
            this.patientArchivesId = patientArchivesId;
        }

        public String getPatientIdcard() {
            return patientIdcard;
        }

        public void setPatientIdcard(String patientIdcard) {
            this.patientIdcard = patientIdcard;
        }

        public String getPatientIdcardUrl() {
            return patientIdcardUrl;
        }

        public void setPatientIdcardUrl(String patientIdcardUrl) {
            this.patientIdcardUrl = patientIdcardUrl;
        }

        public String getPatientRealName() {
            return patientRealName;
        }

        public void setPatientRealName(String patientRealName) {
            this.patientRealName = patientRealName;
        }

        public String getPatientSecurityNo() {
            return patientSecurityNo;
        }

        public void setPatientSecurityNo(String patientSecurityNo) {
            this.patientSecurityNo = patientSecurityNo;
        }

        public String getPatientSecurityUrl() {
            return patientSecurityUrl;
        }

        public void setPatientSecurityUrl(String patientSecurityUrl) {
            this.patientSecurityUrl = patientSecurityUrl;
        }

        public int getPatientSex() {
            return patientSex;
        }

        public void setPatientSex(int patientSex) {
            this.patientSex = patientSex;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(double realPrice) {
            this.realPrice = realPrice;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }
    }
}
