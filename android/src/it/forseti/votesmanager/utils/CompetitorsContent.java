/*
 * Copyright (c) 2013, Forseti and the VotesManager project. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package it.forseti.votesmanager.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.forseti.votesmanager.bean.Competitor;
import it.forseti.votesmanager.bean.Voter;
import it.forseti.votesmanager.database.DataSource;
import it.forseti.votesmanager.database.DbHelper;
import it.forseti.votesmanager.engine.Aggregator;
import it.forseti.votesmanager.exception.DatabaseException;

/**
 * Helper for Fragments to hold a list of Competitor.
 * 
 * @author dturrina
 * @since  0.1
 */
public class CompetitorsContent {

	private static final String LOG_PREFIX = CompetitorsContent.class.getSimpleName();
	
	/**
	 * Holds a list of Competitor and the corresponding Map, whose key is the Competitor id
	 */
    public static List<Competitor> ITEMS = new ArrayList<Competitor>();
    public static Map<String, Competitor> ITEM_MAP = new HashMap<String, Competitor>();

    /**
     * Load competitor lists, choosing the appropriate source (database or XML) automatically.
     *
     * @param context the application context
     * @throws DatabaseException if database creation/update does not perform correctly
     */
    public static void loadCompetitors(Context context) throws DatabaseException {
        /** If database exists, load data from it, then write to XML file */
        if (DbHelper.existsDb()) {
            loadCompetitorsFromDb(context);
            writeAllToXml(context);
        } else {
            /** If database does not exist, load data from XML */
            loadCompetitorsFromXML(context);

            /** Try writing data to database */
            DataSource ds = new DataSource(context);
            ds.openDatabase();
            boolean result = ds.writeAllToDb(ITEMS);
            ds.closeDatabase();

            /** If result is false, throw DatabaseException */
            if (! result) {
                throw new DatabaseException();
            }
        }
    }

    /**
     * Read data from database and update lists
     *
     * @param context the application context
     */
    private static void loadCompetitorsFromDb(Context context) {
        /** Clear current list */
        ITEMS.clear();

        /** Create data source and open database */
        DataSource ds = new DataSource(context);
        ds.openDatabase();

        /** For each competitor, add item to lists */
        for (Competitor cp : ds.readAllFromDb()) {
            Log.d(LOG_PREFIX, cp.nameAndVoteString());
            addItem(cp);
        }

        /** Close database */
        ds.closeDatabase();
    }

    /**
     * Load Competitors data from an XML file.
     * TODO: Get XML filename from configuration.
     * 
     * @param ctx the application context
     */
    private static void loadCompetitorsFromXML(Context ctx) {
    	
    	/** Clear current list */
    	ITEMS.clear();
    	
    	/** Instantiate XML manager */
    	XmlManager manager = new XmlManager(ctx);
    	
    	/** Parse XML to get competitors */
    	List<Competitor> lc = manager.getCompetitors("data.xml");
    	
    	/** Add competitors to list and map */
    	for (Competitor c : lc) {
    		addItem(c);
    		Log.d(LOG_PREFIX, c.nameAndVoteString());
    		for (Voter v : c.getVoters()) {
    			Log.d(LOG_PREFIX, Double.toString(v.getVote()));
    		}
    	}
    }

    /**
     * Write all data to XML file
     * TODO: Get XML filename from configuration
     *
     * @param context the application context
     */
    private static void writeAllToXml(Context context) {
        /** Create XmlManager instance */
        XmlManager manager = new XmlManager(context);

        /** For each Competitor object in list, aggregate votes and write to file */
        for (Competitor competitor : ITEMS) {
            competitor.setVote(Aggregator.aggregate(competitor.getVoters(), Aggregator.SUM));
            manager.addVotesToFile(competitor, "data.xml");
        }
    }

    /**
     * Add a Competitor to both list and map
     * 
     * @param item the Competitor to add
     */
    private static void addItem(Competitor item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getIdString(), item);
    }
    
    /**
     * Get a list of all Competitor names for presentation purpose.
     * 
     * @return a list of all Competitor names
     */
    public static List<String> getCompetitorNames() {
    	List<String> names = new ArrayList<String>();
    	
    	/** Loop on the list to get Competitor name */
    	for (Competitor c : ITEMS) {
    		names.add(c.getName());
    	}
    	
    	return names;
    }
}