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
        m_AccentEquivalence.put("á", "a");        
        m_AccentEquivalence.put("à", "a");        
        m_AccentEquivalence.put("â", "a");        
        m_AccentEquivalence.put("ä", "a");        
        m_AccentEquivalence.put("ã", "a");        
        m_AccentEquivalence.put("å", "a");        

        m_AccentEquivalence.put("ç", "c");        

        m_AccentEquivalence.put("é", "e");        
        m_AccentEquivalence.put("è", "e");        
        m_AccentEquivalence.put("ê", "e");        
        m_AccentEquivalence.put("ë", "e");        

        m_AccentEquivalence.put("ì", "i");        
        m_AccentEquivalence.put("í", "i");        
        m_AccentEquivalence.put("î", "i");        
        m_AccentEquivalence.put("ï", "i");        
        
        m_AccentEquivalence.put("ñ", "n");        

        m_AccentEquivalence.put("ò", "o");        
        m_AccentEquivalence.put("ó", "o");        
        m_AccentEquivalence.put("ô", "o");        
        m_AccentEquivalence.put("ö", "o");        
        m_AccentEquivalence.put("õ", "o");

        m_AccentEquivalence.put("ù", "u");
        m_AccentEquivalence.put("ú", "u");
        m_AccentEquivalence.put("ü", "u");
        m_AccentEquivalence.put("û", "u");
        
        m_AccentEquivalence.put("ý", "y");
        m_AccentEquivalence.put("ÿ", "y");
        
        // uppercased
        m_AccentEquivalence.put("Á", "A");        
        m_AccentEquivalence.put("À", "A");        
        m_AccentEquivalence.put("Â", "A");        
        m_AccentEquivalence.put("Ä", "A");        
        m_AccentEquivalence.put("Ã", "A");        
        m_AccentEquivalence.put("Å", "A");        

        m_AccentEquivalence.put("Ç", "C");        

        m_AccentEquivalence.put("É", "E");        
        m_AccentEquivalence.put("È", "E");        
        m_AccentEquivalence.put("Ê", "E");        
        m_AccentEquivalence.put("Ë", "E");        

        m_AccentEquivalence.put("Ì", "I");        
        m_AccentEquivalence.put("Í", "I");        
        m_AccentEquivalence.put("Î", "I");        
        m_AccentEquivalence.put("Ï", "I");        
        
        m_AccentEquivalence.put("Ñ", "N");        

        m_AccentEquivalence.put("Ò", "O");        
        m_AccentEquivalence.put("Ó", "O");        
        m_AccentEquivalence.put("Ô", "O");        
        m_AccentEquivalence.put("Ö", "O");        
        m_AccentEquivalence.put("Õ", "O");

        m_AccentEquivalence.put("Ù", "U");
        m_AccentEquivalence.put("Ú", "U");
        m_AccentEquivalence.put("Ü", "U");
        m_AccentEquivalence.put("Û", "U");
        
        m_AccentEquivalence.put("Ý", "Y");        
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
