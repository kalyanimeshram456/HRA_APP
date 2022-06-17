package com.ominfo.hra_app.ui.salary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.ui.salary.model.SalarySheetList;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SalaryNewAdapter extends RecyclerView.Adapter<SalaryNewAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<SalaryAllList> mListData;
    private Context mContext;
    private String mDate;

    public SalaryNewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public SalaryNewAdapter(Context context, List<SalaryAllList> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_salary, parent, false);

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
            if(mListData.get(position).getIsActive().equals("0")){
                holder.tvActive.setText("Inactive");
                holder.tvActive.setTextColor(mContext.getResources().getColor(R.color.deep_red));
            }else{
                holder.tvActive.setText("Active");
                holder.tvActive.setTextColor(mContext.getResources().getColor(R.color.green));
            }
            holder.tvAmount.setText(AppUtils.dateConvertYYYYToDD(item.getLastSalpaidDate()));
            holder.textViewTitle.setText(item.getEmpName());
            holder.textViewDescription.setText(item.getEmpPosition());
            AppUtils.loadImageURL(mContext,mListData.get(position).getEmpProfilePic(),
                    holder.imgBirthPro,holder.progress_barBirth);

            holder.layCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemSelectListener.onItemClick(0,mListData.get(position));
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
        AppCompatTextView textViewTitle,tvActive;
        AppCompatTextView textViewDescription;
        AppCompatTextView tvDate;
        AppCompatTextView tvAmount;
        CardView layCard;
        CircleImageView imgBirthPro;
        ProgressBar progress_barBirth;

        ViewHolder(View itemView) {
            super(itemView);
            tvActive = itemView.findViewById(R.id.tvActive);
            textViewTitle = itemView.findViewById(R.id.tvEmpName);
            textViewDescription = itemView.findViewById(R.id.tvDesi);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            layCard = itemView.findViewById(R.id.layCard);
            imgBirthPro = itemView.findViewById(R.id.imgBirthPro);
            progress_barBirth = itemView.findViewById(R.id.progress_barBirth);
           }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData, SalaryAllList SalarySheetList);
    }
}
