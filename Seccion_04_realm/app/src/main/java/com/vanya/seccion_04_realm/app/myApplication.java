package com.vanya.seccion_04_realm.app;

import android.app.Application;

import com.vanya.seccion_04_realm.models.Board;
import com.vanya.seccion_04_realm.models.Note;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static io.realm.RealmConfiguration.*;

public class myApplication extends Application {

    public static AtomicInteger BoardID = new AtomicInteger();
    public static AtomicInteger NoteID = new AtomicInteger();

    @Override
    public void onCreate() {

        super.onCreate();
        setUpRealConfig();
        Realm realm = Realm.getDefaultInstance();
        BoardID = getIdByTable(realm, Board.class);
        NoteID = getIdByTable(realm, Note.class);
        realm.close();

    }

    private void setUpRealConfig(){
        Realm.init(getApplicationContext());
        RealmConfiguration config;
        config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll() ;
        return (result.size() > 0) ? new AtomicInteger(result.max("id").intValue()) : new AtomicInteger() ;

    }
}
