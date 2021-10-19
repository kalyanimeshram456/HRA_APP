package com.ominfo.staff.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.staff.R;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;

import java.util.List;

public class TripAwadhiAdapter extends RecyclerView.Adapter<TripAwadhiAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public TripAwadhiAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TripAwadhiAdapter(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_trip_awadhi_textview, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DriverHisabModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            if(!mListData.get(position).getDriverHisabTitle().equals("")) {
                holder.AutoComTextView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_corner_shape_without_fill_thin_blue));
                holder.AutoComTextView.setText(mListData.get(position).getDriverHisabTitle());
            }
            holder.AutoComTextView.setHint(mListData.get(position).getDriverHisabValue());
        }

      /*  holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listItemSelectListener.onItemClick(mListData.get(position));
                }catch (Exception e){}
            }
        });*/


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
        AppCompatAutoCompleteTextView AutoComTextView;
        //LinearLayoutCompat layClick;

        ViewHolder(View itemView) {
            super(itemView);
            AutoComTextView = itemView.findViewById(R.id.AutoComTextView);
            //layClick = itemView.findViewById(R.id.layClick);
            //tvHisabDekhiye = itemView.findViewById(R.id.tvHisabDekhiye);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData);
    }
}
