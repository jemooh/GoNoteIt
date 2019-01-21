package com.skyz.noteit.ui.notes;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import com.skyz.noteit.data.results.DeletedResult;
import com.skyz.noteit.data.results.NotesResult;
import com.skyz.noteit.repository.notes.NotesRepository;

public class NotesViewModel extends ViewModel {

    private NotesRepository notesRepository;

    @Inject
    public NotesViewModel(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public NotesResult getNotes() {
        return notesRepository.getNotes();
    }


    public DeletedResult deleteNote(Long id) {
        return notesRepository.deleteNote(id);
    }
}
