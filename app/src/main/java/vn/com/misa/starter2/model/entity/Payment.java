package vn.com.misa.starter2.model.entity;

import java.io.Serializable;

/**
 * ‐ Lớp thanh toán
 * ‐ @created_by giangpb on 2/9/2021
 */
public class Payment implements Serializable {
    private String refID;

    private int refType;

    private String refNO;

    private String refDate;

    private int amount;

    private int promotionItemsAmount;

    private int totalItemAmount;

    private int promotionRate;

    private int promotionAmount;

    private int discountAmount;

    private int preTaxAmount;

    private int totalAmount;

    private int receiveAmount;

    private int returnAmount;

    private int paymentStatus;

    private String orderID;

    private int orderType;

    private String tableName;

    private String createdDate;

    public Payment() {
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public int getRefType() {
        return refType;
    }

    public void setRefType(int refType) {
        this.refType = refType;
    }

    public String getRefNO() {
        return refNO;
    }

    public void setRefNO(String refNO) {
        this.refNO = refNO;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
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

    public int getPreTaxAmount() {
        return preTaxAmount;
    }

    public void setPreTaxAmount(int preTaxAmount) {
        this.preTaxAmount = preTaxAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(int receiveAmount) {
        this.receiveAmount = receiveAmount;
    }


    public int getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(int returnAmount) {
        this.returnAmount = returnAmount;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
