package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/10.
 */

public class ActiveFamlilyFileData extends BaseData {


    /**
     * data : {"doctorId":"21"}
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
         * doctorId : 21
         */

        @SerializedName("doctorId")
        private String doctorId;

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }
    }
}
