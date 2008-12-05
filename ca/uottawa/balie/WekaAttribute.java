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
 * Created on May 23, 2004
 */
package ca.uottawa.balie;

import weka.core.FastVector;

/**
 * Wrapper around the Weka attributes.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class WekaAttribute {

	/**
	 * Construct a numeric attribute.
	 * Possible values are real numbers.
	 * 
	 * @param pi_Label	Name of the attribute
	 */
	public WekaAttribute(String pi_Label) {
		m_IsNumeric = true;
		m_Label = pi_Label;
		m_Values = null;
	}
	
	/**
	 * Construct a nominal attribute.
	 * Possible values must be given.
	 * 
	 * @param pi_Label	Name of the attribute
	 * @param pi_Values	Possible values
	 */
	public WekaAttribute(String pi_Label, FastVector pi_Values) {
		m_IsNumeric = false;
		m_Label = pi_Label;
		m_Values = pi_Values;
	}
	
	private boolean 	m_IsNumeric;
	private String  	m_Label;
	private FastVector 	m_Values;
		
	/**
	 * Checks if the attribute is numeric
	 * 
	 * @return True if numeric, False if nominal
	 */
	public boolean IsNumeric() {
		return m_IsNumeric;
	}
	
	/**
	 * Gets the name of an attribute.
	 * 
	 * @return Name
	 */
	public String Label() {
		return m_Label;
	}
	
	/**
	 * Gets the possible values for a nominal attribute.
	 * 
	 * @return A vector of values (null if the attribute is numeric)
	 */
	public FastVector Values() {
		return m_Values;
	}
	
}
