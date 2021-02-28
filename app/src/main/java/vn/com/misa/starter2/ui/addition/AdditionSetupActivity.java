package vn.com.misa.starter2.ui.addition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.AdditionSetupAdapter;
import vn.com.misa.starter2.ui.additem.UnitPresenter;
import vn.com.misa.starter2.ui.order_add.AdditionPresenter;

public class AdditionSetupActivity extends AppCompatActivity {
    private static final String TAG = "UnitSetupActivity";

    // khai báo các điều khiển
    private ImageView ivBack;
    private EditText etUnitName;

    //
    private RecyclerView rcvLstUnit;
    AdditionPresenter additionPresenter;
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
        etUnitName.requestFocus();

        //
        rcvLstUnit = findViewById(R.id.rcvLstUnit);
        additionPresenter = new AdditionPresenter(getApplicationContext());
        additionSetupAdapter = new AdditionSetupAdapter(additionPresenter.getAllAddition());
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
    }
}