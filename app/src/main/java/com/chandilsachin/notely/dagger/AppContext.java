package com.chandilsachin.notely.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppContext {

    private Context context;

    public AppContext(Context context){
        this.context = context;
    }

    @Provides
    public Context getContext(){
        return context;
    }

}
