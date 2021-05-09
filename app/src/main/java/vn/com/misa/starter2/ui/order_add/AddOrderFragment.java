package vn.com.misa.starter2.ui.order_add;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import vn.com.misa.starter2.adapter.AdditionAdapter;
import vn.com.misa.starter2.adapter.CategoryAdapter;
import vn.com.misa.starter2.adapter.OrderBottomSheetAdapter;
import vn.com.misa.starter2.adapter.SwipeItemAdapter;
import vn.com.misa.starter2.model.dto.AdditionCategory;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.model.entity.OrderDetail;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.listorder.OrderPresenter;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodPresenter;
import vn.com.misa.starter2.util.App;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGConstants;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * Lớp chọn món (add order)
 * @author GIANG PHAN
 * @date 28/01/2021
 */
public class AddOrderFragment extends Fragment implements ICategoryListener, IFoodListener, View.OnClickListener {
    private static final String TAG = "AddOrderFragment";

    private CategoryPresenter categoryPresenter;
    private ItemFoodPresenter itemFoodPresenter;

    // format tiền
    private DecimalFormat decimalFormat;

    private EditText etValue;

    private boolean isNewOrder = true;

    // chi tiết hoá đơn
    private OrderDetailPresenter mOrderDetailPresenter;
    private ArrayList<OrderDetail> mOrderDetails;

    // danh sách chọn sản phẩm
    public static ArrayList<Item> lstItemSelected=null;

    // setup bottom sheet
    private MaterialCardView cadOrder;
    private RelativeLayout bottom_sheet_layout;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView ivCloseBottomSheet;

    // khai báo các điều khiển
    private ImageView ivBack;
    // danh sách danh mục
    private RecyclerView rcvListCategory;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> lstCategory;

    // danh sách sản phẩm theo danh mục
    private RecyclerView rcvListItemOfCategory;
    private SwipeItemAdapter swipeItemAdapter;
    // danh sách hiển thị tất cả sản phẩm (sp ban đầu)
    private ArrayList<Item> lstItem;


    private int priceItemSelected=0;
    private TextView tvGiaPhaiThu;
    private TextView tvGiaPhaiThuLst;

    // danh sách danh mục bổ sung
    private AdditionPresenter additionPresenter;
    private ArrayList<AdditionCategory> mAdditionCategories;


    private TextView tvItemCount;
    private TextView tvItemCountLst;
    private TextView tvTableName;
    //
    private int itemQuantitySelected;

    // lấy vị trí chọn cho danh mục
    public static int categoryPositionSelected = -1;

    // order
    private OrderPresenter orderPresenter;
    boolean checkAddFirst=false;
    private Order mOrder =null;

    // check payment
    private boolean checkPayment= false;

    // điều khiển
    MaterialButton btnLuuLai;
    MaterialButton btnThuTien;

    private NavController navController;
    private OrderDetailPresenter orderDetailPresenter;

    // lst bottomSheet
    private RecyclerView rcvOrderBottomSheet;
    private OrderBottomSheetAdapter orderBottomSheetAdapter;

    // Chọn bàn
    private FloatingActionButton fabSelectTable;
    private FloatingActionButton fabSelectDiscount;

    private RelativeLayout rlViewPromotion;
    private LinearLayout lnViewPromotionSheet;

    private TextInputLayout textField;
    private RelativeLayout rlToolbar;
    private TextInputEditText etFind;

    private boolean isPromotionRate =  true;

    private TextView tvPromotion;

    // promotion bottomsheet
    private TextView tvGiaPhaiThuLst2;// Tổng tiền
    private TextView tvGiaPhaiThuLst1; // khuyến mại

