package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qy on 2018/1/12.
 */

public class PatientDrugData extends BaseData {


    /**
     * data : {"prescriptionId":777,"addExplain":"1","feeType":"自费","diagnosisResult":"1","medicineList":[{"classInfo":"消化不良","prescriptionType":"非处方药","indication":"白头翁、马齿苋、黄柏、丹参、儿茶","onePrice":"58.00","specifications":"100ml","ingredients":"白头翁、马齿苋、黄柏、丹参、儿茶","medicineId":2943,"num":"21","price":58,"name":"正士 肠泰合剂 100ml","tcac":" 本品为棕褐色的液体，具有少量经振摇可分散的沉淀；气微，味苦、涩。","brand":"正士","usageAndDosage":"口服，一次10～20毫升，一日3次。","doctorName":"盖伟伟","notes":"1 饮食宜清淡，忌烟、酒及辛辣、生冷、油腻食物。2 不宜在服药期间同时服用滋补性中药。3 有慢性结肠炎、溃疡性结肠炎便脓血等慢性病史者，患泄泻时应去医院就诊。4 有高血压、心脏病、肝病、糖尿病、肾病等慢性病严重者应在医师指导下服用。5 服药3天症状未缓解，应去医院就诊。6 儿童、年老体弱者应在医师指导下服用。7 对本品过敏者禁用，过敏体质者慎用。8 本品性状发生改变时禁止使用。9 儿童必须在成人监护下使用。10 请将本品放在儿童不能接触的地方。11 如正在使用其他药品，使用本品前请咨询医师或药师。","medPicUrl":"http://public.zgzcw.com/zhima/zmtx_man.png","interactions":"如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。"}],"orderId":"10067","prescriptionDate":"2018.01.12"}
     */

