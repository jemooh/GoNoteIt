package com.skyz.noteit.di.modules;

import dagger.Module;
import dagger.Provides;
import com.skyz.noteit.rx.AppSchedulers;
import com.skyz.noteit.rx.RxSchedulers;

@Module
public class RxModule {

    @Provides
    RxSchedulers provideRxSchedulers() {
        return new AppSchedulers();
    }
}
