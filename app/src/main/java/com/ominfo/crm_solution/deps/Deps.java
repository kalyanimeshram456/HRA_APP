package com.ominfo.crm_solution.deps;


import com.ominfo.crm_solution.MainActivity;
import com.ominfo.crm_solution.alarm.activity.RingActivity;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.network.NetworkModule;
import com.ominfo.crm_solution.ui.attendance.StartAttendanceActivity;
import com.ominfo.crm_solution.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.crm_solution.ui.dispatch_pending.DispatchPendingFragment;
import com.ominfo.crm_solution.ui.enquiry_report.EnquiryReportFragment;
import com.ominfo.crm_solution.ui.login.LoginActivity;
import com.ominfo.crm_solution.ui.lost_apportunity.LostApportunityFragment;
import com.ominfo.crm_solution.ui.my_account.MyAccountFragment;
import com.ominfo.crm_solution.ui.my_account.leave.LeaveListFragment;
import com.ominfo.crm_solution.ui.my_account.report.ReportListFragment;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.product.ProductFragment;
import com.ominfo.crm_solution.ui.quotation_amount.QuotationFragment;
import com.ominfo.crm_solution.ui.receipt.ReceiptFragment;
import com.ominfo.crm_solution.ui.reminders.ReminderFragment;
import com.ominfo.crm_solution.ui.sale.SaleFragment;
import com.ominfo.crm_solution.ui.sales_credit.activity.View360Activity;
import com.ominfo.crm_solution.ui.sales_credit.fragment.SalesCreditFragment;
import com.ominfo.crm_solution.ui.search.SearchFragment;
import com.ominfo.crm_solution.ui.top_customer.TopCustomerFragment;
import com.ominfo.crm_solution.ui.visit_report.VisitReportFragment;
import com.ominfo.crm_solution.ui.visit_report.activity.StartEndVisitActivity;
import com.ominfo.crm_solution.ui.visit_report.activity.UploadVisitActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface Deps
{
    void inject(BaseActivity baseActivity);

    void inject(LeaveListFragment leaveListFragment);

    void inject(ReportListFragment reportListFragment);

    void inject(LostApportunityFragment lostApportunityFragment);

    void inject(StartAttendanceActivity startAttendanceActivity);

    void inject(View360Activity view360Activity);

    void inject(TopCustomerFragment topCustomerFragment);

    void inject(SalesCreditFragment salesCreditFragment);

    void inject(RingActivity ringActivity);

    void inject(NotificationsActivity notificationsActivity);

    void inject(SearchFragment searchFragment);

    void inject(ReceiptFragment receiptFragment);

    void inject(ReminderFragment reminderFragment);

    void inject(DispatchPendingFragment dispatchPendingFragment);

    void inject(QuotationFragment fragment);

    void inject(MyAccountFragment myAccountFragment);

    void inject(SaleFragment saleFragment);

    void inject(ProductFragment productFragment);

    void inject(DashboardFragment productFragment);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(EnquiryReportFragment enquiryReportFragment);

    void inject(VisitReportFragment visitReportFragment);

    void inject(UploadVisitActivity uploadVisitActivity);

    void inject(StartEndVisitActivity startEndVisitActivity);

   // void inject(DashbooardActivity dashbooardActivity);

}
