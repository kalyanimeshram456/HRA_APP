package com.ominfo.hra_app.ui.leave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.employees.model.Searchresult;

import java.util.List;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<Searchresult> mListData;
    private Context mContext;
    private String mDate;

    public LeaveAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public LeaveAdapter(Context context, List<Searchresult> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_leaves, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<Searchresult> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            //holder.layStatus.setVisibility(View.GONE);
            holder.setIsRecyclable(false);
           /* holder.tvName.setText(mListData.get(position).getDocNo()+" ("+mListData.get(position).getType()+")");
            holder.tvCity.setText(mListData.get(position).getCityName());*/
        }
        else {
            holder.setIsRecyclable(true);
        }

     /*   holder.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListData.get(position).getType().equals("Customer")) {
                    //LogUtil.printToastMSG(mContext,mListData.get(position).getType());
                    listItemSelectListener.onItemClick(0,mListData.get(position));
                }
                else{
                    if(mListData.get(position).getUrl()!=null &&
                            !mListData.get(position).getUrl().equals("")) {
                        //LogUtil.printToastMSG(mContext, mListData.get(position).getType());
                        listItemSelectListener.onItemClick(1, mListData.get(position));
                    }
                    else{
                        LogUtil.printToastMSG(mContext,"Url not available!");
                    }
                }
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListData.get(position).getType().equals("Customer")) {
                    //LogUtil.printToastMSG(mContext,mListData.get(position).getType());
                    listItemSelectListener.onItemClick(0,mListData.get(position));
                }
                else{
                    if(mListData.get(position).getUrl()!=null &&
                            !mListData.get(position).getUrl().equals("")) {
                        //LogUtil.printToastMSG(mContext, mListData.get(position).getType());
                        listItemSelectListener.onItemClick(1, mListData.get(position));
                    }
                    else{
                        LogUtil.printToastMSG(mContext,"Url not available!");
                    }
                }
            }
        });
*/
       /*
        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layStatus.setVisibility(View.GONE);
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvName,tvCity;
        LinearLayoutCompat layClick;
        AppCompatImageView imgExit,imgClose;
        FrameLayout layStatus;

        ViewHolder(View itemView) {
            super(itemView);
            //tvName = itemView.findViewById(R.id.tvName);
           // tvCity = itemView.findViewById(R.id.tvCity);
            //layClick = itemView.findViewById(R.id.layClick);
          }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,Searchresult searchresult);
    }
}
