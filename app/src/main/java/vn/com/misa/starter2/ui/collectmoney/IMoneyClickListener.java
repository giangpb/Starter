package vn.com.misa.starter2.ui.collectmoney;

/**
 * ‐ Lắng nghe sự kiện lúc nhấn vào danh sách gợi ý tiền
 * ‐ @created_by giangpb on 2/8/2021
 */
public interface IMoneyClickListener {

    /**
     * Lắng nghe sự kiện onclick
     * @param money trả về số tiền đang chọn
     * @author giangpb
     * @date 08/02/2021
     */
    void onListMoneyClick(int money);
}
