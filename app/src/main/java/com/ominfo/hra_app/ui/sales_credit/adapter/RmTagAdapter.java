package com.ominfo.hra_app.ui.sales_credit.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.sales_credit.model.RmListModel;

import java.util.List;

public class RmTagAdapter extends RecyclerView.Adapter<RmTagAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<RmListModel> mListData;
    private Context mContext;
    private String mDate;
    private int valStatus;
    public RmTagAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public RmTagAdapter(Context context, List<RmListModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_add_rm, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<RmListModel> list,int val){
        mListData = list;
        notifyDataSetChanged();
        valStatus = val;
    }

   /* @Override
    public int getCount() {
        if(RMDropdown.size()>0)
            return 1;
        return 0;
    }*/


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // setDropdownRM(position,RMDropdown,holder.tvTitle);
        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            holder.tvTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        holder.tvTitle.showDropDown();
                    }
                }
            });
            if(mListData.get(position).getValue().equals("1")){
                //holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_shape_corners_8_blue));
                holder.layClick.setBackground(null);
                holder.imgCross.setVisibility(View.GONE);
                holder.tvTitle.setHint("Add Tag");
                holder.tvTitle.setGravity(Gravity.LEFT);
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.back_text_colour));
                holder.tvTitle.setHintTextColor(mContext.getResources().getColor(R.color.app_gray));
            }
           else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_shape_corners_list_blue));
                holder.imgCross.setVisibility(View.VISIBLE);
                holder.tvTitle.setEnabled(false);
                holder.tvTitle.setText(mListData.get(position).getTitle());
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvTitle.setHintTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvTitle.setGravity(Gravity.CENTER);
            }
            if(valStatus==1) {
                holder.tvTitle.requestFocus();
                holder.tvTitle.postDelayed(new Runnable(){
                                               @Override public void run(){
                                                   InputMethodManager keyboard=(InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                   keyboard.showSoftInput(holder.tvTitle,0);
                                               }
                                           }
                        ,200);
            }
            if(mListData.size()-1 == position) {
                holder.tvTitle.setEnabled(true);
            }else {
                //tvTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvTitle.setEnabled(false);
            }

        }
        else {
            holder.setIsRecyclable(true);
        }


        int finalPosition = position;
        holder.imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListData.size()>1) {
                    mListData.remove(mListData.get(finalPosition));
                    notifyItemRemoved(finalPosition);
                    listItemSelectListener.onItemClick(mListData);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layClick;
        AppCompatImageView imgCross;
        AppCompatAutoCompleteTextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            imgCross = itemView.findViewById(R.id.imgCross);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }

    public interface ListItemSelectListener {
        void onItemClick(List<RmListModel> mData);
    }
}
