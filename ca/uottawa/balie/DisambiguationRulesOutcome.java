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
 * File created on 07-05-15
 */
package ca.uottawa.balie;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class DisambiguationRulesOutcome {

    public DisambiguationRulesOutcome(String pi_Class, double pi_Likelohood) {
        m_Class = pi_Class;
        m_Likelihood = pi_Likelohood;
    }
    
    public String Class() {
        return m_Class;
    }
    
    public double Likelihood() {
        return m_Likelihood;
    }
    
    private String m_Class;
    private double m_Likelihood;
}