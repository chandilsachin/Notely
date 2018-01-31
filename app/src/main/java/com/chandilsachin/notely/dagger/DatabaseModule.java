package com.chandilsachin.notely.dagger;

import android.content.Context;

import com.chandilsachin.personal_finance.database.NoteDao;
import com.chandilsachin.personal_finance.database.NoteDatabase;
import com.chandilsachin.notely.database.LocalRepo;
import com.chandilsachin.personal_finance.database.Preferences;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public NoteDao getDatabaseObject(Context context) {
        return NoteDatabase.Companion.getInstance(context);
    }

    @Provides
    public LocalRepo getLocalRepo(){
        return new LocalRepo();
    }

    @Provides
    public Preferences getPreferences(Context context){
        return new Preferences(context);
    }
}
