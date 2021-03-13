package vn.com.misa.starter2.ui.login;

import vn.com.misa.starter2.ui.login.dto.User;

/**
 * ‐ @created_by giangpb on 2/28/2021
 */
public class LoginModel {
    private ILoginProcessCallBack mCallBack;

    public LoginModel(ILoginProcessCallBack callBack){
        this.mCallBack = callBack;
    }

    /**
     *
     * @param user
     */
    public void processingLogin(User user){
        if(user.getPhone().equals("0962818571") && user.getPassword().equals("123")){
            mCallBack.onLoginSuccess();
        }
        else {
            mCallBack.onLoginFalse();
        }
    }
}
