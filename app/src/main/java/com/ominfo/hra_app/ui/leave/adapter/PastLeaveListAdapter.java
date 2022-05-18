package com.ominfo.hra_app.ui.leave.adapter;

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
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.leave.model.PastLeave;
import com.ominfo.hra_app.ui.my_account.model.ApplicationLeave;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PastLeaveListAdapter extends RecyclerView.Adapter<PastLeaveListAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<PastLeave> mListData;
    private Context mContext;
    private String mDate;

    public PastLeaveListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public PastLeaveListAdapter(Context context, List<PastLeave> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_sale_table, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<PastLeave> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
                holder.setIsRecyclable(false);
                String start = "00:00:00" ,end = "00:00:00";
                try{
                    start = AppUtils.convertyyyytodd(mListData.get(position).getStartTime());
                    end = AppUtils.convertyyyytodd(mListData.get(position).getEndTime());
                }catch (Exception e){
                }
                holder.tvCompanyName.setText(start+"-"+end);
                holder.tvState.setText(mListData.get(position).getLeaveType());
                holder.tvRs.setText(mListData.get(position).getStatus());
                holder.tvRs.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
                holder.imgIndicator.setVisibility(View.GONE);
               /* if(mListData.get(position).getPaymentStatus().equals("PAID"))
                {
                    holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.Light_Green_quo));
                }
                else  if(mListData.get(position).getPaymentStatus().equals("UNPAID") ||
                        mListData.get(position).getPaymentStatus().equals("PART PAID")){
                    holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.Light_Yellow));
                }
                else {
                    holder.imgIndicator.setColorFilter(mContext.getResources().getColor(R.color.Light_Red));
                }*/

                if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }
        }
        else {
            holder.setIsRecyclable(true);
        }

       /* View view = holder.itemView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "View where A: " + position + " is Clicked", Toast.LENGTH_SHORT).show();
            }
        });*/
        holder.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
        holder.tvRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1,mListData.get(position));
            }
        });
        holder.tvCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(0,mListData.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvCompanyName , tvState , tvRs;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;
        CircleImageView imgIndicator;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvState = itemView.findViewById(R.id.tvState);
            tvRs = itemView.findViewById(R.id.tvRS);
            imgIndicator = itemView.findViewById(R.id.imgIndicator);
          /*  tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
       */ }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,PastLeave applicationLeave);
    }
}
