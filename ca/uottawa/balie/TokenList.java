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
 * Created on Apr 29, 2004
 */
package ca.uottawa.balie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * List of Tokens to represent a text.
 * Comes with a bunch of manipulation functions.
 * Also an XML representation.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TokenList implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private ArrayList<String>				m_WordsOnly;
    private ArrayList<Integer>              m_SentenceTokenBounds;
    private int                      		m_NumToken;
	private Hashtable<Integer, Token>		m_HashAccess;
	private Hashtable<String, Double>		m_TermFrequency;
	
    private NamedEntityTypeEnumI[]           m_NETypes;
    
	// for sentence boundary recognition
	private boolean							m_DetectSentenceBoundaries;
	private Token 							m_tokSentBeginning;
	private Token 							m_tokLast;
	private Token 							m_tokCurrent;
	private Token 							m_tokNext;
	
	/**
	 * Construct an empty TokenList. 
	 * Ready for incremental constitution.
	 * 
	 * @param pi_DetectSentenceBoundaries	True if the sentences boundaries must be detected
	 */
	public TokenList(boolean pi_DetectSentenceBoundaries, NamedEntityTypeEnumI[] pi_Types) {
		m_WordsOnly	= new ArrayList<String>();
		m_SentenceTokenBounds = new ArrayList<Integer>(); 
		m_NumToken  = 0;
		m_HashAccess = new Hashtable<Integer, Token>();
		m_TermFrequency = new Hashtable<String, Double>();
        m_NETypes = pi_Types;
		
		m_DetectSentenceBoundaries = pi_DetectSentenceBoundaries;

		m_tokSentBeginning 	= null;
		m_tokLast 			= null;
		m_tokCurrent 		= null;
		m_tokNext 			= null;
	}
	
	/**
	 * Add a token a the end of the TokenList.
	 * 
	 * @param pi_Token		A new token
	 * @param pi_SBR		The SBR object
	 * @param pi_SBRModel	The learned SBR model
	 * @return True if the previous token (current-1) was a sentence break
	 */
	public boolean Add(Token pi_Token, SentenceBoundariesRecognition pi_SBR, WekaLearner pi_SBRModel) {
		m_HashAccess.put(new Integer(m_NumToken++), pi_Token);
		m_WordsOnly.add(pi_Token.Raw());
		
		if (TokenConsts.Is(pi_Token.Type(), TokenConsts.TYPE_WORD)) {
		    if (m_TermFrequency.containsKey(pi_Token.Canon())) {
		        double nFreq = ((Double)m_TermFrequency.get(pi_Token.Canon())).doubleValue();
		        m_TermFrequency.put(pi_Token.Canon(), new Double(nFreq+1));
		    } else {
		        m_TermFrequency.put(pi_Token.Canon(), new Double(1));
		    }
		}
		
		// Sentence boudary is checked on previous tokens
		boolean bIsSB = false;

		if (m_DetectSentenceBoundaries) {	
			bIsSB = CheckForSentenceBoundary(pi_SBR, pi_SBRModel);
			// Record the sentence boundary
			if(bIsSB) {
				m_SentenceTokenBounds.add(new Integer((int)pi_Token.Position() - 1));
			}
		}
		
		return bIsSB;
	}
	
	private boolean CheckForSentenceBoundary(SentenceBoundariesRecognition pi_SBR, WekaLearner pi_SBRModel) {
		boolean bIsSB = false;
		// Assign the first "tokSentenceBeginning" with the first token
		if (m_NumToken != 0 && m_tokSentBeginning == null) {
			m_tokSentBeginning = (Token)m_HashAccess.get(new Integer(0));
			m_tokSentBeginning.FlagAsSentenceStart();
		}
						
		// Let's test for sentence boundary using 3 last tokens
		if (m_NumToken > 1) {
			if (m_NumToken == 2) {
				m_tokNext    = (Token)m_HashAccess.get(new Integer(1));
				m_tokCurrent = (Token)m_HashAccess.get(new Integer(0));
			} else if (m_NumToken >= 3) {
		         m_tokLast    = m_tokCurrent;
		         m_tokCurrent = m_tokNext;
		         m_tokNext    = (Token)m_HashAccess.get(new Integer(m_NumToken-1));
	         
				 // if last and current are on different sentences, remove last
	 			 if (m_tokLast.SentenceNumber() != m_tokCurrent.SentenceNumber()) {
	 			 	m_tokLast = null;
				 }
			}
			
			if (pi_SBR.IsSentenceBoundary(pi_SBRModel, m_tokSentBeginning, m_tokLast, m_tokCurrent, m_tokNext)) {
				LastTokenWasInNewSentence();
				if (pi_SBR.SentenceIsAllCapitalized()) {
					m_tokSentBeginning.FlagAsAllCapSentence();
				}
				m_tokSentBeginning = m_tokNext;
				m_tokSentBeginning.FlagAsSentenceStart();
				bIsSB = true;		
			}
		}
		return bIsSB;
	}
		
	/**
	 * Gets the size (number of tokens) of the TokenList.
	 * 
	 * @return Size
	 */
	public int Size() {
		return m_NumToken;
	}
	
	/**
	 * Gets the token at the given index.
	 * 
	 * @param pi_Index Index of the token to get.
	 * @return	A token
	 */
	public Token Get(int pi_Index) {
		return (Token)m_HashAccess.get(new Integer(pi_Index));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object pi_Obj){
		return m_NumToken == ((TokenList)pi_Obj).Size() &&
				m_HashAccess.equals(((TokenList)pi_Obj).HashAccess());
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
	    int result = HashCodeUtil.SEED;
	    result = HashCodeUtil.hash( result, m_NumToken );
	    result = HashCodeUtil.hash( result, m_HashAccess );
	    return result;	    
	}
	
	private void LastTokenWasInNewSentence() {
		Token tokLast = (Token)m_HashAccess.get(new Integer(m_NumToken-1));
		tokLast.IncrementSentenceNumber();
	}
	 
	/**
	 * Gets the text version of the sentence at the given index. 
	 * 
	 * @param pi_Index		      Index of the sentence to get (in number of sentences)
	 * @param pi_Canonic	      True if the text must be returned in its canonical version
	 * @param pi_PrintNewLines    Print \n characters
	 * @return The text of a sentence (String) 
	 */
	public String SentenceText(int pi_Index, boolean pi_Canonic, boolean pi_PrintNewLines) {
      
		int maxSentenceTokenPos = (pi_Index == m_SentenceTokenBounds.size()) ? m_NumToken: ((Integer)m_SentenceTokenBounds.get(pi_Index)).intValue() + 1;
		int minSentenceTokenPos = pi_Index == 0 ? 0 : ((Integer)m_SentenceTokenBounds.get(pi_Index-1)).intValue() + 1;
		
      	return TokenRangeText(minSentenceTokenPos, maxSentenceTokenPos, pi_Canonic, pi_PrintNewLines, false, false, false, false);
	}
   
    /**
     * Get String representation of a part of the tokenlist
     * 
     * @param pi_Start Start token number (inclusive)
     * @param pi_Stop end token number (exclusive)
     * @param pi_Canonic print in canonical (lowercased, etc) form    
     * @param pi_PrintNewLines print \n characters
     * @param pi_TagEntities add XML tags around named entities
     * @param pi_AddAlias add alias network infos in XML tag
     * @param pi_AddExplanation add explanations infos in XML tag
     * @param pi_EscapeXML escape XML reserved characters in the text (so that output is valid XML)
     * 
     * @return textual representation of the tokenlist
     */
    public String TokenRangeText(int pi_Start, int pi_Stop, boolean pi_Canonic, boolean pi_PrintNewLines, boolean pi_TagEntities, boolean pi_AddAlias, boolean pi_AddExplanation, boolean pi_EscapeXML) {
		
	    if (pi_Start > pi_Stop) {
	        throw new Error("Start index must be lower than Stop index.");
	    }
	    
	    StringBuffer strText = new StringBuffer();
		
	    //int nCurEntityType = 0;
	    
		for (int i = pi_Start; i != pi_Stop; ++i) {
			Token tokCur = (Token)m_HashAccess.get(new Integer(i));
			if (pi_PrintNewLines || (tokCur.Type() != TokenConsts.TYPE_PUNCTUATION || tokCur.PartOfSpeech() != TokenConsts.PUNCT_NEWLINE)) {
   				
				StringBuffer strTok = new StringBuffer();

				for (int j = 0; j != tokCur.NumWhiteBefore(); ++j) {
   					strTok.append(" ");
   				}
                
				// open entity tag
   				if (pi_TagEntities && tokCur.EntityType().IsStart() && tokCur.EntityType().GetLabel(m_NETypes) != null) {
   					strTok.append("<ENAMEX TYPE=\"");
   					strTok.append(tokCur.EntityType().GetLabel(m_NETypes));
                    if (pi_AddAlias) {
                        strTok.append("\" ALIAS=\"");
                        strTok.append(tokCur.NamedEntityAlias());
                    }
                    if (pi_AddExplanation) {
                        strTok.append("\" INFO=\"");
                        strTok.append(XmlUtil.Encode(tokCur.EntityType().GetInfo()));
                    }
                    strTok.append("\">");
   				}
   				
                if (pi_EscapeXML) {
                    strTok.append(XmlUtil.Encode(pi_Canonic?tokCur.Canon():tokCur.Raw()));
                } else {
                    strTok.append(pi_Canonic?tokCur.Canon():tokCur.Raw());
                }
   				
				// close entity tag
   				if (pi_TagEntities && tokCur.EntityType().IsEnd() && tokCur.EntityType().GetLabel(m_NETypes) != null) {
					strTok.append("</ENAMEX>");
   				}
   				
				strText.append(strTok);
			} else {
                strText.append(" ");
            }
		}
		
		return strText.toString();	    
	}

    /**
     * Map new NE types. Override the current NE tag set
     * 
     * @param pi_Mapping
     */
    public void MapNewNETypes(NamedEntityTypeEnumI[] pi_Mapping) {
        m_NETypes = pi_Mapping;
    }
    
    /**
     * Get the current NE tag set
     * 
     * @return NETagSet
     */
    public NamedEntityTypeEnumI[] NETagSet() {
        return m_NETypes;
    }
    
	/**
     * Gets the number of sentences found.
     * 
     * @return Number of sentences.
     */
     public int getSentenceCount() {  
    		return (m_NumToken-1 > 0)? Get(m_NumToken-1).SentenceNumber() + 1 : 0;
     }
     
     
	/**
	 * Gets the TF table.
	 * That is a lookup that maps words to their frequency in the text.
	 * 
	 * @return Hashtable
	 */
	public Hashtable<String, Double> TermFrequencyTable() {
	    return m_TermFrequency;
	}
	
	/**
     * Get the map index-to-token
	 * @return Map token position to token
	 */
	public Hashtable<Integer, Token> HashAccess() {
		return m_HashAccess;
	}
	
	/**
     * Get the (ordered) list of words in this tokenlist
	 * @return list of words
	 */
	public ArrayList<String> WordList(){
		return m_WordsOnly;
	}
	
	/**
	 * Sets the Part-of-speech of the token at the given index.
	 * 
	 * @param pi_Index	Index of the token to update
	 * @param pi_POS	Part-of-speech of this token (see {@link TokenConsts} for the enumeration)
	 * @see TokenConsts
	 */
	public void SetPOS(int pi_Index, int pi_POS) {
		Integer idx = new Integer(pi_Index);
		Token tok = (Token)m_HashAccess.get(idx);
		if (TokenConsts.Is(tok.Type(), TokenConsts.TYPE_WORD)) {
			tok.PartOfSpeech(pi_POS);
			m_HashAccess.put(idx, tok);
		}
	}

	/**
     * Set the type of an entity (deep copy)
	 * @param pi_Index index of the entity
	 * @param pi_Type type to set
	 */
	public void SetEntityType(int pi_Index, NamedEntityType pi_Type) {
		Integer idx = new Integer(pi_Index);
		Token tok = (Token)m_HashAccess.get(idx);
		tok.EntityType(pi_Type);
		m_HashAccess.put(idx, tok);
	}
	
    /**
     * Set the string that match a lexicon for this entity (deep copy)
     * @param pi_Index index of the entity
     * @param pi_Match string that matches in lexicon 
     */
    public void SetLexiconMatch(int pi_Index, String pi_Match) {
        Integer idx = new Integer(pi_Index);
        Token tok = (Token)m_HashAccess.get(idx);
        tok.LexiconMatch(pi_Match);
        m_HashAccess.put(idx, tok);
    }

	
	/**
	 * Gets the tokenlist in XML format
	 * 
	 * @return an XML StringBuffer
	 */
	public StringBuffer ToXML() {
		StringBuffer sb = new StringBuffer();
		sb.append(XmlUtil.Header());
		sb.append("<balie>\n");
		sb.append("<tokenList>\n");
		sb.append("<s>");
		
		int curSentence = 0;
		for (int i = 0; i != m_NumToken; ++i) {
			Token tok = (Token)m_HashAccess.get(new Integer(i));
			if (tok.SentenceNumber() != curSentence) {
				curSentence = tok.SentenceNumber(); 
				sb.append("</s>\n<s>");
			}
			sb.append(tok.ToXML());
		}
		sb.append("</s>\n");
		sb.append("</tokenList>\n");
		sb.append("</balie>\n");
		return sb;
	}
	
	/**
	 * Gets an iterator for the tokenList
	 * 
	 * @return the iterator (type {@link TokenListIterator})
	 * 
	 * @see TokenListIterator
	 */
	public TokenListIterator Iterator() {
	    return new TokenListIterator(this);
	}

	
}
