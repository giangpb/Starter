package vn.com.misa.starter2.ui.paymentdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.PaymentDetailAdapter;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.model.entity.PaymentDetail;
import vn.com.misa.starter2.ui.collectmoney.PaymentDetailPresenter;

public class PaymentDetailActivity extends AppCompatActivity {
    private static final String TAG = "PaymentDetailActivity";

    private Payment mPayment;

    private DecimalFormat decimalFormat;

    // controls
    ImageView ivBack;
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
        //
        decimalFormat = new DecimalFormat("#,###");
        tvAmount.setText(decimalFormat.format(mPayment.getAmount()));
        tvPaymentNO.setText("Số: "+mPayment.getRefNO());
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

        paymentDetailPresenter = new PaymentDetailPresenter(getApplicationContext());
        dataPaymentDetail = paymentDetailPresenter.getAllPaymentDetail(mPayment.getRefID());
        paymentDetailAdapter = new PaymentDetailAdapter(getApplicationContext(),dataPaymentDetail);

        tvCountQuantity.setText(paymentDetailPresenter.quantityCount(dataPaymentDetail)+"");
        rcvListPaymentDetail.setAdapter(paymentDetailAdapter);
        rcvListPaymentDetail.setHasFixedSize(true);
        rcvListPaymentDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rcvListPaymentDetail.setNestedScrollingEnabled(false);
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
    }
}