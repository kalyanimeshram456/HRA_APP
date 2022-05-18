package com.ominfo.hra_app.ui.employees.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.notifications.model.NotificationResult;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeTimeAdapter extends RecyclerView.Adapter<EmployeeTimeAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<NotificationResult> mListData;
    private Context mContext;
    private String mDate;
    final Calendar myCalendar = Calendar.getInstance();

    public EmployeeTimeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public EmployeeTimeAdapter(Context context, List<NotificationResult> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_employee_time_schedule, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<NotificationResult> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.imgFromTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenTimePicker(holder.tvTimeValueFrom);
                }
            });
            holder.imgToTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenTimePicker(holder.tvTimeValueTo);
                }
            });
            holder.tvTimeValueFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenTimePicker(holder.tvTimeValueFrom);
                }
            });
            holder.tvTimeValueTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenTimePicker(holder.tvTimeValueTo);
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
        AppCompatTextView tvTimeValueFrom , tvTimeValueTo ;
        LinearLayoutCompat layClick,layPopup,imgPopup,layCross;
        AppCompatImageView imgFromTime,imgToTime;
        CircleImageView imgNotify;
        View viewColour;

        ViewHolder(View itemView) {
            super(itemView);
            tvTimeValueFrom = itemView.findViewById(R.id.tvTimeValueFrom);
            tvTimeValueTo = itemView.findViewById(R.id.tvTimeValue);
            imgFromTime = itemView.findViewById(R.id.imgFromTime);
            imgToTime = itemView.findViewById(R.id.imgTime);
        }
    }

    public void OpenTimePicker(AppCompatTextView appCompatTextView){
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

                // AutoComTextViewTime.setText(strHrsToShow + ":" + min + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public interface ListItemSelectListener {
        void onItemClick(NotificationResult mData,List<NotificationResult> notificationResults,boolean status);
    }
}
