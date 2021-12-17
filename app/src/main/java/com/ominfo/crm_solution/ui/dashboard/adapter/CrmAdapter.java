package com.ominfo.crm_solution.ui.dashboard.adapter;

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

import com.ominfo.crm_solution.MainActivity;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.dispatch_pending.DispatchPendingFragment;
import com.ominfo.crm_solution.ui.enquiry_report.EnquiryReportFragment;
import com.ominfo.crm_solution.ui.product.ProductFragment;
import com.ominfo.crm_solution.ui.quotation_amount.QuotationFragment;
import com.ominfo.crm_solution.ui.receipt.ReceiptFragment;
import com.ominfo.crm_solution.ui.sales_credit.fragment.SalesCreditFragment;
import com.ominfo.crm_solution.ui.top_customer.TopCustomerFragment;
import com.ominfo.crm_solution.ui.visit_report.VisitReportFragment;

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
        }

        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemSelectListener.onItemClick(mListData.get(position));
                if (mListData.get(position).getTitle().equals("Sales Credit")) {
                    Fragment fragment = new SalesCreditFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Receipt")){
                    Fragment fragment = new ReceiptFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Top Customer")){
                    Fragment fragment = new TopCustomerFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Total Quotation Amount")){
                    Fragment fragment = new QuotationFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Dispatch Pending")){
                    Fragment fragment = new DispatchPendingFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Enquiry Report")){
                    Fragment fragment = new EnquiryReportFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Visit Report")){
                    Fragment fragment = new VisitReportFragment();
                    moveFromFragment(fragment);
                }
                if(mListData.get(position).getTitle().equals("Products")) {
                    Fragment fragment = new ProductFragment();
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
