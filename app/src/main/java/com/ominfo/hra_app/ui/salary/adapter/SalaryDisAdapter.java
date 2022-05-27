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
import com.ominfo.hra_app.ui.employees.BaseViewHolder;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SalaryDisAdapter extends RecyclerView.Adapter<BaseViewHolder> {
  private static final int VIEW_TYPE_LOADING = 0;
  private static final int VIEW_TYPE_NORMAL = 1;
  private boolean isLoaderVisible = false;
  ListItemSelectListener listItemSelectListener;
  Context context;
  private List<SalaryAllList> mPostItems;

  public SalaryDisAdapter(Context context, List<SalaryAllList> postItems, ListItemSelectListener listItemSelectListener)
  {
    this.context = context;
    this.mPostItems = postItems;
    this.listItemSelectListener = listItemSelectListener;
  }

  @NonNull @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    switch (viewType) {
      case VIEW_TYPE_NORMAL:
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.row_salary_disbursment, parent, false));
      case VIEW_TYPE_LOADING:
        return new ProgressHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
      default:
        return null;
    }
  }

  @Override
  public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
    holder.onBind(position);
  }

  @Override
  public int getItemViewType(int position) {
    if (isLoaderVisible) {
      return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
    } else {
      return VIEW_TYPE_NORMAL;
    }
  }

  @Override
  public int getItemCount() {
    return mPostItems == null ? 0 : mPostItems.size();
  }

  public void addItems(List<SalaryAllList> postItems) {
    mPostItems.addAll(postItems);
    notifyDataSetChanged();
  }

  public void addLoading() {
    isLoaderVisible = true;
    mPostItems.add(new SalaryAllList());
    notifyItemInserted(mPostItems.size() - 1);
  }

  public void removeLoading() {
    isLoaderVisible = false;
    int position = mPostItems.size() - 1;
    SalaryAllList item = getItem(position);
    if (item != null) {
      mPostItems.remove(position);
      notifyItemRemoved(position);
    }
  }

  public void clear() {
    mPostItems.clear();
    notifyDataSetChanged();
  }

  SalaryAllList getItem(int position) {
    return mPostItems.get(position);
  }

  public class ViewHolder extends BaseViewHolder {

    @BindView(R.id.imgEdit)
    AppCompatImageView imgEdit;
    @BindView(R.id.cardClick)
    CardView cardClick;
    @BindView(R.id.tvBirthName)
    AppCompatTextView tvBirthName;
    @BindView(R.id.tvBirthValue)
    AppCompatTextView tvBirthValue;
    @BindView(R.id.tvLeave)
    AppCompatTextView tvLeave;
    @BindView(R.id.tvSalary)
    AppCompatTextView tvSalary;
    @BindView(R.id.imgBirthPro)
    CircleImageView imgBirthPro;
    @BindView(R.id.progress_barBirth)
    ProgressBar progress_barBirth;
    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    protected void clear() {

    }

    public void onBind(int position) {
      super.onBind(position);
      tvBirthName.setText(mPostItems.get(position).getEmpName());
      tvBirthValue.setText(mPostItems.get(position).getEmpPosition());
      tvLeave.setText(mPostItems.get(position).getLeaveCountCurMon());
      tvSalary.setText(context.getString(R.string.scr_lbl_rs)+mPostItems.get(position).getSalaryThisMonth());
      AppUtils.loadImageURL(context,mPostItems.get(position).getEmpProfilePic(),
              imgBirthPro,progress_barBirth);

     cardClick.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listItemSelectListener.onItemClick(0, mPostItems.get(position));
        }
      });
     imgEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listItemSelectListener.onItemClick(1, mPostItems.get(position));
        }
      });
    }
  }

  public class ProgressHolder extends BaseViewHolder {
    ProgressHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {
    }
  }

  public interface ListItemSelectListener {
    void onItemClick(int mData, SalaryAllList searchresult);
  }
}
