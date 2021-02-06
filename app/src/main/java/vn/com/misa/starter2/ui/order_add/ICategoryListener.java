package vn.com.misa.starter2.ui.order_add;

import vn.com.misa.starter2.model.entity.Category;

/**
 * ‐ Lắng nghe sự kiện nhấn item
 * ‐ @created_by giangpb on 1/29/2021
 */
public interface ICategoryListener {
    void onClickItem(Category category);
}
