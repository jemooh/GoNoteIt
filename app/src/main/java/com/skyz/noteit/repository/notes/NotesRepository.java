package com.skyz.noteit.repository.notes;

import android.arch.lifecycle.LiveData;

import com.skyz.noteit.data.results.DeletedResult;
import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.data.results.NoteResult;
import com.skyz.noteit.data.results.NotesResult;
import com.skyz.noteit.repository.Resource;

public interface NotesRepository {

    NotesResult getNotes();

    LiveData<Resource> createNote(NoteModel noteModel);

    DeletedResult deleteNote(Long id);

    NoteResult getNote(Long id);

    LiveData<Resource> updateNote(NoteModel noteModel);
}
