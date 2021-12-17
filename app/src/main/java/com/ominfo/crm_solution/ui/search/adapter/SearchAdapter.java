package com.ominfo.crm_solution.ui.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DashModel> mListData;
    private Context mContext;
    private String mDate;

    public SearchAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SearchAdapter(Context context, List<DashModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_search, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DashModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            //holder.layStatus.setVisibility(View.GONE);
            holder.setIsRecyclable(false);
            holder.tvName.setText(mListData.get(position).getTitle());
            holder.tvCity.setText(mListData.get(position).getValue());
        }
        else {
            holder.setIsRecyclable(true);
        }

       /* holder.imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.layStatus.setVisibility(View.VISIBLE);
            }
        });
        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layStatus.setVisibility(View.GONE);
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvName,tvCity;
        FrameLayout layClick;
        AppCompatImageView imgExit,imgClose;
        FrameLayout layStatus;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCity = itemView.findViewById(R.id.tvCity);
           /* layClick = itemView.findViewById(R.id.layClick);
            imgExit = itemView.findViewById(R.id.imgExit);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            layStatus = itemView.findViewById(R.id.layStatus);
            imgClose = itemView.findViewById(R.id.imgClose);*/
          /*  tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
       */ }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData);
    }
}
