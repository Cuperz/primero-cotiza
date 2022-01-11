package com.jumpitt.primero_cotiza;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.orhanobut.hawk.Hawk;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        initRealm();
        initHawk();
        FirebaseApp.initializeApp(this);
        Sentry.init("https://cc4a5bfb3dfe4d4ab42052ead108dd88@sentry.io/1858700", new AndroidSentryClientFactory(this));


    }



    private void initRealm() {
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded() //This will ve deleted in the future
                .build();

        Realm.setDefaultConfiguration(configuration);
    }

    private void initHawk() {
        Hawk.init(this).build();
    }

    public static Context getContext(){
        return context;
    }


}
