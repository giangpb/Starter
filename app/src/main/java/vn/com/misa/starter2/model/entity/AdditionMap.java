package vn.com.misa.starter2.model.entity;

/**
 * ‐ Kết nối danh mục với mục bổ sung
 * ‐ @created_by giangpb on 2/2/2021
 */
public class AdditionMap {
    private String additionMapID;

    private String additionID;

    private String categoryID;

    public AdditionMap(){}

    public AdditionMap(String additionMapID, String additionID, String categoryID) {
        this.additionMapID = additionMapID;
        this.additionID = additionID;
        this.categoryID = categoryID;
    }

    public String getAdditionMapID() {
        return additionMapID;
    }

    public void setAdditionMapID(String additionMapID) {
        this.additionMapID = additionMapID;
    }

    public String getAdditionID() {
        return additionID;
    }

    public void setAdditionID(String additionID) {
        this.additionID = additionID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
