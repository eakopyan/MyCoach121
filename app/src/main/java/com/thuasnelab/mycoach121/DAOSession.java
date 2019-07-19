package com.thuasnelab.mycoach121;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class DAOSession extends DAOBase {

    protected final static String NAME = "MyCoachDatabase.db";
    protected final static int VERSION = 1;

    protected String PATH;
    private SQLiteDatabase db;
    private final Context context;

    public DAOSession(Context context) {
        super(context, NAME, null, VERSION);
        if (Build.VERSION.SDK_INT >= 17)
            PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            PATH = "/data/data/" + context.getPackageName() + "/databases";
        this.context = context;
        this.getWritableDatabase();
    }

    public boolean open() throws SQLException {
        db = SQLiteDatabase.openDatabase(PATH+NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return db != null;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public String getPath() {
        return this.PATH;
    }

    public boolean isInDb(int sessionId) {
        return (getSessionFromDb(sessionId).moveToFirst());
    }

    public long addSessionToDb(Session session) {
        ContentValues newSession = new ContentValues();
        newSession.put(DAOBase.SESSION_DATE, session.getDate());
        newSession.put(DAOBase.SESSION_SPORT, session.getSport());
        newSession.put(DAOBase.SESSION_TITLE, session.getTitle());
        newSession.put(DAOBase.SESSION_DISTANCE, session.getDistance());
        newSession.put(DAOBase.SESSION_FC, session.getFc());
        newSession.put(DAOBase.SESSION_DUREE, session.getDuree());
        newSession.put(DAOBase.SESSION_ENERGY, session.getEnergy());
        long res;
        try {
            res = getDb().insertOrThrow(DAOBase.SESSION_TABLE_NAME, null, newSession);
        } catch (SQLException e) {
            res = 42;
        }
        return res;
    }

    public void removeSessionFromDb(long sessionId) {
        getDb().delete(DAOBase.SESSION_TABLE_NAME, SESSION_ID + " = ?",
                new String[] {Long.toString(sessionId)});
    }

    public void modifySessionInDb(long sessionId, Session newSession) {
        ContentValues update = new ContentValues();
        update.put(DAOBase.SESSION_DATE, newSession.getDate());
        update.put(DAOBase.SESSION_TITLE, newSession.getTitle());
        update.put(DAOBase.SESSION_DISTANCE, newSession.getDistance());
        update.put(DAOBase.SESSION_FC, newSession.getFc());
        update.put(DAOBase.SESSION_DUREE, newSession.getDuree());
        update.put(DAOBase.SESSION_ENERGY, newSession.getEnergy());

        getDb().update(SESSION_TABLE_NAME, update, SESSION_ID + " = ?",
                new String[] {Long.toString(sessionId)});
    }

    public Cursor getAllSessionsInDb() {
        return getDb().rawQuery(
                //"select date, sport, title, distance, fc, duree, energy from "
                "select " + SESSION_DATE + " from "
                        + SESSION_TABLE_NAME,
                new String[] {}
        );
    }

    public Cursor getSessionFromDb(long sessionId) {
        return getDb().rawQuery(
                //"select date, sport, title, distance, fc, duree, energy" +
                "select *" +
                        " from " + SESSION_TABLE_NAME +
                        " where " + SESSION_ID + " = ?",
                new String[] {Long.toString(sessionId)}
        );
    }

    public String getTableName() {
        return SESSION_TABLE_NAME;
    }

}
