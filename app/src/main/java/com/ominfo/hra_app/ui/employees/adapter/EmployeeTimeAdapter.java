package com.ominfo.hra_app.ui.employees.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.my_account.model.WorkTimingList;
import com.ominfo.hra_app.util.AppUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeTimeAdapter extends RecyclerView.Adapter<EmployeeTimeAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<WorkTimingList> mListData;
    private Context mContext;
    private String mDate;
    final Calendar myCalendar = Calendar.getInstance();
    boolean isShowToggle = false,account = false;

    public EmployeeTimeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public EmployeeTimeAdapter(boolean account,boolean isShowToggle,Context context, List<WorkTimingList> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
        this.isShowToggle = isShowToggle; this.account = account;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_employee_time_schedule, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<WorkTimingList> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            if(mListData.get(position).getMonStartTime()==null){
                holder.tvTimeValueFrom.setText(AppUtils.convert24to12Attendance("10:00:00"));
            }
            if(mListData.get(position).getMonEndTime()==null){
                holder.tvTimeValueTo.setText(AppUtils.convert24to12Attendance("19:00:00"));
            }
            if(mListData.get(position).getMonWorking().toLowerCase().equals("yes")){
                holder.switchDay.setChecked(true);
            }
            if(mListData.get(position).getMonWorking().toLowerCase().equals("no")){
                holder.switchDay.setChecked(false);
            }
            holder.tvDayType.setText(mListData.get(position).getMonDay());
            try {
                holder.tvTimeValueFrom.setText(AppUtils.convert24to12Attendance(mListData.get(position).getMonStartTime()));
                holder.tvTimeValueTo.setText(AppUtils.convert24to12Attendance(mListData.get(position).getMonEndTime()));
            }catch (Exception e){}
            if(isShowToggle){
                holder.switchDay.setVisibility(View.VISIBLE);
                holder.layFromTime.setEnabled(true);
                holder.layTime.setEnabled(true);
            }else{
                holder.switchDay.setVisibility(View.INVISIBLE);
                holder.layFromTime.setEnabled(false);
                holder.layTime.setEnabled(false);
            }
            if(account){ //true
                holder.switchDay.setEnabled(true);
                holder.layFromTime.setEnabled(true);
                holder.layTime.setEnabled(true);
            }else{
                holder.switchDay.setEnabled(false);
                holder.layFromTime.setEnabled(false);
                holder.layTime.setEnabled(false);
            }
            holder.switchDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        mListData.get(position).setMonWorking("yes");
                    }
                    else{
                        mListData.get(position).setMonWorking("no");
                    }
                    listItemSelectListener.onItemClick(mListData.get(position), mListData,true);
                }
            });
            holder.layFromTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenTimePicker(0,holder.tvTimeValueFrom,position);
                }
            });
            holder.layTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenTimePicker(1,holder.tvTimeValueTo,position);
                }
            });
           //listItemSelectListener.onItemClick(temp, mListData,true);
        }
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTimeValueFrom , tvTimeValueTo,tvDayType ;
        LinearLayoutCompat layClick,layPopup,imgPopup,layCross;
        AppCompatImageView imgFromTime,imgToTime;
        RelativeLayout layFromTime,layTime;
        SwitchCompat switchDay;

        ViewHolder(View itemView) {
            super(itemView);
            switchDay = itemView.findViewById(R.id.switchDay);
            layFromTime = itemView.findViewById(R.id.layFromTime);
            layTime = itemView.findViewById(R.id.layTime);
            tvDayType = itemView.findViewById(R.id.tvDayType);
            tvTimeValueFrom = itemView.findViewById(R.id.tvTimeValueFrom);
            tvTimeValueTo = itemView.findViewById(R.id.tvTimeValue);
            imgFromTime = itemView.findViewById(R.id.imgFromTime);
            imgToTime = itemView.findViewById(R.id.imgTime);
        }
    }

    public void OpenTimePicker(int val,AppCompatTextView appCompatTextView,int pos){
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm = "";
                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                myCalendar.set(Calendar.MINUTE, selectedMinute);
                if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "am";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "pm";
                String strHrsToShow = (String.valueOf(myCalendar.get(Calendar.HOUR)).length() == 1) ? "0"+myCalendar.get(Calendar.HOUR) : myCalendar.get(Calendar.HOUR) + "";
                //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                //String min = convertDate(myCalendar.get(Calendar.MINUTE));
                boolean isPM = (selectedHour >= 12);
                appCompatTextView.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, myCalendar.get(Calendar.MINUTE), isPM ? "pm" : "am"));
                String time = AppUtils.convert12to24ForAttention(appCompatTextView.getText().toString());
                if(val==0) {
                    mListData.get(pos).setMonStartTime(time);
                }else{
                    mListData.get(pos).setMonEndTime(time);
                }
                listItemSelectListener.onItemClick(mListData.get(pos), mListData,true);
                // AutoComTextViewTime.setText(strHrsToShow + ":" + min + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public interface ListItemSelectListener {
        void onItemClick(WorkTimingList mData,List<WorkTimingList> WorkTimingLists,boolean status);
    }
}
