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

import it.forseti.votesmanager.R;
import it.forseti.votesmanager.bean.Voter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Adapter for Voter objects.
 * 
 * @author dturrina
 * @see    ArrayAdapter
 * @since  0.1
 */
public class VoterAdapter extends ArrayAdapter<Voter> {

	private static final String LOG_PREFIX = VoterAdapter.class.getSimpleName();
	
	/**
	 * Resource and LayoutInflater to be used through all class
	 */
	private int resource;
	private LayoutInflater inflater;
	
	/**
	 * Constructor
	 * 
	 * @param context the application or activity context
	 * @param resource the resource to be filled in
	 * @param objects the list of objects
	 */
	public VoterAdapter(Context context, int resource, List<Voter> objects) {
		super(context, resource, objects);

		/** Sets resource and LayoutInflater */
		this.resource = resource;
		this.inflater = LayoutInflater.from(context);
	}
	
	/**
	 * Overrides getView to fill in the custom layout
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		/** Get item */
		Voter voter = getItem(position);
		
		/** Prepare ViewHolder */
		ViewHolder holder;

		/** Instantiate ViewHolder if input View is null */
		if (v == null) {
			v = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) v.findViewById(R.id.voterName);
			holder.voteEditText = (EditText) v.findViewById(R.id.etVote);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		/** Set text to view holder */
		holder.nameTextView.setText(voter.getName());
		holder.voteEditText.setText(Double.toString(voter.getVote()));

		return v;
	}

	/**
	 * Private class to hold View elements
	 * 
	 * @author dturrina
	 * @since  0.1
	 */
	private static class ViewHolder {
		TextView nameTextView;
		EditText voteEditText;
	}
	
}
