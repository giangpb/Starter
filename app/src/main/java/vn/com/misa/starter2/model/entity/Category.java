package vn.com.misa.starter2.model.entity;

import java.io.Serializable;

/**
 * ‐ Lớp danh sách nhóm thực đơn
 * ‐ @created_by giangpb on 1/25/2021
 */
public class Category implements Serializable {
    private String categoryID;

    private String categoryCode;

    private String categoryName;

    private String iconPath;

    private String createdDate;

    private int sortOrder;

    private int iconType;

    private boolean isActive;


    private int count;


    public Category() {
        this.count = 0;
    }

    public Category(String categoryID, String categoryCode, String categoryName, String iconPath, String createdDate, int sortOrder, int iconType) {
        this.categoryID = categoryID;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.iconPath = iconPath;
        this.createdDate = createdDate;
        this.sortOrder = sortOrder;
        this.iconType = iconType;
        this.isActive = false;
        this.count = 0;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    @Override
    public String toString() {
        return this.getCategoryName();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