    private ImageView ivSearch;
    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);
        // danh mục ban đầu bằng 0
        // subscriber event bbus
        categoryPositionSelected = 0;
        //
        categoryPresenter = new CategoryPresenter(getContext());
        itemFoodPresenter = new ItemFoodPresenter(getActivity());

        // nạp danh sách item
        lstItem = new ArrayList<>();
        lstItem = itemFoodPresenter.getAllItem();

        // order
        orderPresenter = new OrderPresenter(getActivity());

        // khởi tạo điều khiển
        ivBack = view.findViewById(R.id.ivBack);
        cadOrder = view.findViewById(R.id.cadOrder);
        bottom_sheet_layout = view.findViewById(R.id.bottom_sheet_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout);
        ivCloseBottomSheet = view.findViewById(R.id.ivCloseBottomSheet);
        decimalFormat = new DecimalFormat("#,###");
        tvGiaPhaiThu  =view.findViewById(R.id.tvGiaPhaiThu);
        tvItemCount = view.findViewById(R.id.tvItemQuantity);
        btnLuuLai =view.findViewById(R.id.btnLuuLai);
        btnThuTien = view.findViewById(R.id.btnThuTien);
        //


        fabSelectTable = view.findViewById(R.id.fabSelectTable);
        fabSelectDiscount = view.findViewById(R.id.fabSelectDiscount);
        tvTableName = view.findViewById(R.id.tvTableName);
        tvTableName.setVisibility(View.GONE);

        // lst bottom sheet
        tvItemCountLst = view.findViewById(R.id.tvItemCountLst);
        tvGiaPhaiThuLst = view.findViewById(R.id.tvGiaPhaiThuLst); // còn phải thu
        tvGiaPhaiThuLst2 = view.findViewById(R.id.tvGiaPhaiThuLst2); // Tổng tiền
        tvGiaPhaiThuLst1 = view.findViewById(R.id.tvGiaPhaiThuLst1); // khuyến mại

        rlViewPromotion= view.findViewById(R.id.rlViewPromotion);
        rlViewPromotion.setVisibility(View.GONE);
        lnViewPromotionSheet = view.findViewById(R.id.lnViewPromotionSheet);
        lnViewPromotionSheet.setVisibility(View.GONE);

        ivSearch = view.findViewById(R.id.ivSearch);

        textField =view.findViewById(R.id.textField);
        rlToolbar =view.findViewById(R.id.rlToolbar);
        etFind =view.findViewById(R.id.etFind);

        tvPromotion =view.findViewById(R.id.tvPromotion);

        textField.setVisibility(View.GONE);
        rlToolbar.setVisibility(View.VISIBLE);

        rcvOrderBottomSheet = view.findViewById(R.id.rcvOrderBottomSheet);
        orderBottomSheetAdapter = new OrderBottomSheetAdapter(getActivity(), this);
        rcvOrderBottomSheet.setHasFixedSize(true);
        rcvOrderBottomSheet.setAdapter(orderBottomSheetAdapter);
        rcvOrderBottomSheet.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        // khởi tạo danh sách danh mục
        rcvListCategory = view.findViewById(R.id.rcvListCategory);
        categoryAdapter = new CategoryAdapter(getActivity(),categoryPresenter.getListCategory(),this::onClickItem);
        rcvListCategory.setHasFixedSize(true);
        rcvListCategory.setAdapter(categoryAdapter);
        rcvListCategory.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        //
        additionPresenter = new AdditionPresenter(getActivity());
        orderDetailPresenter  = new OrderDetailPresenter(getActivity());

        try{
            // khởi tạo
            mOrder = new Order();
            mOrder.setTotalAmount(0);

            // lấy bundle order
            Bundle bundle = getArguments();
            isNewOrder = true;
            EventBus.getDefault().register(this);
            if(bundle !=null){
                isNewOrder = false;
                mOrder = (Order) bundle.getSerializable("order");
                if (mOrder.getPromotionRate()==0){
                    if (mOrder.getPromotionAmount()>0){
                        isPromotionRate = false;
                        EventBus.getDefault().post(new OrderPromotion(mOrder.getPromotionAmount()));
                    }
                }
                else{
                    isPromotionRate = true;
                    EventBus.getDefault().post(new OrderPromotion(mOrder.getPromotionRate()));
                }
                GIANGUtils.getInstance().checkShowHideView(mOrder.getTableName(),"",tvTableName);
                tvTableName.setText(mOrder.getTableName());
                checkPayment = bundle.getBoolean("check");
                // gán cho danh sách selected item
                lstItemSelected = itemFoodPresenter.getItemInOrderDetail(mOrder.getOrderID());

                // cập nhật sản số lượng danh mục theo sản phẩm chọn
                updateCountCategory();


                // gán cho danh sách order bottomsheet
                orderBottomSheetAdapter.addData(lstItemSelected);

                checkAddFirst=true;
                // kiểm tra đang xem order hay tạo mới

                mOrderDetailPresenter = new OrderDetailPresenter(getActivity());
                mOrderDetails = mOrderDetailPresenter.getOrderDetail(mOrder.getOrderID());
//            Log.d(TAG, "onCreateView: "+mOrderDetails.toString());

                for(int i=0; i<lstItem.size(); i++){
                    Item item = lstItem.get(i);
                    for(OrderDetail orderDetail :mOrderDetails){
                        if (orderDetail.getItemID().equalsIgnoreCase(item.getItemID())){ // fix sau
                            item.setQuantity(orderDetail.getQuantity());
                            lstItem.set(i,item);
                        }
                    }
                }

            tvGiaPhaiThu.setText(decimalFormat.format(mOrder.getTotalAmount()));
            tvGiaPhaiThuLst.setText(decimalFormat.format(mOrder.getTotalAmount()));

                if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
                    tvItemCount.setVisibility(View.VISIBLE);
                    tvItemCount.setText(String.format("%d",itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)));
                }
                tvItemCountLst.setText(String.format("%d",itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)));

            }
            else{
                // khởi tạo danh sách mới chứa item order mới
                lstItemSelected = new ArrayList<>();
                tvTableName.setVisibility(View.GONE);
            }
