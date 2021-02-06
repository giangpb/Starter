package vn.com.misa.starter2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import vn.com.misa.starter2.MainActivity;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.ui.finishsetup.FinishSetupFragment;
import vn.com.misa.starter2.ui.order.OrderActivity;

/**
 * - Splash screen chạy đầu tiên sau khi mở ứng dụng
 * - Show logo 1 giây
 * @author GIANG PHAN
 * @date 20/01/2021
 */
public class SplashScreenActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask timerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // kiểm tra người dùng đã setup trước đó chưa?
        SharedPreferences sharedPreferences = getSharedPreferences(FinishSetupFragment.SHARE_PRE_FINISH_SETUP,MODE_PRIVATE);
        String check = sharedPreferences.getString(FinishSetupFragment.KEY_PRE_FINISH_SETUP,null);

        // khởi tạo luồng
        timer = new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                if(check==null){
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SplashScreenActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };

        // cài đặt thời gian sau 1s thì sẽ chuyển qua màn hình chính
        timer.schedule(timerTask,1000);

    }
}