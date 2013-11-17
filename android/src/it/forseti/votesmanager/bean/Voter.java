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

import java.io.Serializable;

/**
 * Class holding the Voter data.
 * 
 * Implements Serializable to be easily sent via Intent.
 * 
 * @author dturrina
 * @see    Serializable
 * @since  0.1
 */
public class Voter implements Serializable{
	
	private static final String LOG_PREFIX = Voter.class.getSimpleName();

	private static final long serialVersionUID = 8858015563559493194L;
	
	/**
	 * Each Voter has a name and an assigned vote.
	 */
	private String name;
	private double vote = 0.0; /**< Default value 0.0
	
	/**
	 * Name-only constructor
	 * 
	 * @param name the voter name
	 */
	public Voter(String name) {
		/** If the input string is null or empty,
		 * set the name as an empty String
		 */
		if ((name == null) || (name.isEmpty())) {
			this.name = "";
		} else {
			this.name = name;
		}
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
	 * Vote getter
	 * 
	 * @return the vote
	 */
	public double getVote() {
		return vote;
	}

	/**
	 * Vote setter
	 * 
	 * @param vote the vote to set
	 */
	public void setVote(double vote) {
		this.vote = vote;
	}
	
}