//        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));

            //Log.d(TAG, "onCreateView: "+lstItemSelected.toString());
            // khởi tạo danh sách sản phẩm
            rcvListItemOfCategory = view.findViewById(R.id.rcvListItemOfCategory);
            swipeItemAdapter = new SwipeItemAdapter(getActivity(), this);

            // danh sách bổ sung danh mục
            mAdditionCategories = additionPresenter.additionCategories(categoryPresenter.getListCategory().get(0).getCategoryID());

            // danh sách sản phẩm theo danh mục
            swipeItemAdapter.clearAllItem();
            for(Item item : lstItem){
                if(item.getCategoryID().equals(categoryPresenter.getListCategory().get(0).getCategoryID())){
                    swipeItemAdapter.addItem(item);
                }
            }

            rcvListItemOfCategory.setHasFixedSize(true);
            rcvListItemOfCategory.setAdapter(swipeItemAdapter);
            rcvListItemOfCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        }
        catch (Exception exx){
            GIANGUtils.getInstance().handlerLog(exx.getMessage());
        }
        addEvents();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private boolean checkQuantityInList(ArrayList<Item> lstItemSelected){
        for(Item item :lstItemSelected){
            if (item.getQuantity()>0){
                return true;
            }
        }
        return false;
    }

    /**
     * Hàm cập nhật số lượng danh mục theo sản phẩm chọn
     * @author giangpb
     * @date 16/02/2021
     */
    private void updateCountCategory(){
        if (lstItemSelected.size()>0){
            for(int i=0; i<categoryPresenter.getListCategory().size(); i++){
                Category cat = categoryPresenter.getListCategory().get(i);
                int dem = categoryPresenter.getListCategory().get(i).getCount();
                for(Item item : lstItemSelected){
                    if(item.getCategoryID().equals(cat.getCategoryID()))
                        dem+=item.getQuantity();
                    cat.setCount(dem);
                    categoryAdapter.updateCategory(cat,i);
                }
            }
        }
        else{
            categoryAdapter.resetCategory();
        }
    }

    //check payment
    @Override
    public void onStart() {
        super.onStart();
        if(checkPayment){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Hàm nhập dữ liệu thông tin khuyễn mãi
     * @param stringBuilder
     * @param value
     */
    private void addValuePromotionDialog(StringBuilder stringBuilder, String value){
        if (isPromotionRate){
            if (stringBuilder.length()<2)
                stringBuilder.append(value);
        }
        else
        {
            if (stringBuilder.length()<8)
                stringBuilder.append(value);
        }
    }

    private int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * hàm tạo các sự kiện onclick
     * @author giangpb
     * @date 28/01/2021
     */
    private void addEvents(){

        ivSearch.setOnClickListener(v->{
            textField.setVisibility(View.VISIBLE);
            rlToolbar.setVisibility(View.GONE);
            etFind.requestFocus();
            // mở bàn phím
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etFind, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(etFind, InputMethodManager.SHOW_FORCED);
            etFind.setText("");
        });

        textField.setStartIconOnClickListener(v->{
            rlToolbar.setVisibility(View.VISIBLE);
            textField.setVisibility(View.GONE);
            // đóng bàn phím
            GIANGUtils.getInstance().hideKeyBoard(etFind, getActivity());
        });

        // hiển thị khuyến mãi
        fabSelectDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    View alertLayout = getLayoutInflater().inflate(R.layout.dialog_choose_promotion, null);

                    LinearLayout llRecommend = alertLayout.findViewById(R.id.llRecommend);

                    LinearLayout llClosePopup = alertLayout.findViewById(R.id.llClosePopup);

                    StringBuilder stringBuilder= new StringBuilder();
                    EditText etValue = alertLayout.findViewById(R.id.etValue);

                    TextView tvKey0 = alertLayout.findViewById(R.id.key0);
                    TextView tvKey1 = alertLayout.findViewById(R.id.key1);
                    TextView tvKey2 = alertLayout.findViewById(R.id.key2);
                    TextView tvKey3 = alertLayout.findViewById(R.id.key3);
                    TextView tvKey4 = alertLayout.findViewById(R.id.key4);
                    TextView tvKey5 = alertLayout.findViewById(R.id.key5);
                    TextView tvKey6 = alertLayout.findViewById(R.id.key6);
                    TextView tvKey7 = alertLayout.findViewById(R.id.key7);
                    TextView tvKey8 = alertLayout.findViewById(R.id.key8);
                    TextView tvKey9 = alertLayout.findViewById(R.id.key9);

                    TextView keyDellAll = alertLayout.findViewById(R.id.keyDellAll);
                    TextView keyAccept = alertLayout.findViewById(R.id.keyAccept);
                    ImageButton keyDell = alertLayout.findViewById(R.id.keyDell);

                    if (mOrder.getPromotionAmount()!=0){
                        stringBuilder.append(String.valueOf(mOrder.getPromotionAmount()));
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    }

                    // events
                    tvKey0.setOnClickListener((view)->{
                        if (stringBuilder.length()>0)
                            addValuePromotionDialog(stringBuilder,"0");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(tryParseInt(stringBuilder.toString(),0)));
                    });
                    tvKey1.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"1");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey2.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"2");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey3.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"3");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey4.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"4");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey5.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"5");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey6.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"6");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey7.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"7");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey8.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"8");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });
                    tvKey9.setOnClickListener((view)->{
                        addValuePromotionDialog(stringBuilder,"9");
                        etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(Integer.parseInt(stringBuilder.toString())));
                    });

                    keyDell.setOnClickListener(v1 -> {
                        try{
                            etValue.setText(GIANGUtils.getInstance().convertPriceIntToString(tryParseInt(stringBuilder.length()>0?stringBuilder.deleteCharAt(stringBuilder.length()-1).toString():"0",0)));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }

                    });
                    keyDellAll.setOnClickListener(view->{
                        if (stringBuilder.length()>0){
                            stringBuilder.delete(0,stringBuilder.length());
                        }
                        etValue.setText("0");
                    });

                    RadioGroup rg = alertLayout.findViewById(R.id.rdSlected);
                    RadioButton rdPhanTram = alertLayout.findViewById(R.id.rdPhanTram);
                    RadioButton rdSoTien = alertLayout.findViewById(R.id.rdSoTien);
                    isPromotionRate = GIANGCache.getInstance().get(GIANGConstants.CACHE_PROMOTION,Boolean.class);
                    if (isPromotionRate){
                        rdPhanTram.setChecked(true);
                        rdSoTien.setChecked(false);
                        llRecommend.setVisibility(View.VISIBLE);
                    }
                    else{
                        rdPhanTram.setChecked(false);
                        rdSoTien.setChecked(true);
                        llRecommend.setVisibility(View.GONE);
                    }
                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId){
                                case R.id.rdPhanTram:
                                    if (stringBuilder.length()>0){
                                        stringBuilder.delete(0,stringBuilder.length());
                                    }
                                    etValue.setText("0");
                                    isPromotionRate = true;
                                    GIANGCache.getInstance().put(GIANGConstants.CACHE_PROMOTION, true);
                                    llRecommend.setVisibility(View.VISIBLE);
                                    break;
                                case R.id.rdSoTien:
                                    if (stringBuilder.length()>0){
                                        stringBuilder.delete(0,stringBuilder.length());
                                    }
                                    etValue.setText("0");
                                    isPromotionRate = false;
                                    GIANGCache.getInstance().put(GIANGConstants.CACHE_PROMOTION, false);
                                    llRecommend.setVisibility(View.GONE);
                                    break;
                            }
                        }
                    });

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setView(alertLayout);
                    alert.setCancelable(false);
                    AlertDialog dialog = alert.create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    dialog.show();

                    llClosePopup.setOnClickListener(view->{
                        try{
                            dialog.dismiss();
                        }
                        catch (Exception ex){
                            GIANGUtils.getInstance().handlerException(ex);
                        }
                    });

                    keyAccept.setOnClickListener(view->{
                        if (stringBuilder.length()<=0)
                            stringBuilder.append("0");
                        EventBus.getDefault().post(new OrderPromotion(Integer.parseInt(stringBuilder.toString())));
                        dialog.dismiss();
                    });
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // hển thị chọn số bàn phục vụ
        fabSelectTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    View alertLayout = getLayoutInflater().inflate(R.layout.dialog_choose_table, null);

                    // init controls
                    LinearLayout llClosePopup = alertLayout.findViewById(R.id.llClosePopup);
                    etValue = alertLayout.findViewById(R.id.etValue);
                    etValue.setFocusable(false);
                    ImageButton btnClearValue = alertLayout.findViewById(R.id.btnClearValue);

                    // key_table
                    TextView tvKey0 = alertLayout.findViewById(R.id.table_key0);
                    TextView tvKey1 = alertLayout.findViewById(R.id.table_key1);
                    TextView tvKey2 = alertLayout.findViewById(R.id.table_key2);
                    TextView tvKey3 = alertLayout.findViewById(R.id.table_key3);
                    TextView tvKey4 = alertLayout.findViewById(R.id.table_key4);
                    TextView tvKey5 = alertLayout.findViewById(R.id.table_key5);
                    TextView tvKey6 = alertLayout.findViewById(R.id.table_key6);
                    TextView tvKey7 = alertLayout.findViewById(R.id.table_key7);
                    TextView tvKey8 = alertLayout.findViewById(R.id.table_key8);
                    TextView tvKey9 = alertLayout.findViewById(R.id.table_key9);
                    TextView tvKeyClearAll = alertLayout.findViewById(R.id.table_keyClearAll);
                    TextView tvKeyAccept = alertLayout.findViewById(R.id.table_keyAccept);
                    TextView key_plus = alertLayout.findViewById(R.id.key_plus);
                    TextView key_minus = alertLayout.findViewById(R.id.key_minus);

                    StringBuilder stringBuilder=  new StringBuilder();
                    String tableName = tvTableName.getText().toString();
                    if (!tableName.equals("0")){
                        etValue.setText(tableName);
                        stringBuilder.append(tableName);
                    }
                    // process events


                    tvKey0.setOnClickListener((view)->{
                        if (stringBuilder.length()>0) {
                            if (checkTableCount(stringBuilder)){
                                stringBuilder.append("0");
                                EventBus.getDefault().post(stringBuilder);
                            }
                        }
                    });

                    tvKey1.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("1");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey2.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("2");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey3.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("3");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey4.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("4");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey5.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("5");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey6.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("6");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey7.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("7");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey8.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("8");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKey9.setOnClickListener(view->{
                        if (checkTableCount(stringBuilder)){
                            stringBuilder.append("9");
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    key_plus.setOnClickListener(view->{
                        int num = Integer.parseInt(etValue.getText().toString());
                        num ++;
                        if (num<100){
                            stringBuilder.delete(0,stringBuilder.length());
                            stringBuilder.append(num);
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    key_minus.setOnClickListener(view->{
                        int num = Integer.parseInt(etValue.getText().toString());
                        num --;
                        if (num>0){
                            stringBuilder.delete(0,stringBuilder.length());
                            stringBuilder.append(num);
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    btnClearValue.setOnClickListener(view->{
                        if (stringBuilder.length()>0){
                            stringBuilder.deleteCharAt(stringBuilder.length()-1);
                            EventBus.getDefault().post(stringBuilder);
                        }
                    });

                    tvKeyClearAll.setOnClickListener(view->{
                        try{
                            stringBuilder.delete(0, stringBuilder.length());
                            EventBus.getDefault().post(stringBuilder);
                        }
                        catch (Exception exx){
                            GIANGUtils.getInstance().handlerException(exx);
                        }
                    });

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setView(alertLayout);
                    alert.setCancelable(false);
                    AlertDialog dialog = alert.create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // set Events
                    llClosePopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    tvKeyAccept.setOnClickListener(view->{
                        String table = etValue.getText().toString();
                        if (!table.equals("0")){
                            tvTableName.setVisibility(View.VISIBLE);
                            tvTableName.setText(stringBuilder.toString());
                        }
                        else{
                            tvTableName.setVisibility(View.GONE);
                            tvTableName.setText("0");
                        }
                        dialog.dismiss();
                    });

                    dialog.show();
                }
                catch (Exception ex){
                    Log.d(TAG, "addEvents: "+ex.getMessage());
                }
            }
        });

        // sự kiện quay lại
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.listOrderFragment, false).build();
                    navController.navigate(R.id.action_addOrderFragment_to_listOrderFragment, null, navOptions);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // mở bottomsheet
        cadOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(bottomSheetBehavior.getState()!= BottomSheetBehavior.STATE_EXPANDED){
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                    else{
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }

            }
        });

        // xử lý đóng bottom sheet
        ivCloseBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // xử lý lưu lại
        btnLuuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(lstItemSelected.size()>0 ){
                        // kiểm tra order đã tồn tại trước đó hay chưa, nếu chưa thì thêm mới, có rồi thì cập nhật lại
                        if(isNewOrder){
                            long timeMillis = System.currentTimeMillis();
                            mOrder.setOrderID(timeMillis+"");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            String currentDateAndTime = sdf.format(new Date());
                            mOrder.setDateCrate(currentDateAndTime);
                            mOrder.setOrderStatus(1);
                            int totalItemAmount = itemFoodPresenter.tinhTienHoaDon(lstItemSelected);
                            mOrder.setAmount(totalItemAmount);
                            mOrder.setItemNames(lstItemSelected.toString());
                            mOrder.setTableName(tvTableName.getText().toString().equals("0")?"":tvTableName.getText().toString());
                            mOrder.setTotalItemAmount(totalItemAmount);

                            // kiểm tra đã chọn khuyến mại trước đó chưa, nếu chọn rồi thì ko thêm nữa
                            if (mOrder.getTotalAmount() ==0)
                                mOrder.setTotalAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));

                            // thêm vào order
                            orderPresenter.addOrder(mOrder);

                            // thêm danh sách vào hoá đơnA
                            for(Item item :lstItemSelected){
                                if(item.getQuantity()>0){
                                    OrderDetail orderDetail = new OrderDetail();
                                    long idOrder = System.currentTimeMillis();
                                    orderDetail.setOrderDetailID("s"+idOrder);
                                    orderDetail.setOrderID(timeMillis+"");
                                    orderDetail.setItemID(item.getItemID());
                                    orderDetail.setItemName(item.getItemName());
                                    orderDetail.setUnitID(item.getUnitID());
                                    orderDetail.setUnitPrice(item.getPrice());
                                    orderDetail.setQuantity(item.getQuantity());
                                    orderDetail.setAmount(item.getQuantity()*item.getPrice());
                                    orderDetail.setDateCreate(currentDateAndTime);
                                    orderDetailPresenter.themOrderDetail(orderDetail);
                                }
                            }
                        }
                        else{
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            mOrder.setOrderDate(currentDateandTime);
                            int totalItemAmount = itemFoodPresenter.tinhTienHoaDon(lstItemSelected);

                            mOrder.setAmount(totalItemAmount);
                            mOrder.setTotalItemAmount(totalItemAmount);
                            mOrder.setItemNames(lstItemSelected.toString());

                            // kiểm tra đã chọn khuyến mại trước đó chưa, nếu chọn rồi thì ko thêm nữa
                            if (mOrder.getTotalAmount() ==0)
                                mOrder.setTotalAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));

                            mOrder.setTableName(tvTableName.getText().toString().equals("0")?"":tvTableName.getText().toString());
                            // cập nhật hoá đơn
                            orderPresenter.updateOrder(mOrder);
                            // cập nhật chi tiết hoá đơn
                            orderDetailPresenter.deleteOrderDetail(mOrder.getOrderID());

                            // thêm danh sách vào hoá đơn
                            for(Item item :lstItemSelected){
                                if(item.getQuantity()>0){
                                    OrderDetail orderDetail = new OrderDetail();
                                    long idOrder = System.currentTimeMillis();
                                    orderDetail.setOrderDetailID("s"+idOrder);
                                    orderDetail.setOrderID(mOrder.getOrderID());
                                    orderDetail.setItemID(item.getItemID());
                                    orderDetail.setItemName(item.getItemName());
                                    orderDetail.setUnitID(item.getUnitID());
                                    orderDetail.setUnitPrice(item.getPrice());
                                    orderDetail.setQuantity(item.getQuantity());
                                    orderDetail.setAmount(item.getQuantity()*item.getPrice());
                                    orderDetail.setDateCreate(currentDateandTime);
                                    orderDetailPresenter.themOrderDetail(orderDetail);
                                }
                            }

                        }

                        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.listOrderFragment, false).build();
                        navController.navigate(R.id.action_addOrderFragment_to_listOrderFragment, null, navOptions);
                    }
                    else{
                        GIANGUtils.getInstance().showMessage(getContext(), "Vui lòng chọn món !!!", 1);
                    }

                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        btnThuTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // kiểm tra xem danh sách order có sản phẩm
                    if(lstItemSelected.size()>0 ){
                        // kiểm tra order đã tồn tại trước đó hay chưa, nếu chưa thì thêm mới, có rồi thì cập nhật lại
                        if(isNewOrder){
                            long timeMillis = System.currentTimeMillis();
                            mOrder.setOrderID(timeMillis+"");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            String currentDateAndTime = sdf.format(new Date());
                            mOrder.setDateCrate(currentDateAndTime);
                            mOrder.setOrderStatus(1);
                            int totalItemAmount = itemFoodPresenter.tinhTienHoaDon(lstItemSelected);
                            mOrder.setAmount(totalItemAmount);
                            mOrder.setItemNames(lstItemSelected.toString());
                            mOrder.setTableName(tvTableName.getText().toString().equals("0")?"":tvTableName.getText().toString());
                            mOrder.setTotalItemAmount(totalItemAmount);

                            // kiểm tra đã chọn khuyến mại trước đó chưa, nếu chọn rồi thì ko thêm nữa
                            if (mOrder.getTotalAmount() ==0)
                                mOrder.setTotalAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));

                            // thêm vào order
                            orderPresenter.addOrder(mOrder);

                            // thêm danh sách vào hoá đơnA
                            for(Item item :lstItemSelected){
                                if(item.getQuantity()>0){
                                    OrderDetail orderDetail = new OrderDetail();
                                    long idOrder = System.currentTimeMillis();
                                    orderDetail.setOrderDetailID("s"+idOrder);
                                    orderDetail.setOrderID(timeMillis+"");
                                    orderDetail.setItemID(item.getItemID());
                                    orderDetail.setItemName(item.getItemName());
                                    orderDetail.setUnitID(item.getUnitID());
                                    orderDetail.setUnitPrice(item.getPrice());
                                    orderDetail.setQuantity(item.getQuantity());
                                    orderDetail.setAmount(item.getQuantity()*item.getPrice());
                                    orderDetail.setDateCreate(currentDateAndTime);
                                    orderDetailPresenter.themOrderDetail(orderDetail);
                                }
                            }
                        }
                        else{
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            mOrder.setOrderDate(currentDateandTime);
                            int totalItemAmount = itemFoodPresenter.tinhTienHoaDon(lstItemSelected);

                            mOrder.setAmount(totalItemAmount);
                            mOrder.setTotalItemAmount(totalItemAmount);
                            mOrder.setItemNames(lstItemSelected.toString());

                            // kiểm tra đã chọn khuyến mại trước đó chưa, nếu chọn rồi thì ko thêm nữa
                            if (mOrder.getTotalAmount() ==0)
                                mOrder.setTotalAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));

                            mOrder.setTableName(tvTableName.getText().toString().equals("0")?"":tvTableName.getText().toString());
                            // cập nhật hoá đơn
                            orderPresenter.updateOrder(mOrder);
                            // cập nhật chi tiết hoá đơn
                            orderDetailPresenter.deleteOrderDetail(mOrder.getOrderID());

                            // thêm danh sách vào hoá đơn
                            for(Item item :lstItemSelected){
                                if(item.getQuantity()>0){
                                    OrderDetail orderDetail = new OrderDetail();
                                    long idOrder = System.currentTimeMillis();
                                    orderDetail.setOrderDetailID("s"+idOrder);
                                    orderDetail.setOrderID(mOrder.getOrderID());
                                    orderDetail.setItemID(item.getItemID());
                                    orderDetail.setItemName(item.getItemName());
                                    orderDetail.setUnitID(item.getUnitID());
                                    orderDetail.setUnitPrice(item.getPrice());
                                    orderDetail.setQuantity(item.getQuantity());
                                    orderDetail.setAmount(item.getQuantity()*item.getPrice());
                                    orderDetail.setDateCreate(currentDateandTime);
                                    orderDetailPresenter.themOrderDetail(orderDetail);
                                }
                            }

                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", mOrder);
                        navController.navigate(R.id.action_addOrderFragment_to_collectMoneyFragment,bundle, null);
                    }
                    else{
                        GIANGUtils.getInstance().showMessage(getContext(), "Vui lòng chọn món !!!", 1);
                    }
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    private boolean checkTableCount(StringBuilder stringBuilder){
        return stringBuilder.length()<2;
    }

    @SuppressLint("DefaultLocale")
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void getPromotion(OrderPromotion orderPromotion){
        if (orderPromotion.getPromotion() ==0){
            lnViewPromotionSheet.setVisibility(View.GONE);
            rlViewPromotion.setVisibility(View.GONE);
        }
        else{
            // kiểm tra có lớn hơn <= số tiền của order ko
            if (!isPromotionRate){ // khuyến mại tiền
                int totalItemAmount = itemFoodPresenter.tinhTienHoaDon(lstItemSelected);
                mOrder.setTotalItemAmount(totalItemAmount);
                if (totalItemAmount>=orderPromotion.getPromotion()){
                    // tiền khuyễn mãi
                    mOrder.setPromotionRate(0);
                    mOrder.setPromotionAmount(orderPromotion.getPromotion());
                    int totalAmount = totalItemAmount - orderPromotion.getPromotion();
                    mOrder.setTotalAmount(totalAmount);

                    lnViewPromotionSheet.setVisibility(View.VISIBLE);
                    rlViewPromotion.setVisibility(View.VISIBLE);
                    tvPromotion.setText(String.format("-%s",GIANGUtils.getInstance().convertPriceIntToString(orderPromotion.getPromotion())));
                    tvGiaPhaiThu.setText(decimalFormat.format(totalAmount));

                    // bottomSheet
                    tvGiaPhaiThuLst2.setText(decimalFormat.format(totalItemAmount)); // tổng tiền
                    tvGiaPhaiThuLst.setText(decimalFormat.format(totalAmount));
                    tvGiaPhaiThuLst1.setText(decimalFormat.format(orderPromotion.getPromotion()));
                }
                else{
                    Toast.makeText(getContext(), "Tiền khuyễn mại không được lớn hơn tổng tiền order.", Toast.LENGTH_SHORT).show();
                    lnViewPromotionSheet.setVisibility(View.GONE);
                    rlViewPromotion.setVisibility(View.GONE);
                }
            }
            else{ // khuyến mại phần trăm
                lnViewPromotionSheet.setVisibility(View.VISIBLE);
                rlViewPromotion.setVisibility(View.VISIBLE);
                tvPromotion.setText(String.format("-%s",GIANGUtils.getInstance().convertPriceIntToString(orderPromotion.getPromotion())));
            }

        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            etValue.setText(stringBuilder.toString());
        } else {
            etValue.setText("0");
        }
    }

    @Override
    public void onClickItem(Category category) {
        categoryPositionSelected = category.getSortOrder()-1;
        swipeItemAdapter.clearAllItem();
        for(Item item : lstItem){
            if(item.getCategoryID().equals(category.getCategoryID())){
                swipeItemAdapter.addItem(item);
            }
        }
        // đặt danh mục bổ sung (addition) theo danh mục sản phẩm
        mAdditionCategories =  additionPresenter.additionCategories(category.getCategoryID());
    }

    @Override
    public void onClickItemSwipe(Item item) {

        if (checkAddFirst ==false){
            lstItemSelected.add(item);
            Log.d(TAG, "VÀO ĐÂY");
        }

        boolean check2=false;
        int vt=0;
        for(int i=0; i<lstItemSelected.size(); i++){
            if(item.equals(lstItemSelected.get(i))){
                check2 =true;
                vt=i;
                break;
            }
        }
        if(check2){
            lstItemSelected.set(vt,item);
        }
        else{
            lstItemSelected.add(item);
        }

        checkAddFirst =true;

        itemQuantitySelected=item.getQuantity();
        // tạo view dialog bottomsheet
        View viewDialogBottomSheet = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_item, null);
        // khởi tạo các điều khiển và cài đặt view
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(viewDialogBottomSheet);

        // nếu danh mục có addition thì show dialog bottomsheet lên
        if(mAdditionCategories.size()>0){
            bottomSheetDialog.show();
            // không cho
            bottomSheetDialog.setCancelable(false);

            // khởi tạo các điều khiển trên view bottomsheet
            TextView tvFoodName = viewDialogBottomSheet.findViewById(R.id.tvFoodName);
            TextView tvFoodPrice = viewDialogBottomSheet.findViewById(R.id.tvFoodPrice);
            MaterialButton btnHuyBo = viewDialogBottomSheet.findViewById(R.id.btnHuyBo);
            RecyclerView rcvAddition = viewDialogBottomSheet.findViewById(R.id.rcvListItemAddition);
            TextView tvQuantityBottomSheet = viewDialogBottomSheet.findViewById(R.id.tvQuantityBottomSheet);
            FloatingActionButton fabMinusQuantityBottomSheet = viewDialogBottomSheet.findViewById(R.id.fabMinusQuantityBottomSheet);
            FloatingActionButton fabAddQuantityBottomSheet = viewDialogBottomSheet.findViewById(R.id.fabAddQuantityBottomSheet);
            MaterialButton btnDongY = viewDialogBottomSheet.findViewById(R.id.btnDongY);

            // khởi tạo mul toggle button
            MultiStateToggleButton buttonToggle = (MultiStateToggleButton) viewDialogBottomSheet.findViewById(R.id.mstb_multi_id);
            // gán giá trị mặc định
            buttonToggle.setValue(0);


            AdditionAdapter additionAdapter = new AdditionAdapter();
            additionAdapter.addItemAddition(mAdditionCategories);
            rcvAddition.setAdapter(additionAdapter);
            rcvAddition.setHasFixedSize(true);
            rcvAddition.setLayoutManager(new GridLayoutManager(getActivity(),2));

            // gán thông tin
            tvFoodName.setText(item.getItemName());
            tvFoodPrice.setText(decimalFormat.format(item.getPrice()));
            tvQuantityBottomSheet.setText(item.getQuantity()+"");
            // khởi tạo các sự kiện trên view

            // sự kiện giảm số lượng sản phẩm
            fabMinusQuantityBottomSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if(itemQuantitySelected>0){
                            itemQuantitySelected--;
                            tvQuantityBottomSheet.setText(itemQuantitySelected+"");
                        }
                        else{
                            fabMinusQuantityBottomSheet.setEnabled(false);
                            fabMinusQuantityBottomSheet.setColorFilter(getResources().getColor(R.color.greyDark200));
                        }
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });

            // sự kiện tăng số lượng sản phẩm
            fabAddQuantityBottomSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!fabMinusQuantityBottomSheet.isEnabled()){
                        fabMinusQuantityBottomSheet.setEnabled(true);
                        fabMinusQuantityBottomSheet.setColorFilter(getResources().getColor(R.color.purple_500));
                    }
                    itemQuantitySelected++;
                    tvQuantityBottomSheet.setText(itemQuantitySelected+"");
                }
            });

            // sự kiện bấm nút đồng ý
            btnDongY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{

                        item.setQuantity(itemQuantitySelected);
                        bottomSheetDialog.dismiss();

                        swipeItemAdapter.notifyItemChanged(item.getPosition()-1);

                        boolean check2=false;
                        int vt=0;
                        for(int i=0; i<lstItemSelected.size(); i++){
                            if(item.equals(lstItemSelected.get(i))){
                                check2 =true;
                                vt=i;
                                break;
                            }
                        }
                        if(check2){
                            lstItemSelected.set(vt,item);
                        }
                        else{
                            lstItemSelected.add(item);
                        }
                        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
                        tvGiaPhaiThuLst.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
                        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
                            tvItemCount.setVisibility(View.VISIBLE);
                            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
                        }

                        // cập nhật danh sách bottom sheet
                        orderBottomSheetAdapter.addData(lstItemSelected);
                        tvItemCountLst.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
                        //Log.d(TAG, "onClickItemSwipe: "+lstItemSelected.toString());

                        // cập nhật sản số lượng danh mục theo sản phẩm chọn
                        updateCountCategory();
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });

            // sự kiện huỷ bỏ
            btnHuyBo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        bottomSheetDialog.dismiss();
                        return;
                    }
                    catch (Exception ex){
                        Log.d(TAG, "Err BottomSheet: "+ex.getMessage());
                    }
                }
            });
        }
        else{ // ngược lại thì không làm gì cả
//            countItem ++;
            itemQuantitySelected ++;
            item.setQuantity(itemQuantitySelected);
            swipeItemAdapter.notifyItemChanged(item.getPosition()-1);

        }
        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
        tvGiaPhaiThuLst.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
            tvItemCount.setVisibility(View.VISIBLE);
            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        }

        // cập nhật danh sách bottom sheet
        orderBottomSheetAdapter.addData(lstItemSelected);
        tvItemCountLst.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
