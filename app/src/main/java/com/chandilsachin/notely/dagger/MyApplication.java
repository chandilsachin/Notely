package com.chandilsachin.notely.dagger;

import android.app.Application;

/**
 * Created by sachin on 17/8/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponent();
    }

    private void buildComponent(){
        component = DaggerDatabaseComponent.builder()
                .appContext(new AppContext(this)).build();
    }

    public static DatabaseComponent component;

    public static DatabaseComponent getComponent() {
        return component;
    }

    public static void setComponent(DatabaseComponent component) {
        MyApplication.component = component;
    }
}
