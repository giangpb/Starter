package vn.com.misa.starter2.ui.setupmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.ListMenuPagerAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.category.CategorySetupActivity;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * - Fragment thiết lập thực đơn
 * - Cho phép chọn các loại thực đơn
 * - Hiển thị sản phẩm theo thực đơn
 * @author GIANG PHAN
 * @date: 22/01/2021
 */
public class SetupMenuFragment extends Fragment{


    // presenter nhận dữ liệu từ view của danh mục sản phẩm
    private CategoryPresenter categoryPresenter;

    // lấy vị trí
    private int pos=0;

    private View viewShowPopup ;

    private TabLayout tabLayout;
    private ViewPager2 viewPagerListFoodMenu;

    private ArrayList<Category> mData;
    private ListMenuPagerAdapter menuPagerAdapter;

    private NavController navController;

    private static final String TAG = "SetupMenuFragment";

    private ImageView ivArrowBackHome;
    private MaterialButton btnContinueSetupMenu;
    private FloatingActionButton fabAddFood;

    private ImageView ivOptionMenu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup_menu, container, false);

        try {
            viewShowPopup = view.findViewById(R.id.viewShowPopup);
            // khởi tạo các điều khiển
            btnContinueSetupMenu = view.findViewById(R.id.btnContinueSelectMenu);
            ivArrowBackHome = view.findViewById(R.id.ivArrowBackHome);
            fabAddFood = view.findViewById(R.id.fabAddFood);


            categoryPresenter = new CategoryPresenter(getActivity());


            ivOptionMenu = view.findViewById(R.id.ivOptionMenu);

            // khởi tạo viewpager
            mData = categoryPresenter.getListCategory();
//        menuPagerAdapter = new ListMenuPagerAdapter(getActivity(),mData);
            menuPagerAdapter = new ListMenuPagerAdapter(getParentFragmentManager(), getLifecycle(),mData);
            viewPagerListFoodMenu = view.findViewById(R.id.viewPagerListFoodMenu);
            tabLayout = view.findViewById(R.id.tabLayout);
            viewPagerListFoodMenu.setAdapter(menuPagerAdapter);
            viewPagerListFoodMenu.setOffscreenPageLimit(10);

            new TabLayoutMediator(tabLayout, viewPagerListFoodMenu, ((tab, position) -> {
                tab.setText(mData.get(position).getCategoryName());
                tab.setIcon(getMyDrawable(getActivity(), mData.get(position).getIconPath()));
            })).attach();

            // khởi tạo các sự kiện

            addEvents();
        }
        catch (Exception ex){
            GIANGUtils.getInstance().handlerLog(ex.getMessage());
        }

        return view;
    }

    /**
     * Hàm Lấy icon xml drawable từ 1 đường dẫn
     * @param c màn hình truyền vào
     * @param ImageName đường dẫn
     * @return trả về 1 drawble
     * @author: giangpb
     * @date 25/01/2021
     */
    Drawable getMyDrawable(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    /**
     * Hàm show popup menu khi nhấn vào option menu
     * hiển thị icon
     * @param view neo vào view nào khi hiển thị
     * @author giangpb
     * @date 23/01/2021
     */
    private void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.mnu_setup_food_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            // sự kiện nhấn vào từng item
            @SuppressLint("NonConstantResourceId")
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_delete_all_food:
                        Toast.makeText(getActivity(), ""+category.getCategoryName(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_setupGroupMenu:
                        Intent intent = new Intent(getContext(), CategorySetupActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    /**
     * Hàm thiết lập các sự kiện
     * @author: giangpb
     * @date: 22/01/2021
     */
    Category category;
    private void addEvents(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                category = mData.get(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // sự kiện hoàn thành cài đặt
        btnContinueSetupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // chuyển đến màn hình hoàn thành cài đặt
                    navController.navigate(R.id.action_setupMenuFragment_to_finishSetupFragment);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // sự kiện quay trở lại
        ivArrowBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navController.navigate(R.id.action_setupMenuFragment_to_selectRestaurantFragment);
//                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.selectRestaurantFragment,true).build();
//                navController.navigate(R.id.action_setupMenuFragment_to_selectRestaurantFragment,null,navOptions);
                getActivity().onBackPressed();
            }
        });

        // sự kiện thêm món ăn
        fabAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    navController.navigate(R.id.action_setupMenuFragment_to_addFoodFragment);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        ivOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(viewShowPopup);
            }
        });
    }

}