package com.skyz.noteit.repository.notes;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import com.skyz.noteit.api.ApiEntity;
import com.skyz.noteit.api.Note;
import com.skyz.noteit.data.notes.NotesLocal;
import com.skyz.noteit.data.notes.NotesRemote;
import com.skyz.noteit.data.results.DeletedResult;
import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.data.results.NoteResult;
import com.skyz.noteit.data.results.NotesResult;
import com.skyz.noteit.repository.Resource;
import com.skyz.noteit.utils.ErrorMessages;
import com.skyz.noteit.utils.NetworkHelper;

public class NotesRepositoryImpl implements NotesRepository {

    private final ErrorMessages errorMessages;
    private final NotesLocal notesLocal;
    private NotesRemote notesRemote;
    private NetworkHelper networkHelper;

    MutableLiveData<Resource> resource = new MutableLiveData<>();

    @Inject
    public NotesRepositoryImpl(NotesRemote notesRemote, NotesLocal notesLocal,
                               NetworkHelper networkHelper, ErrorMessages errorMessages) {
        this.notesRemote = notesRemote;
        this.notesLocal = notesLocal;
        this.networkHelper = networkHelper;
        this.errorMessages = errorMessages;
    }

    @Override
    public NotesResult getNotes() {

        if (networkHelper.isNetworkAvailable()) {
            updateNotesFromRemote();
        } else {
            resource.postValue(Resource.error(new Throwable(errorMessages.getOfflineMessage())));
        }

        return new NotesResult(notesLocal.getNotes(), resource);
    }

    private void updateNotesFromRemote() {

        if (notesRemote.shouldFetchChangelog()) {
            getNotesChangelog();
        } else {
            getNotesFromRemote();
        }
    }

    @SuppressLint("CheckResult")
    private void getNotesChangelog() {
        notesRemote.getChangelog()
                .doOnSubscribe(it -> resource.postValue(Resource.loading(null)))
                .filter(dataResponse -> dataResponse.data().changelog() != null)
                .singleOrError()
                .map(dataResponse -> dataResponse.data().changelog())
                .doOnSuccess(it -> notesLocal.saveChangelog(it))
                .doOnSuccess(it -> notesRemote.saveTimestamp(it.timestamp()))
                .subscribe(
                        changelog -> resource.postValue(Resource.success(null)),
                        error -> resource.postValue(Resource.error(error))
                );
    }

    @SuppressLint("CheckResult")
    private void getNotesFromRemote() {
        notesRemote.getNotes()
                .doOnSubscribe(it -> resource.postValue(Resource.loading(null)))
                .subscribe(
                        data -> {
                            resource.postValue(Resource.success(null));
                            notesLocal.saveEntities(data);
                        },
                        error -> resource.postValue(Resource.error(error))
                );
    }

    @Override
    public LiveData<Resource> createNote(NoteModel noteModel) {

        if (networkHelper.isNetworkAvailable()) {
            createNoteOnRemote(noteModel);
        } else {
            resource.postValue(Resource.error(new Throwable(errorMessages.getCreatingNoteNotImplementedOfflineMessage())));
        }

        return resource;
    }

    @SuppressLint("CheckResult")
    private void createNoteOnRemote(NoteModel noteModel) {
        notesRemote.createNote(noteModel)
                .doOnSubscribe(it -> resource.postValue(Resource.loading(null)))
                .filter(response -> response.data() != null)
                .filter(response -> response.data().createEntity() != null)
                .filter(response -> response.data().createEntity().ok())
                .singleOrError()
                .doOnSuccess(it -> getNotesChangelog())
                .map(dataResponse -> dataResponse.data().createEntity().entity())
                .map(entity -> (NoteModel) ((Note) entity.data()).parseNote(new ApiEntity(entity)))
                .doOnSuccess(notesLocal::saveEntity)
                .subscribe(
                        response -> resource.postValue(Resource.success(null)),
                        error -> resource.postValue(Resource.error(error))
                );
    }

    @Override
    public DeletedResult deleteNote(Long id) {
        MutableLiveData<Resource> resource = new MutableLiveData<>();

        if (networkHelper.isNetworkAvailable()) {
            deleteNoteOnRemote(id, resource);
        } else {
            resource.postValue(Resource.error(new Throwable(errorMessages.getDeletingNoteNotImplementedOfflineMessage())));
        }

        return new DeletedResult(id, resource);
    }

    @SuppressLint("CheckResult")
    private void deleteNoteOnRemote(Long id, MutableLiveData<Resource> resource) {
        notesRemote.deleteNote(id)
                .doOnSubscribe(it -> resource.postValue(Resource.loading(null)))
                .filter(response -> response.data() != null)
                .filter(response -> response.data().deleteEntity() != null)
                .filter(response -> response.data().deleteEntity().deleted())
                .singleOrError()
                .doOnSuccess(it -> getNotesChangelog())
                .subscribe(
                        response -> resource.postValue(Resource.success(null)),
                        error -> resource.postValue(Resource.error(error))
                );
    }

    @Override
    public NoteResult getNote(Long id) {

        if (networkHelper.isNetworkAvailable()) {
            getNoteFromRemote(id);
        } else {
            resource.postValue(Resource.error(new Throwable(errorMessages.getOfflineMessage())));
        }

        return new NoteResult(notesLocal.getNote(id), resource);
    }

    @SuppressLint("CheckResult")
    private void getNoteFromRemote(Long id) {
        notesRemote.getNote(id)
                .doOnSubscribe(it -> resource.postValue(Resource.loading(null)))
                .subscribe(
                        noteModel -> {
                            notesLocal.saveEntity(noteModel);
                            resource.postValue(Resource.success(null));
                        },
                        error -> resource.postValue(Resource.error(error))
                );
    }

    @Override
    public LiveData<Resource> updateNote(NoteModel noteModel) {

        if (networkHelper.isNetworkAvailable()) {
            updateNoteOnRemote(noteModel);
        } else {
            resource.postValue(Resource.error(errorMessages.getUpdatingNoteNotImplementedOfflineMessage()));
        }

        return resource;
    }

    @SuppressLint("CheckResult")
    private void updateNoteOnRemote(NoteModel noteModel) {
        notesRemote.updateNote(noteModel)
                .doOnSubscribe(it -> resource.postValue(Resource.loading(null)))
                .filter(response -> response.data() != null)
                .filter(response -> response.data().updateEntity() != null)
                .filter(response -> response.data().updateEntity().ok())
                .singleOrError()
                .map(dataResponse -> dataResponse.data().updateEntity().entity())
                .map(entity -> (NoteModel) ((Note) entity.data()).parseNote(new ApiEntity(entity)))
                .doOnSuccess(notesLocal::saveEntity)
                .doOnSuccess(it -> getNotesChangelog())
                .subscribe(
                        response -> resource.postValue(Resource.success(null)),
                        error -> resource.postValue(Resource.error(error))
                );
    }
}
