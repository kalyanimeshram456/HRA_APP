package com.ominfo.crm_solution.ui.receipt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.receipt.model.ReceiptResult;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<ReceiptResult> mListData;
    private Context mContext;
    private String mDate;

    public ReceiptAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ReceiptAdapter(Context context, List<ReceiptResult> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_receipt_table, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<ReceiptResult> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            holder.tvCompanyName.setText(mListData.get(position).getCompanyName());
            holder.tvState.setText(mListData.get(position).getReceiptNumber());
            holder.tvRs.setText("₹"+mListData.get(position).getAmount());
            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.tvRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(0,mListData.get(position));
            }
        });
        holder.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(0,mListData.get(position));
            }
        });
        holder.tvCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvCompanyName , tvRs,tvState;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvRs = itemView.findViewById(R.id.tvRS);
            tvState = itemView.findViewById(R.id.tvState);
          /*  tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
       */ }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,ReceiptResult receiptResult);
    }
}
