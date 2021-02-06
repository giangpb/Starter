package vn.com.misa.starter2.ui.setuplistitem;

import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;

/**
 * ‐ Bắt sự kiện click
 * ‐ @created_by giangpb on 1/26/2021
 */
public interface IOnClick {


    void onItemClick(Item item);
    void onUpdateItem(Item item);
}