//        Log.d(TAG, "onClickItemSwipe: "+lstItemSelected.toString());

        // cập nhật sản số lượng danh mục theo sản phẩm chọn
        updateCountCategory();
    }

    // swipe list bottomsheet
    @Override
    public void onItemBottomSheetRemove(Item item, int pos) {

        lstItemSelected.remove(item);
        orderBottomSheetAdapter.deleteItem(item, pos);

        tvItemCountLst.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
        tvGiaPhaiThuLst.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));

        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
            tvItemCount.setVisibility(View.VISIBLE);
            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        }
        else{
            tvItemCount.setVisibility(View.GONE);
        }

        // cập nhật lại danh sách
        for(int i=0; i<lstItem.size(); i++){
            Item itemGoc = lstItem.get(i);
            if(itemGoc.equals(item)){
                itemGoc.setQuantity(0);
                lstItem.set(i,itemGoc);
            }
        }
        // thông báo danh sách
        swipeItemAdapter.notifyItemChanged(item.getPosition()-1);

        // cập nhật sản số lượng danh mục theo sản phẩm chọn
        updateCountCategory();

    }

    /**
     * Hàm tăng số lượng cho item order
     * @param item
     * @param pos
     * @author giangpb
     * @date 16/02/2021
     */
    @Override
    public void onItemPlusQuantity(Item item, int pos) {
        int quan = item.getQuantity();
        quan ++;
        item.setQuantity(quan);

        lstItemSelected.set(pos, item);
        orderBottomSheetAdapter.updateItem(item, pos);

        tvItemCountLst.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
        tvGiaPhaiThuLst.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));

        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
            tvItemCount.setVisibility(View.VISIBLE);
            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        }
        else{
            tvItemCount.setVisibility(View.GONE);
        }

        // cập nhật lại danh sách
        for(int i=0; i<lstItem.size(); i++){
            Item itemGoc = lstItem.get(i);
            if(itemGoc.equals(item)){
                itemGoc.setQuantity(quan);
                lstItem.set(i,itemGoc);
            }
        }
        // thông báo danh sách
        swipeItemAdapter.notifyItemChanged(item.getPosition()-1);

        // cập nhật sản số lượng danh mục theo sản phẩm chọn
        updateCountCategory();

    }

    /**
     * Hàm giảm số lượng item
     * @param item item food
     * @param pos position of item
     * @author giangpb
     * @date 14/02/2021
     */
    @Override
    public void onItemMinusQuantity(Item item, int pos) {

        int quan = item.getQuantity();
        quan --;
        item.setQuantity(quan);

        lstItemSelected.set(pos, item);
        orderBottomSheetAdapter.updateItem(item, pos);

        tvItemCountLst.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));
        tvGiaPhaiThuLst.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));

        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
            tvItemCount.setVisibility(View.VISIBLE);
            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        }
        else{
            tvItemCount.setVisibility(View.GONE);
        }

        // cập nhật lại danh sách
        for(int i=0; i<lstItem.size(); i++){
            Item itemGoc = lstItem.get(i);
            if(itemGoc.equals(item)){

                itemGoc.setQuantity(quan);
                lstItem.set(i,itemGoc);
            }
        }
        // thông báo danh sách
        swipeItemAdapter.notifyItemChanged(item.getPosition()-1);

        // cập nhật sản số lượng danh mục theo sản phẩm chọn
        updateCountCategory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}