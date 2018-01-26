package cn.v1.kanglewanjia.model;

/**
 * Created by qy on 2018/1/4.
 */

public class RelationShipData {

    private String roleName;
    private int roleResourse;

    public RelationShipData(String roleName, int roleResourse) {
        this.roleName = roleName;
        this.roleResourse = roleResourse;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleResourse() {
        return roleResourse;
    }

    public void setRoleResourse(int roleResourse) {
        this.roleResourse = roleResourse;
    }
}
