package vn.com.misa.starter2.model.entity;

/**
 * ‐ lớp order detail (chi tiết hoá đơn)
 * 1 - hoá đơn có nhiều chi tiết hoá đơn
 * ‐ @created_by giangpb on 2/1/2021
 */
public class OrderDetail {

    private String orderDetailID; // mã chi tiết

    private String orderID; // id hoá đơn

    private String parentID; // danh sách bổ sung cho từng thuộc tính, vd: không đường

    private String itemID; // mặt hàng, item bổ sung

    private String itemName;

    private int orderDetailType;

    private String unitID; // đơn vị

    private int unitPrice; // giá mỗi item

    private int quantity; // số lượng

    private int promotionRate; // tỉ lệ khuến mãi

    private int promotionAmount; // số tiền khuyến mãi

    private int amount ;// số tiền

    private String dateCreate;

    public OrderDetail(){}

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    public int getOrderDetailType() {
        return orderDetailType;
    }

    public void setOrderDetailType(int orderDetailType) {
        this.orderDetailType = orderDetailType;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
