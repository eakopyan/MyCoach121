package com.thuasnelab.mycoach121;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAOBase extends SQLiteOpenHelper {

    protected final static String SESSION_TABLE_NAME = "Sessions";

    protected final static String SESSION_ID = "_id";
    protected final static String SESSION_DATE = "date";
    protected final static String SESSION_SPORT = "sport";
    protected final static String SESSION_TITLE = "title";
    protected final static String SESSION_DISTANCE = "distance";
    protected final static String SESSION_FC = "FC";
    protected final static String SESSION_DUREE = "duree";
    protected final static String SESSION_ENERGY = "energy";

    protected final static String SESSION_TABLE_CREATE =
            "CREATE TABLE "+SESSION_TABLE_NAME+" ("+
                    SESSION_ID + " INTEGER PRIMARY KEY, " +
                    SESSION_DATE + " TEXT NOT NULL, " +
                    SESSION_SPORT + " TEXT NOT NULL, " +
                    SESSION_TITLE + " TEXT DEFAULT \"Nouvelle séance\", " +
                    SESSION_DISTANCE + " REAL, " +
                    SESSION_FC + " INTEGER NOT NULL, " +
                    SESSION_DUREE + " TEXT NOT NULL, " +
                    SESSION_ENERGY + " INTEGER)";

    protected final static String SESSION_TABLE_DROP =
            "DROP TABLE IF EXISTS " + SESSION_TABLE_NAME + ";";

    public DAOBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SESSION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SESSION_TABLE_DROP);
        onCreate(db);
    }
}
