package com.ominfo.staff.ui.lr_number.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.staff.R;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsLrImage;
import com.ominfo.staff.util.AppUtils;

import java.util.List;

public class LrDetailsAdapter extends RecyclerView.Adapter<LrDetailsAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<VehicleDetailsLrImage> mListData;
    private Context mContext;
    private String mDate;

    public LrDetailsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public LrDetailsAdapter(Context context, List<VehicleDetailsLrImage> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_lr_details
                , parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<VehicleDetailsLrImage> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.etNoOfLr.setText(mListData.get(position).getLr());
            AppUtils.loadImageURL(mContext,mListData.get(position).getImage(),holder.imgShow,null);
             }

       /* holder.mActivityName.setOnClickListener(new View.OnClickListener() {
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
        AppCompatEditText etNoOfLr ;
        AppCompatImageView imgShow;


        ViewHolder(View itemView) {
            super(itemView);
            etNoOfLr = itemView.findViewById(R.id.etNoOfLr);
            imgShow = itemView.findViewById(R.id.imgShow);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData);
    }
}
