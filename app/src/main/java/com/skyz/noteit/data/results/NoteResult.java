package com.skyz.noteit.data.results;

import android.arch.lifecycle.LiveData;

import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.repository.Resource;

public class NoteResult {

    LiveData<NoteModel> note;
    LiveData<Resource> resource;

    public NoteResult(LiveData<NoteModel> note, LiveData<Resource> resource) {
        this.note = note;
        this.resource = resource;
    }

    public LiveData<NoteModel> getNote() {
        return note;
    }

    public LiveData<Resource> getResource() {
        return resource;
    }
}
