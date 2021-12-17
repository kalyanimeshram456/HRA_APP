package com.ominfo.crm_solution.ui.sales_credit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.sales_credit.model.GraphModel;

import java.util.List;

public class SalesGraphAdapter extends RecyclerView.Adapter<SalesGraphAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<GraphModel> mListData;
    private Context mContext;
    private String mDate;

    public SalesGraphAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SalesGraphAdapter(Context context, List<GraphModel> listData, ListItemSelectListener itemClickListener) {
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

    public void updateList(List<GraphModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            try {holder.tvCompany.setText(mListData.get(position).getxValue()); }catch (Exception e){e.printStackTrace();}
            try { holder.tvState.setText(mListData.get(position).getTitle()); }catch (Exception e){e.printStackTrace();}
            try {
                holder.tvRs.setText("₹" + mListData.get(position).getyValue());
            }catch (Exception e){e.printStackTrace();}
            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        /*holder.layClick.setOnClickListener(new View.OnClickListener() {
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
        AppCompatTextView tvCompany , tvRs,tvState;
        RelativeLayout layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvCompany = itemView.findViewById(R.id.tvCompanyName);
            tvRs = itemView.findViewById(R.id.tvRS);
            tvState = itemView.findViewById(R.id.tvState);
            //imgDash = itemView.findViewById(R.id.imgDash);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DashModel mData);
    }
}
