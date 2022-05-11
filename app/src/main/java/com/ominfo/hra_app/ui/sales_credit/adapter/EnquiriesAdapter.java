package com.ominfo.hra_app.ui.sales_credit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.sales_credit.model.CustomerAllRecord;

import java.util.List;

public class EnquiriesAdapter extends RecyclerView.Adapter<EnquiriesAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<CustomerAllRecord> mListData;
    private Context mContext;
    private String mDate;

    public EnquiriesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public EnquiriesAdapter(Context context, List<CustomerAllRecord> listData, ListItemSelectListener itemClickListener) {
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

    public void updateList(List<CustomerAllRecord> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            if(mListData.get(position).getDoctype().equals("ENQUIRY")) {
                holder.tvCompanyName.setText(mListData.get(position).getDocdate());
                holder.tvState.setText(mListData.get(position).getDocnumber());
                holder.tvRS.setText(mListData.get(position).getDocstatus());
                }
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
        AppCompatTextView tvCompanyName , tvState,tvRS;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClickNew);
            tvRS = itemView.findViewById(R.id.tvRS);
            tvState = itemView.findViewById(R.id.tvState);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
          }
    }

    public interface ListItemSelectListener {
        void onItemClick(CustomerAllRecord mData);
    }
}
