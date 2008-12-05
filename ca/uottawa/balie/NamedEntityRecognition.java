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
 * NER abstract class
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public abstract class NamedEntityRecognition {

	/**
     * Initialize NER on a tokenlist.
     * 
     * @param pi_Lexicon    lexicons used for NER. see {@link LexiconOnDisk} 
     * @param pi_TokenList  a tokenlist (must be English)
	 */
	public NamedEntityRecognition(LexiconOnDiskI pi_Lexicon, TokenList pi_TokenList) {
		m_TokenList = pi_TokenList;
		m_Lexicon = pi_Lexicon;
		m_DesiredEntities = NamedEntityTypeEnum.Enamex(); 
        m_TagSetSize = pi_Lexicon.Types().length;
	}
	
	/**
	 * Process the token list and tag entities
	 */
	public void RecognizeEntities() {
		LookupLexicon();
		ApplyDisambiguation();
	}
	
	/**
	 * Apply disambiguation rules
	 * Should be done by parsing the tokenlist and resolving ambiguous cases
	 * Ambiguous case are when an entity type is the disjunction of multiple types (e.g.: France == NE_PERSON | NE_LOCATION)
	 * 
	 * class "NamedEntityRecognitionExt" implements such rules
	 */
	protected abstract void    ApplyDisambiguation();
	
	/**
	 * Allows filtering some counter example entities during lexicon lookup
	 * For instance, in "NamedEntityRecognitionExt", counter examples are months and language that are frequent false positive entities
	 * 
	 * @param pi_EntityType type of the current entity
	 * @return true if the entity is a counter example
	 */
	protected abstract boolean IsEntityCounterExample(NamedEntityType pi_EntityType);
	
	private void LookupLexicon() {
		// massively reallocated string
		String strCandidate[] = new String[Balie.MAX_TOKEN_PER_ENTITY];
		
		for (int i = 0; i != m_TokenList.Size(); ++i) {
			// enforce start on a word (not a punct) that is not already tagged
			Token tok = m_TokenList.Get(i);
			if (TokenConsts.Is(tok.Type(), TokenConsts.TYPE_WORD) && tok.EntityType().HasNoTag()) {
				
				// create entities from 1 to max tokens
				int maxj = 0;
				for (int j = 0; j != Balie.MAX_TOKEN_PER_ENTITY && i+j != m_TokenList.Size(); ++j) {
					// do not allow ending by a newline char
					Token lastTok = m_TokenList.Get(i+j);
					if (!(TokenConsts.Is(lastTok.Type(), TokenConsts.TYPE_PUNCTUATION) && 
						TokenConsts.Is(lastTok.PartOfSpeech(), TokenConsts.PUNCT_NEWLINE))) {
						
						strCandidate[j] = m_TokenList.TokenRangeText(i, i+j+1, true, false, false, false, false, false).trim();
						maxj = j;
						
					} else {
						strCandidate[j] = "";
					}
				}
				
				// lookup from longest
				for (int k = maxj; k != -1; --k) {
				    String strMatch = m_Lexicon.GetEntityInLexicon(strCandidate[k], Balie.ALLOW_FUZZY_MATCH);
	
				    if (strMatch != null) {
	
                        NamedEntityType entityType = m_Lexicon.GetEntityType(strCandidate[k], Balie.ALLOW_FUZZY_MATCH);
	
						// tag entity
						if (entityType.Intersect(m_DesiredEntities)) {
								
						    if (!IsEntityCounterExample(entityType)) {
								// remove anything else than entity type..
								entityType.KeepOnly(m_DesiredEntities, null);
								
                                for (int l = i; l != i + k + 1; ++l) {
									// add information about entity boundary
									if (l == i && k == 0) {
                                        NamedEntityType curEntityType = entityType.clone(null);
                                        curEntityType.AddType(NamedEntityTypeEnum.START, null);
                                        curEntityType.AddType(NamedEntityTypeEnum.END, null);
                                        m_TokenList.SetLexiconMatch(l, strMatch);
										m_TokenList.SetEntityType(l, curEntityType);
									} else if (l == i) {
                                        NamedEntityType curEntityType = entityType.clone(null);
                                        curEntityType.AddType(NamedEntityTypeEnum.START, null);
                                        m_TokenList.SetLexiconMatch(l, strMatch);
										m_TokenList.SetEntityType(l, curEntityType);
									} else if (l == i + k) {
                                        NamedEntityType curEntityType = entityType.clone(null);
                                        curEntityType.AddType(NamedEntityTypeEnum.END, null);
										m_TokenList.SetEntityType(l, curEntityType);
									} else {
										m_TokenList.SetEntityType(l, entityType.clone(null));
									}
							    }
							}
						} 
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Get the resulting tokenlist. 
	 * Named entities are tagged if the method "RecognizeEntities" was called.
	 * 
	 * @return The tokenlist. 
	 */
	public TokenList GetTokenList() {
		return m_TokenList;
	}
	
	protected TokenList 		m_TokenList;				// the tokenlist to work on
	protected LexiconOnDiskI 	m_Lexicon;					// lexicons (person, location, organization, cues, etc.)
	protected NamedEntityType	m_DesiredEntities;			// Entities to hanlde
    protected int               m_TagSetSize;               // number of entities to handle
}
