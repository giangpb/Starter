package vn.com.misa.starter2.ui.finishsetup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;


public class SAInvoice implements Serializable {
    @SerializedName("RefID")
    private String refID;

    @SerializedName("RefType")
    private int refType;

    @SerializedName("RefNo")
    private String refNo;

    @SerializedName("RefDate")
    private Date refDate;

    @SerializedName("Amount")
    private int amount;

    @SerializedName("PromotionItemsAmount")
    private int promotionItemsAmount;

    @SerializedName("TotalItemAmount")
    private int totalItemAmount;

    @SerializedName("PromotionRate")
    private int promotionRate;

    @SerializedName("PromotionAmount")
    private int promotionAmount;

    @SerializedName("DiscountAmount")
    private int discountAmount;

    @SerializedName("PreTaxAmount")
    private int preTaxAmount;

    @SerializedName("TotalAmount")
    private int totalAmount;

    @SerializedName("ReceiveAmount")
    private int receiveAmount;

    @SerializedName("ReturnAmount")
    private int returnAmount;

    @SerializedName("PaymentStatus")
    private int paymentStatus;

    @SerializedName("OrderID")
    private String orderID;

    @SerializedName("OrderType")
    private int orderType;

    @SerializedName("TableName")
    private String tableName;

    @SerializedName("CreateDate")
    private String createDate;

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

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Date getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
