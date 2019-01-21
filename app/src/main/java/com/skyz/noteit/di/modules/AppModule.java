package com.skyz.noteit.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.apollographql.apollo.ApolloClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.skyz.noteit.api.ApolloRxHelper;
import com.skyz.noteit.api.NoteAdapter;
import com.skyz.noteit.api.UUIDAdapter;
import com.skyz.noteit.app.GoNoteItApp;
import com.skyz.noteit.utils.NetworkHelper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.content.Context.MODE_PRIVATE;
import static com.skyz.noteit.api.ApiConsts.API_URL;
import static com.skyz.noteit.type.CustomType.GENERICSCALAR;
import static com.skyz.noteit.type.CustomType.UUID;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

@Module
public class AppModule {

    @Provides
    Context context(GoNoteItApp application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    ApolloClient provideGoNoteItClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return ApolloClient.builder()
                .serverUrl(API_URL)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(GENERICSCALAR, new NoteAdapter())
                .addCustomTypeAdapter(UUID, new UUIDAdapter())
                .build();
    }

    @Provides
    SharedPreferences provideSharedPrefs(Context context) {
        return context.getSharedPreferences("gonoteit", MODE_PRIVATE);
    }

    @Provides
    ApolloRxHelper providesApolloRxHelper() {
        return new ApolloRxHelper();
    }

    @Provides
    NetworkHelper providesNetworkHelper(Context context) {
        return new NetworkHelper(context);
    }
}
