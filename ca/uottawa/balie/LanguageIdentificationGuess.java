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
 * Created on Mar 17, 2005
 */
package ca.uottawa.balie;

/**
 * A guess is compose of a language (e.g.: French) along with its probability (ex.: 0.9).
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LanguageIdentificationGuess implements Comparable<LanguageIdentificationGuess> {

    /**
     * Creates a guess.
     * 
     * @param pi_Language		The language
     * @param pi_Probability	The probability of this language
     */
    public LanguageIdentificationGuess(String pi_Language, Double pi_Probability) {
        m_Language = pi_Language;
        m_Probability = pi_Probability;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(LanguageIdentificationGuess pi_Obj) {
        return pi_Obj.Probability().compareTo(m_Probability);
    }
    
    /**
     * Gets the language.
     * 
     * @return	Language String (see {@link Balie} for enumeration)
     * @see Balie
     */
    public String Language() {
        return m_Language;
    }
    
    /**
     * Gets the probability of a language.
     * 
     * @return Probability [0,1]
     */
    public Double Probability() {
        return m_Probability;
    }
    
    private String m_Language;
    private Double m_Probability;
}
