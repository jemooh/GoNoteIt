package com.skyz.noteit.ui.notes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.skyz.noteit.repository.notes.NotesRepository;

@RunWith(MockitoJUnitRunner.class)
public class NotesViewModelTest {

    private NotesViewModel notesViewModel;

    @Mock
    NotesRepository notesRepository;

    @Before
    public void init() {
        this.notesViewModel = new NotesViewModel(notesRepository);
    }

    @Test
    public void testCallingGetNotes() {
        this.notesViewModel.getNotes();

        Mockito.verify(notesRepository).getNotes();
    }
}