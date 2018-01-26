package cn.v1.kanglewanjia.model;

import android.content.Intent;

import java.util.List;

/**
 * Created by qy on 2017/11/21.
 */

public class DoctorRecordData {

    private List<Record> imgList;

    public List<Record> getImgList() {
        return imgList;
    }

    public void setImgList(List<Record> imgList) {
        this.imgList = imgList;
    }

    public static class Record {
        private int imgId;
        private int tag;

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }
}
