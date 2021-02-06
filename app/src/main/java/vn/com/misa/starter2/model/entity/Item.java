package vn.com.misa.starter2.model.entity;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Objects;

/**
 * ‐ Entity thực thể sản phẩm
 * - chi tiết sản phẩm
 * ‐ @created_by giangpb on 1/26/2021
 */
public class Item implements Serializable {

    private String itemID;

    private String categoryID;

    private String itemCode;

    private String itemName;

    private int price;

    private String unitID;

    private byte[] image;

    private int position;

    //
    private int quantity;


    public Item() {
    }

    public Item(String itemID, String categoryID, String itemCode, String itemName, int price, String unitID, byte[] image, int pos, int quan) {
        this.itemID = itemID;
        this.categoryID = categoryID;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.unitID = unitID;
        this.image = image;
        this.position = pos;
        this.quantity = quan;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.quantity +" "+this.getItemName();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.itemID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.itemID, other.itemID)) {
            return false;
        }
        return true;
    }
}
