package vn.com.misa.starter2.ui.finishsetup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.ui.order.OrderActivity;

/**
 * - Fragment hiển thị thiết lập hoàn thành của khách hàng
 * -
 * -
 * @author GIANG PHAN
 * @date: 22/01/2021
 */
public class FinishSetupFragment extends Fragment {

    public static final String SHARE_PRE_FINISH_SETUP="SHARE_FINISH_SETUP";
    public static final String KEY_PRE_FINISH_SETUP="DONE";

    private static final String TAG = "FinishSetupFragment";

    private NavController navController;

    private MaterialButton btnFinishSetup;
    private ImageView ivArrowBackSetupMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish_setup, container, false);

        // thiết lập các điều khiển
        ivArrowBackSetupMenu = view.findViewById(R.id.ivArrowBackSetupMenu);
        btnFinishSetup = view.findViewById(R.id.btnFinishSetup);
        // khởi tạo sự kiện
        addEvents();

        return view;
    }

    /**
     * Hàm khởi tạo sự kiện
     * -
     * -
     * @author: giangpb
     * @date: 22/01/2021
     */
    private void addEvents(){
        ivArrowBackSetupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.setupMenuFragment,false).build();
                    //navController.navigate(R.id.action_finishSetupFragment_to_setupMenuFragment,null,navOptions);
                    getActivity().onBackPressed();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        btnFinishSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    View alertLayout = getLayoutInflater().inflate(R.layout.view_custom_progress_dialog, null);

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setView(alertLayout);
                    alert.setCancelable(false);
                    AlertDialog dialog = alert.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    new CountDownTimer(3000,100){
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                        }
                    };

                     //lưu trạng thái setup thành công !
                    setupStateFinishSetup();

                    // chuyển sang màn hình order và kết thúc quá trình setup
                    Intent intent =new Intent(getActivity(), OrderActivity.class);
                    startActivity(intent);

                    getActivity().finish();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    /**
     * Hàm lưu trạng thái quá trình setup thành công !
     * @author: giangpb
     * @date 27/01/2021
     */
    private void setupStateFinishSetup(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARE_PRE_FINISH_SETUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PRE_FINISH_SETUP, "doneS");
        editor.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}