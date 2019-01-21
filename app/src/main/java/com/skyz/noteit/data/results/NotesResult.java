package com.skyz.noteit.data.results;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.repository.Resource;

public class NotesResult {

    LiveData<PagedList<NoteModel>> notes;
    LiveData<Resource> resource;

    public NotesResult(LiveData<PagedList<NoteModel>> notes, LiveData<Resource> resource) {
        this.notes = notes;
        this.resource = resource;
    }

    public LiveData<PagedList<NoteModel>> getNotes() {
        return notes;
    }

    public LiveData<Resource> getResource() {
        return resource;
    }
}
