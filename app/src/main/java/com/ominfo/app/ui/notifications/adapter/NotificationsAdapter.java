package com.ominfo.app.ui.notifications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.app.R;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public NotificationsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public NotificationsAdapter(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_notifications, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DriverHisabModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            if (mListData.get(position).getDriverHisabValue().equals("1")) {
                holder.tvPrice.setText("₹ 1200");
                holder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.tvTitle.setText(R.string.scr_lbl_advance_prapt);
                holder.tvGadiNo.setText(R.string.scr_lbl_branch_nam);
                holder.tvTrip.setText(R.string.scr_lbl_staff_nam);
                holder.tvGadiNoValue.setText("Vashi");
                holder.tvTripValue.setText("Deepak");
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_shape_corners_8_grey));
            }
            if (mListData.get(position).getDriverHisabValue().equals("0")) {
                holder.tvPrice.setText(R.string.scr_lbl_hisab_upload);
                holder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.deep_red));
                holder.tvTitle.setText(R.string.scr_lbl_trip_kharcha_alert);
                holder.tvGadiNo.setText(R.string.scr_lbl_gadi_no);
                holder.tvTrip.setText(R.string.scr_lbl_trip);
                holder.tvGadiNoValue.setText("MH 03 CV 5243");
                holder.tvTripValue.setText("Vashi-Dadar-\nSilvasa-Surat");
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_shape_corners_8_semi_blue));
            }
        }

        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(mListData.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvPrice,tvGadiNo,tvTrip,tvGadiNoValue,tvTripValue,tvTitle;
        LinearLayoutCompat layClick;

        //AppCompatEditText etHisab;


        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTrip = itemView.findViewById(R.id.tvTrip);
            tvGadiNo = itemView.findViewById(R.id.tvGadiNo);
            tvTripValue = itemView.findViewById(R.id.tvTripValue);
            tvGadiNoValue = itemView.findViewById(R.id.tvGadiNoValue);
            layClick = itemView.findViewById(R.id.layClick);
            //tvHisabDekhiye = itemView.findViewById(R.id.tvHisabDekhiye);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData);
    }
}
