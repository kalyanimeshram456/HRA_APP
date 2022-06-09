package com.ominfo.hra_app.ui.salary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SalaryNewDisAdapter extends RecyclerView.Adapter<SalaryNewDisAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<SalaryAllList> mListData;
    private Context mContext;
    private String mDate;

    public SalaryNewDisAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SalaryNewDisAdapter(Context context, List<SalaryAllList> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_salary_disbursment, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<SalaryAllList> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            SalaryAllList item = mListData.get(position);
            holder.tvBirthName.setText(mListData.get(position).getEmpName().trim());
            holder.tvBirthValue.setText(mListData.get(position).getEmpPosition().trim());
            holder.tvLeave.setText(mListData.get(position).getLeaveCountCurMon());
            holder.tvSalary.setText(mContext.getString(R.string.scr_lbl_rs)+mListData.get(position).getSalaryThisMonth());
            AppUtils.loadImageURL(mContext,mListData.get(position).getEmpProfilePic(),
                    holder.imgBirthPro,holder.progress_barBirth);

            holder.cardClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listItemSelectListener.onItemClick(position, mListData.get(position));
                }
            });
            holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemSelectListener.onItemClick(position, mListData.get(position),mListData);
                }
            });
        }
        else {
            holder.setIsRecyclable(true);
        }


    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgEdit;
        CardView cardClick;
        AppCompatTextView tvBirthName;
        AppCompatTextView tvBirthValue;
        AppCompatTextView tvLeave;
        AppCompatTextView tvSalary;
        CircleImageView imgBirthPro;
        ProgressBar progress_barBirth;

        ViewHolder(View itemView) {
            super(itemView);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            cardClick = itemView.findViewById(R.id.cardClick);
            tvBirthName = itemView.findViewById(R.id.tvBirthName);
            tvBirthValue = itemView.findViewById(R.id.tvBirthValue);
            tvLeave = itemView.findViewById(R.id.tvLeave);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            imgBirthPro = itemView.findViewById(R.id.imgBirthPro);
            progress_barBirth = itemView.findViewById(R.id.progress_barBirth);
           }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData, SalaryAllList SalarySheetList,List<SalaryAllList>list);
    }
}
