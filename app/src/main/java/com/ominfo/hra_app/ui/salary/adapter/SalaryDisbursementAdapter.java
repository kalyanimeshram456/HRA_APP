package com.ominfo.hra_app.ui.salary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;

import java.util.List;

public class SalaryDisbursementAdapter extends RecyclerView.Adapter<SalaryDisbursementAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<EmployeeList> mListData;
    private Context mContext;
    private String mDate;

    public SalaryDisbursementAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SalaryDisbursementAdapter(Context context, List<EmployeeList> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_salary_disbursment, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<EmployeeList> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            //holder.layStatus.setVisibility(View.GONE);
            holder.setIsRecyclable(false);
           /* holder.tvName.setText(mListData.get(position).getDocNo()+" ("+mListData.get(position).getType()+")");
            holder.tvCity.setText(mListData.get(position).getCityName());*/
        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.cardClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(0, mListData.get(position));
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(1, mListData.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvName,tvCity;
        LinearLayoutCompat layClick;
        AppCompatImageView imgEdit,imgClose;
        CardView cardClick;

        ViewHolder(View itemView) {
            super(itemView);
            imgEdit = itemView.findViewById(R.id.imgEdit);
           // tvCity = itemView.findViewById(R.id.tvCity);
            cardClick = itemView.findViewById(R.id.cardClick);
          }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData, EmployeeList searchresult);
    }
}
