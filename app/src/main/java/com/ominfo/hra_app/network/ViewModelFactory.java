package com.ominfo.hra_app.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ominfo.hra_app.ui.attendance.model.LocationPerHourViewModel;
import com.ominfo.hra_app.ui.attendance.model.GetAttendanceViewModel;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceViewModel;
import com.ominfo.hra_app.ui.dashboard.model.AddHolidayViewModel;
import com.ominfo.hra_app.ui.dashboard.model.AttendanceDetailsViewModel;
import com.ominfo.hra_app.ui.dashboard.model.CalenderHolidaysListViewModel;
import com.ominfo.hra_app.ui.dashboard.model.EditHolidayViewModel;
import com.ominfo.hra_app.ui.dashboard.model.GetBirthDayListViewModel;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.ChangePasswordViewModel;
import com.ominfo.hra_app.ui.employees.model.DeactivateEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.SingleEmployeeListViewModel;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeaveListViewModel;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListViewModel;
import com.ominfo.hra_app.ui.leave.model.LeaveCountViewModel;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListViewModel;
import com.ominfo.hra_app.ui.login.model.LoginViewModel;
import com.ominfo.hra_app.ui.login.model.LogoutMobileTokenViewModel;
import com.ominfo.hra_app.ui.login.model.LogoutViewModel;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.my_account.model.ChangeProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.EditCompanyViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetTicketViewModel;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationViewModel;
import com.ominfo.hra_app.ui.my_account.model.ProfileViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetTicketNoViewModel;
import com.ominfo.hra_app.ui.my_account.model.RaiseTicketViewModel;
import com.ominfo.hra_app.ui.my_account.model.UpdateTicketViewModel;
import com.ominfo.hra_app.ui.notifications.model.AbsentMarkCountViewModel;
import com.ominfo.hra_app.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.hra_app.ui.notifications.model.GetSingleRecordViewModel;
import com.ominfo.hra_app.ui.notifications.model.LateMarkCountViewModel;
import com.ominfo.hra_app.ui.notifications.model.LeaveStatusViewModel;
import com.ominfo.hra_app.ui.notifications.model.NotificationViewModel;
import com.ominfo.hra_app.ui.payment.model.AddUserViewModel;
import com.ominfo.hra_app.ui.payment.model.MyPlanViewModel;
import com.ominfo.hra_app.ui.payment.model.PayRenewPlanViewModel;
import com.ominfo.hra_app.ui.payment.model.RenewPlanViewModel;
import com.ominfo.hra_app.ui.registration.model.ApplyCouponViewModel;
import com.ominfo.hra_app.ui.registration.model.CheckPrefixViewModel;
import com.ominfo.hra_app.ui.registration.model.GetOtpViewModel;
import com.ominfo.hra_app.ui.registration.model.RegistrationViewModel;
import com.ominfo.hra_app.ui.registration.model.ResendOtpViewModel;
import com.ominfo.hra_app.ui.registration.model.SubscriptionViewModel;
import com.ominfo.hra_app.ui.registration.model.VerifyOtpViewModel;
import com.ominfo.hra_app.ui.salary.model.DeductLeaveViewModel;
import com.ominfo.hra_app.ui.salary.model.MarkNotLateViewModel;
import com.ominfo.hra_app.ui.salary.model.MarkPresentViewModel;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListViewModel;
import com.ominfo.hra_app.ui.salary.model.SalaryDisbursetViewModel;
import com.ominfo.hra_app.ui.salary.model.SalarySheetViewModel;
import com.ominfo.hra_app.ui.salary.model.SalarySlipViewModel;
import com.ominfo.hra_app.ui.salary.model.UnpaidLeaveViewModel;
import com.ominfo.hra_app.ui.salary.model.UpdateSalaryViewModel;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditViewModel;
import com.ominfo.hra_app.ui.sales_credit.model.View30ViewModel;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.visit_report.model.AddVisitViewModel;
import com.ominfo.hra_app.ui.visit_report.model.EditVisitViewModel;
import com.ominfo.hra_app.ui.visit_report.model.GetTourViewModel;
import com.ominfo.hra_app.ui.visit_report.model.GetVisitNoViewModel;
import com.ominfo.hra_app.ui.visit_report.model.GetVisitViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Service service;

    @Inject
    public ViewModelFactory(Service service) {
        this.service = service;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(service);
          }
         else  if (modelClass.isAssignableFrom(GetTourViewModel.class)) {
             return (T) new GetTourViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetVisitNoViewModel.class)) {
             return (T) new GetVisitNoViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetVisitViewModel.class)) {
             return (T) new GetVisitViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetBirthDayListViewModel.class)) {
             return (T) new GetBirthDayListViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
                 return (T) new ProfileViewModel(service);
             }
         else  if (modelClass.isAssignableFrom(SalesCreditViewModel.class)) {
             return (T) new SalesCreditViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetProfileImageViewModel.class)) {
             return (T) new GetProfileImageViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(ChangeProfileImageViewModel.class)) {
             return (T) new ChangeProfileImageViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(EmployeeListViewModel.class)) {
             return (T) new EmployeeListViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(AddVisitViewModel.class)) {
             return (T) new AddVisitViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(EditVisitViewModel.class)) {
             return (T) new EditVisitViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(NotificationViewModel.class)) {
             return (T) new NotificationViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(DeleteNotificationViewModel.class)) {
             return (T) new DeleteNotificationViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(View30ViewModel.class)) {
             return (T) new View30ViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(ApplyLeaveViewModel.class)) {
             return (T) new ApplyLeaveViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetAttendanceViewModel.class)) {
             return (T) new GetAttendanceViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(UpdateAttendanceViewModel.class)) {
             return (T) new UpdateAttendanceViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(LocationPerHourViewModel.class)) {
             return (T) new LocationPerHourViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(LeaveApplicationViewModel.class)) {
             return (T) new LeaveApplicationViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetSingleRecordViewModel.class)) {
             return (T) new GetSingleRecordViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(LeaveStatusViewModel.class)) {
             return (T) new LeaveStatusViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(LogoutViewModel.class)) {
             return (T) new LogoutViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetTicketNoViewModel.class)) {
             return (T) new GetTicketNoViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(RaiseTicketViewModel.class)) {
             return (T) new RaiseTicketViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetTicketViewModel.class)) {
             return (T) new GetTicketViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(UpdateTicketViewModel.class)) {
             return (T) new UpdateTicketViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
             return (T) new RegistrationViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(CheckPrefixViewModel.class)) {
             return (T) new CheckPrefixViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SubscriptionViewModel.class)) {
             return (T) new SubscriptionViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(AddEmployeeViewModel.class)) {
             return (T) new AddEmployeeViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(EmployeeListViewModel.class)) {
             return (T) new EmployeeListViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(DeactivateEmployeeViewModel.class)) {
             return (T) new DeactivateEmployeeViewModel(service);
         }else  if (modelClass.isAssignableFrom(EditEmployeeViewModel.class)) {
                 return (T) new EditEmployeeViewModel(service);
         }else  if (modelClass.isAssignableFrom(AcceptRejectLeaveListViewModel.class)) {
             return (T) new AcceptRejectLeaveListViewModel(service);
         }else  if (modelClass.isAssignableFrom(com.ominfo.hra_app.ui.leave.model.LeaveStatusViewModel.class)) {
             return (T) new com.ominfo.hra_app.ui.leave.model.LeaveStatusViewModel(service);
         }else  if (modelClass.isAssignableFrom(PastLeaveListViewModel.class)) {
             return (T) new PastLeaveListViewModel(service);
         }else  if (modelClass.isAssignableFrom(LeaveCountViewModel.class)) {
             return (T) new LeaveCountViewModel(service);
         }else  if (modelClass.isAssignableFrom(SalaryAllListViewModel.class)) {
             return (T) new SalaryAllListViewModel(service);
         }else  if (modelClass.isAssignableFrom(SalarySheetViewModel.class)) {
             return (T) new SalarySheetViewModel(service);
         }else  if (modelClass.isAssignableFrom(SalarySheetViewModel.class)) {
             return (T) new SalarySheetViewModel(service);
         }else  if (modelClass.isAssignableFrom(ApplyCouponViewModel.class)) {
        return (T) new ApplyCouponViewModel(service);
         }else  if (modelClass.isAssignableFrom(GetCompanyViewModel.class)) {
             return (T) new GetCompanyViewModel(service);
         }else  if (modelClass.isAssignableFrom(CalenderHolidaysListViewModel.class)) {
             return (T) new CalenderHolidaysListViewModel(service);
         }else  if (modelClass.isAssignableFrom(AddHolidayViewModel.class)) {
             return (T) new AddHolidayViewModel(service);
         }else  if (modelClass.isAssignableFrom(ActiveEmployeeListViewModel.class)) {
             return (T) new ActiveEmployeeListViewModel(service);
         }else  if (modelClass.isAssignableFrom(EditHolidayViewModel.class)) {
             return (T) new EditHolidayViewModel(service);
         }else  if (modelClass.isAssignableFrom(EditCompanyViewModel.class)) {
             return (T) new EditCompanyViewModel(service);
         }else  if (modelClass.isAssignableFrom(SalaryDisbursetViewModel.class)) {
             return (T) new SalaryDisbursetViewModel(service);
         }else  if (modelClass.isAssignableFrom(UpdateSalaryViewModel.class)) {
             return (T) new UpdateSalaryViewModel(service);
         }else  if (modelClass.isAssignableFrom(MarkPresentViewModel.class)) {
             return (T) new MarkPresentViewModel(service);
         }else  if (modelClass.isAssignableFrom(UnpaidLeaveViewModel.class)) {
             return (T) new UnpaidLeaveViewModel(service);
         }else  if (modelClass.isAssignableFrom(MarkNotLateViewModel.class)) {
             return (T) new MarkNotLateViewModel(service);
         }else  if (modelClass.isAssignableFrom(DeductLeaveViewModel.class)) {
             return (T) new DeductLeaveViewModel(service);
         }else  if (modelClass.isAssignableFrom(SalarySlipViewModel.class)) {
             return (T) new SalarySlipViewModel(service);
         }else  if (modelClass.isAssignableFrom(LateMarkCountViewModel.class)) {
             return (T) new LateMarkCountViewModel(service);
         }else  if (modelClass.isAssignableFrom(AbsentMarkCountViewModel.class)) {
             return (T) new AbsentMarkCountViewModel(service);
         } else  if (modelClass.isAssignableFrom(GetOtpViewModel.class)) {
             return (T) new GetOtpViewModel(service);
         }else  if (modelClass.isAssignableFrom(ResendOtpViewModel.class)) {
             return (T) new ResendOtpViewModel(service);
         }else  if (modelClass.isAssignableFrom(VerifyOtpViewModel.class)) {
             return (T) new VerifyOtpViewModel(service);
         }else  if (modelClass.isAssignableFrom(VerifyOtpViewModel.class)) {
             return (T) new VerifyOtpViewModel(service);
         }else  if (modelClass.isAssignableFrom(MyPlanViewModel.class)) {
             return (T) new MyPlanViewModel(service);
         }else  if (modelClass.isAssignableFrom(RenewPlanViewModel.class)) {
             return (T) new RenewPlanViewModel(service);
         }else  if (modelClass.isAssignableFrom(PayRenewPlanViewModel.class)) {
             return (T) new PayRenewPlanViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(AddUserViewModel.class)) {
             return (T) new AddUserViewModel(service);
         } else  if (modelClass.isAssignableFrom(SingleEmployeeListViewModel.class)) {
             return (T) new SingleEmployeeListViewModel(service);
         }else  if (modelClass.isAssignableFrom(ChangePasswordViewModel.class)) {
             return (T) new ChangePasswordViewModel(service);
         }else  if (modelClass.isAssignableFrom(LogoutMobileTokenViewModel.class)) {
             return (T) new LogoutMobileTokenViewModel(service);
         }else  if (modelClass.isAssignableFrom(AttendanceDetailsViewModel.class)) {
             return (T) new AttendanceDetailsViewModel(service);
         }

        throw new IllegalArgumentException("Unknown class name");

    }
}
