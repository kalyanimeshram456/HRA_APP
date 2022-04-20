package com.ominfo.crm_solution.alarm.alarmslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.ui.dashboard.adapter.TripAwadhiAdapter;
import com.ominfo.crm_solution.ui.driver_hisab.model.DriverHisabModel;

import java.util.ArrayList;
import java.util.List;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarms;
    private OnToggleAlarmListener listener;
    private ListItemSelectListener listItemSelectListener;
    AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private Context context;

    public AlarmRecyclerViewAdapter(OnToggleAlarmListener listener, Context context,AlarmRecyclerViewAdapter alarmRecyclerViewAdapter,
                                    ListItemSelectListener listItemSelectListener) {
        this.alarms = new ArrayList<Alarm>();
        this.listener = listener;
        this.context = context;
        this.alarmRecyclerViewAdapter = alarmRecyclerViewAdapter;
        this.listItemSelectListener = listItemSelectListener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reminder, parent, false);
        return new AlarmViewHolder(itemView, listener,listItemSelectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm,context,alarmRecyclerViewAdapter);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmStarted.setOnCheckedChangeListener(null);
    }

    public interface ListItemSelectListener {
        void onItemClick(Alarm alarm,int checkStatus);
    }
}

