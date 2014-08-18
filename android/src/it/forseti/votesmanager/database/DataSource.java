package it.forseti.votesmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.forseti.votesmanager.bean.Competitor;
import it.forseti.votesmanager.bean.Voter;
import it.forseti.votesmanager.engine.Aggregator;

/**
 * Holds all main database operations to facilitate its usability.
 *
 * @author dturrina
 * @since 0.2
 */
public class DataSource {

    /**
     * The DataSource has a database it refers to, a DbHelper to interact with the database,
     * and string arrays containing all column names for each table the database has.
     */
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allVoterColumns = {
            DbHelper.COL_VR_ID,
            DbHelper.COL_VR_NAME
        };
    private String[] allCompetitorColumns = {
            DbHelper.COL_CP_ID,
            DbHelper.COL_CP_NAME
        };
    private String[] allVoteColumns = {
            DbHelper.COL_VT_ID,
            DbHelper.COL_VT_COMPETITORID,
            DbHelper.COL_VT_VOTERID,
            DbHelper.COL_VT_VOTE
        };

    private static String LOG_PREFIX = DataSource.class.getSimpleName();

    /**
     * The Context constructor initializes the database helper internal variable.
     *
     * @param ctx the context to pass to the DbHelper
     */
    public DataSource(Context ctx) {
        dbHelper = new DbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    /**
     * Open the database (read/write mode).
     *
     * @throws SQLException when the database cannot be opened
     */
    public void openDatabase() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close the database
     */
    public void closeDatabase() {
        dbHelper.close();
    }

    /**
     * Create a new Voter record with the given name.
     *
     * @param voterName the name the DbVoter will have
     * @return a new DbVoter object with the given name and a database-retrieved id
     */
    public DbVoter createDbVoter(String voterName) {
        /** Initialize ContentValues object for insert operation */
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_VR_NAME, voterName);

        /** Insert the record with the given values and retrieve the new id */
        long insertId = database.insert(DbHelper.TBL_VOTERS, null, values);

        /** Retrieve the new record through a Cursor */
        Cursor cursor = database.query(
                DbHelper.TBL_VOTERS,
                allVoterColumns,
                DbHelper.COL_VR_ID + " = " + insertId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        DbVoter voter = cursorToVoter(cursor);
        cursor.close();

        /** Return the new record as a DbVoter object */
        return voter;
    }

    /**
     * Delete the Voter record with the corresponding id.
     * Also deletes all its given votes.
     *
     * @param voter the voter whose data must be deleted
     */
    public void deleteDbVoter(DbVoter voter) {
        /** Get the id from the input object */
        long id = voter.getId();
        /** Delete the votes with the corresponding voter id */
        database.delete(DbHelper.TBL_VOTES, DbHelper.COL_VT_VOTERID + " = " + id, null);
        /** Delete the voter with the corresponding id */
        database.delete(DbHelper.TBL_VOTERS, DbHelper.COL_VR_ID + " = " + id, null);
    }

    /**
     * Retrieve the list with all voters in the database.
     *
     * @return a list containing all retrieved voters
     */
    public List<DbVoter> getAllVoters() {
        /** Initialize the output list */
        List<DbVoter> allVoters = new ArrayList<DbVoter>();

        /** Query the database and get the cursor */
        Cursor cursor = database.query(DbHelper.TBL_VOTERS, allVoterColumns, null, null, null, null, null);

        /* Loop on cursor to get all records */
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            DbVoter voter = cursorToVoter(cursor);
            /** Add record to output list */
            allVoters.add(voter);
            cursor.moveToNext();
        }

        /** Close cursor and return list */
        cursor.close();
        return allVoters;
    }

