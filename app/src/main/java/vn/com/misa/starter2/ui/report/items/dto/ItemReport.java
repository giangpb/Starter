package vn.com.misa.starter2.ui.report.items.dto;

/**
 * ‐ @created_by giangpb on 2/19/2021
 */
public class ItemReport {
    private String itemID;

    private String itemName;

    private int itemQuantity;

    private int itemPrice;

    public ItemReport() {
    }

    public ItemReport(String itemID, String itemName, int itemQuantity, int itemPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
}
