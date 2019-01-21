package com.skyz.noteit.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.skyz.noteit.app.NotesDataBase;
import com.skyz.noteit.dao.NoteDao;

@Module
public class StorageModule {

    @Singleton
    @Provides
    NotesDataBase noteDataBase(Context context) {
        return Room
                .databaseBuilder(context, NotesDataBase.class, NotesDataBase.NOTES_DATA_BASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    NoteDao noteDao(Context context) {
        return noteDataBase(context).noteDao();
    }
}
