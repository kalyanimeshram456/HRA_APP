package com.ominfo.crm_solution.deps;


import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.network.NetworkModule;
import com.ominfo.crm_solution.ui.dashboard.DashbooardActivity;
import com.ominfo.crm_solution.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface Deps
{
    void inject(BaseActivity baseActivity);

    void inject(LoginActivity loginActivity);

   // void inject(AddLrActivity addLrActivity);

    //void inject(SelectedLrNumberActivity selectedLrNumberActivity);

   // void inject(ViewFragment viewFragment);

   // void inject(LrDetailsActivity lrDetailsActivity);

    void inject(DashbooardActivity dashbooardActivity);

}
