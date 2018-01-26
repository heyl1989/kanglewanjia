package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/12.
 */

public class PatientCaseData extends BaseData {


    /**
     * data : {"allergy":"1","anamnesis":"1","auxiliaryExamination":"1","cause":"1","clinicTime":"Jan 11, 2018 7:22:59 PM","departments":"呼吸科","family":"1","id":10003,"medicalHistory":"1","orderId":10067,"patientInfoId":68338,"personage":"1","physicalExamination":"1","primaryDiagnosis":"1","realName":"盖伟伟","treatmentSuggestion":"1","userId":1}
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
         * allergy : 1
         * anamnesis : 1
         * auxiliaryExamination : 1
         * cause : 1
         * clinicTime : Jan 11, 2018 7:22:59 PM
         * departments : 呼吸科
         * family : 1
         * id : 10003
         * medicalHistory : 1
         * orderId : 10067
         * patientInfoId : 68338
         * personage : 1
         * physicalExamination : 1
         * primaryDiagnosis : 1
         * realName : 盖伟伟
         * treatmentSuggestion : 1
         * userId : 1
         */

        @SerializedName("allergy")
        private String allergy;
        @SerializedName("anamnesis")
        private String anamnesis;
        @SerializedName("auxiliaryExamination")
        private String auxiliaryExamination;
        @SerializedName("cause")
        private String cause;
        @SerializedName("clinicTime")
        private String clinicTime;
        @SerializedName("departments")
        private String departments;
        @SerializedName("family")
        private String family;
        @SerializedName("marital")
        private String marital = "";
        @SerializedName("id")
        private int id;
        @SerializedName("medicalHistory")
        private String medicalHistory;
        @SerializedName("orderId")
        private int orderId;
        @SerializedName("patientInfoId")
        private int patientInfoId;
        @SerializedName("personage")
        private String personage;
        @SerializedName("physicalExamination")
        private String physicalExamination;
        @SerializedName("primaryDiagnosis")
        private String primaryDiagnosis;
        @SerializedName("realName")
        private String realName;
        @SerializedName("treatmentSuggestion")
        private String treatmentSuggestion;
        @SerializedName("userId")
        private int userId;


        public String getMarital() {
            return marital;
        }

        public void setMarital(String marital) {
            this.marital = marital;
        }

        public String getAllergy() {
            return allergy;
        }

        public void setAllergy(String allergy) {
            this.allergy = allergy;
        }

        public String getAnamnesis() {
            return anamnesis;
        }

        public void setAnamnesis(String anamnesis) {
            this.anamnesis = anamnesis;
        }

        public String getAuxiliaryExamination() {
            return auxiliaryExamination;
        }

        public void setAuxiliaryExamination(String auxiliaryExamination) {
            this.auxiliaryExamination = auxiliaryExamination;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getClinicTime() {
            return clinicTime;
        }

        public void setClinicTime(String clinicTime) {
            this.clinicTime = clinicTime;
        }

        public String getDepartments() {
            return departments;
        }

        public void setDepartments(String departments) {
            this.departments = departments;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMedicalHistory() {
            return medicalHistory;
        }

        public void setMedicalHistory(String medicalHistory) {
            this.medicalHistory = medicalHistory;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getPatientInfoId() {
            return patientInfoId;
        }

        public void setPatientInfoId(int patientInfoId) {
            this.patientInfoId = patientInfoId;
        }

        public String getPersonage() {
            return personage;
        }

        public void setPersonage(String personage) {
            this.personage = personage;
        }

        public String getPhysicalExamination() {
            return physicalExamination;
        }

        public void setPhysicalExamination(String physicalExamination) {
            this.physicalExamination = physicalExamination;
        }

        public String getPrimaryDiagnosis() {
            return primaryDiagnosis;
        }

        public void setPrimaryDiagnosis(String primaryDiagnosis) {
            this.primaryDiagnosis = primaryDiagnosis;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getTreatmentSuggestion() {
            return treatmentSuggestion;
        }

        public void setTreatmentSuggestion(String treatmentSuggestion) {
            this.treatmentSuggestion = treatmentSuggestion;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
