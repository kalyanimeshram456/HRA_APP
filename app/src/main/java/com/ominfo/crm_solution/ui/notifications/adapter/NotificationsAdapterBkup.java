package com.ominfo.crm_solution.ui.notifications.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.driver_hisab.model.DriverHisabModel;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapterBkup extends RecyclerView.Adapter<NotificationsAdapterBkup.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DriverHisabModel> mListData;
    private Context mContext;
    private String mDate;

    public NotificationsAdapterBkup(Context mContext) {
        this.mContext = mContext;
    }

    public NotificationsAdapterBkup(Context context, List<DriverHisabModel> listData, ListItemSelectListener itemClickListener) {
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
            int[] colors = new int[] {R.color.color_blue_10,R.color.blue_graph,R.color.deep_red};
            //the choose from the integer array randomly
            int randomColor = colors[new Random().nextInt(colors.length)];
            //finally set the color of the ImageView as follows
            holder.imgNotify.setImageDrawable(new ColorDrawable(randomColor));

        }

       /* holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listItemSelectListener.onItemClick(mListData.get(position));
                }catch (Exception e){}
            }
        });
*/

    }

    public void removeItem(int position) {
        mListData.remove(position);
        notifyItemRemoved(position);
    }

    /*public void restoreItem(String item, int position) {
        mListData.add(position, item);
        notifyItemInserted(position);
    }*/

    public List<DriverHisabModel> getData() {
        return mListData;
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //AppCompatTextView tvPrice,tvGadiNo,tvTrip,tvGadiNoValue,tvTripValue,tvTitle;
        //LinearLayoutCompat layClick;
        CircleImageView imgNotify;


        ViewHolder(View itemView) {
            super(itemView);
            imgNotify = itemView.findViewById(R.id.imgNotify);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DriverHisabModel mData);
    }
}