    /**
     * Retrieve a single DbVoter (voter record)
     *
     * @param id the id of the voter to retrieve
     * @return the retrieved record, or NULL if no such id exists
     */
    public DbVoter getVoter(long id) {
        /** Prepare select query parameters */
        String select = DbHelper.COL_VR_ID + "=?";
        String[] selectArgs = { Long.toString(id) };
        /** Perform query and get cursor */
        Cursor cursor = database.query(DbHelper.TBL_VOTES, allVoteColumns, select, selectArgs, null, null, null);

        /** Declare return object */
        DbVoter result;

        /** If cursor contains no record, set return object as NULL */
        if (cursor.getCount() == 0) {
            result = null;
        /** Else, retrieve the first (only) returned object */
        } else {
            cursor.moveToFirst();
            result = cursorToVoter(cursor);
        }

        /** Close cursor and return appropriate object */
        cursor.close();
        return result;
    }

    /**
     * Turn cursor into DbVoter object
     *
     * @param cursor a record from the database
     * @return a DbVoter object with cursor data
     */
    private DbVoter cursorToVoter(Cursor cursor) {
        /** Create new empty DbVoter object */
        DbVoter voter = new DbVoter();
        /** Set id from cursor, as first long column */
        voter.setId(cursor.getLong(0));
        /** Set id from cursor, as first string column */
        voter.setName(cursor.getString(0));
        /** Return voter with values */
        return voter;
    }

    /**
     * Create a new Competitor record with the given name.
     *
     * @param competitorName the name the DbCompetitor will have
     * @return a new DbCompetitor object with the given name and a database-retrieved id
     */
    public DbCompetitor createDbCompetitor(String competitorName) {
        /** Initialize ContentValues object for insert operation */
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_CP_NAME, competitorName);

        /** Insert the record with the given values and retrieve the new id */
        long insertId = database.insert(DbHelper.TBL_COMPETITORS, null, values);

        /** Retrieve the new record through a Cursor */
        Cursor cursor = database.query(
                DbHelper.TBL_COMPETITORS,
                allCompetitorColumns,
                DbHelper.COL_CP_ID + " = " + insertId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        DbCompetitor competitor = cursorToCompetitor(cursor);
        cursor.close();

        /** Return the new record as a DbCompetitor object */
        return competitor;
    }

    /**
     * Delete the Competitor record with the corresponding id.
     * Also deletes all its votes.
     *
     * @param competitor the competitor whose data must be deleted
     */
    public void deleteDbCompetitor(DbCompetitor competitor) {
        /** Get the id from the input object */
        long id = competitor.getId();
        /** Delete the votes with the corresponding competitor id */
        database.delete(DbHelper.TBL_VOTES, DbHelper.COL_VT_COMPETITORID + " = " + id, null);
        /** Delete the competitor with the corresponding id */
        database.delete(DbHelper.TBL_COMPETITORS, DbHelper.COL_CP_ID + " = " + id, null);
    }

    /**
     * Retrieve the list with all competitors in the database.
     *
     * @return a list containing all retrieved competitors
     */
    public List<DbCompetitor> getAllCompetitors() {
        /** Initialize the output list */
        List<DbCompetitor> allCompetitors = new ArrayList<DbCompetitor>();

        /** Query the database and get the cursor */
        Cursor cursor = database.query(DbHelper.TBL_COMPETITORS, allCompetitorColumns, null, null, null, null, null);

        /* Loop on cursor to get all records */
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            DbCompetitor competitor = cursorToCompetitor(cursor);
            /** Add record to output list */
            allCompetitors.add(competitor);
            cursor.moveToNext();
        }

