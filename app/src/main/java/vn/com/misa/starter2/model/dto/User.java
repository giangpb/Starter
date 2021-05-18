package vn.com.misa.starter2.model.dto;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("UserID")
    private int id;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Password")
    private String password;

    @SerializedName("Permission")
    private int permission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
