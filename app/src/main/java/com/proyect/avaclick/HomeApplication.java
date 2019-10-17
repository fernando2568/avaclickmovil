package com.proyect.avaclick;


import android.app.Application;
import android.app.IntentService;
import android.content.Intent;

import com.proyect.avaclick.entity.EntityProvider;

public class HomeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EntityProvider.setDatabaseName("HomeDB");
       // EntityProvider.getEntities().add(SessionInfo.class);
        EntityProvider.newSession(getBaseContext());

        // Servicio

    }

}
