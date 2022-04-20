package com.ominfo.crm_solution.alarm.alarmslist;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import static com.ominfo.crm_solution.ui.reminders.ReminderFragment.alarmsListViewModel;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.service.AlarmService;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.ui.reminders.adapter.ReminderAdapter;
import com.ominfo.crm_solution.util.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private ImageView alarmRecurring;
    private TextView alarmRecurringDays;
    private TextView alarmTitle;
    AlarmRecyclerViewAdapter.ListItemSelectListener itemSelectListener;
    Switch alarmStarted;
    AppCompatTextView tvStatus,tvTitle,tvTime;
    FrameLayout layClick;
    AppCompatImageView imgExit;//,imgClose;

    private OnToggleAlarmListener listener;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener, AlarmRecyclerViewAdapter.ListItemSelectListener itemSelectListener) {
        super(itemView);

        layClick = itemView.findViewById(R.id.layClick);
        imgExit = itemView.findViewById(R.id.imgExit);
        tvStatus = itemView.findViewById(R.id.tvStatus);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvTime = itemView.findViewById(R.id.tvTime);
        this.itemSelectListener = itemSelectListener;
        this.listener = listener;
    }

  /*  public static class PopupMenuCustomLayout {
        private ReminderAdapter.PopupMenuCustomLayout.PopupMenuCustomOnClickListener onClickListener;
        private Context context;
        private PopupWindow popupWindow;
        private int rLayoutId;
        private View popupView;

        public PopupMenuCustomLayout(Context context, int rLayoutId, ReminderAdapter.PopupMenuCustomLayout.PopupMenuCustomOnClickListener onClickListener) {
            this.context = context;
            this.onClickListener = onClickListener;
            this.rLayoutId = rLayoutId;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(rLayoutId, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setElevation(10);

            LinearLayout linearLayout = (LinearLayout) popupView.findViewById(R.id.layReminder);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View v = linearLayout.getChildAt(i);
                v.setOnClickListener( v1 -> {
                    onClickListener.onClick(v1.getId());
                    popupWindow.dismiss();
                });
            }
        }
        public void setAnimationStyle( int animationStyle) {
            popupWindow.setAnimationStyle(animationStyle);
        }
        public void show() {
            popupWindow.showAtLocation( popupView, Gravity.CENTER, 0, 0);
        }

        public void show( View anchorView, int gravity, int offsetX, int offsetY) {
            popupWindow.showAsDropDown( anchorView, 0, -2 * (anchorView.getHeight()));
        }

        public interface PopupMenuCustomOnClickListener {
            void onClick(int menuItemId);
        }
    }
*/
    public void bind(Alarm mListData,Context mContext,AlarmRecyclerViewAdapter alarmRecyclerViewAdapter) {
        layClick = itemView.findViewById(R.id.layClick);
        imgExit = itemView.findViewById(R.id.imgExit);
        tvStatus = itemView.findViewById(R.id.tvStatus);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvTime = itemView.findViewById(R.id.tvTime);

        if(mListData!=null) {
            //holder.layStatus.setVisibility(View.GONE);
            setIsRecyclable(false);
            try{tvTitle.setText(mListData.getTitle());}catch (Exception e){e.printStackTrace();}
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListData.setValue("0");
                    //alarmRecyclerViewAdapter.notifyItemChanged(mListData);
                    //alarmRecyclerViewAdapter.notifyDataSetChanged();
                    listener.onToggle(mListData);
                }
            });
            String mTime = AppUtils.dateFormate(mListData.getDate());
            try{tvTime.setText(mTime+" - "+mListData.getTime());}catch (Exception e){e.printStackTrace();}
            try{
                if(mListData.getStatus().equals("COMPLETED")) {
                    tvStatus.setText("Completed");
                    imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_checked));
                }
                else if(mListData.getStatus().equals("CANCELLED")) {
                    tvStatus.setText("Cancelled");
                    imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_x_mark));
                }
                else if(mListData.getStatus().equals("CREATED")) {
                    tvStatus.setText("Update");
                    imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_arrow));
                }
                else if(mListData.getStatus().equals("SNOOZED")) {
                    tvStatus.setText("Snoozed");
                   imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_arrow));
                }
            }catch (Exception e){e.printStackTrace();}
        }
        else {
            setIsRecyclable(true);
        }

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListData.getValue().equals("0")) {
                    //listItemSelectListener.onItemClick(0, v);
                    //popupMenu.setOnMenuItemClickListener(Sample1.this);
                    ReminderAdapter.PopupMenuCustomLayout popupMenu = new ReminderAdapter.PopupMenuCustomLayout(
                            v.getContext(), R.layout.rem_popup,
                            new ReminderAdapter.PopupMenuCustomLayout.PopupMenuCustomOnClickListener() {
                                @Override
                                public void onClick(int itemId) {
                                    switch (itemId) {
                                        case R.id.imgClose:
                                            //LogUtil.printToastMSG(v.getContext(), "Whats delete");
                                            break;
                                        case R.id.tvTaskComple:
                                            mListData.setValue("1");
                                            mListData.setStatus("COMPLETED");
                                            listener.onToggle(mListData);
                                            //alarmsListViewModel.update(mListData);
                                            Intent intentService = new Intent(mContext, AlarmService.class);
                                            mContext.stopService(intentService);
                                            itemSelectListener.onItemClick(mListData,0);
                                            break;
                                        case R.id.tvCancelled:
                                            mListData.setValue("1");
                                            mListData.setStatus("CANCELLED");
                                            listener.onToggle(mListData);
                                            //alarmsListViewModel.update(mListData);
                                            Intent intentServiceCan = new Intent(mContext, AlarmService.class);
                                            mContext.stopService(intentServiceCan);
                                            itemSelectListener.onItemClick(mListData,2);
                                            break;

                                        case R.id.tvRemLater:
                                            //LogUtil.printToastMSG(mContext,"Will remind you after 10 minutes");
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTimeInMillis(mListData.getCreated());//System.currentTimeMillis());
                                            calendar.add(Calendar.MINUTE, Constants.afterAlarm);//10

                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            String mDate = dateFormat.format(calendar.getTime());
                                            SimpleDateFormat dateFormatTime = new SimpleDateFormat("hh:mm a");
                                            String mDateTime = dateFormatTime.format(calendar.getTime());

                                            Alarm alarm = new Alarm(
                                                    new Random().nextInt(Integer.MAX_VALUE),
                                                    calendar.get(Calendar.HOUR_OF_DAY),
                                                    calendar.get(Calendar.MINUTE),
                                                    mListData.getTitle(),
                                                    calendar.getTimeInMillis(),
                                                    true,
                                                    false,
                                                    false,
                                                    false,
                                                    false,
                                                    false,
                                                    false,
                                                    false,
                                                    false,
                                                    mDate,
                                                    mDateTime,
                                                    mListData.getValue(),
                                                    mListData.getRecordId(),
                                                   "SNOOZED"

                                            );

                                            alarm.schedule(mContext);
                                            alarm.setAlarmId(mListData.getAlarmId());
                                            alarmsListViewModel.update(alarm);
                                            Intent intentServiceQ = new Intent(mContext, AlarmService.class);
                                            mContext.stopService(intentServiceQ);
                                            itemSelectListener.onItemClick(alarm,1);
                                            break;
                                    }
                                }
                            });
                    // Method 1: popupMenu.show();
                    // Method 2: via an anchor view:
                    popupMenu.show(v, Gravity.CENTER, 0, 0);
                }
            }
        });
    }
}
