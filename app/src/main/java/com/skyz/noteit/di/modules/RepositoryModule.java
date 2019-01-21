package com.skyz.noteit.di.modules;

import dagger.Binds;
import dagger.Module;
import com.skyz.noteit.repository.notes.NotesRepository;
import com.skyz.noteit.repository.notes.NotesRepositoryImpl;
import com.skyz.noteit.repository.user.UserRepository;
import com.skyz.noteit.repository.user.UserRepositoryImpl;

@Module
public interface RepositoryModule {

    @Binds
    NotesRepository notesRepository(NotesRepositoryImpl notesRepository);

    @Binds
    UserRepository userRepository(UserRepositoryImpl userRepository);
}
