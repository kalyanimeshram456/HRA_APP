package com.ominfo.staff.deps;


import com.ominfo.staff.basecontrol.BaseActivity;
import com.ominfo.staff.network.NetworkModule;
import com.ominfo.staff.ui.dashboard.DashbooardActivity;
import com.ominfo.staff.ui.login.LoginActivity;
import com.ominfo.staff.ui.lr_number.AddLrActivity;
import com.ominfo.staff.ui.lr_number.LrDetailsActivity;
import com.ominfo.staff.ui.lr_number.SelectedLrNumberActivity;
import com.ominfo.staff.ui.lr_number.fragment.ViewFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface Deps
{
    void inject(BaseActivity baseActivity);

    void inject(LoginActivity loginActivity);

    void inject(AddLrActivity addLrActivity);

    void inject(SelectedLrNumberActivity selectedLrNumberActivity);

    void inject(ViewFragment viewFragment);

    void inject(LrDetailsActivity lrDetailsActivity);

    void inject(DashbooardActivity dashbooardActivity);

}
