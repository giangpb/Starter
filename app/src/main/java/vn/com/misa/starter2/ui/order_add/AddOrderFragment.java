package vn.com.misa.starter2.ui.order_add;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.AdditionAdapter;
import vn.com.misa.starter2.adapter.CategoryAdapter;
import vn.com.misa.starter2.adapter.SwipeItemAdapter;
import vn.com.misa.starter2.model.dto.AdditionCategory;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.model.entity.OrderDetail;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.listorder.OrderPresenter;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodPresenter;

/**
 * Lớp chọn món (add order)
 * @author GIANG PHAN
 * @date 28/01/2021
 */
public class AddOrderFragment extends Fragment implements ICategoryListener, IFoodListener{
    private static final String TAG = "AddOrderFragment";

    private CategoryPresenter categoryPresenter;
    private ItemFoodPresenter itemFoodPresenter;

    // format tiền
    private DecimalFormat decimalFormat;

    // chi tiết hoá đơn
    private OrderDetailPresenter mOrderDetailPresenter;
    private ArrayList<OrderDetail> mOrderDetails;
    //
    private ArrayList<Item> lstItemSelected=null;

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

    // danh sách sản phẩm theo danh mục
    private RecyclerView rcvListItemOfCategory;
    private SwipeItemAdapter swipeItemAdapter;
    private ArrayList<Item> lstItem;


    private int priceItemSelected=0;
    private TextView tvGiaPhaiThu;

    // danh sách danh mục bổ sung
    private AdditionPresenter additionPresenter;
    private ArrayList<AdditionCategory> mAdditionCategories;


    private TextView tvItemCount;
    //
    private int itemQuantitySelected;

    // order
    private OrderPresenter orderPresenter;
    boolean checkAddFirst=false;
    private Order mOrder =null;

    // điều khiển
    MaterialButton btnLuuLai;
    MaterialButton btnThuTien;

    private NavController navController;
    private OrderDetailPresenter orderDetailPresenter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);

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


        // khởi tạo danh sách danh mục
        rcvListCategory = view.findViewById(R.id.rcvListCategory);
        categoryAdapter = new CategoryAdapter(getActivity(),categoryPresenter.getListCategory(),this::onClickItem);
        rcvListCategory.setHasFixedSize(true);
        rcvListCategory.setAdapter(categoryAdapter);
        rcvListCategory.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        //
        additionPresenter = new AdditionPresenter(getActivity());
        orderDetailPresenter  = new OrderDetailPresenter(getActivity());

        // lấy bundle order
        Bundle bundle = getArguments();
        if(bundle !=null){
            mOrder = (Order) bundle.getSerializable("order");
            lstItemSelected = itemFoodPresenter.getItemInOrderDetail(mOrder.getOrderID());
            checkAddFirst=true;
            // kiểm tra đang xem order hay tạo mới

            mOrderDetailPresenter = new OrderDetailPresenter(getActivity());
            mOrderDetails = mOrderDetailPresenter.getOrderDetail(mOrder.getOrderID());

            Log.d(TAG, "onCreateView: "+mOrderDetails.toString());
//            lstItemSelected = m

            for(int i=0; i<lstItem.size(); i++){
                Item item = lstItem.get(i);
                for(OrderDetail orderDetail :mOrderDetails){
                    if (orderDetail.getItemID().equalsIgnoreCase(item.getItemID())){ // fix sau
                        item.setQuantity(orderDetail.getQuantity());
                        lstItem.set(i,item);
                    }
                }
            }

            for (OrderDetail orderDetail :mOrderDetails){
                priceItemSelected += orderDetail.getAmount();
            }
            tvGiaPhaiThu.setText(decimalFormat.format(priceItemSelected));

            if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
                tvItemCount.setVisibility(View.VISIBLE);
                tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
            }

        }
        else{
            // khởi tạo danh sách mới chứa item order mới
            lstItemSelected = new ArrayList<>();
        }
//        tvGiaPhaiThu.setText(decimalFormat.format(itemFoodPresenter.tinhTienHoaDon(lstItemSelected)));

        Log.d(TAG, "onCreateView: "+lstItemSelected.toString());
        // khởi tạo danh sách sản phẩm
        rcvListItemOfCategory = view.findViewById(R.id.rcvListItemOfCategory);
        swipeItemAdapter = new SwipeItemAdapter(getActivity(), this);

        // danh sách bổ sung danh mục
        mAdditionCategories = additionPresenter.additionCategories(categoryPresenter.getListCategory().get(0).getCategoryID());

        swipeItemAdapter.clearAllItem();
        for(Item item : lstItem){
            if(item.getCategoryID().equals(categoryPresenter.getListCategory().get(0).getCategoryID())){
                swipeItemAdapter.addItem(item);
            }
        }

        rcvListItemOfCategory.setHasFixedSize(true);
        rcvListItemOfCategory.setAdapter(swipeItemAdapter);
        rcvListItemOfCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        addEvents();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    /**
     * hàm tạo các sự kiện onclick
     * @author giangpb
     * @date 28/01/2021
     */
    private void addEvents(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    getActivity().onBackPressed();
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
                    // kiểm tra order đã tồn tại trước đó hay chưa, nếu chưa thì thêm mới, có rồi thì cập nhật lại
                    if(mOrder ==null){
                        Order order =new Order();
                        long timeMillis = System.currentTimeMillis();
                        order.setOrderID(timeMillis+"");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        order.setDateCrate(currentDateandTime);
                        order.setOrderStatus(1);
                        order.setAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));
                        order.setItemNames(lstItemSelected.toString());
                        order.setTotalAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));

                        // thêm vào order
                        orderPresenter.addOrder(order);

                        // thêm danh sách vào hoá đơn
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
                                orderDetail.setDateCreate(currentDateandTime);
                                orderDetailPresenter.themOrderDetail(orderDetail);
                            }
                        }
                    }
                    else{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                        mOrder.setOrderDate(currentDateandTime);
                        mOrder.setAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));
                        mOrder.setItemNames(lstItemSelected.toString());
                        mOrder.setTotalAmount(itemFoodPresenter.tinhTienHoaDon(lstItemSelected));

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
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        btnThuTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onClickItem(Category category) {
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

                        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
                            tvItemCount.setVisibility(View.VISIBLE);
                            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
                        }

                        Log.d(TAG, "onClickItemSwipe: "+lstItemSelected.toString());
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
        if(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)>0){
            tvItemCount.setVisibility(View.VISIBLE);
            tvItemCount.setText(itemFoodPresenter.tongSoLuongSanPham(lstItemSelected)+"");
        }

        Log.d(TAG, "onClickItemSwipe: "+lstItemSelected.toString());
    }
}