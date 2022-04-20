package com.ominfo.crm_solution.ui.sales_credit.adapter;

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
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiry;
import com.ominfo.crm_solution.ui.sales_credit.model.SalesCreditReport;

import java.util.List;

public class SalesCreditReportAdapter extends RecyclerView.Adapter<SalesCreditReportAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<SalesCreditReport> mListData;
    private Context mContext;

    public SalesCreditReportAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SalesCreditReportAdapter(Context context, List<SalesCreditReport> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_sales_credit_report, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<SalesCreditReport> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            SalesCreditReport mData = mListData.get(position);
            holder.tvCompanyName.setText(mData.getCustomerName());
            holder.tvLimit.setText(mData.getCreditLimit());
            holder.tvBal.setText(mData.getBalance().toString());
            holder.tvOverdue.setText(mData.getOverdue().toString());
            holder.tvOverdue.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));

            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        else {
            holder.setIsRecyclable(true);
        }

       /* holder.tvEnquiryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
        holder.tvEnquiryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });*/
        holder.tvCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(0,mListData.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvCompanyName , tvLimit , tvOverdue,tvBal;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvBal = itemView.findViewById(R.id.tvBal);
            tvOverdue = itemView.findViewById(R.id.tvOverdue);
            tvLimit = itemView.findViewById(R.id.tvLimit);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,SalesCreditReport salesCreditReport);
    }
}
