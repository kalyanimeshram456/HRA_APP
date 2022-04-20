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
        View listItem = layoutInflater.inflate(R.layout.row_sales_credit_invoice_table, parent, false);

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
                holder.tvDate.setText(mListData.get(position).getDocdate());
                holder.tvInvoiceNum.setText(mListData.get(position).getDocnumber());
                holder.tvQuotedNo.setText(""+mListData.get(position).getAmount());
                holder.tvBilledAmt.setText(mListData.get(position).getDocstatus());
           // }
            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
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
        AppCompatTextView tvDocType ,tvDate, tvInvoiceNum,tvQuotedNo,tvBilledAmt;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvInvoiceNum = itemView.findViewById(R.id.tvInvoiceNum);
            tvDocType = itemView.findViewById(R.id.tvDocType);
            tvQuotedNo = itemView.findViewById(R.id.tvQuotedNo);
            tvBilledAmt = itemView.findViewById(R.id.tvBilledAmt);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(CustomerAllRecord mData);
    }
}
