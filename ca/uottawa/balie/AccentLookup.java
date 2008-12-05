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
 * File created on 06-10-04
 */
package ca.uottawa.balie;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Lookup for accent equivalences
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class AccentLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Initialize the table with accent equivalence
     */
    public AccentLookup() {
        m_AccentEquivalence = new Hashtable<String,String>();

        // lowercased
        m_AccentEquivalence.put("�", "a");        
        m_AccentEquivalence.put("�", "a");        
        m_AccentEquivalence.put("�", "a");        
        m_AccentEquivalence.put("�", "a");        
        m_AccentEquivalence.put("�", "a");        
        m_AccentEquivalence.put("�", "a");        

        m_AccentEquivalence.put("�", "c");        

        m_AccentEquivalence.put("�", "e");        
        m_AccentEquivalence.put("�", "e");        
        m_AccentEquivalence.put("�", "e");        
        m_AccentEquivalence.put("�", "e");        

        m_AccentEquivalence.put("�", "i");        
        m_AccentEquivalence.put("�", "i");        
        m_AccentEquivalence.put("�", "i");        
        m_AccentEquivalence.put("�", "i");        
        
        m_AccentEquivalence.put("�", "n");        

        m_AccentEquivalence.put("�", "o");        
        m_AccentEquivalence.put("�", "o");        
        m_AccentEquivalence.put("�", "o");        
        m_AccentEquivalence.put("�", "o");        
        m_AccentEquivalence.put("�", "o");

        m_AccentEquivalence.put("�", "u");
        m_AccentEquivalence.put("�", "u");
        m_AccentEquivalence.put("�", "u");
        m_AccentEquivalence.put("�", "u");
        
        m_AccentEquivalence.put("�", "y");
        m_AccentEquivalence.put("�", "y");
        
        // uppercased
        m_AccentEquivalence.put("�", "A");        
        m_AccentEquivalence.put("�", "A");        
        m_AccentEquivalence.put("�", "A");        
        m_AccentEquivalence.put("�", "A");        
        m_AccentEquivalence.put("�", "A");        
        m_AccentEquivalence.put("�", "A");        

        m_AccentEquivalence.put("�", "C");        

        m_AccentEquivalence.put("�", "E");        
        m_AccentEquivalence.put("�", "E");        
        m_AccentEquivalence.put("�", "E");        
        m_AccentEquivalence.put("�", "E");        

        m_AccentEquivalence.put("�", "I");        
        m_AccentEquivalence.put("�", "I");        
        m_AccentEquivalence.put("�", "I");        
        m_AccentEquivalence.put("�", "I");        
        
        m_AccentEquivalence.put("�", "N");        

        m_AccentEquivalence.put("�", "O");        
        m_AccentEquivalence.put("�", "O");        
        m_AccentEquivalence.put("�", "O");        
        m_AccentEquivalence.put("�", "O");        
        m_AccentEquivalence.put("�", "O");

        m_AccentEquivalence.put("�", "U");
        m_AccentEquivalence.put("�", "U");
        m_AccentEquivalence.put("�", "U");
        m_AccentEquivalence.put("�", "U");
        
        m_AccentEquivalence.put("�", "Y");        
    }
    
    /**
     * check if a character has an equivalent (a version without accent)
     * 
     * @param pi_Char char to check
     * @return true if the char has a non-accentuated equivalent
     */
    public boolean HasAccentEquivalence(String pi_Char) {
        return m_AccentEquivalence.containsKey(pi_Char);
    }
    
    /**
     * Get the equivalence for a given char
     * 
     * @param pi_Char char to get an equivalent for
     * @return equivalent, non-accentuated character
     */
    public String GetAccentEquivalence(String pi_Char) {
        return (String)m_AccentEquivalence.get(pi_Char); 
    }
    
    private Hashtable<String,String> m_AccentEquivalence;

}
