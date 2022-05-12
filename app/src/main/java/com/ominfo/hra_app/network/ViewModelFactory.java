package com.ominfo.hra_app.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ominfo.hra_app.ui.attendance.model.LocationPerHourViewModel;
import com.ominfo.hra_app.ui.attendance.model.MarkAttendanceViewModel;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceViewModel;
import com.ominfo.hra_app.ui.dashboard.model.GetDashboardViewModel;
import com.ominfo.hra_app.ui.login.model.LoginViewModel;
import com.ominfo.hra_app.ui.login.model.LogoutViewModel;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.my_account.model.ChangePasswordViewModel;
import com.ominfo.hra_app.ui.my_account.model.ChangeProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetTicketViewModel;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationViewModel;
import com.ominfo.hra_app.ui.my_account.model.ProfileViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetTicketNoViewModel;
import com.ominfo.hra_app.ui.my_account.model.RaiseTicketViewModel;
import com.ominfo.hra_app.ui.my_account.model.UpdateTicketViewModel;
import com.ominfo.hra_app.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.hra_app.ui.notifications.model.LeaveSingleRecordViewModel;
import com.ominfo.hra_app.ui.notifications.model.LeaveStatusViewModel;
import com.ominfo.hra_app.ui.notifications.model.NotificationViewModel;
import com.ominfo.hra_app.ui.registration.model.CheckPrefixViewModel;
import com.ominfo.hra_app.ui.registration.model.RegistrationViewModel;
import com.ominfo.hra_app.ui.registration.model.SubscriptionViewModel;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditViewModel;
import com.ominfo.hra_app.ui.sales_credit.model.View30ViewModel;
import com.ominfo.hra_app.ui.search.model.SearchCrmViewModel;
import com.ominfo.hra_app.ui.top_customer.model.TopCustomerViewModel;
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
         else  if (modelClass.isAssignableFrom(GetDashboardViewModel.class)) {
             return (T) new GetDashboardViewModel(service);
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
         else  if (modelClass.isAssignableFrom(ChangePasswordViewModel.class)) {
             return (T) new ChangePasswordViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SearchCrmViewModel.class)) {
             return (T) new SearchCrmViewModel(service);
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
         else  if (modelClass.isAssignableFrom(MarkAttendanceViewModel.class)) {
             return (T) new MarkAttendanceViewModel(service);
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
         else  if (modelClass.isAssignableFrom(LeaveSingleRecordViewModel.class)) {
             return (T) new LeaveSingleRecordViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(LeaveStatusViewModel.class)) {
             return (T) new LeaveStatusViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(LogoutViewModel.class)) {
             return (T) new LogoutViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(TopCustomerViewModel.class)) {
             return (T) new TopCustomerViewModel(service);
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

        throw new IllegalArgumentException("Unknown class name");

    }
}
