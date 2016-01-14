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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * An activity representing a list of Competitors.
 * 
 * This activity has different presentations for handset and tablet-size
 * devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a CompetitorDetailActivity representing item details. On
 * tablets, the activity presents the list of items and item details
 * side-by-side using two vertical panes.
 * The activity makes heavy use of fragments. The list of items is a
 * CompetitorListFragment and the item details (if present) is a
 * CompetitorDetailFragment.
 * This activity also implements the required CompetitorListFragment.Callbacks
 * interface to listen for item selections.
 * 
 * @author dturrina
 * @see    FragmentActivity
 * @since  0.1
 */
public class CompetitorListActivity extends FragmentActivity implements
		CompetitorListFragment.Callbacks {

	private static final String LOG_PREFIX = CompetitorListActivity.class.getSimpleName();

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	public static boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_competitor_list);

		if (findViewById(R.id.competitor_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((CompetitorListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.competitor_list))
					.setActivateOnItemClick(true);
		}
	}

	/**
	 * Callback method from CompetitorListFragment.Callbacks indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {		
		if (mTwoPane) {
			/** In two-pane mode, show the detail view in this activity by
			 *  adding or replacing the detail fragment using a
			 * fragment transaction.
			 */
			Bundle arguments = new Bundle();
			arguments.putString(CompetitorDetailFragment.ARG_ITEM_ID, id);
			CompetitorDetailFragment fragment = new CompetitorDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.competitor_detail_container, fragment)
					.commit();

		} else {
			/** In single-pane mode, simply start the detail activity
			 * for the selected item ID.
			 */
			Intent detailIntent = new Intent(this,
					CompetitorDetailActivity.class);
			detailIntent.putExtra(CompetitorDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
