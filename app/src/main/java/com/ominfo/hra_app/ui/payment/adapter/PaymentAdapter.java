package com.ominfo.hra_app.ui.payment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<SalaryAllList> mListData;
    private Context mContext;
    private String mDate;

    public PaymentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public PaymentAdapter(Context context, List<SalaryAllList> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_my_plan, parent, false);

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

           /* holder.layCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemSelectListener.onItemClick(0,mListData.get(position));
                }
            });*/
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
        AppCompatTextView textViewTitle;
        AppCompatTextView textViewDescription;
        AppCompatTextView tvDate;
        AppCompatTextView tvAmount;
        CardView layCard;
        CircleImageView imgBirthPro;
        ProgressBar progress_barBirth;

        ViewHolder(View itemView) {
            super(itemView);

           }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData, SalaryAllList SalarySheetList);
    }
}
