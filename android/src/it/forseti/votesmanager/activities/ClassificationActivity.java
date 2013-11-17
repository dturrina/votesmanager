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
package it.forseti.votesmanager.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.forseti.votesmanager.R;
import it.forseti.votesmanager.bean.Competitor;
import it.forseti.votesmanager.utils.CompetitorsContent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Show the complete Competitor classification.
 * 
 * Manage the classification presentation; the Activity takes the items from the
 * CompetitorsContent class, sorts them with the reverse (from highest to
 * lowest-voted Competitor) comparator and presents the sorted list within a
 * ListView.
 * All operations are invoked inside the onCreate() method.
 * 
 * @todo The comparator should be configurable, instead of hardcoded.
 * 
 * @author dturrina
 * @see    SuperActivity
 * @see    android.app.Activity
 * @since  0.1
 */
public class ClassificationActivity extends SuperActivity {
	
	private static final String LOG_PREFIX = ClassificationActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Set Activity layout */
		setContentView(R.layout.activity_classification);
		
		/** Retrieve Competitor list from CompetitorsContent */
		List<Competitor> list = CompetitorsContent.ITEMS;
		
		/** Sort the list */
		Collections.sort(list, Competitor.getReverseComparator());
		
		/** Get a (sorted) String list, each entry holding the name and
		 * aggregated vote
		 */
		List<String> stringList = new ArrayList<String>();
		for (Competitor c : list) {
			stringList.add(c.nameAndVoteString());
		}
		
		/** Show the (sorted) String list within the ListView */
		ListView lvClass = (ListView) findViewById(R.id.lvClassification);
		lvClass.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList));
	}

}
