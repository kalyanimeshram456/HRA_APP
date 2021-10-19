package com.ominfo.staff.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.staff.R;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;

import java.util.List;

public class CallManagerAdapter extends RecyclerView.Adapter<CallManagerAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public CallManagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CallManagerAdapter(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_call_manager, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DriverHisabModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.tvCallName.setHint(mListData.get(position).getDriverHisabTitle());
            if(position==mListData.size()-1){
                holder.viewCaller.setVisibility(View.GONE);
            }
            else {  holder.viewCaller.setVisibility(View.VISIBLE); }
        }

        holder.layCaller1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listItemSelectListener.onItemClick(holder.tvCaller1.getText().toString().trim());
                }catch (Exception e){}
            }
        });

        holder.layCaller2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listItemSelectListener.onItemClick(holder.tvCaller2.getText().toString().trim());
                }catch (Exception e){}
            }
        });


    }

    public void removeItem(int position) {
        mListData.remove(position);
        notifyItemRemoved(position);
    }

    /*public void restoreItem(String item, int position) {
        mListData.add(position, item);
        notifyItemInserted(position);
    }*/

    public List<DriverHisabModel> getData() {
        return mListData;
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvCallName,tvCaller1,tvCaller2;
        RelativeLayout layCaller1,layCaller2;
        View viewCaller;

        ViewHolder(View itemView) {
            super(itemView);
            tvCallName = itemView.findViewById(R.id.tvCallName);
            layCaller1 = itemView.findViewById(R.id.layCaller1);
            layCaller2 = itemView.findViewById(R.id.layCaller2);
            tvCaller1 = itemView.findViewById(R.id.tvCaller1);
            tvCaller2 = itemView.findViewById(R.id.tvCaller2);
            viewCaller = itemView.findViewById(R.id.viewCaller);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(String mData);
    }
}
