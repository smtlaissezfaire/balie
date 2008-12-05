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
 * Created on Nov 28, 2005
 */
package ca.uottawa.balie;

/**
 * Lexicon loader
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public interface LexiconOnDiskI {
    public String GetEntityInLexicon(String pi_Word, boolean pi_bFuzzy);
    public NamedEntityType GetEntityType(String pi_Word, boolean pi_bFuzzy);
    public NamedEntityTypeEnumI[] Types();
}
