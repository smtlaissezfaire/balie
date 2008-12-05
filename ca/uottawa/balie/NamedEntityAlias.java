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
 * Created on Dec 2, 2005
 */
package ca.uottawa.balie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


/**
 * NE alias object is a set of labels that represent the same entity 
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class NamedEntityAlias {

	/**
     * A concept (alias) that represents one or many named entity
     * 
	 * @param pi_alBow words that belongs to this alias
	 * @param pi_EntityIndex index of an entity (at least this entity is required to create an alias)
	 */
	public NamedEntityAlias(ArrayList<String> pi_alBow, int pi_EntityIndex, int pi_NETagSetSize) {
		m_Bow = pi_alBow;
		m_Entities = new ArrayList<Integer>();
		m_Entities.add(new Integer(pi_EntityIndex));
		m_DominantType = new NamedEntityType(pi_NETagSetSize, null);
	}

	private ArrayList<String> 	       m_Bow;
	private ArrayList<Integer> 	       m_Entities;
	private NamedEntityType	           m_DominantType;
	
	/**
     * Add an entity bag-of-word to this alias
     * 
	 * @param pi_alBow Words of this entity
	 * @param pi_Index index of this entity
	 */
	public void Add(ArrayList<String> pi_alBow, int pi_Index) {
		m_Bow.addAll(pi_alBow);
		m_Bow = new ArrayList<String>(new HashSet<String>(m_Bow)); //remove duplicates
		m_Entities.add(new Integer(pi_Index));
	}

	/**
     * Merge aliases
	 * @param pi_Alias alias to mege with
	 */
	public void Merge(NamedEntityAlias pi_Alias) {
		m_Bow.addAll(pi_Alias.Bow());
		m_Bow = new ArrayList<String>(new HashSet<String>(m_Bow)); //remove duplicates
		m_Entities.addAll(pi_Alias.Entities());
	}

    /**
     * Check if this alias and a bag-of-word have a significant overlap (at least half of words overlap)
     * @param pi_Bow bow to check
     * @return true if overlap
     */
    public boolean SignificantOverlap(ArrayList<String> pi_Bow) {
        double nOverlap = 0;
        for (int i = 0; i != pi_Bow.size(); ++i) {
            for (int j = 0; j != m_Bow.size(); ++j) {
                if (pi_Bow.get(i).equals(m_Bow.get(j))) nOverlap++;
            }
        }
        return (2*nOverlap / (pi_Bow.size()+m_Bow.size())) >= 0.5;
    }

    
	/**
     * Get list of indexes of entities
	 * @return list of indexes
	 */
	public ArrayList<Integer> Entities() {
		return m_Entities;
	}

	/**
     * Get entire BOW for this alias
	 * @return the union of all BOW of all entities of this alias
	 */
	public ArrayList<String> Bow() {
		return m_Bow;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		Iterator<String> iCur = m_Bow.iterator();
		while (iCur.hasNext()) {
			str.append(iCur.next());
			str.append(", ");
		}
		str.append("\n");
		Iterator<Integer> iECur = m_Entities.iterator();
		while (iECur.hasNext()) {
			str.append(iECur.next());
			str.append(", ");
		}
		
		return str.toString();
	}

	/**
     * Set the dominant type for this entity
     * the dominant type is the preferred type to be applied to all elements of the alias
	 * @param pi_NE a type (must contains ionly one primitive type)
	 */
	public void SetDominantType(NamedEntityType pi_NE) {
        if (pi_NE.TypeCount() != 1) {
            throw new Error("Can't set a combined type (must be one and only one primitive)");
        }
		if (!m_DominantType.HasNoTag() && !m_DominantType.Intersect(pi_NE)) {
			throw new Error("Should be one dominant type per alias");
		}
		m_DominantType.MergeWith(pi_NE, null);
	}

	/**
	 * @return the dominant type
	 */
	public NamedEntityType DominantType() {
		return m_DominantType;
	}


}
