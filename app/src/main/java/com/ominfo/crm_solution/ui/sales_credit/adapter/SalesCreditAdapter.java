package com.ominfo.crm_solution.ui.sales_credit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.sales_credit.activity.View360Activity;
import com.ominfo.crm_solution.util.LogUtil;

import java.util.List;

public class SalesCreditAdapter extends RecyclerView.Adapter<SalesCreditAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DashModel> mListData;
    private Context mContext;
    private String mDate;

    public SalesCreditAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SalesCreditAdapter(Context context, List<DashModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_sales_credit_table, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DashModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            if(mListData.get(position).getValue().equals("quote"))
            {
                holder.tvState.setText("CRM/2021/ARC");
                holder.tvRs.setText("Accepted");
                holder.tvRs.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
            }
            if(mListData.get(position).getValue().equals("dispatch"))
            {
                holder.tvState.setText("PO3048");
                holder.tvRs.setText("Steel");
                holder.tvRs.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
            }
            if(mListData.get(position).getValue().equals("report"))
            {
                holder.tvCompanyName.setText("CRM/2021/HH");
                holder.tvState.setText("Steel private Lmt.");
                holder.tvRs.setText("Quotation Converted");
                holder.tvRs.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
            }
            if(mListData.get(position).getValue().equals("sale"))
            {
                holder.tvCompanyName.setText("Steel private Lmt.");
                holder.tvState.setText("CRM/2021/HH");
                holder.tvRs.setText("22/10/2021");
                holder.tvRs.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
            }

            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        else {
            holder.setIsRecyclable(true);
        }

       /* View view = holder.itemView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "View where A: " + position + " is Clicked", Toast.LENGTH_SHORT).show();
            }
        });*/
        holder.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1);
            }
        });
        holder.tvRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1);
            }
        });
        holder.tvCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(0);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvCompanyName , tvState , tvRs;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvState = itemView.findViewById(R.id.tvState);
            tvRs = itemView.findViewById(R.id.tvRS);

          /*  tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
       */ }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData);
    }
}
