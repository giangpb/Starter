package vn.com.misa.starter2.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
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

//    public ListMenuPagerAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<Category> data) {
//        super(fragmentActivity);
//        this.mData = data;
//    }


    public ListMenuPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Category> data) {
        super(fragmentManager, lifecycle);
        this.mData = data;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ItemListSetupMenuFragment.getInstance(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData != null? mData.size():0;
    }
}
