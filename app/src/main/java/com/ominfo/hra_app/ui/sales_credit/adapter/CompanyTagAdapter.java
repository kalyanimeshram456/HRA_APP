package com.ominfo.hra_app.ui.sales_credit.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;

import java.util.List;

public class CompanyTagAdapter extends RecyclerView.Adapter<CompanyTagAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DashModel> mListData;
    private Context mContext;
    private String mDate;
    private int valStatus;

    public CompanyTagAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CompanyTagAdapter(Context context, List<DashModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_add_tag_company, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DashModel> list,int val){
        mListData = list;
        notifyDataSetChanged();
        valStatus = val;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
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
                //holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvTitle.setEnabled(false);
            }

        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.tvTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(holder.tvTitle.getText().toString().trim()!=null && !holder.tvTitle.getText().toString().trim().equals("")) {
                        mListData.set(position, new DashModel(holder.tvTitle.getText().toString().trim(), "0", null));
                        notifyItemChanged(position);
                        mListData.add(new DashModel("", "1", null));
                        notifyItemInserted(mListData.size());
                        listItemSelectListener.onItemClick(mListData);
                    }
                }
                return false;
            }
        });

        holder.imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListData.size()>1) {
                    mListData.remove(mListData.get(position));
                    notifyItemRemoved(position);
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
        AppCompatEditText tvTitle;
        RelativeLayout layClick;
        AppCompatImageView imgCross;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            imgCross = itemView.findViewById(R.id.imgCross);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }

    public interface ListItemSelectListener {
        void onItemClick(List<DashModel> mData);
    }
}
