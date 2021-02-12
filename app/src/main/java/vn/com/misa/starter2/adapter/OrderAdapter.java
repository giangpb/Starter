package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.ui.listorder.IOrderListener;

/**
 * ‐ Hiển thị danh sách order
 * ‐ @created_by giangpb on 2/1/2021
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {


    private static final String TAG = "OrderAdapter";

    private IOrderListener mIOrderListener;

    private ArrayList<Order> mData;
    private Context mContext;

    public OrderAdapter(Context context, IOrderListener iOrderListener, ArrayList<Order> data){
        this.mIOrderListener = iOrderListener;
        this.mContext = context;
        this.mData = data;
    }

    /**
     * Hàm thêm danh sách order
     * @param data danh sách order
     * @author giangpb
     * @date 1/2/2021
     */
    public void addData(ArrayList<Order> data){
        if(mData ==null)
            mData = new ArrayList<>();
        mData.clear();
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Order order = mData.get(position);
        holder.tvFoodItemsName.setText(order.getItemNames());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.tvTotalAmount.setText(decimalFormat.format(order.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        if(mData==null)
            return 0;
        return mData.size();
    }

    public void removeItem(Order order, int pos){
        mData.remove(order);
        notifyItemRemoved(pos);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvFoodItemsName;

        TextView tvTotalAmount;

        MaterialButton btnThuTien;

        MaterialButton btnHuy;

        MaterialCardView itemOrder;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodItemsName = itemView.findViewById(R.id.tvFoodItemNames);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            btnThuTien = itemView.findViewById(R.id.btnThuTien);
            itemOrder = itemView.findViewById(R.id.itemOrder);

            // xử lý sự kiện

            itemOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIOrderListener.onOrderClickListener(mData.get(getAdapterPosition()));
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });

            // sự kiện huỷ order
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIOrderListener.onDeleteOrderClickListener(mData.get(getAdapterPosition()), getAdapterPosition());
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });

            // sự kiện thu tiền
            btnThuTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIOrderListener.onPaymentOrderClickListener(mData.get(getAdapterPosition()));
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }

}
