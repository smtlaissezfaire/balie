/*
 * Balie - BAseLine Information Extraction
 * Copyright (C) 2004-2007  David Nadeau
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
 
/*
 * Created on May 6, 2004
 */
package ca.uottawa.balie;

import java.util.Hashtable;

/**
 * Squeleton of language specific routines.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public abstract class LanguageSpecific {

	/**
	 * Decomposes a word in its parts (ex.: French decomposition on apostrophe. German word split, ...).
	 * 
	 * @param pi_Composed	A string to decompose
	 * @return	Array containing each word part
	 */
	public abstract String[] Decompound(String pi_Composed);
	
	// all languages share some decomposition like email, new line, ...
	protected String CommonFilter(String pi_Composed) {
        if (pi_Composed.indexOf("-") != -1) {
            pi_Composed = pi_Composed.replaceAll("-", " - ");       
        }
		if (pi_Composed.indexOf("@") != -1) {
			pi_Composed = pi_Composed.replaceAll("@", " @ ");		
		}
		if (pi_Composed.indexOf("\n") != -1) {
			pi_Composed = pi_Composed.replaceAll("\n", " \n ");		
		}
		if (pi_Composed.indexOf("\t") != -1) {
			pi_Composed = pi_Composed.replaceAll("\t", " \t ");		
		}
		return pi_Composed;
	}
	
	/**
	 * Gets the list of abbreviations (mainly for SBD).
	 * 
	 * @return Table of abbreviation (for fast lookup)
	 */
	public abstract Hashtable<String, Integer> GetAbbreviations();
	
	// Complement the POSLookup with language-dependant hard-coded POS
	protected void AddLanguageSpecificPOS(Hashtable<String, Integer> pio_Hash) {};
}
