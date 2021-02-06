package vn.com.misa.starter2.ui.setuplistitem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.FoodAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.ui.setupmenu.SetupMenuFragment;
import vn.com.misa.starter2.ui.updateitem.UpdateItemActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemListSetupMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListSetupMenuFragment extends Fragment implements IOnClick{

    private static final String TAG = "ItemListSetupMenuFragme";

    private RecyclerView rcvListItemFood;
    private FoodAdapter foodAdapter;

    private ItemFoodPresenter itemFoodPresenter;

    // chia sẻ mã danh mục giữa các activity
    public static Category category;
    private Category category2;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItemListSetupMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemListSetupMenuFragment newInstance(Category cat) {
        ItemListSetupMenuFragment fragment = new ItemListSetupMenuFragment();
        fragment.category2 = cat;
//        category = cat;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list_setup_menu, container, false);

        // khởi tạo item
        // recyclerview
        rcvListItemFood = view.findViewById(R.id.rcvListItemFood);
        itemFoodPresenter = new ItemFoodPresenter(getContext());
        Log.d(TAG, "onCreateView: ");
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: ");

        // thiết lập danh sách recyclerview
        foodAdapter = new FoodAdapter(getContext(),itemFoodPresenter.getListItemFood(category2.getCategoryID()), this);
        rcvListItemFood.setAdapter(foodAdapter);
        rcvListItemFood.setHasFixedSize(true);
        rcvListItemFood.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    // xoá item
    @Override
    public void onItemClick(Item item) {
        try{
            itemFoodPresenter.deleteItem(item.getItemID());
            foodAdapter.removeItem(item);
        }
        catch (Exception ex){
            Log.d(TAG, "onItemClick: "+ex.getMessage());
        }

    }



    // cập nhật item
    @Override
    public void onUpdateItem(Item item) {
        Intent intent = new Intent(getContext(), UpdateItemActivity.class);

        intent.putExtra("id", item.getItemID());
        intent.putExtra("name", item.getItemName());
        intent.putExtra("price", item.getPrice());
        intent.putExtra("unit", item.getUnitID());
        if(item.getImage()!=null)
            intent.putExtra("image", item.getImage());
        intent.putExtra("category", item.getCategoryID());
        startActivity(intent);
    }

}