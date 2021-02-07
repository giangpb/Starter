package vn.com.misa.starter2.ui.collectmoney;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.MoneyRequirementAdapter;

/**
 * - frangment thu tiền
 * @author GIANG PHAN
 * @date 07/02/2021
 */
public class CollectMoneyFragment extends Fragment {
    private static final String TAG = "CollectMoneyFragment";


    // khai báo các điều khiển
    private ImageView ivBack;
    private MultiStateToggleButton toggleButton ;

    private RecyclerView rcvLstMoneyRequirement;
    private MoneyRequirementAdapter moneyRequirementAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collect_money, container, false);

        // khởi tạo điều khiển
        ivBack = view.findViewById(R.id.ivBack);

        toggleButton = (MultiStateToggleButton) view.findViewById(R.id.mstb_multi_id);
        toggleButton.setValue(0);

        // khởi tạo lst
        rcvLstMoneyRequirement = view.findViewById(R.id.rcvLstMoneyRequirement);
        moneyRequirementAdapter = new MoneyRequirementAdapter(getContext());
        rcvLstMoneyRequirement.setAdapter(moneyRequirementAdapter);
        rcvLstMoneyRequirement.setHasFixedSize(true);
        rcvLstMoneyRequirement.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // khởi tạo sự kiện
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
                    getActivity().onBackPressed();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }
}