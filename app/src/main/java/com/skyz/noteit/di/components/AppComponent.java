package com.skyz.noteit.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import com.skyz.noteit.app.GoNoteItApp;
import com.skyz.noteit.di.modules.ActivityModule;
import com.skyz.noteit.di.modules.AppModule;
import com.skyz.noteit.di.modules.FragmentModule;
import com.skyz.noteit.di.modules.RepositoryModule;
import com.skyz.noteit.di.modules.RxModule;
import com.skyz.noteit.di.modules.StorageModule;
import com.skyz.noteit.di.modules.UtilsModule;
import com.skyz.noteit.di.modules.viewmodel.ViewModelModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityModule.class,
        RepositoryModule.class,
        RxModule.class,
        StorageModule.class,
        ViewModelModule.class,
        AppModule.class,
        UtilsModule.class,
        FragmentModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(GoNoteItApp application);

        AppComponent build();
    }

    void inject(GoNoteItApp application);
}
