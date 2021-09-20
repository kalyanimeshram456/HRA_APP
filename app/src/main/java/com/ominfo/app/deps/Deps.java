package com.ominfo.app.deps;


import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface Deps
{
    void inject(BaseActivity baseActivity);

}
