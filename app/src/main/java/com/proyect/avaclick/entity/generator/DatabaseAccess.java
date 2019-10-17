package com.proyect.avaclick.entity.generator;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

import com.proyect.avaclick.entity.EntityProvider;
import com.proyect.avaclick.entity.orm.IEntity;

public class DatabaseAccess extends SQLiteOpenHelper {

    public DatabaseAccess(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, EntityProvider.getDatabaseName(), factory, EntityProvider.getVersion());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        for (Class<?> entity : EntityProvider.getEntities()) {
            if (entity.isAnnotationPresent(IEntity.class)) {
                database.execSQL("DROP TABLE IF EXISTS " + entity.getAnnotation(IEntity.class).table() + ";");
                createTable(database, entity);
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (Class<?> entity : EntityProvider.getEntities()) {
            if (entity.isAnnotationPresent(IEntity.class)) {
                createTable(database, entity);
            }
        }
    }

    private void createTable(SQLiteDatabase database, Class<?> entity) {
        String table = EntityProvider.getDaoGenerator().createTable(entity);
        if (table != null) {
            database.execSQL(table);
        }
    }

}
