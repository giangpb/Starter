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
import vn.com.misa.starter2.ui.report.items.dto.ItemReport;

/**
 * ‐ @created_by giangpb on 2/19/2021
 */
public class ItemReportAdapter extends RecyclerView.Adapter<ItemReportAdapter.MyHolder> {

    private Context mContext;

    private ArrayList<ItemReport> mData;

    private DecimalFormat decimalFormat;

    public ItemReportAdapter(Context context) {
        this.mContext = context;
        decimalFormat = new DecimalFormat("#,###");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_detail, parent, false);
        return new MyHolder(view);
    }

    public void addData(ArrayList<ItemReport> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvItemReportNumber.setBackground(mContext.getResources().getDrawable(R.drawable.bg_number_report));
        holder.tvItemReportNumber.setTextColor(mContext.getResources().getColor(R.color.greyDark200));
        if (position == 0){
            holder.tvItemReportNumber.setBackground(mContext.getResources().getDrawable(R.drawable.bg_number_report_1));
            holder.tvItemReportNumber.setTextColor(mContext.getResources().getColor(R.color.c1));
        }
        if (position==1){
            holder.tvItemReportNumber.setBackground(mContext.getResources().getDrawable(R.drawable.bg_number_report_2));
            holder.tvItemReportNumber.setTextColor(mContext.getResources().getColor(R.color.c2));
        }
        if (position==2){
            holder.tvItemReportNumber.setBackground(mContext.getResources().getDrawable(R.drawable.bg_number_report_3));
            holder.tvItemReportNumber.setTextColor(mContext.getResources().getColor(R.color.c3));
        }
        holder.tvItemReportNumber.setText(position+1+"");
        holder.tvItemReportName.setText(mData.get(position).getItemName());
        holder.tvItemReportDiscount.setText("0");
        holder.tvItemReportQuantity.setText(mData.get(position).getItemQuantity()+"");
        holder.tvItemReportPrice.setText(decimalFormat.format(mData.get(position).getItemPrice()));
        holder.tvItemReportAmount.setText(decimalFormat.format(mData.get(position).getItemPrice()));
    }

    @Override
    public int getItemCount() {
        if(mData == null)
            return 0;
        else
            return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvItemReportNumber;
        TextView tvItemReportName;
        TextView tvItemReportQuantity;
        TextView tvItemReportPrice;
        TextView tvItemReportAmount;
        TextView tvItemReportDiscount;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvItemReportAmount = itemView.findViewById(R.id.tvItemReportAmount);
            tvItemReportPrice = itemView.findViewById(R.id.tvItemReportPrice);
            tvItemReportName = itemView.findViewById(R.id.tvItemReportName);
            tvItemReportNumber = itemView.findViewById(R.id.tvItemReportNumber);
            tvItemReportQuantity = itemView.findViewById(R.id.tvItemReportQuantity);
            tvItemReportDiscount = itemView.findViewById(R.id.tvItemReportDiscount);

        }
    }
}
