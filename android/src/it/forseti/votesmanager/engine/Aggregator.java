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
package it.forseti.votesmanager.engine;

import it.forseti.votesmanager.bean.Voter;

import java.util.List;

/**
 * Provide static methods to aggregate votes.
 * It is to be called through the only public method.
 * 
 * 2 algorithms are available:
 * - sum
 * - (arithmetic) average
 * 
 * @author dturrina
 * @since  0.1
 */
public class Aggregator {
	
	private static final String LOG_PREFIX = Aggregator.class.getSimpleName();

	/**
	 * Each aggregation algorithm must have its own public
	 * static final variable in order to call the appropriate
	 * (private) method from the only public aggregate() method.
	 */
	public static final int SUM = 0;
	public static final int AVG = 1;
	
	/**
	 * Public method to choose the appropriate aggregation
	 * algorithm through the input integer variable.
	 * 
	 * @param list the list of Voter objects, whose votes need to be aggregated
	 * @param method the integer identifying the aggregation algorithm
	 * @return the aggregated
	 */
	public static double aggregate(List<Voter> list, int method) {
		double result = 0.0;
		
		/** Choose the algorithm by switching on the method parameter. */
		switch (method) {
		case SUM: // sum
			result = sum(list);
			break;
		case AVG: // arithmetic average
			result = arithmeticAverage(list);
			break;
		default: /** Default is sum */
			result = sum(list);
		}
		
		return result;
	}

	/**
	 * Sum all votes.
	 * 
	 * @param list the list of Voters
	 * @return the votes aggregated with sum
	 * 
	 * @since 0.1
	 */
	private static double sum(List<Voter> list) {
		double result = 0.0;
		
		/** For each Voter, add its vote to result */
		for (Voter v : list) {
			result += v.getVote();
		}
		
		return result;
	}
	
	/**
	 * Calculate the arithmetic average of all votes.
	 * 
	 * @param list the list of Voters
	 * @return the votes aggregated with arithmetic average
	 * 
	 * @since 0.1
	 */
	private static double arithmeticAverage(List<Voter> list) {
		double result = 0.0;
		
		/** Call sum method and divide the result by the size of the list */
		result = sum(list);
		result /= list.size();
		
		return result;
	}
	
}
