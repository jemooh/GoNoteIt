package com.skyz.noteit.data.notes;

import android.annotation.SuppressLint;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;

import java.util.List;

import javax.inject.Inject;

import com.skyz.noteit.CreateNoteMutation;
import com.skyz.noteit.DeleteNoteMutation;
import com.skyz.noteit.GetChangelogMutation;
import com.skyz.noteit.GetNoteByIdQuery;
import com.skyz.noteit.GetNotesQuery;
import com.skyz.noteit.UpdateNoteMutation;
import com.skyz.noteit.api.ApiEntity;
import com.skyz.noteit.api.ApolloRxHelper;
import com.skyz.noteit.api.Note;
import com.skyz.noteit.auth.StoreAuth;
import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.rx.RxSchedulers;
import com.skyz.noteit.utils.TimestampStore;
import io.reactivex.Observable;

public class NotesRemote {

    private final RxSchedulers rxSchedulers;
    private final TimestampStore timestampStore;
    private StoreAuth storeAuth;
    private ApolloClient apolloClient;
    private ApolloRxHelper apolloRxHelper;


    @Inject
    public NotesRemote(ApolloClient apolloClient, StoreAuth storeAuth, ApolloRxHelper apolloRxHelper,
                       RxSchedulers rxSchedulers, TimestampStore timestampStore) {
        this.apolloClient = apolloClient;
        this.storeAuth = storeAuth;
        this.apolloRxHelper = apolloRxHelper;
        this.rxSchedulers = rxSchedulers;
        this.timestampStore = timestampStore;
    }

    @SuppressLint("CheckResult")
    public Observable<List<GetNotesQuery.AllEntity>> getNotes() {
        //TODO add token to query

        return apolloRxHelper
                .from(apolloClient.query(new GetNotesQuery()))
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.io())
                .doOnNext(dataResponse -> timestampStore.saveTimestamp(dataResponse.data().timestamp()))
                .flatMap(dataResponse -> Observable.fromArray(dataResponse.data().allEntities()));
    }

    public Observable<Response<CreateNoteMutation.Data>> createNote(NoteModel noteModel) {
        Note note = new Note(noteModel);

        return apolloRxHelper
                .from(apolloClient.mutate(new CreateNoteMutation(note.getNoteDataString(), noteModel.getReadAccess(), noteModel.getWriteAccess())))
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.io());
    }

    public Observable<Response<DeleteNoteMutation.Data>> deleteNote(Long id) {
        return apolloRxHelper
                .from(apolloClient.mutate(new DeleteNoteMutation(id)))
                .observeOn(rxSchedulers.io())
                .subscribeOn(rxSchedulers.androidMainThread());
    }

    public Observable<NoteModel> getNote(Long id) {
        return apolloRxHelper
                .from(apolloClient.query(new GetNoteByIdQuery(id)))
                .subscribeOn(rxSchedulers.io())
                .map(dataResponse -> dataResponse.data().entity())
                .map(entity -> ((Note) entity.data()).parseNote(new ApiEntity(entity)));
    }

    public Observable<Response<UpdateNoteMutation.Data>> updateNote(NoteModel noteModel) {
        Note note = new Note(noteModel);

        return apolloRxHelper
                .from(apolloClient.mutate(new UpdateNoteMutation(noteModel.getId(), note.getNoteDataString(), noteModel.getReadAccess(), noteModel.getWriteAccess())))
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.io());
    }

    public boolean shouldFetchChangelog() {
        return timestampStore.hasTimestamp();
    }

    public Observable<Response<GetChangelogMutation.Data>> getChangelog() {
        return apolloRxHelper
                .from(apolloClient.mutate(new GetChangelogMutation(timestampStore.getTimestamp())))
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.io());
    }

    public void saveTimestamp(Long timestamp) {
        timestampStore.saveTimestamp(timestamp);
    }
}
