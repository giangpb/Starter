package vn.com.misa.starter2.ui.payment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.PaymentAdapter;
import vn.com.misa.starter2.adapter.SelectDaySpinner;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.ui.collectmoney.PaymentPresenter;
import vn.com.misa.starter2.ui.paymentdetail.PaymentDetailActivity;

public class PaymentActivity extends AppCompatActivity implements IPaymentClickListener {
    private static final String TAG = "PaymentActivity";
    private DecimalFormat decimalFormat;

    private PaymentPresenter paymentPresenter;

    private PaymentAdapter paymentAdapter;
    private RecyclerView rcvLstPayment;

    private ImageView ivBack;

    private TextView tvDaySelected;
    private TextView tvDateFill;
    private TextView tvPriceFill;

    // vị trí chọn của spinner mặc định ban đầu
    public static int posOfItemSpinnerSelected = -1;
    private SelectDaySpinner selectDaySpinner;
    private Spinner spSelectDay;

    // parent view
    LinearLayout llMaiPayment;
    LinearLayout llNoData;
    RelativeLayout rlPaymentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        addControls();
        addEvents();
    }

    /**
     * Hàm khởi tạo các điều khiênr
     * @author giangpb
     * @date 12/02/2021
     */
    private void addControls(){
        //view
        tvDaySelected = findViewById(R.id.tvDaySelected);
        tvDateFill = findViewById(R.id.tvDateFill);
        tvPriceFill = findViewById(R.id.tvPriceFill);
        rlPaymentActivity = findViewById(R.id.rlPaymentActivity);


        llMaiPayment = findViewById(R.id.llMaiPayment);
        llNoData = findViewById(R.id.llNoData);
        llMaiPayment.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);


        decimalFormat = new DecimalFormat("#,###");

        String [] lstDay = getResources().getStringArray(R.array.choose_day);
        spSelectDay = findViewById(R.id.spSelectDay);

        selectDaySpinner = new SelectDaySpinner(getApplicationContext(), R.layout.item_selected_spinner_date, lstDay);
        spSelectDay.setAdapter(selectDaySpinner);

        paymentPresenter = new PaymentPresenter(getApplicationContext());
        rcvLstPayment = findViewById(R.id.rcvLstPayment);
        ivBack = findViewById(R.id.ivBack);
        paymentAdapter = new PaymentAdapter(getApplicationContext(), this);
        rcvLstPayment.setAdapter(paymentAdapter);
        rcvLstPayment.setHasFixedSize(true);
        rcvLstPayment.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    /**
     * Hàm khởi tạo sự kiện
     * @author giangpb
     * @date 12/02/2021
     */
    private void addEvents(){
        // sự kiện quay lại
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

        spSelectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posOfItemSpinnerSelected=position;
                Instant now = Instant.now();
                // format for querry
                DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withZone(ZoneId.systemDefault());

                DateTimeFormatter DATE_TIME_FORMATTER_SHOW = DateTimeFormatter.ofPattern("EEEE - dd/MM/yyyy")
                        .withZone(ZoneId.systemDefault());

                DateTimeFormatter DATE_TIME_FORMATTER_SHOW_FILL = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        .withZone(ZoneId.systemDefault());
                ArrayList<Payment> data = null;

                if(position ==0){
                    data = paymentPresenter.getAllPayment(DATE_TIME_FORMATTER.format(now)+"%");
                    tvDaySelected.setText(DATE_TIME_FORMATTER_SHOW.format(now));
                    tvDateFill.setText(DATE_TIME_FORMATTER_SHOW_FILL.format(now));
                    tvPriceFill.setText(decimalFormat.format(paymentPresenter.totalPrice(data)));
                }
                else if(position ==1){ // minus 1 day
                    Instant yesterday = now.minus(1, ChronoUnit.DAYS);
                    data = paymentPresenter.getAllPayment(DATE_TIME_FORMATTER.format(yesterday)+"%");
                    tvDaySelected.setText(DATE_TIME_FORMATTER_SHOW.format(yesterday));
                    tvDateFill.setText(DATE_TIME_FORMATTER_SHOW_FILL.format(yesterday));
                    tvPriceFill.setText(decimalFormat.format(paymentPresenter.totalPrice(data)));
                }
                else if(position ==2){ // minus 7 days
                    Instant day7Ago = now.minus(7, ChronoUnit.DAYS);
                    data = paymentPresenter.getAllPayment(DATE_TIME_FORMATTER.format(day7Ago)+"%",DATE_TIME_FORMATTER.format(now)+"%");
                    tvDaySelected.setText(DATE_TIME_FORMATTER_SHOW.format(now));
                    tvDateFill.setText(DATE_TIME_FORMATTER_SHOW_FILL.format(day7Ago)+" - "+DATE_TIME_FORMATTER_SHOW_FILL.format(now));
                    tvPriceFill.setText(decimalFormat.format(paymentPresenter.totalPrice(data)));
                }

                // xử lý show
                if(data !=null){
                    llMaiPayment.setVisibility(View.VISIBLE);
                    llNoData.setVisibility(View.GONE);
                    rlPaymentActivity.setBackgroundColor(getResources().getColor(R.color.divider));
                }
                else{
                    llMaiPayment.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    rlPaymentActivity.setBackgroundColor(getResources().getColor(R.color.white));
                    tvDaySelected.setText(DATE_TIME_FORMATTER_SHOW.format(now));
                }
                paymentAdapter.addData(data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onPaymentClick(Payment payment) {
        Intent intent = new Intent(PaymentActivity.this, PaymentDetailActivity.class);
        intent.putExtra("payment", payment);
        startActivity(intent);
    }
}