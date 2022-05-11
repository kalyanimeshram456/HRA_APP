package com.ominfo.hra_app.ui.sales_credit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;

import java.util.List;

public class EnquiryPageAdapter extends RecyclerView.Adapter<EnquiryPageAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<EnquiryPagermodel> mListData;
    private Context mContext;

    public EnquiryPageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public EnquiryPageAdapter(Context context, List<EnquiryPagermodel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_pager_enquiry, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<EnquiryPagermodel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    public void updatePageList(int pos){
        mListData.get(pos).setStatus(1);
        for(int i=0;i<mListData.size();i++){
            if(i!=pos){
                mListData.get(i).setStatus(0);
            }
        }
        listItemSelectListener.onItemClick(mListData.get(pos),mListData);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            if(mListData.get(position).getPageNo().length()==1){
                holder.tvPageNo.setText("0"+mListData.get(position).getPageNo());
            }
            else {
                holder.tvPageNo.setText(mListData.get(position).getPageNo());
            }
            if(mListData.get(position).getStatus()==0) {
                holder.tvPageNo.setBackground(null);
                holder.tvPageNo.setTextColor(mContext.getResources().getColor(R.color.color_blue_icon));
            }else {
                holder.tvPageNo.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvPageNo.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_round_corner_2));
            }
            if (position % 2 != 0) {
                //holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                //holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.tvPageNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posTest = Integer.parseInt(holder.tvPageNo.getText().toString().trim())-1;
                holder.tvPageNo.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvPageNo.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_round_corner_2));
                for(int i=0;i<mListData.size();i++){
                    mListData.get(i).setStatus(0);
                }
                mListData.get(posTest).setStatus(1);
                notifyDataSetChanged();
                listItemSelectListener.onItemClick(mListData.get(posTest),mListData);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvPageNo;
        LinearLayoutCompat layClick;

        ViewHolder(View itemView) {
            super(itemView);
            tvPageNo = itemView.findViewById(R.id.tvPageNo);
            layClick = itemView.findViewById(R.id.layClick);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(EnquiryPagermodel mData,List<EnquiryPagermodel> mDataList);
    }
}
