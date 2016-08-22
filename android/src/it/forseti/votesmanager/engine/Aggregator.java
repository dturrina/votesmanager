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

import java.util.ArrayList;
import java.util.List;

/**
 * Provide static methods to aggregate votes.
 * It is to be called through the only public method.
 * 
 * 4 algorithms are available:
 * - standard sum
 * - standard (arithmetic) average
 * - weighted sum
 * - weighted average
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
	public static final int SSUM = 0;
	public static final int SAVG = 1;
	public static final int WSUM = 2;
	public static final int WAVG = 3;
	
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
		case SSUM: // Standard sum
			result = standardSum(list);
			break;
		case SAVG: // Standard arithmetic average
			result = arithmeticAverage(list);
			break;
		case WSUM: //Weighted sum
			result = weightedSum(list);
			break;
		case WAVG: //Weighted average
			result = weightedAvg(list);
			break;
		default: /** Default is standard sum */
			result = standardSum(list);
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
	private static double standardSum(List<Voter> list) {
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
		result = standardSum(list);
		result /= list.size();
		
		return result;
	}
	
	/**
	 * Weighted sum calculator
	 * 
	 * Formula-sum=(voter's vote*voter's weight)+...
	 * @param list list of voters to be used
	 * @return sum the weighted sum of the votes
	 */
	private static double weightedSum(List<Voter> list){
		double sum=0;
		/**iterates through the list getting votes and multiplying them by their weight*/ 
		for(Voter i: list){
			sum+=i.getVote()*i.getWeight();
		}
		return sum;
	}
	

	/**
	 * Weighted Average calculator
	 * 
	 * Formula-avg=(weighted sum)/(sum of weights)
	 * @param list list of voters to be used
	 * @return avg the weighted average of the votes
	 */
	private static double weightedAvg(List<Voter> list){
		double sumVotes=0;
		double sumWeights=0;
		/**iterates through the list getting votes and multiplying them by their weight
		 * and sum up weights*/
		for(Voter i: list){
			sumVotes+=i.getVote()*i.getWeight();
			sumWeights+=i.getWeight();
		}
		double wAvg=sumVotes/sumWeights;
		return wAvg;
	}
	
}
