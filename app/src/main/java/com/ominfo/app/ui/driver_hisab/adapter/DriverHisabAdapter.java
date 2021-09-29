package com.ominfo.app.ui.driver_hisab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.app.R;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DriverHisabAdapter extends RecyclerView.Adapter<DriverHisabAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public DriverHisabAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public DriverHisabAdapter(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_driver_hisab, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DriverHisabModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.tvHisabName.setText(mListData.get(position).getDriverHisabTitle());
            holder.etHisab.setText(mListData.get(position).getDriverHisabValue());
             }

        /*holder.mActivityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(mListData.get(position));
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvHisabName ;
        AppCompatEditText etHisab;


        ViewHolder(View itemView) {
            super(itemView);
            tvHisabName = itemView.findViewById(R.id.tvHisabName);
            etHisab = itemView.findViewById(R.id.etHisab);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData);
    }
}
