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
package it.forseti.votesmanager.bean;

import it.forseti.votesmanager.engine.Aggregator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class holding the Competitor data.
 * Implements Serializable to be easily sent via Intent, if needed.
 * 
 * @author dturrina
 * @see    Serializable
 * @since  0.1
 */
public class Competitor implements Serializable {

	private static final String LOG_PREFIX = Competitor.class.getSimpleName();

	private static final long serialVersionUID = -7545697764376129993L;
	
	/**
	 * Each Competitor has a unique identifier (id), a name,
	 * the aggregated vote, a list of Voters and the aggregation
	 * algorithm identifier.
	 * Even if the aggregated vote can be easily obtained by
	 * aggregating various votes, it is <b>not</b> defined
	 * as transient to keep its value through Intents.
	 */
	private long id;
	private String name;
	private double vote;
	private List<Voter> voters = new ArrayList<Voter>();
	private int method = -1; /**< Default value -1

	/**
	 * Long and String constructor.
	 * 
	 * Set the aggregation algorithm to the default.
	 * 
	 * @param id The competitor identifier, as a long
	 * @param name The competitor name, as a String
	 */
	public Competitor(long id, String name) {
		this.setId(id);
		this.name = name;
		this.method = -1;
		
		/** Add 5 default Voter objects */
		for (int i=0; i<5; i++) {
			Voter voter = new Voter("Voter "+(i+1));
			voter.setVote(0.0);
			
			voters.add(voter);
		}
		
		/** Aggregate votes */
		this.vote = Aggregator.aggregate(voters, method);
	}
	
	/**
	 * String and String constructor.
	 * 
	 * Call the Long and String constructor, parsing the id as a long.
	 * Set the aggregation algorithm to the default.
	 * 
	 * @param id The competitor identifier, as a String
	 * @param name The competitor name, as a String
	 */
	public Competitor(String id, String name) {
		new Competitor(Long.parseLong(id), name);
	}
	
	/**
	 * Identifier getter
	 * 
	 * @return the identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * Identifier getter
	 * 
	 * @return the identifier, as a String
	 */
	public String getIdString() {
		return Long.toString(id);
	}

	/**
	 * Identifier setter
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Name getter
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Name setter
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Aggregate vote getter
	 * 
	 * @return the vote
	 */
	public double getVote() {
		return vote;
	}

	/**
	 * Aggregate vote setter
	 * 
	 * @param vote the vote to set
	 */
	public void setVote(double vote) {
		this.vote = vote;
	}

	/**
	 * Voter list getter
	 * 
	 * @return the voters
	 */
	public List<Voter> getVoters() {
		return voters;
	}

	/**
	 * Voter list setter
	 * 
	 * @param voters the voters to set
	 */
	public void setVoters(List<Voter> voters) {
		this.voters = voters;
	}
	
	/**
	 * Aggregation method getter
	 * 
	 * @return the identifier of the aggregation method
	 */
	public int getMethod() {
		return method;
	}
	
	/**
	 * Aggregation method setter
	 * 
	 * @param method the method (identifier) to set
	 */
	public void setMethod(int method) {
		this.method = method;
	}
	
	/**
	 * Return a String containing id, name and aggregated vote.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return id+" "+name+" "+vote;
	}
	
	/**
	 * Return a String containing name and vote.
	 * 
	 * @return a human-useful Competitor representation
	 */
	public String nameAndVoteString() {
		return this.name + " " + this.vote;
	}
	
	/**
	 * Retrieve a Comparator in order to sort Competitor lists
	 * from lower-voted to higher-voted.
	 * 
	 * @return a Comparator for Competitor object
	 * @see java.util.Comparator
	 */
	public static Comparator<Competitor> getForwardComparator() {
		
		return new Comparator<Competitor>() {

			@Override
			public int compare(Competitor lhs, Competitor rhs) {
				int ret = 0;
				
				if (lhs.getVote() > rhs.getVote()) {
					ret = 1;
				} else if (lhs.getVote() < rhs.getVote()) {
					ret = -1;
				}
				
				return ret;
			}
		};
	}

	
	/**
	 * Retrieve a Comparator in order to sort Competitor lists
	 * from higher-voted to lower-voted.
	 * 
	 * @return a Comparator for Competitor object
	 * @see java.util.Comparator
	 */
	public static Comparator<Competitor> getReverseComparator() {
		
		return new Comparator<Competitor>() {

			@Override
			public int compare(Competitor lhs, Competitor rhs) {
				int ret = 0;
				
				if (lhs.getVote() > rhs.getVote()) {
					ret = -1;
				} else if (lhs.getVote() < rhs.getVote()) {
					ret = 1;
				}
				
				return ret;
			}
		};
	}
	
}
