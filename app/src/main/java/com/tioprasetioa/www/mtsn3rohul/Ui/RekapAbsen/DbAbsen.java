package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbAbsen extends SQLiteOpenHelper {

    private static final String KEY_NamaDb = "dbAbsen.db";
    private static final int Version = 14;

    public DbAbsen(Context context) {
        super(context,KEY_NamaDb,null,Version);
    }

    static  abstract class KolomDb implements BaseColumns{
        public static final String KEY_Namatabel = "Namatabel";
        static final String KEY_IdTgl = "Id_Tgl";
        static final String Key_Masuk ="Masuk";
        static final String KEY_Pulang = "Pulang";
        static final String KEY_DeletAll = "Deletall";
        static final String KEY_HARI = "HARI";

    }

    private static final String KEY_SQL = " CREATE TABLE "
            + KolomDb.KEY_Namatabel
            + " ("+ KolomDb.KEY_IdTgl
            + " TEXT PRIMARY KEY, "
            + KolomDb.Key_Masuk
            + " TEXT NOT NULL, "
            + KolomDb.KEY_Pulang
            + " TEXT, "
            + KolomDb.KEY_DeletAll
            + " TEXT NOT NULL, "
            + KolomDb.KEY_HARI
            + " TEXT NOT NULL)";

    private static final String KEY_Delete = "DROP TABLE IF EXISTS "+ KolomDb.KEY_Namatabel;

    public DbAbsen(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KEY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KEY_Delete);
        onCreate(db);
    }
}
