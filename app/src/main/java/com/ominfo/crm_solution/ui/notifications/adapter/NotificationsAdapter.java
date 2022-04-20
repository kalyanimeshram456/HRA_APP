package com.ominfo.crm_solution.ui.notifications.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.notifications.model.NotificationResult;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<NotificationResult> mListData;
    private Context mContext;
    private String mDate;

    public NotificationsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public NotificationsAdapter(Context context, List<NotificationResult> listData, ListItemSelectListener itemClickListener) {
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

    public void updateList(List<NotificationResult> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            int[] colors = new int[] {R.color.color_blue_10,R.color.blue_graph,R.color.deep_red};
                //the choose from the integer array randomly
                int randomColor = colors[new Random().nextInt(colors.length)];
                //finally set the color of the ImageView as follows
                holder.imgNotify.setImageDrawable(new ColorDrawable(randomColor));

            holder.tvTitle.setText(mListData.get(position).getHeading());
            holder.tvDescription.setText(mListData.get(position).getText());
        }

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NotificationResult temp = mListData.get(position);
                    mListData.remove(position);
                    notifyItemRemoved(position);
                    listItemSelectListener.onItemClick(temp, mListData,true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(null, mListData,false);
            }
        });
        holder.tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(null, mListData,false);
            }
        });
        holder.imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(null, mListData,false);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTitle,tvDescription;
        LinearLayoutCompat layClick,layPopup,imgPopup;
        AppCompatImageView imgDelete;
        CircleImageView imgNotify;

        ViewHolder(View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            layPopup = itemView.findViewById(R.id.layPopup);
            imgPopup = itemView.findViewById(R.id.imgPopup);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            layClick = itemView.findViewById(R.id.layClick);
            imgNotify = itemView.findViewById(R.id.imgNotify);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(NotificationResult mData,List<NotificationResult> notificationResults,boolean status);
    }
}
