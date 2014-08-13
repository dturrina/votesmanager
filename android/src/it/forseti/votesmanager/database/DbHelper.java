package it.forseti.votesmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Wrapper class to perform database operations.
 *
 * @author dturrina
 * @since 0.2
 */
public class DbHelper extends SQLiteOpenHelper {

    /** Constants for table and column names */
    public static final String TBL_VOTERS = "voters";
    public static final String COL_VR_ID = "_id";
    public static final String COL_VR_NAME = "name";

    public static final String TBL_COMPETITORS = "competitors";
    public static final String COL_CP_ID = "_id";
    public static final String COL_CP_NAME = "name";

    public static final String TBL_VOTES = "votes";
    public static final String COL_VT_ID = "_id";
    public static final String COL_VT_VOTERID = "voter_id";
    public static final String COL_VT_COMPETITORID = "comp_id";
    public static final String COL_VT_VOTE = "vote";

    /** Database name and version */
    private static final String DB_NAME = "votes.db";
    private static final int DB_VERSION = 1;

    /** Query strings for tables creation */
    private static final String TBL_CREATE_VOTERS = "create table " + TBL_VOTERS + "(" +
            COL_VR_ID + " integer primary key autoincrement, " +
            COL_VR_NAME + " text not null);";
    private static final String TBL_CREATE_COMPETITORS = "create table " + TBL_COMPETITORS + "(" +
            COL_CP_ID + " integer primary key autoincrement, " +
            COL_CP_NAME + " text not null);";
    private static final String TBL_CREATE_VOTES = "create table " + TBL_VOTES + "(" +
            COL_VT_ID + " integer primary key autoincrement, " +
            COL_VT_VOTERID + " integer, " +
            COL_VT_COMPETITORID     + " integer, " +
            COL_VT_VOTE + " real, " +
            "FOREIGN KEY (" + COL_VT_VOTERID + ") " +
            "REFERENCES " + TBL_VOTERS + "(" + COL_VT_ID + "), " +
            "FOREIGN KEY (" + COL_VT_COMPETITORID + ") " +
            "REFERENCES " + TBL_COMPETITORS + "(" + COL_VR_ID + "));";

    /** Query string for database creation */
    private static final String CREATE_DB = TBL_CREATE_VOTERS +
            TBL_CREATE_COMPETITORS +
            TBL_CREATE_VOTES;

    /** Query string to drop all tables */
    private static final String DROP_ALL = "DROP TABLE IF EXISTS " + TBL_VOTES + "; " +
            "DROP TABLE IF EXISTS " + TBL_VOTERS + "; " +
            "DROP TABLE IF EXISTS " + TBL_COMPETITORS + ";";

    /**
     * Context constructor
     *
     * @param context the application context
     * @see android.database.sqlite.SQLiteOpenHelper#SQLiteOpenHelper(android.content.Context, String, android.database.sqlite.SQLiteDatabase.CursorFactory, int)
     */
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Create database
     *
     * @param db the database to create
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    /**
     * Upgrade database
     *
     * @param db the database to upgrade
     * @param oldVersion old database version
     * @param newVersion new database version
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion +
                " to " + newVersion);
        /** Drop all tables */
        db.execSQL(DROP_ALL);
        /** Call database creation method */
        onCreate(db);
    }

    /**
     * Utility method to check database existence
     *
     * @return TRUE if default database exists, FALSE if it does not
     */
    public static boolean existsDb() {
        SQLiteDatabase database;

        try {
            /** Try to open database */
            database = SQLiteDatabase.openDatabase(DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
            database.close();
        } catch (SQLiteException e) {
            /** When a SQLiteException is thrown, database does not exist */
            return false;
        }

        /** Return true if no exception was thrown */
        return true;
    }
}
