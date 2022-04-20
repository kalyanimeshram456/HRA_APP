package com.ominfo.crm_solution.ui.visit_report.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.sale.model.RmListModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourStatuslist;
import com.ominfo.crm_solution.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class TourTagAdapter extends RecyclerView.Adapter<TourTagAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<RmListModel> mListData;
    private Context mContext;
    private String mDate;
    private int valStatus;
    List<GetTourStatuslist> RMDropdown = new ArrayList<>();
    public TourTagAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TourTagAdapter(Context context, List<RmListModel> listData, ListItemSelectListener itemClickListener) {
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


    public void updateRmList(List<GetTourStatuslist> RMDropdown){
        this.RMDropdown = RMDropdown;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setDropdownRM(position,RMDropdown,holder.tvTitle);
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

    //set value to RM dropdown
    private void setDropdownRM(int position,List<GetTourStatuslist> RMDropdown,AppCompatAutoCompleteTextView tvTitle) {
        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(/*RMDropdown.get(i).getEmpUsername()+" : "+*/
                            RMDropdown.get(i).getTourName());
                    /*if (mRmId!=null && !mRmId.equals("")) {
                        if (mRmId.equals(RMDropdown.get(i).getEmpId())) {
                            pos = i;
                            AutoComTextViewRM.setText(mDropdownList[pos]);
                            selectedRM = RMDropdown.get(i);
                        }
                    }*/
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                tvTitle.setThreshold(1);
                tvTitle.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                tvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard((Activity) mContext);
                        String getID = "";
                        for(int i=0;i<RMDropdown.size();i++){
                            if(RMDropdown.get(i).getTourName().equals(tvTitle.getText().toString().trim()))
                            {
                                getID =RMDropdown.get(i).getTourId();
                            }
                        }
                        if(tvTitle.getText().toString().trim()!=null && !tvTitle.getText().toString().trim().equals("")) {
                            mListData.set(position, new RmListModel(tvTitle.getText().toString().trim(), "0", getID));
                            notifyItemChanged(position);
                            mListData.add(new RmListModel("", "1", ""));
                            notifyItemInserted(mListData.size());
                            listItemSelectListener.onItemClick(mListData);
                            notifyDataSetChanged();
                        }
                        //tvTitle.invalidate();
                        //setDropdownRM(RMDropdown);
                    }

                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
