package com.skyz.noteit.ui.create;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import com.apollographql.apollo.api.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.skyz.noteit.CreateNoteMutation;
import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.repository.Resource;
import com.skyz.noteit.repository.notes.NotesRepository;

@RunWith(MockitoJUnitRunner.class)
public class CreateViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    NotesRepository notesRepository;

    @Mock
    Response<CreateNoteMutation.Data> createNoteMutationResponse;

    private CreateViewModel createViewModel;

    @Before
    public void init() {
        createViewModel = new CreateViewModel(notesRepository);

        Mockito.when(notesRepository.createNote(Mockito.any()))
                .thenReturn(new MutableLiveData<Resource>());
    }

    @Test
    public void testThatCallRepositoryCreate() {
        createViewModel.createNote(new NoteModel());

        Mockito.verify(notesRepository).createNote(Mockito.any(NoteModel.class));
    }

    @Test
    public void testThatCallWithProvidedValues() {
        NoteModel noteModel = new NoteModel();
        noteModel.setTitle("test title");
        noteModel.setContent("test content");

        createViewModel.createNote(noteModel);

        Mockito.verify(notesRepository).createNote(noteModel);
    }
}