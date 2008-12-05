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
 * Created on March 26, 2007
 */
package ca.uottawa.balie;

import java.util.Hashtable;

/**
 * Routines specific to Italian language.
 * 
 * TODO: THIS CLASS NEEDS TO BE FILL WITH ITALIAN KNOWLEDGE!
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LanguageSpecificItalian extends LanguageSpecific {

    /**
     * Contruct library of english Spanish data and routines
     */
    public LanguageSpecificItalian() {
    }
    
    /* (non-Javadoc)
     * @see ca.uottawa.balie.LanguageSpecific#Decompound(java.lang.String)
     */
    public String[] Decompound(String pi_Composed) {
	    pi_Composed = CommonFilter(pi_Composed);	
		return pi_Composed.split(" ");
    }
    
	/* (non-Javadoc)
	 * @see ca.uottawa.balie.LanguageSpecific#GetAbbreviations()
	 */
	public Hashtable<String, Integer> GetAbbreviations() {
		Hashtable<String, Integer> hAbbreviations = new Hashtable<String, Integer>();
		return hAbbreviations;
	}

}
