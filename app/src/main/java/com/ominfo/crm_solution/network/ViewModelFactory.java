package com.ominfo.crm_solution.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ominfo.crm_solution.ui.dashboard.model.GetDashboardViewModel;
import com.ominfo.crm_solution.ui.dispatch_pending.model.DisaptchViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryStatusViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiryViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.SearchCustViewModel;
import com.ominfo.crm_solution.ui.login.model.LoginViewModel;
import com.ominfo.crm_solution.ui.lost_apportunity.model.GetLostApportunityViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.GetVehicleViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.PlantViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.SearchViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsViewModel;
import com.ominfo.crm_solution.ui.my_account.model.ChangePasswordViewModel;
import com.ominfo.crm_solution.ui.my_account.model.ChangeProfileImageViewModel;
import com.ominfo.crm_solution.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.crm_solution.ui.my_account.model.ProfileViewModel;
import com.ominfo.crm_solution.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.crm_solution.ui.notifications.model.NotificationViewModel;
import com.ominfo.crm_solution.ui.product.model.ProductViewModel;
import com.ominfo.crm_solution.ui.quotation_amount.model.QuotationViewModel;
import com.ominfo.crm_solution.ui.receipt.model.ReceiptListViewModel;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderViewModel;
import com.ominfo.crm_solution.ui.reminders.model.EmployeeListViewModel;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListViewModel;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderViewModel;
import com.ominfo.crm_solution.ui.sale.model.SalesViewModel;
import com.ominfo.crm_solution.ui.sales_credit.model.SalesCreditViewModel;
import com.ominfo.crm_solution.ui.sales_credit.model.View30ViewModel;
import com.ominfo.crm_solution.ui.search.model.SearchCrmViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.EditVisitViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitNoViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitViewModel;

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

         else  if (modelClass.isAssignableFrom(PlantViewModel.class)) {
             return (T) new PlantViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetVehicleViewModel.class)) {
             return (T) new GetVehicleViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(VehicleDetailsViewModel.class)) {
             return (T) new VehicleDetailsViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SearchViewModel.class)) {
             return (T) new SearchViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetRmViewModel.class)) {
             return (T) new GetRmViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SearchCustViewModel.class)) {
             return (T) new SearchCustViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SaveEnquiryViewModel.class)) {
             return (T) new SaveEnquiryViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetEnquiryViewModel.class)) {
             return (T) new GetEnquiryViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(EnquiryStatusViewModel.class)) {
             return (T) new EnquiryStatusViewModel(service);
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
         else  if (modelClass.isAssignableFrom(ProductViewModel.class)) {
             return (T) new ProductViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SalesViewModel.class)) {
             return (T) new SalesViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(QuotationViewModel.class)) {
             return (T) new QuotationViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SalesCreditViewModel.class)) {
             return (T) new SalesCreditViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(DisaptchViewModel.class)) {
             return (T) new DisaptchViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(ReminderListViewModel.class)) {
             return (T) new ReminderListViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(ReceiptListViewModel.class)) {
             return (T) new ReceiptListViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetLostApportunityViewModel.class)) {
             return (T) new GetLostApportunityViewModel(service);
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
         else  if (modelClass.isAssignableFrom(AddReminderViewModel.class)) {
             return (T) new AddReminderViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(EmployeeListViewModel.class)) {
             return (T) new EmployeeListViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(UpdateReminderViewModel.class)) {
             return (T) new UpdateReminderViewModel(service);
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

        throw new IllegalArgumentException("Unknown class name");

    }
}
