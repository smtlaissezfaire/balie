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
 * Created on Sept 21, 2005
 */
package ca.uottawa.balie;

/**
 * BASE CLASS Named Entity Recognition (NER).
 * 
 * Consists of lexicon lookup: identifying matches in lexicons of entities.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class NamedEntityRecognitionBase extends NamedEntityRecognition {

    /**
     * Initialize Base NER on a tokenlist.
     * Base NER corresponds to a simple lexicon lookup
     * 
     * @param pi_Lexicon    lexicons used for NER.
     * @param pi_TokenList  a tokenlist (must be English)
     */
    public NamedEntityRecognitionBase(LexiconOnDiskI pi_Lexicon, TokenList pi_TokenList) {
        super(pi_Lexicon, pi_TokenList);
    }

    protected void ApplyDisambiguation() {}

    protected boolean IsEntityCounterExample(NamedEntityType pi_EntityType) {
        return false;
    }


    
}
