package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/11.
 */

public class InquiryLineData extends BaseData {


    /**
     * data : {"booString":"1","count":8}
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
         * booString : 1
         * count : 8
         */

        @SerializedName("booString")
        private String booString;
        @SerializedName("count")
        private int count;

        public String getBooString() {
            return booString;
        }

        public void setBooString(String booString) {
            this.booString = booString;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
