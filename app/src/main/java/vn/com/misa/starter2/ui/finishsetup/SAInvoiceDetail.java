package vn.com.misa.starter2.ui.finishsetup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class SAInvoiceDetail implements Serializable {
    @SerializedName("RefDetailID")
    private String refDetailID;

    @SerializedName("RefID")
    private String refID;

    @SerializedName("ParentID")
    private String parentID;

    @SerializedName("ItemID")
    private String itemID;

    @SerializedName("ItemName")
    private String itemName;

    @SerializedName("RefDetailType")
    private int refDetailType;

    @SerializedName("UnitID")
    private String unitID;

    @SerializedName("UnitPrice")
    private int unitPrice;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("PromotionRate")
    private int promotionRate;

    @SerializedName("PromotionAmount")
    private int promotionAmount;

    @SerializedName("DiscountAmount")
    private int discountAmount;

    @SerializedName("Amount")
    private int amount;

    @SerializedName("SortOrder")
    private int sortOrder;

    @SerializedName("CreateDate")
    private Date createDate;

    public String getRefDetailID() {
        return refDetailID;
    }

    public void setRefDetailID(String refDetailID) {
        this.refDetailID = refDetailID;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
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

    public int getRefDetailType() {
        return refDetailType;
    }

    public void setRefDetailType(int refDetailType) {
        this.refDetailType = refDetailType;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(int promotionRate) {
        this.promotionRate = promotionRate;
    }

    public int getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(int promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
