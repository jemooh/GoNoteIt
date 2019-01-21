package com.skyz.noteit.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.skyz.noteit.dao.NoteDao;
import com.skyz.noteit.dao.NoteEntity;
import com.skyz.noteit.utils.ReadAccessConverter;
import com.skyz.noteit.utils.WriteAccessConverter;

@Database(entities = {NoteEntity.class}, version = 1)
@TypeConverters({
        ReadAccessConverter.class,
        WriteAccessConverter.class})
public abstract class NotesDataBase extends RoomDatabase {

    public static final String NOTES_DATA_BASE_NAME = "gonoteit.db";

    public abstract NoteDao noteDao();
}
