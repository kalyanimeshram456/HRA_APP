package com.ominfo.hra_app.deps;


import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.network.NetworkModule;
import com.ominfo.hra_app.ui.attendance.StartAttendanceActivity;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.employees.EmployeeFragment;
import com.ominfo.hra_app.ui.leave.LeaveFragment;
import com.ominfo.hra_app.ui.leave.fragment.PastLeaveFragment;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.my_account.MyAccountFragment;
import com.ominfo.hra_app.ui.my_account.leave.LeaveListFragment;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.registration.RegistrationActivity;
import com.ominfo.hra_app.ui.salary.SalaryFragment;
import com.ominfo.hra_app.ui.salary.fragment.SalaryDisbursementFragment;
import com.ominfo.hra_app.ui.sales_credit.activity.View360Activity;
import com.ominfo.hra_app.ui.sales_credit.fragment.SalesCreditFragment;
import com.ominfo.hra_app.ui.employees.AddEmployeeActivity;
import com.ominfo.hra_app.ui.top_customer.TopCustomerFragment;
import com.ominfo.hra_app.ui.visit_report.VisitReportFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface Deps
{
    void inject(BaseActivity baseActivity);

    void inject(LeaveFragment leaveFragment);

    void inject(SalaryFragment salaryFragment);

    void inject(SalaryDisbursementFragment salaryDisbursementFragment);

    void inject(EmployeeFragment employeeFragment);

    void inject(PastLeaveFragment pastLeaveFragment);

    void inject(LeaveListFragment leaveListFragment);

    void inject(StartAttendanceActivity startAttendanceActivity);

    void inject(View360Activity view360Activity);

    void inject(TopCustomerFragment topCustomerFragment);

    void inject(RegistrationActivity registrationActivity);

    void inject(AddEmployeeActivity addEmployeeActivity);

    void inject(SalesCreditFragment salesCreditFragment);

    void inject(NotificationsActivity notificationsActivity);

    void inject(MyAccountFragment myAccountFragment);

    void inject(DashboardFragment productFragment);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(VisitReportFragment visitReportFragment);

}
