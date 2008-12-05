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
 * Created on May 8, 2005
 */
package ca.uottawa.balie;

/**
 * Iterator for token list
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TokenListIterator {

    protected TokenListIterator(TokenList pi_List) {
        m_TokenList = pi_List;
        m_CurrentPosition = 0;
    }
    
    private TokenList 	m_TokenList;
    private int			m_CurrentPosition;
    
    /**
     * Check if there is more elements in the list
     * @return true if there is a next element
     */
    public boolean HasNext() {
        return m_CurrentPosition < m_TokenList.Size();
    }
    
    /**
     * Get the next token
     * @return next token
     */
    public Token Next() {
        return m_TokenList.Get(m_CurrentPosition++);
    }
}
