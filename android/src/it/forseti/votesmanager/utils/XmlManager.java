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

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import it.forseti.votesmanager.bean.Competitor;
import it.forseti.votesmanager.bean.Voter;
import it.forseti.votesmanager.engine.Aggregator;

/**
 * Manage input/output operations through XML files.
 * 
 * @author dturrina
 * @since  0.1
 */
public class XmlManager {
	
	private static final String LOG_PREFIX = XmlManager.class.getSimpleName();
	
	/**
	 * Provide a static variable for each XML element.
	 * 
	 * Not all elements must necessarily be used.
	 */
	private final static String ROOT = "data";
	private final static String VOTERS = "voters";
	private final static String VOTER = "voter";
	private final static String COMPETITORS = "competitors";
	private final static String COMPETITOR = "competitor";
	private final static String VOTE = "vote";
	private final static String NAME = "name";
	
	/**
	 * Class-wide context and boolean flag for SD card
	 */
	private Context context;
    private boolean hasSD;
	
	/**
	 * Simple constructor.
     * If SD Card is available, set file directory to SD card; else, use internal storage
	 * 
	 * @param ctx The application context
	 */
	public XmlManager(Context ctx) {
		context = ctx;

        /** If SD card is available and writable, set flag as true */
        hasSD = Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())
                && !Environment.MEDIA_MOUNTED_READ_ONLY.equalsIgnoreCase(Environment.getExternalStorageState());
	}

	/**
	 * Parse the XML file to get the org.w3c.dom.Document element.
	 * 
	 * @param xml the XML file name
	 * @return the parsed Document
	 */
	private Document getDomElement(String xml) {
		
		// Prepare method variables
		Document doc = null;
		DocumentBuilder builder;
		
		// Prepare DocumentBuilderFactory instance
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			// Instantiate DocumentBuilder
			builder = factory.newDocumentBuilder(); // throws ParserConfigurationException
			
			// Prepare FileReader and InputSource
			FileReader fileRead = null;
			InputSource in;
			
			/** Check if given file exists on SD card.
			 * If it does, reads it from SD card and instantiates FileReader;
			 */
            Log.d(LOG_PREFIX, "Prepare file writing");
            File fileWithPath;
            if (hasSD) {
                fileWithPath = new File(Environment.getExternalStorageDirectory(), xml);
                Log.d(LOG_PREFIX, "Writing file on SD card, path: " + fileWithPath.getPath());
            } else {
                fileWithPath = new File(context.getFilesDir(), xml);
                Log.d(LOG_PREFIX, "Writing file on internal storage, path: " + fileWithPath.getPath());
            }

			if (fileWithPath.exists()) {
				Log.i(LOG_PREFIX, "Reading from storage");
				fileRead = new FileReader(fileWithPath); // throws FileNotFoundException
				
				in = new InputSource(fileRead);
			}
			/** if it does not, loads data from assets */
			else {
				Log.i(LOG_PREFIX, "Reading from Asset");
				InputStream is = context.getResources().getAssets().open(xml);
				in = new InputSource(is);
			}
			
			/** Parse document */
			doc = builder.parse(in); // throws SAXException, IOException
			
			// If FileReader was used, close it
			if (fileRead != null) {
				fileRead.close(); // throws IOException
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return doc;
	}
	
	/**
	 * Parse XML file to get voters
	 * 
	 * @param xml the XML file name
	 * @return a list of Voters
	 */
	public List<Voter> getVoters(String xml) {
		// Get Document element
		Document doc = getDomElement(xml);
		
		// Find voters
		NodeList voters = doc.getElementsByTagName(VOTER);
		
		List<Voter> result = new ArrayList<Voter>();
		
		/** Parse voter nodes to get all data */
		for (int i=0; i < voters.getLength(); i++) {
			result.add(new Voter(i, voters.item(i).getAttributes().getNamedItem(NAME).getTextContent()));
			Log.d(LOG_PREFIX, result.get(i).getName());
		}
		
		return result;
	}
	
	/**
	 * Parse XML file to get competitors
	 * 
	 * @param xml the XML file name
	 * @return a list of Competitors
	 */
	public List<Competitor> getCompetitors(String xml) {
		// Get Document element
		Document doc = getDomElement(xml);
		
		// Get both competitors and voters
		NodeList competitors = doc.getElementsByTagName(COMPETITOR);
		List<Voter> voters = getVoters(xml);
		
		Log.d(LOG_PREFIX, "Voters: "+voters.size()+" | Competitors: "+competitors.getLength());
		
		// Prepare competitor list
		List<Competitor> result = new ArrayList<Competitor>();
		
		/** Loop through all competitor nodes */
		for (int i=0; i < competitors.getLength(); i++) {
			// Get current competitor node
			Node comp = competitors.item(i);
			
			/** Create Competitor objects */
			Competitor current = new Competitor(i, comp.getAttributes().getNamedItem(NAME).getTextContent());
			
			// Get vote nodes
			NodeList votes = ((Element)comp).getElementsByTagName(VOTE);
			
			/** Loop through all voters */
			for (int j=0; j < voters.size(); j++) {
				/** Get Voter and vote */
				Voter voter = new Voter(j, voters.get(j).getName());
				String vote = votes.item(j).getTextContent().trim();
				/** Set vote: if null or empty, set default 0.0 value */
				if (vote.isEmpty()) {
					voter.setVote(0.0);
				} else {
					voter.setVote(Double.parseDouble(vote));
				}
				Log.d(LOG_PREFIX, "Vote "+voter.getVote()+" assigned");
				current.getVoters().set(j, voter);
			}
			
			/** Add Competitor to output list */
			current.setVote(Aggregator.aggregate(voters, Aggregator.SUM));
			result.add(current);
		}
		
		return result;
	}
	
	/**
	 * Write assigned votes to XML file
	 * 
	 * @param competitor the Competitor whose votes have been updated
	 * @param xml the XML file name
	 */
	public void addVotesToFile(Competitor competitor, String xml) {
		/** Parse XML file and gets Document */
		Document doc = getDomElement(xml);
		
		// Get Competitor list
		NodeList competitors = doc.getElementsByTagName(COMPETITOR);
		
		/** Loop through all competitors in XML file */
		for (int i=0; i < competitors.getLength(); i++) {
			Node current = competitors.item(i);
			
			/** Get XML and Java object competitor names */
			String xmlCompName = current.getAttributes().getNamedItem(NAME).getTextContent();
			String javaCompName = competitor.getName();
			
			/** If names are the same, write vote into XML Document and break */
			if (xmlCompName.equalsIgnoreCase(javaCompName)) {
				NodeList votes = ((Element)current).getElementsByTagName(VOTE);
				for (int j=0; j < votes.getLength(); j++) {
					Node thisVote = votes.item(j);
					thisVote.setTextContent(Double.toString(competitor.getVoters().get(j).getVote()));
					Log.d(LOG_PREFIX,thisVote.getTextContent());
				}
				break;
			}
			/** If names are different, proceed with next XML competitor */
		}
		
		// Prepare DOM source from Document
		DOMSource source = new DOMSource(doc);

		try {
			// Instantiate TransformerFactory and Transformer
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(); // throws TransformerConfigurationException

            Log.d(LOG_PREFIX, "Prepare file writing");
			
			// Prepare FileWriter to write down data
            File fileWithPath;
            if (hasSD) {
                fileWithPath = new File(Environment.getExternalStorageDirectory(), xml);
                Log.d(LOG_PREFIX, "Writing file on SD card, path: " + fileWithPath.getPath());
            } else {
                fileWithPath = new File(context.getFilesDir(), xml);
                Log.d(LOG_PREFIX, "Writing file on internal storage, path: " + fileWithPath.getPath());
            }
			FileWriter writer = new FileWriter(fileWithPath);
			
			/** Write data to file on SD card */
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
