package vn.com.misa.starter2.ui.login.dto;

/**
 * ‐ @created_by giangpb on 2/28/2021
 */
public class User {
    private String phone;

    private String password;

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public User() {
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
}
