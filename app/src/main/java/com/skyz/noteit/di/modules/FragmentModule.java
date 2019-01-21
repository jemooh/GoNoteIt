package com.skyz.noteit.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.skyz.noteit.ui.about.AboutFragment;
import com.skyz.noteit.ui.notes.NotesFragment;

@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    NotesFragment bindNotesFragment();

    @ContributesAndroidInjector
    AboutFragment bindAboutFragment();
}
