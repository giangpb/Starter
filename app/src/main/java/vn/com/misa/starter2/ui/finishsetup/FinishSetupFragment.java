package vn.com.misa.starter2.ui.finishsetup;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.ui.collectmoney.PaymentDetailPresenter;
import vn.com.misa.starter2.ui.collectmoney.PaymentPresenter;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.ui.order.OrderActivity;
import vn.com.misa.starter2.ui.order.entities.NumNotiSyn;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGConstants;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * - Fragment hiển thị thiết lập hoàn thành của khách hàng
 * -
 * -
 *
 * @author GIANG PHAN
 * @date: 22/01/2021
 */
public class FinishSetupFragment extends Fragment implements IGetDataFromServiceContracts {

    public static final String SHARE_PRE_FINISH_SETUP = "SHARE_FINISH_SETUP";
    public static final String KEY_PRE_FINISH_SETUP = "DONE";

    private static final String TAG = "FinishSetupFragment";

    private NavController navController;

    private MaterialButton btnFinishSetup;
    private ImageView ivArrowBackSetupMenu;

    private FinishPresenter finishPresenter;

    private PaymentPresenter paymentPresenter;

    private AlertDialog mDialog;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish_setup, container, false);
        mDialog = GIANGUtils.getInstance().showDialog(getContext(), getLayoutInflater());
        // thiết lập các điều khiển
        ivArrowBackSetupMenu = view.findViewById(R.id.ivArrowBackSetupMenu);
        btnFinishSetup = view.findViewById(R.id.btnFinishSetup);
        finishPresenter = new FinishPresenter(this);
        // khởi tạo sự kiện
        addEvents();

        return view;
    }

    /**
     * Hàm khởi tạo sự kiện
     * -
     * -
     *
     * @author: giangpb
     * @date: 22/01/2021
     */
    private void addEvents() {
        ivArrowBackSetupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.setupMenuFragment,false).build();
                    //navController.navigate(R.id.action_finishSetupFragment_to_setupMenuFragment,null,navOptions);
                    getActivity().onBackPressed();
                } catch (Exception ex) {
                    Log.d(TAG, "onClick: " + ex.getMessage());
                }
            }
        });

        btnFinishSetup.setOnClickListener(v -> {
            try {
                User user = GIANGCache.getInstance().get(LoginActivity.KEY_LOGIN, User.class);
                finishPresenter.startSynch(user.getId());
                finishPresenter.startSynSAInvoiceDetail(user.getId());
            } catch (Exception exx) {
                GIANGUtils.getInstance().handlerLog(exx.getMessage());
            }
        });
    }

    /**
     * Hàm lưu trạng thái quá trình setup thành công !
     *
     * @author: giangpb
     * @date 27/01/2021
     */
    private void setupStateFinishSetup() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARE_PRE_FINISH_SETUP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PRE_FINISH_SETUP, "doneS");
        editor.apply();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        paymentPresenter = new PaymentPresenter(getContext());
    }

    @Override
    public void onSuccess(List<SAInvoice> listPayment) {
        try {
            // đồng bộ vào db local
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            GIANGUtils.getInstance().handlerLog(gsonBuilder.create().toJson(listPayment));
//            GIANGUtils.getInstance().handlerLog(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(listPayment.get(0).getRefDate()));

            //lưu trạng thái setup thành công !
            // chuyển sang màn hình order và kết thúc quá trình setup
            for (SAInvoice s : listPayment){
                paymentPresenter.addSAInvoice(s);
            }
            setupStateFinishSetup();
            GIANGCache.getInstance().put(GIANGConstants.CACHE_COUNT_SYNC, new NumNotiSyn());
            mDialog.dismiss();
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            startActivity(intent);

            getActivity().finish();
        } catch (Exception ex) {
            GIANGUtils.getInstance().handlerLog(ex.getMessage());
        }
    }

    @Override
    public void onSuccessDetail(List<SAInvoiceDetail> listPaymentDetail) {
        try{
            mDialog.show();
            PaymentDetailPresenter paymentDetailPresenter = new PaymentDetailPresenter(getContext());
            for (SAInvoiceDetail sa: listPaymentDetail){
                paymentDetailPresenter.addSAInvoiceDetail(sa);
            }
            mDialog.dismiss();
        }
        catch (Exception ex){
            GIANGUtils.getInstance().handlerLog(ex.getMessage());
        }
    }

    @Override
    public void onFailure(String message) {
        mDialog.dismiss();
        Toast.makeText(getContext(), "ERR" + message, Toast.LENGTH_SHORT).show();
        setupStateFinishSetup();
        GIANGCache.getInstance().put(GIANGConstants.CACHE_COUNT_SYNC, new NumNotiSyn());
        mDialog.dismiss();
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        startActivity(intent);

        getActivity().finish();
    }

    @Override
    public void onS() {
        mDialog.show();
    }
}