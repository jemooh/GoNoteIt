package com.skyz.noteit.repository.notes;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloMutationCall;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import com.skyz.noteit.CreateNoteMutation;
import com.skyz.noteit.GetNotesQuery;
import com.skyz.noteit.GetNotesQuery.AllEntity;
import com.skyz.noteit.MockRxSchedulers;
import com.skyz.noteit.api.ApolloRxHelper;
import com.skyz.noteit.auth.StoreAuth;
import com.skyz.noteit.dao.NoteDao;
import com.skyz.noteit.data.notes.NotesRemote;
import com.skyz.noteit.model.note.NoteModel;
import com.skyz.noteit.utils.TimestampStore;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class NotesRepositoryRemoteImplTest {


    @Mock
    ApolloClient apolloClient;

    @Mock
    StoreAuth storeAuth;

    @Mock
    ApolloRxHelper apolloRxHelper;

    @Mock
    ApolloQueryCall<GetNotesQuery.Data> apolloGetNotesCall;

    @Mock
    ApolloMutationCall<CreateNoteMutation.Data> apolloCreateNoteCall;

    @Mock
    Response<GetNotesQuery.Data> getNotesResponse;

    @Mock
    Response<CreateNoteMutation.Data> createNoteResponse;

    @Mock
    GetNotesQuery.Data data;

    @Mock
    AllEntity allEntity;

    @Mock
    NoteDao noteDao;

    @Mock
    TimestampStore timestampStore;

    private NotesRemote notesRemote;

    @Before
    public void init() {
        this.notesRemote = new NotesRemote(apolloClient, storeAuth, apolloRxHelper, new MockRxSchedulers(), timestampStore);

        Mockito.when(apolloClient.query(Mockito.any(GetNotesQuery.class)))
                .thenReturn(apolloGetNotesCall);
        Mockito.when(apolloRxHelper.from(apolloGetNotesCall))
                .thenReturn(Observable.just(getNotesResponse));


        Mockito.when(apolloClient.mutate(Mockito.any(CreateNoteMutation.class)))
                .thenReturn(apolloCreateNoteCall);
        Mockito.when(apolloRxHelper.from(apolloCreateNoteCall))
                .thenReturn(Observable.just(createNoteResponse));
    }


    @Test
    public void testCallGetNotesQuery() {
        notesRemote.getNotes();

        Mockito.verify(apolloClient).query(Mockito.any(GetNotesQuery.class));
    }

    @Test
    public void testCallIntoRx() {
        notesRemote.getNotes();

        Mockito.verify(apolloRxHelper).from(apolloGetNotesCall);
    }

    @Test
    public void testReturnNotes() {
        TestObserver<List<AllEntity>> notesObserver = new TestObserver<>();
        notesRemote.getNotes().subscribe(notesObserver);

        notesObserver.assertSubscribed();
    }

    @Test
    public void testCallNoteMutation() {
        notesRemote.createNote(new NoteModel());

        Mockito.verify(apolloClient).mutate(Mockito.any(CreateNoteMutation.class));
    }

//    @Test
//    public void testCreateNoteSendingProvidedContentValue() {
//        NoteModel noteModel = new NoteModel();
//        noteModel.setTitle("test title");
//        noteModel.setContent("test content");
//
//        notesRemote.createNote(noteModel);
//
//        Mockito.verify(apolloClient).mutate(
//                Mockito.argThat((ArgumentMatcher<CreateNoteMutation>) argument -> {
//                    Input<String> content = argument.variables().content();
//
//                    return content.defined && content.value.equals(noteModel.getContent());
//                }));
//    }
//
//    @Test
//    public void testCreateNoteSendingProvidedTitleValue() {
//        NoteModel noteModel = new NoteModel();
//        noteModel.setTitle("test title");
//        noteModel.setContent("test content");
//
//        notesRemote.createNote(noteModel);
//
//        Mockito.verify(apolloClient).mutate(
//                Mockito.argThat((ArgumentMatcher<CreateNoteMutation>) argument -> {
//                    Input<String> title = argument.variables().title();
//
//                    return title.defined && title.value.equals(noteModel.getTitle());
//                }));
//    }
}