package vn.com.misa.starter2.ui.collectmoney;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.muddzdev.styleabletoast.StyleableToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.MoneyRequirementAdapter;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.model.entity.PaymentDetail;
import vn.com.misa.starter2.ui.listorder.OrderPresenter;
import vn.com.misa.starter2.ui.order.AutoIDPresenter;
import vn.com.misa.starter2.ui.order_add.AddOrderFragment;

/**
 * - frangment thu tiền
 * @author GIANG PHAN
 * @date 07/02/2021
 */
public class CollectMoneyFragment extends Fragment implements IMoneyClickListener, View.OnClickListener {
    private static final String TAG = "CollectMoneyFragment";


    private View mView;
    // autoID
    AutoIDPresenter autoIDPresenter;

    // presenter
    private OrderPresenter orderPresenter;
    private PaymentPresenter paymentPresenter;
    private PaymentDetailPresenter paymentDetailPresenter;


    // order
    private Order mOrder;

    private DecimalFormat decimalFormat;

    private NavController navController;


    // khai báo các điều khiển
    private ImageView ivBack;
    private MultiStateToggleButton toggleButton ;

    private TextView tvTongTien;
    private TextView tvKhachDua;
    private TextView tvTraLai;

    private int moneyReceive = 0;

    private MaterialButton btnHoanThanh;

    private int []dataMoney;

    private RecyclerView rcvLstMoneyRequirement;
    private MoneyRequirementAdapter moneyRequirementAdapter;
    private ArrayList<Integer> lstMoney;

    // hiển thị bàn phím ảo lúc nhập tiền
    private RelativeLayout rlKhachDua;
    private TextView tvKeyXong;
    private LinearLayout llKeyboard;

    // tiền
    private StringBuilder tienNhap;

