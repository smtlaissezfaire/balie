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
 * Created on Feb 1, 2005
 */
package ca.uottawa.balie;

import java.util.Hashtable;

/**
 * Wraps around a hashtable that contains a list of ligatures.
 * A ligature is a char like 'oe' (often from pdf files) that represents
 * two single char ('o' and 'e')
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LigatureLookup {
	
    /**
     * Fill table at construction time.
     */
	public LigatureLookup() {
		if(Balie.DEBUG_LIGATURE) DebugInfo.Out("Initializing Ligatures");
		m_Ligatures = new Hashtable<String,String>();
		
		// detect common ligatures
		m_Ligatures.put("œ", "oe");
		m_Ligatures.put("æ", "ae");
		m_Ligatures.put("ﬀ", "ff");
		m_Ligatures.put("ﬁ", "fi");
		m_Ligatures.put("ﬂ", "fl");
		m_Ligatures.put("ﬃ", "ffi");
		m_Ligatures.put("ﬄ", "ffl");
		m_Ligatures.put("ĳ", "ij");
		m_Ligatures.put("ﬅ", "ft");
		m_Ligatures.put("ﬆ", "st");
		
		// also handle german ß
		m_Ligatures.put("ß", "ss");
	}

	/**
	 * Gets the string equivalence for a ligature char.
	 * 
	 * @param pi_Char	The ligature char (String of lenght 1) 
	 * @return The equivalent String (lenght 2,3 or more)
	 */
	public String GetEquivalence(String pi_Char) {
		String strEquivalent = null;
		if (m_Ligatures.containsKey(pi_Char)) {
			strEquivalent = (String)m_Ligatures.get(pi_Char);
		}
		return strEquivalent; 
	}

	private Hashtable<String,String> m_Ligatures;

}
