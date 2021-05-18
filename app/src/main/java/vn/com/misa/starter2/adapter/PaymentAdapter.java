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

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.ui.payment.IPaymentClickListener;

/**
 * ‐ Adapter danh sách payment
 * ‐ @created_by giangpb on 2/12/2021
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyHolder> {
    private static final String TAG = "PaymentAdapter";
    private Context mContext;

    private IPaymentClickListener mPaymentClickListener;

    private ArrayList<Payment> mData;

    private DecimalFormat decimalFormat;

    public PaymentAdapter(Context context, IPaymentClickListener iPaymentClickListener){
        this.mPaymentClickListener = iPaymentClickListener;
        this.mContext = context;
        decimalFormat = new DecimalFormat("#,###");
    }

    /**
     * Hàm thêm danh sách payment và thông báo cập nhật
     * @param data danh sách
     * @author giangpb
     * @date 14/02/2021
     */
    public void addData(ArrayList<Payment> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_payment, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvPaymentNo.setText(mData.get(position).getRefNO());
        holder.tvPaymentAmount.setText(decimalFormat.format(mData.get(position).getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        if(mData ==null)
            return 0;
        else
            return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvPaymentNo;

        TextView tvPaymentAmount;

        RelativeLayout rlItemPayment;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvPaymentNo = itemView.findViewById(R.id.tvPaymentNo);
            tvPaymentAmount = itemView.findViewById(R.id.tvPaymentAmount);
            rlItemPayment = itemView.findViewById(R.id.rlItemPayment);

            // xử lý sự kiện
            rlItemPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mPaymentClickListener.onPaymentClick(mData.get(getAdapterPosition()));
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }
}
