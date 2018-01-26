package cn.v1.kanglewanjia.model;

/**
 * Created by lawrence on 15/8/29.
 */
public class UploadPicData extends BaseData{

    /**
     * leng : 76143
     * ls : {}
     * fileName : uploadPicTmp.jpg
     * url : 1/M00/00/4F/wKjbZFXhJx-AfwMAAAEpb4MhHNk410.jpg
     * defaultUlr  默认域名
     */

    private int leng;
    private String fileName;
    private String url;
    private String defaultUlr;

    public String getDefaultUlr() {
        return defaultUlr;
    }

    public void setDefaultUlr(String defaultUlr) {
        this.defaultUlr = defaultUlr;
    }

    public void setLeng(int leng) {
        this.leng = leng;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLeng() {
        return leng;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }
}
