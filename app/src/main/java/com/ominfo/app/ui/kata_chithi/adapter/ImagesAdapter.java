package com.ominfo.app.ui.kata_chithi.adapter;

import static com.ominfo.app.util.AppUtils.getBitmapFromView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.app.R;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.app.util.AppUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public ImagesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ImagesAdapter(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_images_kata_chithi, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DriverHisabModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            //File imgFile = new File(mListData.get(position).getDriverHisabTitle());
            if(mListData.get(position).getImgBitmap()!=null) {
                holder.mProgressBar.setVisibility(View.GONE);
                holder.imgShow.setImageBitmap(mListData.get(position).getImgBitmap());
            }else {
                AppUtils.loadImage(mContext, mListData.get(position).getDriverHisabTitle(), holder.imgShow, holder.mProgressBar);
            }
        }

        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmapFromView(holder.imgShow);
                listItemSelectListener.onItemClick(mListData.get(position),bitmap);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgShow;
        RelativeLayout layClick;
        ProgressBar mProgressBar;

        ViewHolder(View itemView) {
            super(itemView);
            imgShow = itemView.findViewById(R.id.imgShow);
            layClick = itemView.findViewById(R.id.layClick);
            mProgressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData,Bitmap bitmap);
    }
}
