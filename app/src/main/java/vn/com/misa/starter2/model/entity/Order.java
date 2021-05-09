package vn.com.misa.starter2.model.entity;

import java.io.Serializable;

/**
 * ‐ Lớp order thêm order
 * ‐ @created_by giangpb on 2/1/2021
 */
public class Order implements Serializable {
    private String orderID;

    private String orderNo;

    private String orderDate;

    private int orderType;

    private int orderStatus;

    private int amount; // tổng tiền

    private int promotionItemsAmount; // giảm giá theo từng sản phẩm

    private int totalItemAmount; // tổng số tiền mặt hàng

    private int promotionRate ; // giảm theo tỉ lệ phần trăm

    private int promotionAmount; // giảm theo giá tiền

    private int discountAmount; // tổng giảm

    private int totalAmount; // tổng phải trả

    private String tableName; // tên bàn

    private String itemNames; // tên các món đc order

    private String dateCrate; // ngày tạo

    public Order(){
        this.amount = 0;
        this.promotionItemsAmount = 0;
        this.totalItemAmount = 0;
        this.promotionRate = 0;
        this.promotionAmount = 0;
        this.discountAmount = 0;
        this.totalAmount = 0;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPromotionItemsAmount() {
        return promotionItemsAmount;
    }

    public void setPromotionItemsAmount(int promotionItemsAmount) {
        this.promotionItemsAmount = promotionItemsAmount;
    }

    public int getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(int totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
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

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

    public String getDateCrate() {
        return dateCrate;
    }

    public void setDateCrate(String dateCrate) {
        this.dateCrate = dateCrate;
    }
}
