package vn.com.misa.starter2.model.entity;

/**
 * ‐ Lớp unit  (đơn vị tính)
 * ‐ @created_by giangpb on 1/26/2021
 */
public class Unit {

    private String unitID;

    private String unitName;

    private boolean isActive;

    public Unit() {
    }

    public Unit(String unitID, String unitName) {
        this.unitID = unitID;
        this.unitName = unitName;
        this.isActive = false;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return this.unitName;
    }
}
