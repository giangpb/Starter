package vn.com.misa.starter2.ui.listorder;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.OrderAdapter;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.ui.order.OrderActivity;

/**
 * Hiển thị danh sách order
 * @author GIANG PHAN
 * @date 28/01/2021
 */
public class ListOrderFragment extends Fragment implements IOrderListener{
    private static final String TAG = "ListOrderFragment";

    private ImageView ivOpenDrawerNavigation;
    private FloatingActionButton fabAddOrder;

    private NavController navController;


    private OrderPresenter orderPresenter;
    private RecyclerView rcvListOrder;
    private OrderAdapter orderAdapter;

    // order empty
    LinearLayout llOrderEmpty;

    // Tìm kiếm order
    private ImageView ivSearch;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

        // khởi tạo các điều khiển
        ivOpenDrawerNavigation = view.findViewById(R.id.ivOpenDrawerNavigation);
        fabAddOrder = view.findViewById(R.id.fabAddOrder);
        rcvListOrder= view.findViewById(R.id.rcvListOrder);
        llOrderEmpty = view.findViewById(R.id.llOrderEmpty);

        ivSearch = view.findViewById(R.id.ivSearch);

        orderPresenter = new OrderPresenter(getContext());

        // tạo các sự kiện
        addEvents();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(orderPresenter.getAllOrder().size()>0){
            llOrderEmpty.setVisibility(View.GONE);
            rcvListOrder.setVisibility(View.VISIBLE);
            orderAdapter = new OrderAdapter(this,orderPresenter.getAllOrder());
            rcvListOrder.setAdapter(orderAdapter);
            rcvListOrder.setHasFixedSize(true);
            rcvListOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        else{
            llOrderEmpty.setVisibility(View.VISIBLE);
            rcvListOrder.setVisibility(View.GONE);
        }

    }

    /**
     * Hàm khởi tạo các sự kiện onClick
     * @author giangpb
     * @date 28/01/2021
     */
    private void addEvents(){

        // sự kiện tìm kiếm
        ivSearch.setOnClickListener((view)->{

        });

        // sự kiện mở drawer navigation
        ivOpenDrawerNavigation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                try{
                if(!OrderActivity.drawerLayout.isDrawerOpen(Gravity.START)) OrderActivity.drawerLayout.openDrawer(Gravity.START);
                else OrderActivity.drawerLayout.closeDrawer(Gravity.END);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // chuyển sang màn thêm món
        fabAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    navController.navigate(R.id.action_listOrderFragment_to_addOrderFragment);
                }
                catch (Exception ex){
                    ex.getMessage();
                }
            }
        });

        // chuyển sang màn thêm món
        llOrderEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    navController.navigate(R.id.action_listOrderFragment_to_addOrderFragment);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onOrderClickListener(Order order) {
        // cập nhật order
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        navController.navigate(R.id.action_listOrderFragment_to_addOrderFragment, bundle, null);
    }

    @Override
    public void onDeleteOrderClickListener(Order order, int pos) {
        // hỏi trước khi xoá... thêm sau
        // xoá trong csdl
        orderPresenter.deleteOrder(order.getOrderID());
        // cập nhật lại giao diện
        orderAdapter.removeItem(order, pos);

        //Log.d(TAG, "onDeleteOrderClickListener: "+orderPresenter.getAllOrder().size());

        if(orderPresenter.getAllOrder().size()>0){
            llOrderEmpty.setVisibility(View.GONE);
            rcvListOrder.setVisibility(View.VISIBLE);
        }
        else{
            llOrderEmpty.setVisibility(View.VISIBLE);
            rcvListOrder.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPaymentOrderClickListener(Order order) {
        // cập nhật order
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        bundle.putBoolean("check", true);
        navController.navigate(R.id.action_listOrderFragment_to_addOrderFragment, bundle, null);
    }
}