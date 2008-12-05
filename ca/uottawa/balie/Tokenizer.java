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

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * The tokenizer takes a text in input and extract a tokenlist.
 * It uses all services function, language dependant routines, SBD algo and POS tagging.
 * The Tokenizer subsumes and extends the "StringTokenizer", a Java built-in utility.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class Tokenizer {
	
	// members
	protected TokenList 						m_TokenList;
	protected PunctLookup 						m_PunctLookup;
    protected AccentLookup                      m_AccentLookup;
	protected LigatureLookup					m_LigatureLookup;
	protected UnbreakableLookup 				m_UnbreakableLookup;
	protected LanguageSpecific					m_LanguageSpecific;

	protected int								m_NumTokens;
	protected int 								m_NumSentences;
	protected int								m_NumChars;
	
	protected boolean						    m_bDetectSB;

	protected SentenceBoundariesRecognition 	m_SBR;
	protected WekaLearner						m_SBRModel;
	
	protected int								m_Rules;
	protected String							m_Language;

    protected NamedEntityTypeEnumI[]            m_NETypes;
	/**
	 * Construct tokenizer by specifying language and some options.
	 * 
	 * @param pi_Language					The language this Tokenizer will accept
	 * @param pi_DetectSentenceBoundaries	Wether or not sentences must be detected
	 */
	public Tokenizer(String pi_Language, boolean pi_DetectSentenceBoundaries) {
		
		m_TokenList 		= new TokenList(pi_DetectSentenceBoundaries, NamedEntityTypeEnum.values());
		m_PunctLookup 		= new PunctLookup();
        m_AccentLookup      = new AccentLookup();
		m_LigatureLookup	= new LigatureLookup();
		m_UnbreakableLookup = new UnbreakableLookup();
		m_LanguageSpecific  = LoadLanguageModule(pi_Language);
		m_NumTokens    		= 0;
		m_NumSentences 		= 0;
		m_NumChars 			= 0;
		m_bDetectSB 		= pi_DetectSentenceBoundaries;

		if (pi_DetectSentenceBoundaries) {
			m_SBR			= new SentenceBoundariesRecognition(m_LanguageSpecific);
			m_SBRModel 		= SentenceBoundariesRecognition.GetModel();
		} else {
		    m_SBR = null;
		    m_SBRModel = null;
		}
		
		m_Rules = Canonizer.RULE_LOWERCASE | Canonizer.RULE_EXPAND_LIGATURES | Canonizer.RULE_NORMALIZE_PUNCT;
		m_Language = pi_Language;
        m_NETypes = NamedEntityTypeEnum.values();
	}

	private static LanguageSpecific LoadLanguageModule(String pi_Language) {
		LanguageSpecific ls = null;
		if (pi_Language.equals(Balie.LANGUAGE_ENGLISH)) {
			ls = new LanguageSpecificEnglish();
		} else if (pi_Language.equals(Balie.LANGUAGE_FRENCH)) {
			ls = new LanguageSpecificFrench();
		} else if (pi_Language.equals(Balie.LANGUAGE_GERMAN)) {
			ls = new LanguageSpecificGerman();
		} else if (pi_Language.equals(Balie.LANGUAGE_SPANISH)) {
			ls = new LanguageSpecificSpanish();
		} else if (pi_Language.equals(Balie.LANGUAGE_ROMANIAN)) {
			ls = new LanguageSpecificRomanian();
        } else if (pi_Language.equals(Balie.LANGUAGE_ITALIAN)) {
            throw new Error("This language is not implemented yet.");
		} else {
			throw new Error("Unsupported Tokenization Language.");
		}
		return ls;
	}
	
	/**
	 * Tokenize a text, turning it in a {@link TokenList}
	 * 
	 * @param pi_SourceText    text to tokenize
	 */
	public void Tokenize(String pi_SourceText) {
		
		if(Balie.DEBUG_TOKENIZER) DebugInfo.Out("Tokenizing");
		
		// Let split the input string on every white char
		String[] rawTokens = pi_SourceText.split(" ");
		
		// Intensively re-allocated Structures are out of loop
		String 		strCurrentComposed 		= "";
		String 		strCurrentDecomposed 	= "";

		String 		strCurrentRawTok 		= "";
		String 		strCurrentCanonTok 		= "";
		
		ArrayList<String[]> alTrailBuffer   = new ArrayList<String[]>();
		
		// count skipped white spaces
		int numSkippedWhite = 0;
		
		for (int i = 0; i != rawTokens.length; ++i) {
			strCurrentComposed = rawTokens[i];
			
			// Handle empty tokens (consecutive white spaces)
			if (strCurrentComposed.equals("")) {
				++numSkippedWhite;
			} else {
				
				String [] strTokens = m_LanguageSpecific.Decompound(strCurrentComposed);
				for (int j = 0; j != strTokens.length; ++j) {
					strCurrentDecomposed  = strTokens[j];
					int nStringLength = strCurrentDecomposed.length();
					
                    // Let's tokenize leading punctuations
					int nNumLead = 0;
					while (nNumLead != nStringLength && !Character.isLetterOrDigit(strCurrentDecomposed.charAt(nNumLead))) {
						strCurrentRawTok   = strCurrentDecomposed.substring(nNumLead,nNumLead+1);
						strCurrentCanonTok = Canonizer.CanonForm(strCurrentRawTok, Canonizer.RULE_NORMALIZE_PUNCT, m_PunctLookup, m_LigatureLookup, m_AccentLookup);
						Token curToken = new Token(	strCurrentRawTok, 
													strCurrentCanonTok,
													TokenConsts.TYPE_PUNCTUATION, 
													m_PunctLookup,
                                                    m_AccentLookup,
													m_NumTokens++,
													m_NumSentences,
													numSkippedWhite,
													m_NumChars,
                                                    m_NETypes.length);
						m_NumChars = curToken.EndPos();
						numSkippedWhite = 0;
						boolean bIsSB = m_TokenList.Add(curToken, m_SBR, m_SBRModel);
						if (bIsSB) {
							NewSentence();
						}
					
						++nNumLead;
					}
					
					// Lets buffer trailing punctuations (if different from leading)
					alTrailBuffer.clear();
					int nNumTrail = 0;
					if (nNumLead < nStringLength) {
						while (!Character.isLetterOrDigit(strCurrentDecomposed.charAt(nStringLength-nNumTrail-1))) {
							// Handle Unbreakables
							if (m_UnbreakableLookup.IsUnbreakable(strCurrentDecomposed.substring(nNumLead, nStringLength-nNumTrail))) {
								break;
							}
							strCurrentRawTok = strCurrentDecomposed.substring(nStringLength-nNumTrail-1,nStringLength-nNumTrail);
							strCurrentCanonTok = Canonizer.CanonForm(strCurrentRawTok, Canonizer.RULE_NORMALIZE_PUNCT, m_PunctLookup, m_LigatureLookup, m_AccentLookup);
							alTrailBuffer.add(new String[] {strCurrentRawTok, strCurrentCanonTok});
							++nNumTrail;
						}
					}
					
					// Let's tokenize remaining token (if something remains)
					if (nNumLead < nStringLength) {
						strCurrentRawTok  = strCurrentDecomposed.substring(nNumLead,nStringLength-nNumTrail);
						
						if (!strCurrentRawTok.equals("")) {
							int nTokenType = TokenConsts.TYPE_WORD;
							if (strCurrentRawTok.length() == 1 && !Character.isLetterOrDigit(strCurrentRawTok.charAt(0))) {
								nTokenType = TokenConsts.TYPE_PUNCTUATION;
								strCurrentCanonTok  = Canonizer.CanonForm(strCurrentRawTok, Canonizer.RULE_NORMALIZE_PUNCT, m_PunctLookup, m_LigatureLookup, m_AccentLookup);					
							} else {
								strCurrentCanonTok  = Canonizer.CanonForm(strCurrentRawTok, m_Rules, m_PunctLookup, m_LigatureLookup, m_AccentLookup);											
							}
							Token curToken = new Token(	strCurrentRawTok,
														strCurrentCanonTok, 
														nTokenType, 
														m_PunctLookup, 
                                                        m_AccentLookup,
														m_NumTokens++,
														m_NumSentences,
														numSkippedWhite,
														m_NumChars,
                                                        m_NETypes.length);
							m_NumChars = curToken.EndPos();
							boolean bIsSB = m_TokenList.Add(curToken, m_SBR, m_SBRModel);
							numSkippedWhite = 0;
							if (bIsSB) {
								NewSentence();
							}
							
						}
					}
					
					// Lets tokenize buffered trailing punctuations			
					ListIterator<String[]> iTrailCur =  alTrailBuffer.listIterator(alTrailBuffer.size());
					while (iTrailCur.hasPrevious()) {
						String [] strBuffered = (String[])iTrailCur.previous();
						Token curToken = new Token(strBuffered[0], 
													strBuffered[1],
													TokenConsts.TYPE_PUNCTUATION, 
													m_PunctLookup, 
                                                    m_AccentLookup,
													m_NumTokens++,
													m_NumSentences,
													0,
													m_NumChars,
                                                    m_NETypes.length);
						m_NumChars = curToken.EndPos();
						boolean bIsSB = m_TokenList.Add(curToken, m_SBR, m_SBRModel);
						if (bIsSB) {
							NewSentence();
						}
					}
				}
			}
			// go to next token (separated wy a white space
			if (numSkippedWhite == 0) {numSkippedWhite = 1;}
		}
		// Close last sentence..
		++m_NumSentences;		
        
		// TODO: may happend that last sentence is not recognized as "all cap" if the text is not ending with \n that helps detecting last sentence
        
		if (m_NumTokens != m_TokenList.Size()) {
			throw new Error("Inconsistant TokenList. Num tokens counted by tokenizer and tokenlist mismatch.");
		}
	}

	private void NewSentence() {
		m_SBR.YieldSentenceBeginning();
		++m_NumSentences;
	}

	/**
	 * Gets the TokenList.
	 * 
	 * @return TokenList
	 */
	public TokenList GetTokenList() {
		return m_TokenList;
	}
	
	/**
	 * Gets the number of token found in the text.
	 * 
	 * @return Number of tokens
	 */
	public int TokenCount() {
		return m_NumTokens;
	}
	
	/**
	 * Gets the number of sentences found.
	 * 
	 * @return Number of sentences.
	 */
	public int SentenceCount() {
		return m_NumSentences;
	}
	
	/**
	 * Set the rules the {@link Canonizer} will operates with.
	 * The default value is set at construction time and is something like:
	 * <code>Canonizer.RULE_LOWERCASE | Canonizer.RULE_REMOVE_INTERNAL_PUNCT | Canonizer.RULE_EXPAND_LIGATURES | Canonizer.RULE_NORMALIZE_PUNCT</code>
	 * 
	 * @param pi_Rules Disjunction of required rules
	 */
	public void SetCanonizerRules(int pi_Rules) {
		m_Rules = pi_Rules;
	}

	/**
	 * Reset the Tokenizer in order to process another text.
	 * Can be called on a tokenizer even if no text has been processed.
	 */
	public void Reset() {
		m_NumTokens    		= 0;
		m_NumSentences 		= 0;
		m_NumChars 			= 0;
		m_TokenList 		= new TokenList(m_bDetectSB, m_NETypes);
        if (m_SBR != null) m_SBR.Reset();
	}

	/**
	 * Reset the Tokenizer in order to process another text, in a different language.
	 * Can be called on a tokenizer even if no text has been processed.
	 * 
	 * @param pi_Language	New language the tokenizer will accept 
	 */
	public void Reset(String pi_Language) {
		Reset();
		m_LanguageSpecific  = LoadLanguageModule(pi_Language);
		m_Language			= pi_Language;
	}
	
	/**
	 * Gets the tokenizer working language.
	 * 
	 * @return the language this tokenizer can handle
	 */
	public String Language() {
	    return m_Language;
	}
	
}
