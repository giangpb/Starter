package vn.com.misa.starter2.model.entity;

/**
 * ‐ Lớp additon hiển thị những thông tin bổ trợ cho sản phẩm, ví dụ : nóng, nhiều đá
 * ‐ @created_by giangpb on 2/2/2021
 */

public class Addition {
    private String additionID;

    private String description;

    private String inventoryItemCategoryAdditionID;

    private int position;

    public Addition() {
    }

    public Addition(String additionID, String description, String inventoryItemCategoryAdditionID, int position) {
        this.additionID = additionID;
        this.description = description;
        this.inventoryItemCategoryAdditionID = inventoryItemCategoryAdditionID;
        this.position = position;
    }

    public String getAdditionID() {
        return additionID;
    }

    public void setAdditionID(String additionID) {
        this.additionID = additionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventoryItemCategoryAdditionID() {
        return inventoryItemCategoryAdditionID;
    }

    public void setInventoryItemCategoryAdditionID(String inventoryItemCategoryAdditionID) {
        this.inventoryItemCategoryAdditionID = inventoryItemCategoryAdditionID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
