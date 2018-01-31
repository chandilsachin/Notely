package com.chandilsachin.notely.dagger;


import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsViewModel;
import com.chandilsachin.notely.fragments.NotesList.NotesListViewModel;
import com.chandilsachin.notely.database.LocalRepo;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppContext.class, DatabaseModule.class})
public interface DatabaseComponent {

    void inject(LocalRepo localRepo);
    void inject(NotesDetailsViewModel localRepo);
    void inject(NotesListViewModel localRepo);

}
