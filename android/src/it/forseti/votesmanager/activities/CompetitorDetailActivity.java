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

import it.forseti.votesmanager.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An Activity representing a single Competitor detail screen.
 * 
 * This activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items in a
 * CompetitorListActivity.
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a CompetitorDetailFragment.
 * 
 * @author dturrina
 * @see    CompetitorDetailFragment
 * @see    android.support.v4.app.FragmentActivity
 * @since  0.1
 */
public class CompetitorDetailActivity extends FragmentActivity {

	private static final String LOG_PREFIX = CompetitorDetailActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set Activity layout
		setContentView(R.layout.activity_competitor_detail);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(
					CompetitorDetailFragment.ARG_ITEM_ID,
					getIntent().getStringExtra(
							CompetitorDetailFragment.ARG_ITEM_ID));
			CompetitorDetailFragment fragment = new CompetitorDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.competitor_detail_container, fragment).commit();
		}
	}
}
