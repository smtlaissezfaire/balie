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
 * Created on Apr 1, 2004
 */
package ca.uottawa.balie;

import java.util.Hashtable;


/**
 * Wraps around a hashtable that contains a list of Unbreakable tokens.
 * An unbreakable is token is a sequence of chars that would be otherwise
 * splitted by the base algorithm but that has a meaning on its own.
 * e.g.: C# is a token that should not be breaked in its constituant ('C' and '#')
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class UnbreakableLookup {

    /**
     * Initializes the hashtable with the unbreakable words.
     */
	public UnbreakableLookup() {
		if(Balie.DEBUG_UNBREAKABLE_LOOKUP) DebugInfo.Out("Initializing Unbreakable");
		m_Unbreakables = new Hashtable<String, Integer>();
		
		// Constraint: Unbreakables CANNOT start with a punctuation!
		m_Unbreakables.put("c++", new Integer(0));
		m_Unbreakables.put("C++", new Integer(0));
		m_Unbreakables.put("c#", new Integer(0));
		m_Unbreakables.put("C#", new Integer(0));
	}

	/**
	 * Checks if a word is an Unbreakable.
	 * 
	 * @param pi_Word The word to check
	 * @return True if the word is an unbreakable
	 */
	public boolean IsUnbreakable(String pi_Word) {
		return m_Unbreakables.containsKey(pi_Word);
	}
	
	private Hashtable<String, Integer> m_Unbreakables;

}
