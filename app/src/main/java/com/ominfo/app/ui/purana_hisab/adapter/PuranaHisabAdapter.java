package com.ominfo.app.ui.purana_hisab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.app.R;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;

import java.util.List;

public class PuranaHisabAdapter extends RecyclerView.Adapter<PuranaHisabAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public PuranaHisabAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public PuranaHisabAdapter(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_purana_hisab, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DriverHisabModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            if (mListData.get(position).getDriverHisabValue().equals("1")) {
                holder.tvHideStatus.setVisibility(View.VISIBLE);
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.tvStatus.setText("1500");
            }
            if (mListData.get(position).getDriverHisabValue().equals("0")) {
                holder.tvHideStatus.setVisibility(View.GONE);
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.deep_red));
                holder.tvStatus.setText("Pending");
            }
        }

        holder.tvHisabDekhiye.setOnClickListener(new View.OnClickListener() {
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
        AppCompatTextView tvHisabDekhiye ,tvHideStatus,tvStatus;
        LinearLayoutCompat layClick;

        //AppCompatEditText etHisab;


        ViewHolder(View itemView) {
            super(itemView);
            tvHideStatus = itemView.findViewById(R.id.tvHideStatus);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            layClick = itemView.findViewById(R.id.layClick);
            tvHisabDekhiye = itemView.findViewById(R.id.tvHisabDekhiye);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData);
    }
}
