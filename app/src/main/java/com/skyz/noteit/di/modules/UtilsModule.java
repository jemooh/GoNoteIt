package com.skyz.noteit.di.modules;

import dagger.Module;
import dagger.Provides;
import com.skyz.noteit.ui.login.UserValidator;

@Module
public class UtilsModule {

    @Provides
    UserValidator providesUserValidator() {
        return new UserValidator();
    }
}
