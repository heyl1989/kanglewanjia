package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qy on 2018/1/5.
 */

public class DepartmentData extends BaseData {


    /**
     * data : {"count":2,"departs":[{"departId":"10000","departName":"呼吸科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"1","lineUpCount":"0"},{"departId":"10001","departName":"高压氧科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"2","lineUpCount":"0"}],"departsCjb":[{"departId":"10000","departName":"呼吸科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"1","lineUpCount":"0"},{"departId":"10001","departName":"高压氧科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"2","lineUpCount":"0"}],"departsMxb":[{"departId":"10000","departName":"呼吸科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"1","lineUpCount":"0"},{"departId":"10001","departName":"高压氧科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"2","lineUpCount":"0"}]}
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
         * count : 2
         * departs : [{"departId":"10000","departName":"呼吸科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"1","lineUpCount":"0"},{"departId":"10001","departName":"高压氧科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"2","lineUpCount":"0"}]
         * departsCjb : [{"departId":"10000","departName":"呼吸科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"1","lineUpCount":"0"},{"departId":"10001","departName":"高压氧科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"2","lineUpCount":"0"}]
         * departsMxb : [{"departId":"10000","departName":"呼吸科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"1","lineUpCount":"0"},{"departId":"10001","departName":"高压氧科","departStatus":"1","description":"高血压，高血脂，糖尿病，偏食，易感冒，体质弱","hosDepId":"2","lineUpCount":"0"}]
         */

        @SerializedName("count")
        private int count;
        @SerializedName("departs")
        private List<DepartsData> departs;
        @SerializedName("departsCjb")
        private List<DepartsCjbData> departsCjb;
        @SerializedName("departsMxb")
        private List<DepartsMxbData> departsMxb;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DepartsData> getDeparts() {
            return departs;
        }

        public void setDeparts(List<DepartsData> departs) {
            this.departs = departs;
        }

        public List<DepartsCjbData> getDepartsCjb() {
            return departsCjb;
        }

        public void setDepartsCjb(List<DepartsCjbData> departsCjb) {
            this.departsCjb = departsCjb;
        }

        public List<DepartsMxbData> getDepartsMxb() {
            return departsMxb;
        }

        public void setDepartsMxb(List<DepartsMxbData> departsMxb) {
            this.departsMxb = departsMxb;
        }

        public static class DepartsData implements Serializable{
            /**
             * departId : 10000
             * departName : 呼吸科
             * departStatus : 1
             * description : 高血压，高血脂，糖尿病，偏食，易感冒，体质弱
             * hosDepId : 1
             * lineUpCount : 0
             */

            @SerializedName("departId")
            private String departId;
            @SerializedName("departName")
            private String departName;
            @SerializedName("departStatus")
            private String departStatus;
            @SerializedName("description")
            private String description;
            @SerializedName("doctorId")
            private String doctorId;
            @SerializedName("hosDepId")
            private String hosDepId;
            @SerializedName("lineUpCount")
            private String lineUpCount;

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getDepartId() {
                return departId;
            }

            public void setDepartId(String departId) {
                this.departId = departId;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDepartStatus() {
                return departStatus;
            }

            public void setDepartStatus(String departStatus) {
                this.departStatus = departStatus;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getHosDepId() {
                return hosDepId;
            }

            public void setHosDepId(String hosDepId) {
                this.hosDepId = hosDepId;
            }

            public String getLineUpCount() {
                return lineUpCount;
            }

            public void setLineUpCount(String lineUpCount) {
                this.lineUpCount = lineUpCount;
            }
        }

        public static class DepartsCjbData {
            /**
             * departId : 10000
             * departName : 呼吸科
             * departStatus : 1
             * description : 高血压，高血脂，糖尿病，偏食，易感冒，体质弱
             * hosDepId : 1
             * lineUpCount : 0
             */

            @SerializedName("departId")
            private String departId;
            @SerializedName("departName")
            private String departName;
            @SerializedName("departStatus")
            private String departStatus;
            @SerializedName("description")
            private String description;
            @SerializedName("doctorId")
            private String doctorId;
            @SerializedName("hosDepId")
            private String hosDepId;
            @SerializedName("lineUpCount")
            private String lineUpCount;


            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getDepartId() {
                return departId;
            }

            public void setDepartId(String departId) {
                this.departId = departId;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDepartStatus() {
                return departStatus;
            }

            public void setDepartStatus(String departStatus) {
                this.departStatus = departStatus;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getHosDepId() {
                return hosDepId;
            }

            public void setHosDepId(String hosDepId) {
                this.hosDepId = hosDepId;
            }

            public String getLineUpCount() {
                return lineUpCount;
            }

            public void setLineUpCount(String lineUpCount) {
                this.lineUpCount = lineUpCount;
            }
        }

        public static class DepartsMxbData {
            /**
             * departId : 10000
             * departName : 呼吸科
             * departStatus : 1
             * description : 高血压，高血脂，糖尿病，偏食，易感冒，体质弱
             * hosDepId : 1
             * lineUpCount : 0
             */

            @SerializedName("departId")
            private String departId;
            @SerializedName("departName")
            private String departName;
            @SerializedName("departStatus")
            private String departStatus;
            @SerializedName("description")
            private String description;
            @SerializedName("doctorId")
            private String doctorId;
            @SerializedName("hosDepId")
            private String hosDepId;
            @SerializedName("lineUpCount")
            private String lineUpCount;

            public String getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(String doctorId) {
                this.doctorId = doctorId;
            }

            public String getDepartId() {
                return departId;
            }

            public void setDepartId(String departId) {
                this.departId = departId;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDepartStatus() {
                return departStatus;
            }

            public void setDepartStatus(String departStatus) {
                this.departStatus = departStatus;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getHosDepId() {
                return hosDepId;
            }

            public void setHosDepId(String hosDepId) {
                this.hosDepId = hosDepId;
            }

            public String getLineUpCount() {
                return lineUpCount;
            }

            public void setLineUpCount(String lineUpCount) {
                this.lineUpCount = lineUpCount;
            }
        }
    }
}
