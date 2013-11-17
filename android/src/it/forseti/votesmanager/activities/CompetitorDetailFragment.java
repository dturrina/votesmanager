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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import it.forseti.votesmanager.R;
import it.forseti.votesmanager.bean.Competitor;
import it.forseti.votesmanager.engine.Aggregator;
import it.forseti.votesmanager.utils.CompetitorsContent;
import it.forseti.votesmanager.utils.VoterAdapter;
import it.forseti.votesmanager.utils.XmlManager;

/**
 * A fragment representing a single Competitor detail screen.
 * 
 * This fragment is either contained in a CompetitorListActivity in two-pane
 * mode (on tablets) or a CompetitorDetailActivity on handsets.
 * In order to provide a single OnClickListener for all Buttons inside the
 * Fragment, it implements the android.view.View.OnClickListener interface.
 * 
 * @todo Use a Loader in onCreate() method to load the content specified by the
 * fragment arguments.
 * @todo Load aggregation method from configuration.
 * 
 * @author dturrina
 * @see    android.support.v4.app.Fragment
 * @see    android.view.View.OnClickListener
 * @since  0.1
 */
public class CompetitorDetailFragment extends Fragment implements OnClickListener {
	
	private static final String LOG_PREFIX = CompetitorDetailActivity.class.getSimpleName();
	
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "competitor_detail";

	/**
	 * The content this fragment is presenting.
	 */
	private Competitor mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CompetitorDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the content specified by the fragment
			// arguments. TODO Use a Loader
			mItem = CompetitorsContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	/**
	 * Fills the layout in with what is not available at Activity creation.
	 * 
	 * Method called <u>just after</u> the Activity creation.
	 * Its purpose is to end the layout completion by adding the required
	 * data.
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(
	 * android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** Inflates the container with the correct layout */
		View rootView = inflater.inflate(R.layout.fragment_competitor_detail,
				container, false);
		
		/** Calls external method to load data */
		loadData(rootView);

		/** Returns the completed View */
		return rootView;
	}

	/**
	 * Method to implement OnClickListener interface.
	 * 
	 * When the "Submit votes" button is tapped, the application saves the votes
	 * in an XML file.
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Log.d(LOG_PREFIX, "Inside onClick"); /**< Checks the method has been called */
		
		/** saveVotes() method is called only if the View id is the expected
		 * one
		 */
		if (v.getId() == R.id.buttonVote) {
			saveVotes(v);
		} else {
			return;
		}		
	}
	
	/**
	 * Method to save votes in a XML file.
	 * 
	 * @param v The tapped Button -- as this method is called by OnClick method.
	 */
	private void saveVotes(View v) {
		/** Get the View parent, i.e. the container */
		View parent = (View) v.getParent();
		/** Get the ListView to be filled with voters */
		ListView lvItems = (ListView) parent.findViewById(R.id.listVoters);
		
		/** Loop on Voters to get their name and vote, and set the text */
		for (int i=0; i < lvItems.getChildCount(); i++) {
			View item = lvItems.getChildAt(i);
			EditText etVote = (EditText) item.findViewById(R.id.etVote);
			mItem.getVoters().get(i).setVote(Double.parseDouble(etVote.getText().toString()));
		}
		
		/** Aggregate votes and set the aggregate value to the Competitor
		 * variable
		 */
		// TODO Load method identifier from configuration
		mItem.setVote(Aggregator.aggregate(mItem.getVoters(), Aggregator.SUM));
		
		/** Instantiate XmlManager and call method to save votes to XML file */
		XmlManager mgr = new XmlManager(getActivity());
		mgr.addVotesToFile(mItem, "data.xml");

		/** Refresh view */
		loadData(parent);
	}
	
	/**
	 * Load data to fill the View in.
	 * 
	 * @param v The View to be filled in
	 */
	private void loadData(View v) {
		/** Check the View is the expected one */
		if (v.getId() == R.id.competitorDetail) {
			/** Show the content as text in a TextView. */
			if (mItem != null) {
				/** Set the Adapter for the Voter list */
				ListView lvVoters = (ListView) v.findViewById(R.id.listVoters);
				lvVoters.setAdapter(new VoterAdapter(getActivity(), R.layout.competitor_detail_list_item, mItem.getVoters()));

				/** Aggregate votes and update title TextView */
				// TODO Load method identifier from configuration
				mItem.setVote(Aggregator.aggregate(mItem.getVoters(), Aggregator.SUM));
				
				((TextView) v.findViewById(R.id.competitorName))
					.setText(mItem.getName() + " - "+mItem.getVote());
			}

			/** Set OnClickListener to "save" button */
			Button btnVote = (Button) v.findViewById(R.id.buttonVote);
			btnVote.setOnClickListener(this);
		}
	}
}
