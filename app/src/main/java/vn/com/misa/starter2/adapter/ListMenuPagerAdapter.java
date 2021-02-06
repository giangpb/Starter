package vn.com.misa.starter2.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.ui.setuplistitem.IOnClick;
import vn.com.misa.starter2.ui.setuplistitem.ItemListSetupMenuFragment;

/**
 * ‐ Apdapter for viewpager 2
 * ‐ @created_by giangpb on 1/24/2021
 */
public class ListMenuPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Category> mData;
    public ListMenuPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Category> data){
        super(fragmentActivity);
        this.mData = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ItemListSetupMenuFragment.newInstance(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
