package vn.com.misa.starter2.model.entity;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

import vn.com.misa.starter2.presenter.CategoryPresenter;

/**
 * ‐ Lớp danh mục nhà hàng
 * - cho phép chọn loại danh mục nhà hàng
 * ‐ @created_by giangpb on 1/21/2021
 */
public class Restaurant implements Serializable {

    private int restaurantId;

    private String restaurantName;

    private int resRestaurantImage;

    public Restaurant() {
    }

    public Restaurant( int restaurantId, String restaurantName, int resRestaurantImage) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.resRestaurantImage = resRestaurantImage;
    }
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getResRestaurantImage() {
        return resRestaurantImage;
    }

    public void setResRestaurantImage(int resRestaurantImage) {
        this.resRestaurantImage = resRestaurantImage;
    }
}
