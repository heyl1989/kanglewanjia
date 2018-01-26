package cn.v1.kanglewanjia.model;

/**
 * Created by qy on 2017/11/15.
 */

public class FamilyFileListData {

    public FamilyFileListData(boolean isbuttonVisible) {
        this.isbuttonVisible = isbuttonVisible;
    }

    private boolean isbuttonVisible;

    public boolean isbuttonVisible() {
        return isbuttonVisible;
    }

    public void setIsbuttonVisible(boolean isbuttonVisible) {
        this.isbuttonVisible = isbuttonVisible;
    }
}
