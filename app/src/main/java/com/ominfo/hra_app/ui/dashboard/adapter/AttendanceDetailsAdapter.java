package com.ominfo.hra_app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.dashboard.model.AttendanceDetailsData;
import com.ominfo.hra_app.ui.dashboard.model.BirthDayDobdatum;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceDetailsAdapter extends RecyclerView.Adapter<AttendanceDetailsAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<AttendanceDetailsData> mListData;
    private Context mContext;
    private String mDate;

    public AttendanceDetailsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public AttendanceDetailsAdapter(Context context, List<AttendanceDetailsData> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_attendance_details, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<AttendanceDetailsData> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            AppUtils.loadImageURL(mContext,mListData.get(position).getProfilePic(),
                    holder.imgBirthPro,  holder.progress_barBirth);
            holder.tvEmpName.setText(mListData.get(position).getEmpName());
            holder.tvDesi.setText(mListData.get(position).getEmpPosition());
            if(mListData.get(position).getLeaveType()==null || mListData.get(position).getLeaveType().equals("")) {
                holder.tvInTime.setText("In Time : " + AppUtils.convert24to12Attendance(mListData.get(position).getStartTime()));
                holder.tvOutTime.setText("Out Time : " + AppUtils.convert24to12Attendance(mListData.get(position).getEndTime()));
                if(mListData.get(position).getIs_early()!=null && (mListData.get(position).getIs_early().equals("1") || mListData.get(position).getIs_early().equals("1"))){
                    holder.imgIndicator.setVisibility(View.VISIBLE);
                }else{ holder.imgIndicator.setVisibility(View.GONE);}
            }else{
                holder.tvInTime.setText(mListData.get(position).getLeaveType());
                holder.tvOutTime.setVisibility(View.GONE);
                holder.imgIndicator.setVisibility(View.GONE);
            }
        }

    }

    private void moveFromFragment(Fragment fragment){
        FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framecontainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvEmpName,tvInTime, tvOutTime;
        AppCompatTextView tvDesi;
        AppCompatTextView tvLeaveBirthValue;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;
        CircleImageView imgBirthPro,imgIndicator;
        ProgressBar progress_barBirth;

        ViewHolder(View itemView) {
            super(itemView);
            tvEmpName = itemView.findViewById(R.id.tvEmpName);
            tvInTime = itemView.findViewById(R.id.tvInTime);
            tvOutTime = itemView.findViewById(R.id.tvOutTime);
            tvDesi = itemView.findViewById(R.id.tvDesi);
            imgBirthPro = itemView.findViewById(R.id.imgBirthPro);
            progress_barBirth = itemView.findViewById(R.id.progress_barBirth);

            imgIndicator = itemView.findViewById(R.id.imgIndicator);
            //tvLeaveBirthValue = itemView.findViewById(R.id.tvLeaveBirthValue);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(AttendanceDetailsData mData);
    }
}
