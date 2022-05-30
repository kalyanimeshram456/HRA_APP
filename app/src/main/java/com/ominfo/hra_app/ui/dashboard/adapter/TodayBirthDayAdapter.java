package com.ominfo.hra_app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.ominfo.hra_app.ui.dashboard.model.BirthDayDobdatum;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.sales_credit.fragment.SalesCreditFragment;
import com.ominfo.hra_app.util.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TodayBirthDayAdapter extends RecyclerView.Adapter<TodayBirthDayAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    private List<BirthDayDobdatum> mListData;
    private Context mContext;
    private String mDate;

    public TodayBirthDayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TodayBirthDayAdapter(Context context, List<BirthDayDobdatum> listData, ListItemSelectListener itemClickListener) {
        this.mListData = listData;
        this.mContext = context;
        this.listItemSelectListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_birth_day_list, parent, false);

        return new ViewHolder(listItem);
    }

    public void updateList(List<BirthDayDobdatum> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            AppUtils.loadImageURL(mContext,mListData.get(position).getEmpProfilePic(),
                    holder.imgBirthPro,  holder.progress_barBirth);
            holder.tvBirthName.setText(mListData.get(position).getEmpName());
            holder.tvBirthValue.setText(mListData.get(position).getEmpPosition());
            String mDate = AppUtils.convertDobDate(mListData.get(position).getDob());
            String[] mArr = mDate.split(",");
             String[] suffixes =
                    //    0     1     2     3     4     5     6     7     8     9
                    { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                            //    10    11    12    13    14    15    16    17    18    19
                            "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                            //    20    21    22    23    24    25    26    27    28    29
                            "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                            //    30    31
                            "th", "st" };


            String dayStr = mArr[0] + suffixes[Integer.parseInt(mArr[0])];
            holder.tvLeaveBirthValue.setText(dayStr+mArr[1]);
            AppUtils.loadImageURL(mContext,mListData.get(position).getEmpProfilePic(),
                    holder.imgBirthPro,holder.progress_barBirth);
        }

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
        AppCompatTextView tvBirthName;
        AppCompatTextView tvBirthValue;
        AppCompatTextView tvLeaveBirthValue;
        LinearLayoutCompat layClick;
        AppCompatImageView imgDash;
        CircleImageView imgBirthPro;
        ProgressBar progress_barBirth;

        ViewHolder(View itemView) {
            super(itemView);
            tvBirthName = itemView.findViewById(R.id.tvBirthName);
            tvBirthValue = itemView.findViewById(R.id.tvBirthValue);
            imgBirthPro = itemView.findViewById(R.id.imgBirthPro);
            progress_barBirth = itemView.findViewById(R.id.progress_barBirth);
            layClick = itemView.findViewById(R.id.layClick);
            tvLeaveBirthValue = itemView.findViewById(R.id.tvLeaveBirthValue);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(DashModel mData);
    }
}
