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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import it.forseti.votesmanager.R;
import it.forseti.votesmanager.exception.DatabaseException;
import it.forseti.votesmanager.utils.CompetitorsContent;

/**
 * A list fragment representing a list of Competitors.
 * 
 * This fragment also supports tablet devices by allowing list items to be given
 * an 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a CompetitorDetailFragment.
 * Activities containing this fragment MUST implement the Callbacks interface.
 * 
 * TODO In onCreate() method, replace the ArrayAdapter with a custom Adapter.
 * 
 * @author dturrina
 * @see    ListFragment
 * @see    android.view.View.OnClickListener
 * @since  0.1
 */
public class CompetitorListFragment extends ListFragment implements OnClickListener {

	private static final String LOG_PREFIX = CompetitorListFragment.class.getSimpleName();

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}

	/**
	 * A dummy implementation of the Callbacks interface that does nothing.
	 * Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CompetitorListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Load content of list */
        try {
            CompetitorsContent.loadCompetitors(this.getActivity());
        } catch (DatabaseException e) {
            Log.e(LOG_PREFIX, "Exception in loading/updating database objects");
            Toast.makeText(this.getActivity().getApplicationContext(), "Database error", Toast.LENGTH_LONG).show();
            this.getActivity().finish();
        }

		// TODO: replace with a real list adapter.
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, CompetitorsContent.getCompetitorNames()));
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		/** Restore the previously serialized activated item position. */
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
		
		/** Set Button OnClickListener */
		Button btnClass = (Button) getView().findViewById(R.id.buttonClassification);
		btnClass.setOnClickListener(this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		/** Activities containing this fragment must implement its callbacks. */
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		/** Reset the active callbacks interface to the dummy implementation. */
		mCallbacks = sCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		/**
		 * Notify the active callbacks interface (the activity, if the
		 * fragment is attached to one) that an item has been selected.
		 */
		mCallbacks.onItemSelected(CompetitorsContent.ITEMS.get(position).getIdString());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			/** Serialize and persist the activated item position. */
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turn on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		/**
		 * When setting CHOICE_MODE_SINGLE, ListView will automatically
		 * give items the 'activated' state when touched.
		 */
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_competitor_list, container, false);
	}

	/**
	 * Implement OnClickListener
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonClassification) {
			/** Start ClassificationActivity */
			Intent intent = new Intent(getActivity(), ClassificationActivity.class);
			/* Extra is no more needed as the same CompetitorsContent instance
			 * is accessible from every Activity of the application.
			 * Left for any future need.
			 */
//			intent.putExtra("comp", (ArrayList<Competitor>) CompetitorsContent.ITEMS);
			startActivity(intent);
		}
		
	}
}
