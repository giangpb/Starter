package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.PaymentDetail;

/**
 * ‐ Adapter hiển thị danh sách payment detail trong phiếu
 * ‐ @created_by giangpb on 2/15/2021
 */
public class PaymentDetailAdapter extends RecyclerView.Adapter<PaymentDetailAdapter.MyHolder> {
    private ArrayList<PaymentDetail> mData;

    private Context mContext;

    private DecimalFormat decimalFormat;

    public PaymentDetailAdapter(Context context, ArrayList<PaymentDetail> data){
        this.mContext = context;
        this.mData = data;
        decimalFormat = new DecimalFormat("#,###");
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_payment_detail, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvItemName.setText(mData.get(position).getItemName());
        holder.tvItemPrice.setText(decimalFormat.format(mData.get(position).getUnitPrice()));
        holder.tvItemQuantity.setText(mData.get(position).getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemQuantity;
        TextView tvItemPrice;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = itemView.findViewById(R.id.tvPaymentDetailItemQuantity);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
        }
    }
}
