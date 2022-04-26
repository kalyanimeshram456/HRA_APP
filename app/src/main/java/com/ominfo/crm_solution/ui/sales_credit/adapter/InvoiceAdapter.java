package com.ominfo.crm_solution.ui.sales_credit.adapter;

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
import com.ominfo.crm_solution.ui.sales_credit.model.CustomerAllRecord;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<CustomerAllRecord> mListData;
    private Context mContext;
    private String mDate;

    public InvoiceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public InvoiceAdapter(Context context, List<CustomerAllRecord> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_customer_360, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<CustomerAllRecord> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
           // if(mListData.get(position).getDoctype().equals("SALES ORDER")){
                String str = "";
                if(mListData.get(position).getDoctype()!=null) {
                    if (mListData.get(position).getDoctype().equals("ENQUIRY")) {
                        str = "ENQ";
                    } else if (mListData.get(position).getDoctype().equals("QUOTATION")) {
                        str = "QUO";
                    } else if (mListData.get(position).getDoctype().equals("SALES ORDER")) {
                        str = "SO";
                    }
                }
                holder.tvDocType.setText(str);
                holder.tvInvoiceNum.setText(mListData.get(position).getDocdate());
                holder.tvDate.setText(mListData.get(position).getDocnumber());
                holder.tvQuotedNo.setText(""+mListData.get(position).getAmount());
               // holder.tvBilledAmt.setText(mListData.get(position).getDocstatus());
           // }
            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
            try{
                String mStatus = mListData.get(position).getDocstatus();
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
        }
        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(mListData.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvDocType ,tvDate, tvInvoiceNum,tvQuotedNo;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;
        CircleImageView imgIndicator;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvInvoiceNum = itemView.findViewById(R.id.tvInvoiceNum);
            tvDocType = itemView.findViewById(R.id.tvDocType);
            tvQuotedNo = itemView.findViewById(R.id.tvQuotedNo);
            imgIndicator = itemView.findViewById(R.id.imgIndicator);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(CustomerAllRecord mData);
    }
}
