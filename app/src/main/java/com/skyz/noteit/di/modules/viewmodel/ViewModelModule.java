package com.skyz.noteit.di.modules.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import com.skyz.noteit.ui.create.CreateViewModel;
import com.skyz.noteit.ui.login.LoginViewModel;
import com.skyz.noteit.ui.main.MainViewModel;
import com.skyz.noteit.ui.note.NoteViewModel;
import com.skyz.noteit.ui.notes.NotesViewModel;

@Module
public interface ViewModelModule {

    @Documented
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel mainViewModel(MainViewModel viewModel);

    @IntoMap
    @Binds
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel loginViewModel(LoginViewModel viewModel);

    @IntoMap
    @Binds
    @ViewModelKey(NotesViewModel.class)
    abstract ViewModel notesViewModel(NotesViewModel viewModel);


    @IntoMap
    @Binds
    @ViewModelKey(CreateViewModel.class)
    abstract ViewModel createViewModel(CreateViewModel viewModel);

    @IntoMap
    @Binds
    @ViewModelKey(NoteViewModel.class)
    abstract ViewModel noteViewModel(NoteViewModel viewModel);
}
