package vn.com.misa.starter2.ui.login;

import vn.com.misa.starter2.model.dto.User;

/**
 * ‐ @created_by giangpb on 2/28/2021
 */
public interface ILoginProcessCallBack {
    void onLoginSuccess(User user);
    void onLoginFalse();
}
