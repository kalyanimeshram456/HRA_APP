package com.ominfo.hra_app.ui.employees.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.employees.model.AttendanceEmployeeListData;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceEmployeeAdapter extends RecyclerView.Adapter<AttendanceEmployeeAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<AttendanceEmployeeListData> mListData;
    private Context mContext;
    private String mDate;

    public AttendanceEmployeeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public AttendanceEmployeeAdapter(Context context, List<AttendanceEmployeeListData> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_employee_attendance, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<AttendanceEmployeeListData> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            AttendanceEmployeeListData item = mListData.get(position);
            if(mListData.get(position).getIsLate()!=null && mListData.get(position).getIsLate().equals("1")){
                holder.imgEndTime.setVisibility(View.VISIBLE);
            }else{
                holder.imgEndTime.setVisibility(View.GONE);
            }
            if(mListData.get(position).getIsEarly()!=null && mListData.get(position).getIsEarly().equals("1")){
                holder.imgIndicatorStart.setVisibility(View.VISIBLE);
            }else{
                holder.imgIndicatorStart.setVisibility(View.GONE);
            }
            holder.tvStartTime.setText(AppUtils.convert24to12Attendance(mListData.get(position).getStartTime()));
            holder.tvEndTime.setText(AppUtils.convert24to12Attendance(mListData.get(position).getEndTime()));
            holder.tvInLocation.setText(mListData.get(position).getOfficeStartAddr());
            holder.tvOutLocation.setText(mListData.get(position).getOfficeEndAddr());
            holder.tvDateAtten.setText(AppUtils.convertDobDate(mListData.get(position).getDate()));
        }
        else {
            holder.setIsRecyclable(true);
        }


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvStartTime,tvEndTime,tvInLocation,tvOutLocation,tvDateAtten;
        CircleImageView imgIndicatorStart, imgEndTime;
        ViewHolder(View itemView) {
            super(itemView);
            tvDateAtten = itemView.findViewById(R.id.tvDateAtten);
            imgIndicatorStart = itemView.findViewById(R.id.imgIndicatorStart);
            imgEndTime = itemView.findViewById(R.id.imgEndTime);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            tvInLocation = itemView.findViewById(R.id.tvInLocation);
            tvOutLocation = itemView.findViewById(R.id.tvOutLocation);
           }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData, AttendanceEmployeeListData SalarySheetList, List<AttendanceEmployeeListData>list);
    }
}
