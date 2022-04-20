package com.ominfo.crm_solution.ui.reminders.adapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.os.Build;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.reminders.model.ReminderModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;

import java.lang.reflect.Field;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<ReminderModel> mListData;
    private Context mContext;
    private String mDate;

    public ReminderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ReminderAdapter(Context context, List<ReminderModel> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_reminder, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<ReminderModel> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mListData.size()>0) {
           /* //holder.layStatus.setVisibility(View.GONE);
            holder.setIsRecyclable(false);
            holder.tvTitle.setText(mListData.get(position).getTitle());
            String mTime = AppUtils.dateFormate(mListData.get(position).getDate());
            holder.tvTime.setText(mTime+" - "+mListData.get(position).getTime());
            if(mListData.get(position).getStatus().equals("COMPLETED")) {
                holder.tvStatus.setText("Completed");
                holder.imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_checked));
            }
            else if(mListData.get(position).getStatus().equals("CANCELLED")) {
                holder.tvStatus.setText("Cancelled");
                holder.imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_x_mark));
            }
            else if(mListData.get(position).getStatus().equals("CREATED")) {
                holder.tvStatus.setText("Update");
                holder.imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_arrow));
            }
            else if(mListData.get(position).getStatus().equals("SNOOZED")) {
                holder.tvStatus.setText("Snoozed");
                holder.imgExit.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_om_arrow));
            }

            if (position % 2 != 0) {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_white_left_right_border_dialog));
            } else {
                holder.layClick.setBackground(mContext.getResources().getDrawable(R.drawable.shape_round_grey_left_right_border_dialog));
            }*/
        }
        else {
            holder.setIsRecyclable(true);
        }

        holder.imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListData.get(position).getValue().equals("0")) {
                    listItemSelectListener.onItemClick(0, v);
                    //popupMenu.setOnMenuItemClickListener(Sample1.this);
                    PopupMenuCustomLayout popupMenu = new PopupMenuCustomLayout(
                            v.getContext(), R.layout.rem_popup,
                            new PopupMenuCustomLayout.PopupMenuCustomOnClickListener() {
                                @Override
                                public void onClick(int itemId) {
                                    switch (itemId) {
                                        case R.id.imgClose:
                                            //LogUtil.printToastMSG(v.getContext(), "Whats delete");
                                            break;
                                        case R.id.tvTaskComple:
                                            //LogUtil.printToastMSG(mContext,"clicked 1");
                                            mListData.get(position).setValue("1");
                                            notifyItemChanged(position);
                                            notifyDataSetChanged();
                                            break;
                                        case R.id.tvRemLater:
                                            LogUtil.printToastMSG(mContext,"Will remind you after 10 minutes");
                                            break;
                                    }
                                }
                            });
                          // Method 1: popupMenu.show();
                          // Method 2: via an anchor view:
                    popupMenu.show(v, Gravity.CENTER, 0, 0);
              /*  PopupMenu popup = new PopupMenu(holder.imgExit.getContext(), holder.itemView);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                LogUtil.printToastMSG(mContext,"Whats delete");
                                return true;
                            case R.id.share:
                                LogUtil.printToastMSG(mContext,"Whats share");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                // here you can inflate your menu
                popup.inflate(R.menu.menu_reminder);
                popup.setGravity(Gravity.RIGHT);

                // if you want icon with menu items then write this try-catch block.
                try {
                    Field mFieldPopup=popup.getClass().getDeclaredField("mPopup");
                    mFieldPopup.setAccessible(true);
                   // MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
                   // mPopup.setForceShowIcon(true);
                } catch (Exception e) {

                }
                popup.show();*/
                }
            }
        });
       /* holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layStatus.setVisibility(View.GONE);
            }
        });
*/

    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }


    public static class PopupMenuCustomLayout {
        private PopupMenuCustomOnClickListener onClickListener;
        private Context context;
        private PopupWindow popupWindow;
        private int rLayoutId;
        private View popupView;

        public PopupMenuCustomLayout(Context context, int rLayoutId, PopupMenuCustomOnClickListener onClickListener) {
            this.context = context;
            this.onClickListener = onClickListener;
            this.rLayoutId = rLayoutId;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(rLayoutId, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setElevation(10);

            LinearLayout linearLayout = (LinearLayout) popupView.findViewById(R.id.layReminder);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View v = linearLayout.getChildAt(i);
                v.setOnClickListener( v1 -> {
                    onClickListener.onClick(v1.getId());
                    popupWindow.dismiss();
                });
            }
        }
        public void setAnimationStyle( int animationStyle) {
            popupWindow.setAnimationStyle(animationStyle);
        }
        public void show() {
            popupWindow.showAtLocation( popupView, Gravity.CENTER, 0, 0);
        }

        public void show( View anchorView, int gravity, int offsetX, int offsetY) {
            popupWindow.showAsDropDown( anchorView, 0, -2 * (anchorView.getHeight()));
        }

        public interface PopupMenuCustomOnClickListener {
             void onClick(int menuItemId);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvStatus,tvTitle,tvTime;
        FrameLayout layClick;
        AppCompatImageView imgExit;//,imgClose;
        //FrameLayout layStatus;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) mContext);
            layClick = itemView.findViewById(R.id.layClick);
            imgExit = itemView.findViewById(R.id.imgExit);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
          /*  tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRs = itemView.findViewById(R.id.tvRs);
            layClick = itemView.findViewById(R.id.layClick);
            imgDash = itemView.findViewById(R.id.imgDash);
       */ }
    }

    public interface ListItemSelectListener {
        void onItemClick(int mData,View v);
    }
}
