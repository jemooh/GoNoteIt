package com.skyz.noteit.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.model.note.SimpleNoteModel;
import static com.skyz.noteit.type.Type.NONE;
import static com.skyz.noteit.type.Type.NOTE;

public class Note {

    private String noteDataString;
    private ApiEntity apiEntity;

    public Note(String noteDataString) {
        this.noteDataString = noteDataString;
    }

    public Note(NoteModel noteModel) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new NoteModelToJsonExclusionStrategy())
                .create();
        this.noteDataString = gson.toJson(noteModel);
    }

    public class NoteModelToJsonExclusionStrategy implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {

            return (f.getDeclaringClass() == NoteModel.class && f.getName().equals("id")) ||
                    (f.getDeclaringClass() == NoteModel.class && f.getName().equals("uuid")) ||
                    (f.getDeclaringClass() == NoteModel.class && f.getName().equals("readAccess")) ||
                    (f.getDeclaringClass() == NoteModel.class && f.getName().equals("writeAccess"));
        }

    }





    public String getNoteDataString() {
        return noteDataString;
    }

    public <T extends NoteModel> T parseNote(ApiEntity apiEntity) {
        this.apiEntity = apiEntity;

        return parseNote();
    }

    private <T extends NoteModel> T parseNote() {

        if (apiEntity.type == NONE) {
            return null;
        }

        if (apiEntity.type == NOTE) {
            return (T) getNoteModel();
        }

        return (T) getNoteModel();
    }

    private SimpleNoteModel getNoteModel() {
        Gson gson = new Gson();
        SimpleNoteModel noteModel = gson.fromJson(noteDataString, SimpleNoteModel.class);
        noteModel.setUuid(apiEntity.uuid);
        noteModel.setId(apiEntity.id);
        noteModel.setUpdatedAt(apiEntity.updatedAt);
        noteModel.setReadAccess(apiEntity.readAccess);
        noteModel.setWriteAccess(apiEntity.writeAccess);

        return noteModel;
    }
}
