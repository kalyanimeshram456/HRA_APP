package com.ominfo.hra_app.ui.leave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.ui.employees.BaseViewHolder;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeave;
import com.ominfo.hra_app.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeLeaveListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
  private static final int VIEW_TYPE_LOADING = 0;
  private static final int VIEW_TYPE_NORMAL = 1;
  private boolean isLoaderVisible = false;
  ListItemSelectListener listItemSelectListener;
  Context context;
  private List<AcceptRejectLeave> mPostItems;

  public EmployeeLeaveListAdapter(Context context, List<AcceptRejectLeave> postItems, ListItemSelectListener listItemSelectListener)
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
            LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee_leave, parent, false));
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

  public void addItems(List<AcceptRejectLeave> postItems) {
    mPostItems.addAll(postItems);
    notifyDataSetChanged();
  }

  public void addLoading() {
    isLoaderVisible = true;
    mPostItems.add(new AcceptRejectLeave());
    notifyItemInserted(mPostItems.size() - 1);
  }

  public void removeLoading() {
    isLoaderVisible = false;
    int position = mPostItems.size() - 1;
    AcceptRejectLeave item = getItem(position);
    if (item != null) {
      mPostItems.remove(position);
      notifyItemRemoved(position);
    }
  }

  public void clear() {
    mPostItems.clear();
    notifyDataSetChanged();
  }

  AcceptRejectLeave getItem(int position) {
    return mPostItems.get(position);
  }

  public class ViewHolder extends BaseViewHolder {
    @BindView(R.id.tvEmpName)
    TextView textViewTitle;
    @BindView(R.id.tvDesi)
    TextView textViewDescription;
    @BindView(R.id.layCard)
    CardView layCard;
    @BindView(R.id.imgBirthPro)
    CircleImageView imgBirthPro;
    @BindView(R.id.progress_barBirth)
    ProgressBar progress_barBirth;
   @BindView(R.id.tvStatus)
   AppCompatTextView tvStatus;
    @BindView(R.id.tvDateEmp)
    AppCompatTextView tvDate;
    @BindView(R.id.tvDays)
    AppCompatTextView tvDays;
    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    protected void clear() {

    }

    public void onBind(int position) {
      super.onBind(position);
      AcceptRejectLeave item = mPostItems.get(position);
      tvStatus.setText(item.getStatus());
      tvDays.setText(item.getDaysDiff()+" Days");
      if(item.getDaysDiff()==0){
        tvDays.setText("Half Day");
      }
      if(item.getDaysDiff()==1){
        tvDays.setText(item.getDaysDiff()+" Day");
      }
      tvDate.setText(AppUtils.convertyyyytoddLeave(item.getStartTime())+"-"+AppUtils.convertyyyytoddLeave(item.getEndTime()));//"2022-05-17 10:00:00
      if(item.getStatus().equals("APPROVED") || item.getStatus().equals("Approved")){
        tvStatus.setTextColor(context.getResources().getColor(R.color.green));
      }
      else if(item.getStatus().equals("APPLIED")||item.getStatus().equals("Applied")){
        tvStatus.setTextColor(context.getResources().getColor(R.color.deep_yellow));
      }
      else { tvStatus.setTextColor(context.getResources().getColor(R.color.deep_red));}
      textViewTitle.setText(item.getEmpName());
      textViewDescription.setText(item.getLeaveType());
      AppUtils.loadImageURL(context,mPostItems.get(position).getProfilePic(),
              imgBirthPro,progress_barBirth);

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
    void onItemClick(String mData, AcceptRejectLeave searchresult);
  }
}
