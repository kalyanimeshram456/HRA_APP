package com.ominfo.crm_solution.ui.enquiry_report.adapter;

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
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiry;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiryResult;

import java.util.List;

public class EnquiryReportAdapter extends RecyclerView.Adapter<EnquiryReportAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<GetEnquiry> mListData;
    private Context mContext;

    public EnquiryReportAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public EnquiryReportAdapter(Context context, List<GetEnquiry> listData, ListItemSelectListener itemClickListener) {
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

    public void updateList(List<GetEnquiry> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            GetEnquiry mData = mListData.get(position);
            holder.tvCompanyName.setText(mData.getCustName());
            holder.tvEnquiryStatus.setText(mData.getStatus());
            holder.tvEnquiryNo.setText(mData.getEnquiryNo());
            holder.tvEnquiryStatus.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));

            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.tvEnquiryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
        holder.tvEnquiryNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
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
        AppCompatTextView tvEnquiryNo , tvCompanyName , tvEnquiryStatus;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClickNew);
            tvEnquiryNo = itemView.findViewById(R.id.tvCompanyName);
            tvCompanyName = itemView.findViewById(R.id.tvState);
            tvEnquiryStatus = itemView.findViewById(R.id.tvRS);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,GetEnquiry getEnquiry);
    }
}