    private TextView tvKey1,tvKey2, tvKey3;
    private TextView tvKey4,tvKey5, tvKey6;
    private TextView tvKey7,tvKey8, tvKey9;
    private TextView tvKey0;
    private ImageView ivKeyDel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StringBuilder stringBuilder) {
        if (stringBuilder.length()>0){
            int tien = Integer.parseInt(stringBuilder.toString());
            moneyReceive = tien;
            if (tien<=mOrder.getAmount()){
                tvTraLai.setText("0");
            }
            else {
                tvTraLai.setText(decimalFormat.format(tien- mOrder.getAmount()));
            }
            tvKhachDua.setText(decimalFormat.format(tien));
        }
        else {
            tvKhachDua.setText("0");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collect_money, container, false);

        autoIDPresenter = new AutoIDPresenter(getContext());

        Bundle bundle = getArguments();
        mOrder = (Order)  bundle.getSerializable("order");

        moneyReceive = mOrder.getAmount();
        // khởi tạo điều khiển
        orderPresenter = new OrderPresenter(getContext());
        paymentPresenter = new PaymentPresenter(getContext());
        paymentDetailPresenter = new PaymentDetailPresenter(getContext());

        // nạp danh sách tiền từ file string xml
        dataMoney = getResources().getIntArray(R.array.tien_vnd_arr);
        lstMoney = new ArrayList<>();

        decimalFormat = new DecimalFormat("#,###");
        ivBack = mView.findViewById(R.id.ivBack);
        tvTongTien = mView.findViewById(R.id.tvTongTien);
        tvKhachDua = mView.findViewById(R.id.tvKhachDua);
        tvTraLai = mView.findViewById(R.id.tvTraLai);
        btnHoanThanh = mView.findViewById(R.id.btnHoanThanh);

        // keyboard
        initKeyBoard();

        toggleButton = mView.findViewById(R.id.mstb_multi_id);
        toggleButton.setValue(0);

        // khởi tạo lst
        rcvLstMoneyRequirement = mView.findViewById(R.id.rcvLstMoneyRequirement);
        // nạp danh sách gợi ý tiền
        for(int i=0; i<dataMoney.length; i++){
            if(dataMoney[i]>=mOrder.getAmount()){
                lstMoney.add(dataMoney[i]);
            }
        }
        moneyRequirementAdapter = new MoneyRequirementAdapter(getContext(),lstMoney, this);
        rcvLstMoneyRequirement.setAdapter(moneyRequirementAdapter);
        rcvLstMoneyRequirement.setHasFixedSize(true);
        rcvLstMoneyRequirement.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // khởi tạo sự kiện

        Log.d(TAG, "onCreateView: "+AddOrderFragment.lstItemSelected.toString());

        // gán thông tin
        tvTongTien.setText(decimalFormat.format(mOrder.getAmount()));
        tvKhachDua.setText(decimalFormat.format(mOrder.getAmount()));
        tvTraLai.setText(0+"");

        addEvents();

        return mView;
    }

    /**
     * Hàm thiết lập bàn phím thu tiền
     * @author giangpb
     * @date 8/3/2021
     */
    private void initKeyBoard(){
        tienNhap = new StringBuilder();

        llKeyboard = mView.findViewById(R.id.llKeyBoard);
        rlKhachDua = mView.findViewById(R.id.rlKhachDua);
        tvKeyXong = mView.findViewById(R.id.tvKey_xong);

        tvKey0 = mView.findViewById(R.id.key0);
        tvKey1 = mView.findViewById(R.id.key1);
        tvKey2 = mView.findViewById(R.id.key2);
        tvKey3 = mView.findViewById(R.id.key3);
        tvKey4 = mView.findViewById(R.id.key4);
        tvKey5 = mView.findViewById(R.id.key5);
        tvKey6 = mView.findViewById(R.id.key6);
        tvKey7 = mView.findViewById(R.id.key7);
        tvKey8 = mView.findViewById(R.id.key8);
        tvKey9 = mView.findViewById(R.id.key9);
        ivKeyDel = mView.findViewById(R.id.keyDel);

    }

    /**
     * Hàm khởi tạo các sự kiện onlcick
     * @author giangpb
     * @date 07/02/2021
     */
    private void addEvents(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", mOrder);
                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.addOrderFragment,false).build();
                    navController.navigate(R.id.action_collectMoneyFragment_to_addOrderFragment, bundle, navOptions);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // xử lý sự kiện lúc bấm vào khách đưa - > show bàn phím ảo
        rlKhachDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    llKeyboard.setVisibility(View.VISIBLE);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // ẩn keyboard
        tvKeyXong.setOnClickListener(this::onClick);
        tvKey0.setOnClickListener(this::onClick);
        tvKey1.setOnClickListener(this::onClick);
        tvKey2.setOnClickListener(this::onClick);
        tvKey3.setOnClickListener(this::onClick);
        tvKey4.setOnClickListener(this::onClick);
        tvKey5.setOnClickListener(this::onClick);
        tvKey6.setOnClickListener(this::onClick);
        tvKey7.setOnClickListener(this::onClick);
        tvKey8.setOnClickListener(this::onClick);
        tvKey9.setOnClickListener(this::onClick);
        ivKeyDel.setOnClickListener(this::onClick);

        // xử lý sự kiện hoàn thành
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // thêm dialog progress
                    if (moneyReceive>= mOrder.getAmount()){
                        orderPresenter.paymentDone(mOrder.getOrderID());
                        autoIDPresenter.addAutoID(mOrder.getOrderID());
                        int type = autoIDPresenter.getIDAuto(mOrder.getOrderID());
                        String refNo = String.format("%07d", type);

                        long timeMillis = System.currentTimeMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String currentDateAndTime = sdf.format(new Date());

                        Payment payment = new Payment();
                        payment.setRefID("ref"+timeMillis);
                        //
                        payment.setRefType(550);
                        payment.setRefNO(refNo);
                        payment.setRefDate(currentDateAndTime);
                        payment.setAmount(mOrder.getAmount());

                        // updating...
                        // khuyến mãi theo từng sản phẩm
                        payment.setPromotionItemsAmount(0);
                        // sau khi khuyến mãi ...
                        payment.setTotalAmount(mOrder.getAmount());
                        payment.setPromotionRate(0);
                        payment.setPromotionAmount(0);
                        payment.setDiscountAmount(0);
                        payment.setPreTaxAmount(mOrder.getAmount());

                        payment.setTotalAmount(mOrder.getAmount());
                        payment.setReceiveAmount(moneyReceive);
                        payment.setReturnAmount(moneyReceive - mOrder.getAmount());
                        payment.setPaymentStatus(3);
                        payment.setOrderID(mOrder.getOrderID());
                        payment.setOrderType(1);
                        payment.setTableName("null");
                        payment.setCreatedDate(currentDateAndTime);
                        paymentPresenter.addPayment(payment);

                        // add payment detail
                        for(int i=0; i<AddOrderFragment.lstItemSelected.size(); i++){
                            Item item = AddOrderFragment.lstItemSelected.get(i);

                            long time = System.currentTimeMillis();
                            PaymentDetail paymentDetail = new PaymentDetail();
                            paymentDetail.setPaymentDetailID("de"+time);
                            paymentDetail.setPaymentID(payment.getRefID());
                            //
//                        paymentDetail.setParentID("");
//
                            paymentDetail.setItemID(item.getItemID());
                            paymentDetail.setItemName(item.getItemName());
//                        //
                            paymentDetail.setRefDetailType(1);
//
                            paymentDetail.setUnitID(item.getUnitID());
                            paymentDetail.setUnitPrice(item.getPrice());
                            paymentDetail.setQuantity(item.getQuantity());
//                        //
                            paymentDetail.setPromotionRate(0);
                            paymentDetail.setPromotionAmount(0);
                            paymentDetail.setDiscountAmount(0);
                            paymentDetail.setAmount(item.getPrice()*item.getQuantity());
//
                            paymentDetail.setSortOrder(i);

                            String currentDateAndTime2 = sdf.format(new Date());
                            paymentDetail.setCreatedDate(currentDateAndTime2);
                            paymentDetailPresenter.addPaymentDetail(paymentDetail);
                        }
                        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.listOrderFragment,false).build();
                        navController.navigate(R.id.action_collectMoneyFragment_to_listOrderFragment,null, navOptions);

                        // show thông báo
                        StyleableToast.makeText(getActivity(), "Thu tiền thành công", Toast.LENGTH_LONG, R.style.mytoast).show();
                    }
                    else{
                        StyleableToast.makeText(getActivity(), "Số tiền không hợp lệ", Toast.LENGTH_LONG, R.style.mytoast).show();
                        return;
                    }

                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onListMoneyClick(int money) {
        moneyReceive = money;
        tvKhachDua.setText(decimalFormat.format(money));
        int moneyChange = money - mOrder.getAmount();
        tvTraLai.setText(decimalFormat.format(moneyChange));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvKey_xong:
                try{
                    llKeyboard.setVisibility(View.GONE);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            break;
            case R.id.key0:
                tienNhap.append("0");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key1:
                tienNhap.append("1");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key2:
                tienNhap.append("2");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key3:
                tienNhap.append("3");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key4:
                tienNhap.append("4");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key5:
                tienNhap.append("5");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key6:
                tienNhap.append("6");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key7:
                tienNhap.append("7");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key8:
                tienNhap.append("8");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.key9:
                tienNhap.append("9");
                EventBus.getDefault().post(tienNhap);
                break;
            case R.id.keyDel:
                if(tienNhap.length()>0){
                    tienNhap.deleteCharAt(tienNhap.length()-1);
                    EventBus.getDefault().post(tienNhap);
                }
                else{
                    tvKhachDua.setText("0");
                    return;
                }
                break;
        }
    }
}