        /** Close cursor and return list */
        cursor.close();
        return allCompetitors;
    }

    /**
     * Retrieve a single DbCompetitor (competitor record)
     *
     * @param id the id of the competitor to retrieve
     * @return the retrieved record, or NULL if no such id exists
     */
    public DbCompetitor getCompetitor(long id) {
        /** Prepare select query parameters */
        String select = DbHelper.COL_CP_ID + "=?";
        String[] selectArgs = { Long.toString(id) };
        /** Perform query and get cursor */
        Cursor cursor = database.query(DbHelper.TBL_COMPETITORS, allCompetitorColumns, select, selectArgs, null, null, null);

        /** Declare return object */
        DbCompetitor result;

        /** If cursor contains no record, set return object as NULL */
        if (cursor.getCount() == 0) {
            result = null;
            /** Else, retrieve the first (only) returned object */
        } else {
            cursor.moveToFirst();
            result = cursorToCompetitor(cursor);
        }

        /** Close cursor and return appropriate object */
        cursor.close();
        return result;
    }

    /**
     * Turn cursor into DbCompetitor object
     *
     * @param cursor a record from the database
     * @return a DbCompetitor object with cursor data
     */
    private DbCompetitor cursorToCompetitor(Cursor cursor) {
        /** Create new empty DbCompetitor object */
        DbCompetitor competitor = new DbCompetitor();
        /** Set id from cursor, as first long column */
        competitor.setId(cursor.getLong(0));
        /** Set name from cursor, as first string column */
        competitor.setName(cursor.getString(0));
        /** Return competitor with values */
        return competitor;
    }

    /**
     * Create a new Vote record with the given vote and the given voter and competitor identifiers.
     *
     * @param vrId the voter identifier
     * @param cpId the competitor identifier
     * @param vote the vote
     * @return a new DbVote object with the given competitor and voter identifier, the given vote
     * and a database-retrieved id
     */
    public DbVote createDbVote(long vrId, long cpId, double vote) {
        /** Initialize ContentValues object for insert operation */
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_VT_COMPETITORID, cpId);
        values.put(DbHelper.COL_VT_VOTERID, vrId);
        values.put(DbHelper.COL_VT_VOTE, vote);

        /** Insert the record with the given values and retrieve the new id */
        long insertId = database.insert(DbHelper.TBL_VOTES, null, values);

        /** Retrieve the new record through a Cursor */
        Cursor cursor = database.query(
                DbHelper.TBL_VOTES,
                allVoteColumns,
                DbHelper.COL_VT_ID + " = " + insertId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        DbVote dbVote = cursorToVote(cursor);
        cursor.close();

        /** Return the new record as a DbVote object */
        return dbVote;
    }

    /**
     * Delete the Vote record with the corresponding id.
     *
     * @param vote the vote whose data must be deleted
     */
    public void deleteDbVote(DbVote vote) {
        /** Get the id from the input object */
        long id = vote.getId();
        /** Delete the votes with the corresponding id */
        database.delete(DbHelper.TBL_VOTES, DbHelper.COL_VT_ID + " = " + id, null);
    }

    /**
     * Retrieve the list with all votes for the given competitor.
     *
     * @param cpId the competitor identifier
     * @return a list containing all votes assigned to the given competitor
     */
    public List<DbVote> getVotesForCompetitor(long cpId) {
        /** Initialize output list */
        List<DbVote> voteList = new ArrayList<DbVote>();

        /** Set query where clause and parameters */
        String whereClause = DbHelper.COL_VT_COMPETITORID + "=?";
        String[] whereArgs = {Long.toString(cpId)};

        /** Query the database and get the cursor */
        Cursor cursor = database.query(DbHelper.TBL_VOTES, allVoteColumns, whereClause, whereArgs, null, null, null);

        /* Loop on cursor to get all records */
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            DbVote vote = cursorToVote(cursor);
            /** Add record to output list */
            voteList.add(vote);
            cursor.moveToNext();
        }

        /** Close cursor and return list */
        cursor.close();
        return voteList;
    }

    /**
     * Retrieve the vote assigned to the given competitor by the given voter.
     *
     * @param cpId the competitor identifier
     * @param vrId the voter identifier
     * @return the queried vote
     */
    public DbVote getVoteForCompetitorAndVoter(long cpId, long vrId) {
        /** Initialize output object */
        DbVote vote = null;

        /** Set query where clause and parameters */
        String whereClause = DbHelper.COL_VT_COMPETITORID + "=? AND " + DbHelper.COL_VT_VOTERID + "=?";
        String[] whereArgs = {Long.toString(cpId), Long.toString(vrId)};

        /** Query the database and get the cursor */
        Cursor cursor = database.query(DbHelper.TBL_VOTES, allVoteColumns, whereClause, whereArgs, null, null, null);

        /** If cursor retrieves exactly one vote, get the vote */
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            vote = cursorToVote(cursor);
        }

        /** Close cursor and return output object */
        cursor.close();
        return vote;
    }

    /**
     * Turn cursor into DbVote object
     *
     * @param cursor a record from the database
     * @return a DbVote object with cursor data
     */
    private DbVote cursorToVote(Cursor cursor) {
        /** Create new empty DbVote object */
        DbVote vote = new DbVote();
        /** Set id from cursor, as first long column */
        vote.setId(cursor.getLong(0));
        /** Set competitor id from cursor, as second long column */
        vote.setCompetitorId(cursor.getLong(1));
        /** Set voter id from cursor, as third long column */
        vote.setVoterId(cursor.getLong(2));
        /** Set voter from cursor, as first double column */
        vote.setVote(cursor.getDouble(0));

        /** Return vote with values */
        return vote;
    }

    /**
     * Read all data from database and put in a Competitor object list.
     *
     * @return a list of Competitor with all database data
     */
    public List<Competitor> readAllFromDb() {
        /** Create a new empty Competitor list */
        List<Competitor> result = new ArrayList<Competitor>();

        /** Get all DbCompetitor objects from database */
        List<DbCompetitor> dbCompetitors = getAllCompetitors();
        /** Get all DbVoter objects from database */
        List<DbVoter> dbVoters = getAllVoters();

        /** Put DbCompetitor data in Competitor objects, for each retrieved element */
        for (DbCompetitor dbCompetitor : dbCompetitors) {
            /** Create a new Competitor object with database-retrieved id and name */
            Competitor competitor = new Competitor(dbCompetitor.getId(), dbCompetitor.getName());

            /** Create Voter list in Competitor object */
            for (DbVoter dbVoter : dbVoters) {
                /** Create a new Voter object with database-retrieved id and name */
                Voter voter = new Voter(dbVoter.getId(), dbVoter.getName());

                /** Get vote for newly created voter and competitor */
                DbVote dbVote = getVoteForCompetitorAndVoter(competitor.getId(), voter.getId());
                voter.setVote(dbVote.getVote());

                /** Add voter to Competitor list */
                competitor.getVoters().add(voter);
            }

            /** Set aggregate vote */
            competitor.setVote(Aggregator.aggregate(competitor.getVoters(), competitor.getMethod()));
            /** Add competitor to result list */
            result.add(competitor);
        }

        /** Return list */
        return result;
    }

    /**
     * Write input Competitor list data to database from scratch
     *
     * @param competitorList a list with all data to save
     * @return TRUE if the update operation performed successfully, FALSE if not
     */
    public boolean writeAllToDb(List<Competitor> competitorList) {
        Log.d(LOG_PREFIX, "Write " + competitorList.size() + " records to database");
        /** Initialize result and check variables */
        boolean result = false;
        int affectedRows = 0;

        /** Extract voters */
        List<Voter> voterList = competitorList.get(0).getVoters();

        /** Writing operation is performed within a transaction */
        database.beginTransaction();

        /** Loop on extracted voters to write them down */
        for (Voter voter : voterList) {
            /** Extract voter from database */
            DbVoter dbVoter = getVoter(voter.getId());
            /** If database returns no result, insert voter */
            if (null == dbVoter) {
                /** Set ContentValues object for insert operation */
                ContentValues values = new ContentValues();
                values.put(DbHelper.COL_VR_NAME, voter.getName());
                /** Perform insert */
                long insId = database.insert(DbHelper.TBL_VOTERS, null, values);
                voter.setId(insId);
            }
        }

        /** Loop on all Competitor objects in the list */
        compLoop : for (Competitor competitor : competitorList) {
            /** If competitor with such id does not exist, insert new record */
            if (null == getCompetitor(competitor.getId())) {
                /** Set ContentValues object for insert operation */
                ContentValues values = new ContentValues();
                values.put(DbHelper.COL_CP_ID, competitor.getId());
                values.put(DbHelper.COL_CP_NAME, competitor.getName());
                /** Perform insert */
                long insId = database.insert(DbHelper.TBL_COMPETITORS, null, values);
                competitor.setId(insId);
            }
            /** Also loop on all Voters for each Competitor */
            for (Voter voter : competitor.getVoters()) {
                Log.d(LOG_PREFIX, "Updating vote for competitor " + competitor.getId() + " and voter " + voter.getId());
                /** Update the vote */
                int updateResult = updateVote(competitor.getId(), voter);

                Log.d(LOG_PREFIX, "Update result: " + updateResult);

                /** If update operation modified exactly 1 row,
                 * update check variable and continue
                 */
                if (1 == updateResult) {
                    affectedRows += updateResult;
                    result = true;
                } else {
                    /** If update affected 0 or more than 1 rows,
                     * break loop on Competitor list
                     */
                    result = false;
                    break compLoop;
                }
            }
        }

        if (result) {
            /** If the update operation modified exactly (number of voters * number of competitors) rows,
             * set transaction as successful and result as true
             */
//        if (affectedRows == competitorList.size() * competitorList.get(0).getVoters().size()) {
            database.setTransactionSuccessful();
            result = true;
        }
        /** End transaction */
        database.endTransaction();

        /** Return boolean result */
        return result;
    }

    /**
     * Update the vote on database for given competitor and voter
     *
     * @param compId the competitor id
     * @param voter the Voter object with voter id and vote
     * @return an integer with the result of the update operation,
     * that is 1 if the operation was successful, 0 or -1 else
     */
    public int updateVote(long compId, Voter voter) {
        /** Prepare where clause and arguments */
        String where = DbHelper.COL_VT_COMPETITORID + "=? AND " + DbHelper.COL_VT_VOTERID + "=?";
        String[] whereArgs = {Long.toString(compId), Long.toString(voter.getId())};

        /** Get vote record with specified voter and competitor IDs, in order to get the record id */
        Cursor cursor = database.query(DbHelper.TBL_VOTES, allVoteColumns, where, whereArgs, null, null, null);

        /** If query returned no records, perform creation */
        if (cursor.getCount() == 0) {
            /** Initialize ContentValues object with values to insert */
            ContentValues values = new ContentValues();
            values.put(DbHelper.COL_VT_COMPETITORID, compId);
            values.put(DbHelper.COL_VT_VOTERID, voter.getId());
            values.put(DbHelper.COL_VT_VOTE, voter.getVote());
            /** Perform insert */
            long insId = database.insert(DbHelper.TBL_VOTES, null, values);
            /** Close cursor and return positive result */
            cursor.close();
            return 1;
        }

        /** If query returned else than 1 record, exit with error */
        if (cursor.getCount() != 1) {
            return -1;
        }

        /** Get DbVote object from cursor */
        cursor.moveToFirst();
        DbVote dbVote = cursorToVote(cursor);
        cursor.close();

        /** Get input vote and initialize ContentValues object */
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_VT_VOTE, voter.getVote());

        /** Prepare where clause and arguments for update operation */
        String updateWhere = DbHelper.COL_VT_ID + "=? AND " +
                DbHelper.COL_VT_VOTERID + "=? AND " +
                DbHelper.COL_VT_COMPETITORID + "=?";
        String[] updateArgs = {
                Long.toString(dbVote.getId()),
                Long.toString(dbVote.getVoterId()),
                Long.toString(dbVote.getCompetitorId())
        };

        /** Perform update operation and return */
        return database.update(DbHelper.TBL_VOTES, values, updateWhere, updateArgs);
    }
}
