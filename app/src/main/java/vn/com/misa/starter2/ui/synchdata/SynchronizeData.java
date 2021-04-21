package vn.com.misa.starter2.ui.synchdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.util.GIANGUtils;

public class SynchronizeData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize_data);
    }

    public void back(View view) {
        try{
            finish();
        }
        catch (Exception ex){
            GIANGUtils.getInstance().handlerException(ex);
        }
    }
}