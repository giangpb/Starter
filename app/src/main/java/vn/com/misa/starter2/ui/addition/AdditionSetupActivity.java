package vn.com.misa.starter2.ui.addition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.AdditionSetupAdapter;
import vn.com.misa.starter2.model.entity.Addition;
import vn.com.misa.starter2.ui.additem.UnitPresenter;
import vn.com.misa.starter2.ui.order_add.AdditionPresenter;

public class AdditionSetupActivity extends AppCompatActivity implements IAdditionListener {
    private static final String TAG = "UnitSetupActivity";

    // khai báo các điều khiển
    private ImageView ivBack;
    private EditText etUnitName;
    private ImageView ivAddAddition;

    //
    private RecyclerView rcvLstUnit;
    private AdditionPresenter additionPresenter;
    private AdditionSetupAdapter additionSetupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setup);

        // khởi tạo các điều khiển
        addControls();

        addEvents();
    }

    private void addControls(){
        ivBack = findViewById(R.id.ivBack);
        etUnitName = findViewById(R.id.etUnitName);
        ivAddAddition = findViewById(R.id.ivAddAddition);
        etUnitName.requestFocus();

        //
        rcvLstUnit = findViewById(R.id.rcvLstUnit);
        additionPresenter = new AdditionPresenter(getApplicationContext());
        additionSetupAdapter = new AdditionSetupAdapter(additionPresenter.getAllAddition(), this);
        rcvLstUnit.setAdapter(additionSetupAdapter);
        rcvLstUnit.setHasFixedSize(true);
        rcvLstUnit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    private void addEvents(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        ivAddAddition.setOnClickListener((v -> {
            try{
                // xử lý thêm addtion trên view và trong database
                String additionDesc = etUnitName.getText().toString();
                if (additionDesc.isEmpty()){
                    StyleableToast.makeText(this,"Sở thích phục vụ không được để trống!!", Toast.LENGTH_LONG, R.style.mytoast).show();
                }
                else {
                    if(additionPresenter.checkExistAddition(additionDesc)){
                        StyleableToast.makeText(this,"Sở thích đã tồn tại !", Toast.LENGTH_LONG, R.style.mytoast).show();
                    }
                    else {
                        Addition addition = new Addition();
                        long time = System.currentTimeMillis();
                        addition.setAdditionID("ad"+time);
                        addition.setDescription(additionDesc);
                        addition.setInventoryItemCategoryAdditionID("72b11292-fdb7-4fe6-8a46-3c540db851c6");
                        addition.setPosition(additionPresenter.getAllAddition().size());
                        additionPresenter.addAddition(addition);
                        additionSetupAdapter.addAddition(addition);
                        etUnitName.setText("");
                    }
                }
            }
            catch (Exception ex){
                Log.d(TAG, "addEvents: "+ex.getMessage());
            }
        }));
    }

    @Override
    public void removeAddition(Addition addition, int position) {
        additionPresenter.removeAddition(addition.getAdditionID());
        additionSetupAdapter.removeAddition(addition, position);
    }
}