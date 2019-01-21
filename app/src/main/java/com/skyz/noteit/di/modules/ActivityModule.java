package com.skyz.noteit.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.skyz.noteit.ui.create.CreateActivity;
import com.skyz.noteit.ui.login.LoginActivity;
import com.skyz.noteit.ui.main.MainActivity;
import com.skyz.noteit.ui.note.NoteActivity;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    MainActivity bindMainActivity();

    @ContributesAndroidInjector
    LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    CreateActivity bindCreateActivity();

    @ContributesAndroidInjector
    NoteActivity bindNoteActivity();
}
