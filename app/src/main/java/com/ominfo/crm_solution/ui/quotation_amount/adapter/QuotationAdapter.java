package com.ominfo.crm_solution.ui.quotation_amount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.quotation_amount.model.QuotationData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<QuotationData> mListData;
    private Context mContext;
    private String mDate;

    public QuotationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public QuotationAdapter(Context context, List<QuotationData> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_quotation_table, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<QuotationData> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
             holder.tvCompanyName.setText(mListData.get(position).getOrderNo());
                holder.tvState.setText(mListData.get(position).getCustName());
                holder.tvRs.setText(mContext.getResources().getString(R.string.scr_lbl_rs)+mListData.get(position).getTotalCharge().toString());
                holder.tvRs.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
                try{
                    String mStatus = mListData.get(position).getOrderStatus();
                    if(mStatus.equals("INCVOICE DELIVERED")||mStatus.equals("FINALIZATION")
                            ||mStatus.equals("CLOSED")) {
                        holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.Light_Green_quo));
                    }
                    if(mStatus.equals("INCVOICE DELIVERED") || mStatus.equals("TENDER")|| mStatus.equals("BUDEGTING")
                            || mStatus.equals("NEGOTIATION")|| mStatus.equals("QUOTATION SENT") || mStatus.equals("PI SENT")
                            || mStatus.equals("PENDING FOR BILLING")|| mStatus.equals("SO CREATED")
                            || mStatus.equals("INVOICE CREATED")|| mStatus.equals("SO ON HOLD")
                            || mStatus.equals("APPROVAL PENDING")|| mStatus.equals("PART INVOICE CREATED")
                            || mStatus.equals("INVOICE DISPATCHED")) {
                        holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.Light_Yellow));
                    }
                    if(mStatus.equals("QUOTATION REJECTED")||mStatus.equals("SO REJECTED BY ADMIN")
                            ||mStatus.equals("SO REJECTED AT FACTORY") || mStatus.equals("ORDER LOST")
                            || mStatus.equals("SO REJECTED BY FACTORY")
                    ) {
                    holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.Light_Red));
                    }
                    if(mStatus.equals("QUOTATION CANCELLED")|| mStatus.equals("SO CANCELLED")) {
                        holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.pro_grey));
                    }
                }catch (Exception e){e.printStackTrace();}
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
                listItemSelectListener.onItemClick(0,mListData.get(position));
            }
        });
        holder.tvRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                //listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
        holder.tvCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
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
        CircleImageView imgIndicator;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvState = itemView.findViewById(R.id.tvState);
            tvRs = itemView.findViewById(R.id.tvRS);
            imgIndicator = itemView.findViewById(R.id.imgIndicator);
          /*  tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
       */ }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,QuotationData quotationData);
    }
}
