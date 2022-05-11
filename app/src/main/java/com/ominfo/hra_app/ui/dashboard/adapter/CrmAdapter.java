package com.ominfo.hra_app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.sales_credit.fragment.SalesCreditFragment;

import java.util.List;

public class CrmAdapter extends RecyclerView.Adapter<CrmAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<DashModel> mListData;
    private Context mContext;
    private String mDate;

    public CrmAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CrmAdapter(Context context, List<DashModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_crm_dashboard, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<DashModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.tvTitle.setText(mListData.get(position).getTitle());
            holder.tvRs.setText(mListData.get(position).getValue());
            holder.imgDash.setImageDrawable(mListData.get(position).getImg());
            if(mListData.get(position).getTitle().equals("Products")) {
                holder.tvRs.setHint("");
            }
        }

        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(mListData.get(position));
                if (mListData.get(position).getTitle().equals("Sales Credit")) {
                    Fragment fragment = new SalesCreditFragment();
                    moveFromFragment(fragment);
                }

            }
        });


    }

    private void moveFromFragment(Fragment fragment){
        FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framecontainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTitle , tvRs;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DashModel mData);
    }
}
