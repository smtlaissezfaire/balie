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
 * Created on Apr 1, 2004
 */
package ca.uottawa.balie;

import java.io.Serializable;

/**
 * Tokens are the unit element of Balie.
 * A text is represneted as a list of consecutives tokens (called {@link TokenList}).
 * <ul>
 * <li>For punctuation token, the POS is resolved at construction time.</li>
 * <li>For word token, the POS must be resolved by examining the whole tokenlist.</li>
 * </ul>
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new token with all the required information.
     * 
     * @param pi_RawLiteral		The word as it appears in the text
     * @param pi_CanonLiteral	The canonical version of the word
     * @param pi_Type			The type (punctuation or word) see {@link TokenConsts} for details
     * @param pi_PunctLookup	The lookup table for punctuation types
     * @param pi_Position		The position of the token, in number of words from the text beginning
     * @param pi_Sentence		The sentence number
     * @param pi_NumWhiteBefore Number fo white chars prior to this token
     * @param pi_NextStart      Start position (in chars) of this token (including white)
     */
	public Token(String pi_RawLiteral, String pi_CanonLiteral, int pi_Type, PunctLookup pi_PunctLookup, AccentLookup pi_AccentLookup, int pi_Position, int pi_Sentence, int pi_NumWhiteBefore, int pi_NextStart, int pi_NETagSetSize) {
		
		if (Balie.DEBUG_TOKEN) {
			if (pi_RawLiteral.equals("\n")) {
				if(Balie.DEBUG_TOKEN) DebugInfo.Out("\\n");
			} else if (pi_RawLiteral.equals("\r")) {
				if(Balie.DEBUG_TOKEN) DebugInfo.Out("\\r");
			} else if (pi_RawLiteral.equals("\t")) {
				if(Balie.DEBUG_TOKEN) DebugInfo.Out("\\t");
			} else {
				if(Balie.DEBUG_TOKEN) DebugInfo.Out(pi_RawLiteral);
			}
		}
		m_RawLiteral 	 = pi_RawLiteral;
		m_CanonLiteral 	 = pi_CanonLiteral;
		m_NumWhiteBefore = pi_NumWhiteBefore;
		m_Type 			 = pi_Type;
		m_EntityType 	 = new NamedEntityType(pi_NETagSetSize, null);
		m_LexiconMatch   = null;
        m_NamedEntityAlias = -1;
        
		// position
		m_Position = pi_Position;
		m_Sentence = pi_Sentence;
		m_StartPos = pi_NextStart + pi_NumWhiteBefore;
		m_EndPos = m_StartPos +  m_RawLiteral.length();
		
		m_FirstTokenOfSentence = false;
		m_AllCapSentence = false;
		
		if (TokenConsts.Is(m_Type, TokenConsts.TYPE_WORD)) {
			m_PartOfSpeech = TokenConsts.POS_UNKNOWN; // must be resolved by an external product (e.g., qTag)
		} else if (TokenConsts.Is(m_Type, TokenConsts.TYPE_PUNCTUATION)) {
			m_PartOfSpeech = pi_PunctLookup.GetPunctType(pi_CanonLiteral);
		} else {
			throw new Error("Token must be Word OR Punct.");
		}
		
		m_Features = new TokenFeature(pi_RawLiteral, pi_PunctLookup);
	}

	
	/**
	 * Gets the raw version of the token.
	 * 
	 * @return String
	 */
	public String Raw() {
		return m_RawLiteral;
	}
	
	/**
	 * Gets the canonical version of the token.
	 * 
	 * @return String
	 */
	public String Canon() {
		return m_CanonLiteral;
	}

	/**
	 * Gets the type of the token (word or punctuation).
	 * see {@link TokenConsts} for enumeration.
	 * 
	 * @return The type of the token
	 * @see TokenConsts
	 */
	public int Type() {
		return m_Type;
	}
	
	/**
	 * Gets the part-of-speech of the token.
	 * Words and punctuations have a POS. 
	 * see {@link TokenConsts} for enumeration of both.
	 * 
	 * @return the POS
	 * @see TokenConsts
	 */
	public int PartOfSpeech() {
		return m_PartOfSpeech;
	}

	/**
	 * Set the part-of-speech of the token.
	 * Words and punctuations have a POS. 
	 * see {@link TokenConsts} for enumeration of both.
	 * 
	 * @param pi_POS	the pos
	 * @see TokenConsts
	 */
	protected void PartOfSpeech(int pi_POS) {
		m_PartOfSpeech = pi_POS;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object pi_Obj) {
		boolean bEqual = false;
		if (m_RawLiteral.equals(((Token)pi_Obj).Raw()) &&
		    m_CanonLiteral.equals(((Token)pi_Obj).Canon()) &&
		    m_Type == ((Token)pi_Obj).Type() &&
			m_PartOfSpeech == ((Token)pi_Obj).PartOfSpeech() &&
			m_Position == ((Token)pi_Obj).Position() &&
			m_Sentence == ((Token)pi_Obj).SentenceNumber()) {
		    	bEqual = true;
		}
		return bEqual;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
	    int result = HashCodeUtil.SEED;
	    result = HashCodeUtil.hash( result, m_RawLiteral );
	    result = HashCodeUtil.hash( result, m_CanonLiteral );
	    result = HashCodeUtil.hash( result, m_Type );
	    result = HashCodeUtil.hash( result, m_PartOfSpeech );
	    result = HashCodeUtil.hash( result, m_Position );
	    result = HashCodeUtil.hash( result, m_Sentence );
	    return result;
	}
	
	/**
	 * Get the number of white spaces that preceed this token in the text
	 * 
	 * @return num white chars
	 */
	public int NumWhiteBefore() {
		return m_NumWhiteBefore;
	}
	
	/**
	 * Get the entity type of this token
	 * see {@link NamedEntityType} for enumeration of types.
	 * 
	 * @return entity type
	 * @see TokenConsts
	 */
	public NamedEntityType EntityType() {
		return m_EntityType;
	}

	/**
	 * Set the entity type
	 * see {@link TokenConsts} for enumeration of types.
	 * 
	 * @param pi_Type
	 * @see TokenConsts
	 */
	public void EntityType(NamedEntityType pi_Type) {
		// deep copy! do not change the logic of this function.
        pi_Type.AddLeadingInfo(m_EntityType.GetInfo());
		m_EntityType = pi_Type.clone(null);
	}
	
    /**
     * Get the alias group (integer ID) for this token
     * 
     * @return group ID (-1 if the token does not belong to an alas group)
     */
    public int NamedEntityAlias() {
        return m_NamedEntityAlias;
    }
    
    /**
     * Set alias group ID for this token
     * 
     * @param pi_ID alias group ID
     */
    public void NamedEntityAlias(int pi_ID) {
        m_NamedEntityAlias = pi_ID;
    }
    
	/**
	 * Gets the sentence number.
	 * 
	 * @return The sentence number
	 */
	public int SentenceNumber() {
		return m_Sentence;
	}
	
	/**
	* Sets the sentence number.
	* 
	* @param numSentence the new sentence number
	*/
	public void setSentenceNumber(int numSentence) {  
		this.m_Sentence = numSentence;
	}

	/**
	* Increments the sentence number of a token.
	* Useful if using the SBR module that can identify sentence break on the late.
	*/
	public void IncrementSentenceNumber() {  
		++m_Sentence;
	}

   /**
	 * Gets the token position.
	 * 
	 * @return The token position.
	 */
	public long Position() {
		return m_Position;
	}
	
	/**
    * Sets the token position.
    * 
    * @param numPosition the new token position
    */
	public void setPosition(int numPosition) {  
  		this.m_Position = numPosition;
	}
	
	/**
	 * Gets the lenght of a token in number fo chars.
	 * 
	 * @return Token lenght
	 */
	public int Length() {
		return m_RawLiteral.length();
	}

	/**
	 * Gets the XML representation of the token.
	 * 
	 * @return An XML representation in a StringBuffer.
	 */
	public StringBuffer ToXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<token type=\""+m_Type+"\" entity-type=\""+m_EntityType+"\" canon=\""+XmlUtil.Encode(m_CanonLiteral)+"\">");
		sb.append(XmlUtil.Encode(m_RawLiteral));
		sb.append("</token>");
		return sb;
	}
   
	/**
	 * @return the start position in number of character relative to document start
	 */
	public int StartPos() {
		return m_StartPos;
	}

	/**
	 * @return the end position in number of character relative to document start
	 */
	public int EndPos() {
		return m_EndPos;
	}

	/**
	 * 
	 */
	public void FlagAsSentenceStart() {
		m_FirstTokenOfSentence = true;
	} 
	
	/**
	 * 
	 */
	public void FlagAsAllCapSentence() {
		m_AllCapSentence = true;
	}
	
	/**
	 * @return true if this token starts a sentence
	 */
	public boolean IsSentenceStart() {
		return m_FirstTokenOfSentence;
	}

	/**
	 * @return true if this token is inside an all-capitalized sentence
	 */
	public boolean IsAllCapSentence() {
		return m_AllCapSentence;
	}	
	/** 
	 * A canonical string representation of this token.
    * @see java.lang.Object#toString()
    */
    public String toString()
    {  
    	return m_CanonLiteral.toString() + ":Pos=" + m_Position + ":Sent=" + m_Sentence;
    }
   
    /**
     * Get features for this token
     * @return the features
     */
    public TokenFeature Features() {
    	return m_Features;
    }
    
    /**
     * Set the lexicon entry that matches the entity starting on this token
     * @param pi_Match lexicon entry matching the entity starting on this token
     */
    public void LexiconMatch(String pi_Match) {
        m_LexiconMatch = pi_Match;
    }
    
    /**
     * Get the lexicon entry that matches the entity starting on this token
     * @return lexicon entry matching the entity starting on this token
     */
    public String LexiconMatch() {
        return m_LexiconMatch;
    }
    
   // Token itself   
   private String  		          m_RawLiteral;
   private String  		          m_CanonLiteral;
   private int 			          m_NumWhiteBefore;   
   private int    		          m_Type;
   private NamedEntityType        m_EntityType;
   private String                 m_LexiconMatch;
   private int                    m_NamedEntityAlias;
   
   // Position
   private long    		m_Position; 
   private int     		m_Sentence;
   private int     		m_StartPos; // in number of chars from doc start
   private int     		m_EndPos;	// in number of chars from doc start
   private boolean  	m_FirstTokenOfSentence;
   private boolean 		m_AllCapSentence;
   
   // Part-of-speech
   // Balie does not resolve part-of-speech. 
   // This is a placeholder for an external tagging (e.g., using qTag)
   private int			m_PartOfSpeech;
   
   // Features
   private TokenFeature m_Features;



}
