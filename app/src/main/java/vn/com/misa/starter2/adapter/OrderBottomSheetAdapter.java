package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.ui.order_add.IFoodListener;

/**
 * ‐ Hiển thị danh sách order ở bottomsheet
 * ‐ @created_by giangpb on 2/8/2021
 */
public class OrderBottomSheetAdapter extends RecyclerView.Adapter<OrderBottomSheetAdapter.MyHolder> {

    private static final String TAG = "OrderBottomSheetAdapter";

    private IFoodListener mIFoodListener;

    private Context mContext;
    private ArrayList<Item> mData;
    private DecimalFormat decimalFormat;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public OrderBottomSheetAdapter(Context context, IFoodListener iFoodListener){
        this.mContext = context;
        this.mIFoodListener = iFoodListener;
        decimalFormat = new DecimalFormat("#,###");
    }

    public void addData(ArrayList<Item> data){
        if(mData == null)
            mData = new ArrayList<>();
        mData = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bottomsheet_order, parent ,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout,mData.get(position).getItemID());
        viewBinderHelper.closeLayout(mData.get(position).getItemID());


        holder.tvItemName.setText(mData.get(position).getItemName());
        holder.tvItemPrice.setText(decimalFormat.format(mData.get(position).getPrice()));
        holder.tvQuantityBottomSheetLst.setText(mData.get(position).getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if (mData ==null)
            return 0;
        else
            return mData.size();
    }

    public void deleteItem(Item item, int pos){
        mData.remove(item);
        notifyItemRemoved(pos);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvItemPrice;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvQuantityBottomSheetLst;

        private RelativeLayout btnRemoveItem;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            tvItemName = itemView.findViewById(R.id.tvNameFoodLst);
            tvItemPrice = itemView.findViewById(R.id.tvFoodPriceLst);
            tvQuantityBottomSheetLst = itemView.findViewById(R.id.tvQuantityBottomSheetLst);
            btnRemoveItem = itemView.findViewById(R.id.btnRemoveItem);


            // xử lý sự kiện
            btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIFoodListener.onItemBottomSheetRemove(mData.get(getAdapterPosition()),getAdapterPosition());
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }
}
