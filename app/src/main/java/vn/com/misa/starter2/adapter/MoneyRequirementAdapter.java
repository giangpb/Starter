package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.ui.collectmoney.IMoneyClickListener;

/**
 * ‐ Hiển thị danh sách tiền gợi ý
 * ‐ @created_by giangpb on 2/6/2021
 */
public class MoneyRequirementAdapter extends RecyclerView.Adapter<MoneyRequirementAdapter.MyHolder> {
    private static final String TAG = "MoneyRequirementAdapter";

    private IMoneyClickListener mIMoneyClickListener;

    private Context mContext;

    private ArrayList<Integer> mLstMoney;

    private DecimalFormat decimalFormat;

    private TextView tvSelected =null;

    public MoneyRequirementAdapter(Context context,ArrayList<Integer> lstMoney, IMoneyClickListener moneyClickListener){
        this.mIMoneyClickListener = moneyClickListener;
        mContext = context;
        this.mLstMoney = lstMoney;
        decimalFormat = new DecimalFormat("#,###");
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_money_requirement, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvMoney.setText(decimalFormat.format(mLstMoney.get(position)));
    }

    @Override
    public int getItemCount() {
        if(mLstMoney.size()<6)
            return mLstMoney.size();
        else
            return 6;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvMoney;
        private LinearLayout itemMoneyRequirement;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvMoney = itemView.findViewById(R.id.tvMoney);
            itemMoneyRequirement = itemView.findViewById(R.id.itemMoneyRequirement);

            // xử lý sự kiện
            itemMoneyRequirement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIMoneyClickListener.onListMoneyClick(mLstMoney.get(getAdapterPosition()));
                        if(tvSelected!=null){
                            tvSelected.setBackground(mContext.getResources().getDrawable(R.drawable.bg_money_requirement));
                            tvSelected.setTextColor(mContext.getResources().getColor(R.color.green));
                        }
                        tvMoney.setBackground(mContext.getResources().getDrawable(R.drawable.bg_money_requirement_selected));
                        tvMoney.setTextColor(mContext.getResources().getColor(R.color.white));

                        tvSelected = tvMoney;
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }
}
