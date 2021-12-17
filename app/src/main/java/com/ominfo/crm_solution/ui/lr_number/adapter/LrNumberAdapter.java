package com.ominfo.crm_solution.ui.lr_number.adapter;

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
import com.ominfo.crm_solution.ui.lr_number.model.GetVehicleListResult;

import java.util.List;

public class LrNumberAdapter extends RecyclerView.Adapter<LrNumberAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<GetVehicleListResult> mListData;
    private Context mContext;
    private String mDate;

    public LrNumberAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public LrNumberAdapter(Context context, List<GetVehicleListResult> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_lr_number, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<GetVehicleListResult> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.tvDate.setText(mListData.get(position).getTransactionDate());
            holder.tvLrNo.setText(mListData.get(position).getNoOfLR());
            holder.tvVehNo.setText(mListData.get(position).getVehicleNo());
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
        AppCompatTextView tvVehNo , tvLrNo , tvDate;
        LinearLayoutCompat layClick;
        AppCompatImageView imgStatus;

        ViewHolder(View itemView) {
            super(itemView);
            tvVehNo = itemView.findViewById(R.id.tvVehNo);
            tvLrNo = itemView.findViewById(R.id.tvLrNo);
            tvDate = itemView.findViewById(R.id.tvDate);
            layClick = itemView.findViewById(R.id.layClick);
            imgStatus = itemView.findViewById(R.id.imgStatus);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(GetVehicleListResult mData);
    }
}
