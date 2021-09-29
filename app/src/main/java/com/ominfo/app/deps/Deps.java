package com.ominfo.app.deps;


import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.network.NetworkModule;
import com.ominfo.app.ui.kata_chithi.KataChithiActivity;
import com.ominfo.app.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface Deps
{
    void inject(BaseActivity baseActivity);

    void inject(LoginActivity loginActivity);

    void inject(KataChithiActivity loginActivity);
}
