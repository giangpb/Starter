package vn.com.misa.starter2.ui.login;

import vn.com.misa.starter2.model.dto.User;

/**
 * ‐ @created_by giangpb on 2/28/2021
 */
public class LoginPresenter implements ILoginProcessCallBack{

    private LoginModel loginModel;
    private ILoginView mILoginView;

    public LoginPresenter(ILoginView mILoginView) {
        this.loginModel = new LoginModel(this);
        this.mILoginView = mILoginView;
    }

    public void receiverUserFromView(User user){
        loginModel.processingLogin(user);
    }

    @Override
    public void onLoginSuccess(User user) {
        mILoginView.onLoginSuccess(user);
    }

    @Override
    public void onLoginFalse() {
        mILoginView.onLoginFalse();
    }
}
