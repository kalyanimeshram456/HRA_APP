package com.ominfo.crm_solution.ui.my_account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DashModel> mListData;
    private Context mContext;
    private String mDate;

    public AccountAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public AccountAdapter(Context context, List<DashModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_account_details, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DashModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            holder.setIsRecyclable(false);
            if(position==0) {
                holder.imgAcc.setImageDrawable(mContext.getDrawable(R.drawable.ic_account_icon));
            }
            if(position==1) {
                holder.imgAcc.setImageDrawable(mContext.getDrawable(R.drawable.ic_office_building));
            }
            if(position==2) {
                holder.imgAcc.setImageDrawable(mContext.getDrawable(R.drawable.ic_call_grey));
            }
            if(position==3) {
                holder.imgAcc.setImageDrawable(mContext.getDrawable(R.drawable.ic_om_email_grey));
            }
            holder.tvName.setText(mListData.get(position).getTitle());
            holder.edtAcc.setText(mListData.get(position).getValue());
        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtil.printToastMSG(mContext,"from adapter");
                listItemSelectListener.onItemClick(1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvName;
        LinearLayoutCompat layClick;
        AppCompatImageView imgAcc,imgEdit;
        AppCompatTextView edtAcc;

        ViewHolder(View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            imgAcc = itemView.findViewById(R.id.imgAcc);
            tvName = itemView.findViewById(R.id.tvName);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            edtAcc = itemView.findViewById(R.id.edtAcc);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData);
    }
}
