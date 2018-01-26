package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/4.
 */

public class SavePatientInfoData extends BaseData {


    /**
     * data : 1
     */

    @SerializedName("data")
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
