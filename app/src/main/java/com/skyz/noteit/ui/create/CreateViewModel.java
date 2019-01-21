package com.skyz.noteit.ui.create;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import com.skyz.noteit.data.results.NoteResult;
import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.repository.Resource;
import com.skyz.noteit.repository.notes.NotesRepository;

public class CreateViewModel extends ViewModel {

    private NotesRepository notesRepository;

    @Inject
    public CreateViewModel(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @SuppressLint("CheckResult")
    public LiveData<Resource> createNote(NoteModel noteModel) {

        if (noteModel.getId() != null) {
            return notesRepository.updateNote(noteModel);
        }

        return notesRepository.createNote(noteModel);
    }

    public NoteResult getNote(Long id) {
        return notesRepository.getNote(id);
    }
}
