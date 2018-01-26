package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qy on 2018/1/10.
 */

public class InquiryOrderListData extends BaseData {


    /**
     * data : {"count":21,"list":[{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10028","orderStatus":"3","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"0","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10027","orderStatus":"3","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"0","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10026","orderStatus":"3","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"0","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10025","orderStatus":"0","orderType":"0","patientAge":"18","patientRealName":"制造者","patientSex":"0","payStatus":"0","payType":"","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10024","orderStatus":"0","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10023","orderStatus":"0","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"}]}
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
         * count : 21
         * list : [{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10028","orderStatus":"3","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"0","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10027","orderStatus":"3","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"0","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10026","orderStatus":"3","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"0","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10025","orderStatus":"0","orderType":"0","patientAge":"18","patientRealName":"制造者","patientSex":"0","payStatus":"0","payType":"","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10024","orderStatus":"0","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"},{"createTime":"2018年01月10日 ","departName":"呼吸科","doctorId":"21","doctorName":"盖伟伟","hospitalName":"泾川县医院","medicalInsuranceDeductibleAmount":"0","orderId":"10023","orderStatus":"0","orderType":"0","patientAge":"18","patientRealName":"十三岁","patientSex":"0","payStatus":"0","payType":"","price":"100","realPrice":100,"talkTime":"","tradeNo":"","userId":"1","userMobile":"13810205545"}]
         */

        @SerializedName("count")
        private int count;
        @SerializedName("list")
        private List<ListData> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListData> getList() {
            return list;
        }

        public void setList(List<ListData> list) {
            this.list = list;
        }

        public static class ListData {
            /**
             * createTime : 2018年01月10日
             * departName : 呼吸科
             * doctorId : 21
             * doctorName : 盖伟伟
             * hospitalName : 泾川县医院
             * medicalInsuranceDeductibleAmount : 0
             * orderId : 10028
             * orderStatus : 3
             * orderType : 0
             * patientAge : 18
             * patientRealName : 十三岁
             * patientSex : 0
             * payStatus : 0
             * payType : 0
             * price : 100
             * realPrice : 100.0
             * talkTime :
             * tradeNo :
             * userId : 1
             * userMobile : 13810205545
             */

            @SerializedName("createTime")
            private String createTime;
            @SerializedName("departName")
            private String departName;
            @SerializedName("doctorId")
            private String doctorId;
            @SerializedName("doctorName")
            private String doctorName;
            @SerializedName("hospitalName")
            private String hospitalName;
            @SerializedName("medicalInsuranceDeductibleAmount")
            private String medicalInsuranceDeductibleAmount;
            @SerializedName("orderId")
            private String orderId;
            @SerializedName("orderStatus")
            private String orderStatus;
            @SerializedName("orderType")
            private String orderType;
            @SerializedName("patientAge")
            private String patientAge;
            @SerializedName("patientRealName")
            private String patientRealName;
            @SerializedName("patientSex")
            private String patientSex;
            @SerializedName("payStatus")
            private String payStatus;
            @SerializedName("payType")
            private String payType;
            @SerializedName("price")
            private String price;
            @SerializedName("realPrice")
            private double realPrice;
            @SerializedName("talkTime")
            private String talkTime;
            @SerializedName("tradeNo")
            private String tradeNo;
            @SerializedName("userId")
            private String userId;
            @SerializedName("userMobile")
            private String userMobile;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getMedicalInsuranceDeductibleAmount() {
                return medicalInsuranceDeductibleAmount;
            }

            public void setMedicalInsuranceDeductibleAmount(String medicalInsuranceDeductibleAmount) {
                this.medicalInsuranceDeductibleAmount = medicalInsuranceDeductibleAmount;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getPatientAge() {
                return patientAge;
            }

            public void setPatientAge(String patientAge) {
                this.patientAge = patientAge;
            }

            public String getPatientRealName() {
                return patientRealName;
            }

            public void setPatientRealName(String patientRealName) {
                this.patientRealName = patientRealName;
            }

            public String getPatientSex() {
                return patientSex;
            }

            public void setPatientSex(String patientSex) {
                this.patientSex = patientSex;
            }

            public String getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(String payStatus) {
                this.payStatus = payStatus;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public double getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(double realPrice) {
                this.realPrice = realPrice;
            }

            public String getTalkTime() {
                return talkTime;
            }

            public void setTalkTime(String talkTime) {
                this.talkTime = talkTime;
            }

            public String getTradeNo() {
                return tradeNo;
            }

            public void setTradeNo(String tradeNo) {
                this.tradeNo = tradeNo;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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
}
