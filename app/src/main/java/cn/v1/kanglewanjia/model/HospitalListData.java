package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qy on 2018/1/5.
 */

public class HospitalListData extends BaseData {


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
         * hospitalPic : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=958727172,2276796764&fm=27&gp=0.jpg
         * hospitalName : 泾川县医院
         * hospitalId : 1
         */

        @SerializedName("hospitalPic")
        private String hospitalPic;
        @SerializedName("hospitalName")
        private String hospitalName;
        @SerializedName("hospitalId")
        private int hospitalId;

        public String getHospitalPic() {
            return hospitalPic;
        }

        public void setHospitalPic(String hospitalPic) {
            this.hospitalPic = hospitalPic;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }
    }
}
