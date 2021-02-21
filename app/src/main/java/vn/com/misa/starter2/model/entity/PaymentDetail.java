package vn.com.misa.starter2.model.entity;

/**
 * ‐ Chi tiết payment
 * ‐ @created_by giangpb on 2/10/2021
 */
public class PaymentDetail {
    private String paymentDetailID;

    private String paymentID;

    private String parentID;

    private String itemID;

    private String itemName;

    private int refDetailType;

    private String unitID;

    private int unitPrice;

    private int quantity;

    private int promotionRate;

    private int promotionAmount;

    private int discountAmount;

    private int amount;

    private int sortOrder;

    private String createdDate;

    public PaymentDetail() {
    }

    public String getPaymentDetailID() {
        return paymentDetailID;
    }

    public void setPaymentDetailID(String paymentDetailID) {
        this.paymentDetailID = paymentDetailID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
