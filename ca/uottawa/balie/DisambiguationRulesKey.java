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
 * File created on 07-02-27
 */
package ca.uottawa.balie;

import java.io.Serializable;

/**
 * A key is made of two NE types
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class DisambiguationRulesKey implements Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * Define the key
     * 
     * @param pi_Type1
     * @param pi_Type2
     */
    public DisambiguationRulesKey(String pi_Type1, String pi_Type2) {
        // store alphabetically
        if (pi_Type1.compareTo(pi_Type2) <= 0) {
            m_Type1 = pi_Type1;
            m_Type2 = pi_Type2;
        } else {
            m_Type1 = pi_Type2;
            m_Type2 = pi_Type1;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object pi_Obj) {
        boolean bEqual = false;
        if (m_Type1.equals(((DisambiguationRulesKey)pi_Obj).m_Type1) && 
            m_Type2.equals(((DisambiguationRulesKey)pi_Obj).m_Type2)) {
                bEqual = true;
        }
        return bEqual;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int result = HashCodeUtil.SEED;
        result = HashCodeUtil.hash( result, m_Type1 );
        result = HashCodeUtil.hash( result, m_Type2 );
        return result;
    }

    public String toString() {
        return m_Type1 + "-" + m_Type2;
    }
    
    private String m_Type1;
    private String m_Type2;

    
}
