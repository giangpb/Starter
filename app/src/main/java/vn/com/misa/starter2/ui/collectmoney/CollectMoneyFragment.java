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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import java.text.DecimalFormat;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.MoneyRequirementAdapter;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.ui.listorder.OrderPresenter;
import vn.com.misa.starter2.ui.order_add.AddOrderFragment;

/**
 * - frangment thu tiền
 * @author GIANG PHAN
 * @date 07/02/2021
 */
public class CollectMoneyFragment extends Fragment implements IMoneyClickListener {
    private static final String TAG = "CollectMoneyFragment";

    // presenter
    OrderPresenter orderPresenter;


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

    private MaterialButton btnHoanThanh;

    private RecyclerView rcvLstMoneyRequirement;
    private MoneyRequirementAdapter moneyRequirementAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collect_money, container, false);

        // khởi tạo điều khiển
        orderPresenter = new OrderPresenter(getContext());

        decimalFormat = new DecimalFormat("#,###");
        ivBack = view.findViewById(R.id.ivBack);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        tvKhachDua = view.findViewById(R.id.tvKhachDua);
        tvTraLai = view.findViewById(R.id.tvTraLai);
        btnHoanThanh = view.findViewById(R.id.btnHoanThanh);

        toggleButton = (MultiStateToggleButton) view.findViewById(R.id.mstb_multi_id);
        toggleButton.setValue(0);

        // khởi tạo lst
        rcvLstMoneyRequirement = view.findViewById(R.id.rcvLstMoneyRequirement);
        moneyRequirementAdapter = new MoneyRequirementAdapter(getContext(), this);
        rcvLstMoneyRequirement.setAdapter(moneyRequirementAdapter);
        rcvLstMoneyRequirement.setHasFixedSize(true);
        rcvLstMoneyRequirement.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // khởi tạo sự kiện

        Bundle bundle = getArguments();
        mOrder = (Order)  bundle.getSerializable("order");
        // gán thông tin
        tvTongTien.setText(decimalFormat.format(mOrder.getAmount()));
        tvKhachDua.setText(decimalFormat.format(mOrder.getAmount()));
        tvTraLai.setText(0+"");

        addEvents();

        return view;
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

        // xử lý sự kiện hoàn thành
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    orderPresenter.paymentDone(mOrder.getOrderID());


                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onListMoneyClick(int money) {
        tvKhachDua.setText(decimalFormat.format(money));
        int moneyChange = money - mOrder.getAmount();
        tvTraLai.setText(decimalFormat.format(moneyChange));
    }
}