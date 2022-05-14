package com.ominfo.hra_app.ui.employees.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;

import java.util.List;

import butterknife.ButterKnife;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<EmployeeList> mListData;
    private Context mContext;
    private String mDate;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    public EmployeeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public EmployeeAdapter(Context context, List<EmployeeList> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
       /* LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_employee, parent, false);

        return new ViewHolder(listItem);*/
    }

    public class ProgressHolder extends ViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        /*@Override
        protected void clear() {
        }*/
    }
    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mListData.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    public void updateList(List<EmployeeList> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
            //holder.layStatus.setVisibility(View.GONE);
            holder.setIsRecyclable(false);
            holder.tvName.setText(mListData.get(position).getEmpName());
            holder.tvDesi.setText(mListData.get(position).getEmpPosition());
            String status = mListData.get(position).getIsActive().equals("1")?"Active":"Inactive";
            holder.tvStatus.setText(status);

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

    public void addItems(List<EmployeeList> postItems) {
        mListData.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;
        mListData.add(new EmployeeList());
        notifyItemInserted(mListData.size() - 1);
    }
    public void removeLoading() {
        isLoaderVisible = false;
        int position = mListData.size() - 1;
        EmployeeList item = getItem(position);
        if (item != null) {
            mListData.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        mListData.clear();
        notifyDataSetChanged();
    }

    EmployeeList getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvName,tvDesi,tvStatus;
        CardView layCard;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvEmpName);
            tvDesi = itemView.findViewById(R.id.tvDesi);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            layCard = itemView.findViewById(R.id.layCard);
          }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData, EmployeeList searchresult);
    }
}