    @SerializedName("data")
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData implements Serializable{
        /**
         * prescriptionId : 777
         * addExplain : 1
         * feeType : 自费
         * diagnosisResult : 1
         * medicineList : [{"classInfo":"消化不良","prescriptionType":"非处方药","indication":"白头翁、马齿苋、黄柏、丹参、儿茶","onePrice":"58.00","specifications":"100ml","ingredients":"白头翁、马齿苋、黄柏、丹参、儿茶","medicineId":2943,"num":"21","price":58,"name":"正士 肠泰合剂 100ml","tcac":" 本品为棕褐色的液体，具有少量经振摇可分散的沉淀；气微，味苦、涩。","brand":"正士","usageAndDosage":"口服，一次10～20毫升，一日3次。","doctorName":"盖伟伟","notes":"1 饮食宜清淡，忌烟、酒及辛辣、生冷、油腻食物。2 不宜在服药期间同时服用滋补性中药。3 有慢性结肠炎、溃疡性结肠炎便脓血等慢性病史者，患泄泻时应去医院就诊。4 有高血压、心脏病、肝病、糖尿病、肾病等慢性病严重者应在医师指导下服用。5 服药3天症状未缓解，应去医院就诊。6 儿童、年老体弱者应在医师指导下服用。7 对本品过敏者禁用，过敏体质者慎用。8 本品性状发生改变时禁止使用。9 儿童必须在成人监护下使用。10 请将本品放在儿童不能接触的地方。11 如正在使用其他药品，使用本品前请咨询医师或药师。","medPicUrl":"http://public.zgzcw.com/zhima/zmtx_man.png","interactions":"如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。"}]
         * orderId : 10067
         * prescriptionDate : 2018.01.12
         */

        @SerializedName("prescriptionId")
        private int prescriptionId;
        @SerializedName("addExplain")
        private String addExplain;
        @SerializedName("feeType")
        private String feeType;
        @SerializedName("diagnosisResult")
        private String diagnosisResult;
        @SerializedName("orderId")
        private String orderId;
        @SerializedName("prescriptionDate")
        private String prescriptionDate;
        @SerializedName("medicineList")
        private List<MedicineListData> medicineList;
        @SerializedName("patientName")
        private String patientName = "";
        @SerializedName("patientAge")
        private String patientAge = "";
        @SerializedName("patientSex")
        private String patientSex = "";
        @SerializedName("createTime")
        private String createTime = "";
        @SerializedName("departMent")
        private String departMent = "";
        @SerializedName("doctorName")
        private String doctorName = "";


        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getPatientAge() {
            return patientAge;
        }

        public void setPatientAge(String patientAge) {
            this.patientAge = patientAge;
        }

        public String getPatientSex() {
            return patientSex;
        }

        public void setPatientSex(String patientSex) {
            this.patientSex = patientSex;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDepartMent() {
            return departMent;
        }

        public void setDepartMent(String departMent) {
            this.departMent = departMent;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public int getPrescriptionId() {
            return prescriptionId;
        }

        public void setPrescriptionId(int prescriptionId) {
            this.prescriptionId = prescriptionId;
        }

        public String getAddExplain() {
            return addExplain;
        }

        public void setAddExplain(String addExplain) {
            this.addExplain = addExplain;
        }

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }

        public String getDiagnosisResult() {
            return diagnosisResult;
        }

        public void setDiagnosisResult(String diagnosisResult) {
            this.diagnosisResult = diagnosisResult;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPrescriptionDate() {
            return prescriptionDate;
        }

        public void setPrescriptionDate(String prescriptionDate) {
            this.prescriptionDate = prescriptionDate;
        }

        public List<MedicineListData> getMedicineList() {
            return medicineList;
        }

        public void setMedicineList(List<MedicineListData> medicineList) {
            this.medicineList = medicineList;
        }

        public static class MedicineListData implements Serializable{
            /**
             * classInfo : 消化不良
             * prescriptionType : 非处方药
             * indication : 白头翁、马齿苋、黄柏、丹参、儿茶
             * onePrice : 58.00
             * specifications : 100ml
             * ingredients : 白头翁、马齿苋、黄柏、丹参、儿茶
             * medicineId : 2943
             * num : 21
             * price : 58.0
             * name : 正士 肠泰合剂 100ml
             * tcac :  本品为棕褐色的液体，具有少量经振摇可分散的沉淀；气微，味苦、涩。
             * brand : 正士
             * usageAndDosage : 口服，一次10～20毫升，一日3次。
             * doctorName : 盖伟伟
             * notes : 1 饮食宜清淡，忌烟、酒及辛辣、生冷、油腻食物。2 不宜在服药期间同时服用滋补性中药。3 有慢性结肠炎、溃疡性结肠炎便脓血等慢性病史者，患泄泻时应去医院就诊。4 有高血压、心脏病、肝病、糖尿病、肾病等慢性病严重者应在医师指导下服用。5 服药3天症状未缓解，应去医院就诊。6 儿童、年老体弱者应在医师指导下服用。7 对本品过敏者禁用，过敏体质者慎用。8 本品性状发生改变时禁止使用。9 儿童必须在成人监护下使用。10 请将本品放在儿童不能接触的地方。11 如正在使用其他药品，使用本品前请咨询医师或药师。
             * medPicUrl : http://public.zgzcw.com/zhima/zmtx_man.png
             * interactions : 如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。
             */

            @SerializedName("classInfo")
            private String classInfo;
            @SerializedName("prescriptionType")
            private String prescriptionType;
            @SerializedName("indication")
            private String indication;
            @SerializedName("onePrice")
            private String onePrice;
            @SerializedName("specifications")
            private String specifications;
            @SerializedName("ingredients")
            private String ingredients;
            @SerializedName("medicineId")
            private int medicineId;
            @SerializedName("num")
            private String num;
            @SerializedName("price")
            private double price;
            @SerializedName("name")
            private String name;
            @SerializedName("tcac")
            private String tcac;
            @SerializedName("brand")
            private String brand;
            @SerializedName("usageAndDosage")
            private String usageAndDosage;
            @SerializedName("doctorName")
            private String doctorName;
            @SerializedName("notes")
            private String notes;
            @SerializedName("medPicUrl")
            private String medPicUrl;
            @SerializedName("interactions")
            private String interactions;

            public String getClassInfo() {
                return classInfo;
            }

            public void setClassInfo(String classInfo) {
                this.classInfo = classInfo;
            }

            public String getPrescriptionType() {
                return prescriptionType;
            }

            public void setPrescriptionType(String prescriptionType) {
                this.prescriptionType = prescriptionType;
            }

            public String getIndication() {
                return indication;
            }

            public void setIndication(String indication) {
                this.indication = indication;
            }

            public String getOnePrice() {
                return onePrice;
            }

            public void setOnePrice(String onePrice) {
                this.onePrice = onePrice;
            }

            public String getSpecifications() {
                return specifications;
            }

            public void setSpecifications(String specifications) {
                this.specifications = specifications;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public int getMedicineId() {
                return medicineId;
            }

            public void setMedicineId(int medicineId) {
                this.medicineId = medicineId;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTcac() {
                return tcac;
            }

            public void setTcac(String tcac) {
                this.tcac = tcac;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getUsageAndDosage() {
                return usageAndDosage;
            }

            public void setUsageAndDosage(String usageAndDosage) {
                this.usageAndDosage = usageAndDosage;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getMedPicUrl() {
                return medPicUrl;
            }

            public void setMedPicUrl(String medPicUrl) {
                this.medPicUrl = medPicUrl;
            }

            public String getInteractions() {
                return interactions;
            }

            public void setInteractions(String interactions) {
                this.interactions = interactions;
            }
        }
    }
}
