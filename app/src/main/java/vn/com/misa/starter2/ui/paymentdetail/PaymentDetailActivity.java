package vn.com.misa.starter2.ui.paymentdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.PaymentDetailAdapter;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.model.entity.PaymentDetail;
import vn.com.misa.starter2.ui.collectmoney.PaymentDetailPresenter;
import vn.com.misa.starter2.util.GIANGUtils;

public class PaymentDetailActivity extends AppCompatActivity {
    private static final String TAG = "PaymentDetailActivity";

    private Payment mPayment;

    private DecimalFormat decimalFormat;

    // controls
    ImageView ivBack;
    private ImageView ivOptionMenu;
    //
    private TextView tvPaymentNO;
    private TextView tvAmount;
    private TextView tvReceiveAmount;
    private TextView tvOrderID;
    private TextView tvCountQuantity;
    private TextView tvReturnAmount;
    private TextView tvPaymentDateCreate;

    private RelativeLayout rlTraLai;

    //
    private RecyclerView rcvListPaymentDetail;
    private PaymentDetailAdapter paymentDetailAdapter;
    private ArrayList<PaymentDetail> dataPaymentDetail;

    //
    private PaymentDetailPresenter paymentDetailPresenter;

    //
    private TextView tvTableName;

    //
    private RelativeLayout rlPromotion;
    private TextView tvPromotion;
    private TextView tvTotalAmout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        mPayment = (Payment) getIntent().getSerializableExtra("payment");
        addControls();
        addEvents();
    }

    private void addControls(){
        tvPaymentDateCreate = findViewById(R.id.tvPaymentDateCreate);
        rlTraLai = findViewById(R.id.rlTraLai);
        ivBack = findViewById(R.id.ivBack);
        rcvListPaymentDetail = findViewById(R.id.rcvPaymentDetail);
        tvPaymentNO = findViewById(R.id.tvPaymentNO);
        tvAmount = findViewById(R.id.tvAmount);
        tvReceiveAmount = findViewById(R.id.tvReceiveAmount);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvCountQuantity = findViewById(R.id.tvCountQuantity);
        tvReturnAmount = findViewById(R.id.tvReturnAmount);
        ivOptionMenu = findViewById(R.id.ivOptionMenu);
        tvTableName = findViewById(R.id.tvTableName);

        rlPromotion = findViewById(R.id.rlPromotion);
        tvPromotion = findViewById(R.id.tvPromotion);
        tvTotalAmout = findViewById(R.id.tvTotalAmout);

        try{
            GIANGUtils.getInstance().checkShowHideView(mPayment.getTableName(),"",tvTableName);
            tvTableName.setText(String.format(" - Bàn: %s",mPayment.getTableName()));
            //
            decimalFormat = new DecimalFormat("#,###");
            tvAmount.setText(decimalFormat.format(mPayment.getAmount()));
            tvPaymentNO.setText(String.format("Số: %s", mPayment.getRefNO()));
            tvReceiveAmount.setText(decimalFormat.format(mPayment.getReceiveAmount()));
            tvOrderID.setText(mPayment.getOrderID());
            tvPaymentDateCreate.setText(mPayment.getRefDate());
            int returnMoney = mPayment.getReturnAmount();
            // kiểm tra tiền trả lại để ẩn
            if(returnMoney ==0){
                rlTraLai.setVisibility(View.GONE);
            }
            else {
                rlTraLai.setVisibility(View.VISIBLE);
                tvReturnAmount.setText(decimalFormat.format(returnMoney));
            }

            if (mPayment.getDiscountAmount()>0){
                rlPromotion.setVisibility(View.VISIBLE);
                tvPromotion.setText(decimalFormat.format(mPayment.getPromotionAmount()));
                tvTotalAmout.setText(decimalFormat.format(mPayment.getTotalAmount()));
            }
            else{
                rlPromotion.setVisibility(View.GONE);
            }

            paymentDetailPresenter = new PaymentDetailPresenter(getApplicationContext());
            dataPaymentDetail = paymentDetailPresenter.getAllPaymentDetail(mPayment.getRefID());
            paymentDetailAdapter = new PaymentDetailAdapter(getApplicationContext(),dataPaymentDetail);

            tvCountQuantity.setText(paymentDetailPresenter.quantityCount(dataPaymentDetail)+"");
            rcvListPaymentDetail.setAdapter(paymentDetailAdapter);
            rcvListPaymentDetail.setHasFixedSize(true);
            rcvListPaymentDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
            rcvListPaymentDetail.setNestedScrollingEnabled(false);
        }
        catch (Exception exception){
            GIANGUtils.getInstance().handlerLog(exception.getMessage());
        }
    }

    private void addEvents(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    onBackPressed();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        ivOptionMenu.setOnClickListener(v->{
            try{
                showPopupWindow(v);
            }
            catch (Exception ex){
                GIANGUtils.getInstance().handlerLog(ex.getMessage());
            }
        });
    }

    /**
     * Hàm show popup menu khi nhấn vào option menu
     * hiển thị icon
     * @param view neo vào view nào khi hiển thị
     * @author giangpb
     * @date 23/01/2021
     */
    private void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.mnu_print_order_detail, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            // sự kiện nhấn vào từng item
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });
        popup.show();
    }
}