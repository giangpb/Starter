package vn.com.misa.starter2.ui.order_add;

import vn.com.misa.starter2.model.entity.Item;

/**
 * ‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
 * ‐ @created_by giangpb on 1/31/2021
 */
public interface IFoodListener {
    void onClickItemSwipe(Item item);

    /**
     * Contracts bắt sự kiện swipe delete bottomSheet list
     * @param item sản phẩm
     * @param pos vị trí
     * @author giangpb
     * @date 09/02/2021
     */
    void onItemBottomSheetRemove(Item item, int pos);

    /**
     * Sự kiện tăng số lượng
     * @param item
     * @param pos
     * @author giangpb
     * @date 16/02/2021
     */
    void onItemPlusQuantity(Item item, int pos);
}
