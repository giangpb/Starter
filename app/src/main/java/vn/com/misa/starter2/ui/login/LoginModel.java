package vn.com.misa.starter2.ui.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.service.APIService;

/**
 * ‐ @created_by giangpb on 2/28/2021
 */
public class LoginModel {
    private ILoginProcessCallBack mCallBack;

    public LoginModel(ILoginProcessCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * @param user
     */
    public void processingLogin(User user) {

        APIService.API_SERVICE.getAllUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    boolean check = false;
                    for (User u : response.body()) {
                        if (u.getPhone().equalsIgnoreCase(user.getPhone()) && u.getPassword().equalsIgnoreCase(user.getPassword())) {
                            mCallBack.onLoginSuccess(u);
                            check = true;
                            break;
                        }
                    }
                    if (!check)
                        mCallBack.onLoginFalse("Tài khoản hoặc mật khẩu không đúng!");

                } else {
                    mCallBack.onLoginFalse("Lỗi kết nối tới máy chủ, vui lòng thử lại!");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mCallBack.onLoginFalse("Không thể kết nối tới máy chủ, vui lòng thử lại!");
            }
        });
    }
}
