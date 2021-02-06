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


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOrderFragment newInstance(String param1, String param2) {
        ListOrderFragment fragment = new ListOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

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

        orderPresenter = new OrderPresenter(getContext());

        rcvListOrder= view.findViewById(R.id.rcvListOrder);
        orderAdapter = new OrderAdapter(getContext(), this);
        rcvListOrder.setAdapter(orderAdapter);
        rcvListOrder.setHasFixedSize(true);
        rcvListOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        orderAdapter.addData(orderPresenter.getAllOrder());

        // tạo các sự kiện
        addEvents();

        return view;
    }

    /**
     * Hàm khởi tạo các sự kiện onClick
     * @author giangpb
     * @date 28/01/2021
     */
    private void addEvents(){

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
    }

    @Override
    public void onOrderClickListener(Order order) {
        // cập nhật order
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        navController.navigate(R.id.action_listOrderFragment_to_addOrderFragment, bundle, null);
    }
}