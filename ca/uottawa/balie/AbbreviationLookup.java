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
 * Lists of abbreviations were created by Peter Turney (http://www.apperceptual.com/)
 * and are use with permission in Balie.
 */

/*
 * Created on Oct 8, 2004
 * 
 */
package ca.uottawa.balie;

import java.util.Hashtable;

/**
 * Wraps around a hashtable that contains a list of abbreviation.
 * Also offers IsAbbreviation function that uses some heuristics.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class AbbreviationLookup {

	/**
	 * Initializes the hashtable with the language specific abbreviations.
	 * 
	 * @param pi_LanguageSpecific A languageSpecific object (French, English, Spanish...)
	 */
	public AbbreviationLookup(LanguageSpecific pi_LanguageSpecific) {
		if(Balie.DEBUG_ABBREVIATION_LOOKUP) DebugInfo.Out("Initializing Abbreviations");
		m_Abbreviations = pi_LanguageSpecific.GetAbbreviations();
	}

	/**
	 * Check if a word is an abbreviation.
	 * Lookup in hashtable and uses heuristics.
	 * 
	 * @param pi_Word The string to test
	 * @return True if the word is an abbreviation
	 */
	public boolean IsAbbreviation(String pi_Word) {
		// Let's handle 1-letter words as abbreviations
		return (pi_Word.length() == 1 && Character.isLetter(pi_Word.charAt(0))) || 
		       m_Abbreviations.containsKey(pi_Word);
	}
	
	private Hashtable<String, Integer> m_Abbreviations;

}
