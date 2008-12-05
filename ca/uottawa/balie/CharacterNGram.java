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
 * Created on Apr 9, 2004
 */
package ca.uottawa.balie;

import java.util.Hashtable;

/**
 * Methods to collect and handle character n-gram.
 * A character n-gram is a sequence of n chars.
 * For instance, in the word WORD, there are 3 bigrams: WO, OR and RD.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class CharacterNGram {

	private static final int MAX_BUFFER = 10 << 10; // speed optimization
	
	/**
	 * Creates a new n-gram handler.
	 * 
	 * @param pi_NGramSize The value of N (must be at least 2, to extract bigrams)
	 */
	public CharacterNGram(int pi_NGramSize) {
		if (pi_NGramSize < 2) {
			throw new Error("NGram must be at least 2 chars long.");
		}
		m_NGramSize 		= pi_NGramSize;
		m_NGramFrequency 	= new Hashtable<String,Integer>();
		m_UNIGramFrequency  = new Hashtable<String,Integer>();
		m_DocumentFrequency	= 0;
		m_PunctLookup		= new PunctLookup();
		m_LigatureLookup 	= new LigatureLookup(); 
        m_AccentLookup      = new AccentLookup();
	}
	
	private int 			            m_NGramSize;
	private Hashtable<String,Integer>	m_UNIGramFrequency;
	private Hashtable<String,Integer>	m_NGramFrequency;
	private int				            m_DocumentFrequency;
	private PunctLookup 	            m_PunctLookup;
	private LigatureLookup 	            m_LigatureLookup;	
    private AccentLookup                m_AccentLookup;

	/**
	 * Feed a text to the N-gram handler.
	 * The handler reads the text and find n-grams.
	 * It also computes statistics.
	 * 
	 * @param pi_InString The text to split in n-grams
	 */
	public void Feed(String pi_InString) {
		String strCanonFeed = Canonizer.CanonForm(pi_InString, Canonizer.RULE_LOWERCASE | Canonizer.RULE_EXPAND_LIGATURES | Canonizer.RULE_NORMALIZE_PUNCT, m_PunctLookup, m_LigatureLookup, m_AccentLookup);

		// We don't want to learn white char sequences
		strCanonFeed = strCanonFeed.replaceAll("[ ]+", " ");

		// We don't want to learn punctuations
		StringBuffer sb = new StringBuffer();
		char[] cFeed = strCanonFeed.toCharArray();
		for (int i = 0; i != cFeed.length; ++i) {
		    if ((!m_PunctLookup.IsPunctuation(cFeed[i]) && !Character.isDigit(cFeed[i])) || cFeed[i] == ' ') {
		        sb.append(cFeed[i]);
		    }
		}
		strCanonFeed = sb.toString();
		

		// read the string
		int nHead = 0;
		int nTail = m_NGramSize;
		while (nTail < strCanonFeed.length()) {
			int nGramSize = m_NGramSize;

			// Add the NGrams (size nGramSize, nGramSize-1, ...) 
			while (nGramSize >= 2) {
				String strNGram = strCanonFeed.substring(nHead,nHead+nGramSize);
				AddNGram(m_NGramFrequency,strNGram);
				--nGramSize;
			}
			
			// Also add the current unigram
			AddNGram(m_UNIGramFrequency,strCanonFeed.substring(nHead,nHead+1));

			++nTail;
			// Stop at the maximum buffer size (optimization)
			if (++nHead > MAX_BUFFER) {
				break;
			}
		}
		
		// Add end of string unigrams
		while (++nHead < strCanonFeed.length()) {
			//Add the current unigram
			AddNGram(m_UNIGramFrequency,strCanonFeed.substring(nHead,nHead+1));
			
			// Stop at the maximum buffer size (optimization)
			if (nHead > MAX_BUFFER) {
				break;
			}
		}
	}

	private void AddNGram(Hashtable<String,Integer> pi_hashFrequency, String pi_Ngram) {
	    if (!pi_Ngram.equals(" ")) {
			if (pi_hashFrequency.containsKey(pi_Ngram)) {
				int nFreq = pi_hashFrequency.get(pi_Ngram).intValue(); 
				pi_hashFrequency.put(pi_Ngram,new Integer(++nFreq));
			} else {
				pi_hashFrequency.put(pi_Ngram,new Integer(1));
			}
			
			++m_DocumentFrequency;	        
	    }
	}
	
    
    /* REMOVED on Sept 21, 2006... seems to be no more in use
	private String[] SortNGrams() {
		String[] sortedNGrams = new String[m_NGramFrequency.size()];
		Enumeration keyEnum = m_NGramFrequency.keys();
		int index = 0;
		while (keyEnum.hasMoreElements()) {
			sortedNGrams[index++] = (String)keyEnum.nextElement();
		}
		Arrays.sort(sortedNGrams);
		return sortedNGrams;
	}
	 */

	/**
	 * Creates an instance made of n-gram relative frequencies for a given set of reference n-grams.
	 * The relative frequency of a given n-gram is its frequency divided by the total number of n-gram. 
	 * The reference n-gram list is a subset of the entire n-gram list, proper or not. 
	 *  
	 * @param pi_RefNGrams Reference n-grams for which the statistics are required. 
	 * @return A parrallel array containing the relative frequency of the reference n-grams.
	 */
	public Double[] Instance(String[] pi_RefNGrams) {
	    
		 Double[] instValues = new Double[pi_RefNGrams.length];
		 double nFreq = 0.0;
		 for (int i = 0; i != pi_RefNGrams.length; ++i) {
		 	if (m_NGramFrequency.containsKey(pi_RefNGrams[i])) {		 		nFreq += ((Integer)m_NGramFrequency.get(pi_RefNGrams[i])).doubleValue();
		 	}
		 	if (m_UNIGramFrequency.containsKey(pi_RefNGrams[i])) {
		 		nFreq += ((Integer)m_UNIGramFrequency.get(pi_RefNGrams[i])).doubleValue();
			}
		 	instValues[i] = new Double(nFreq / m_DocumentFrequency);
		 }
		 return instValues;
		 
	}
	
	/**
	 * Get the table that associates each n-gram to its frequency.
	 * 
	 * @return Hashtable for which the keys are n-gram and the values are frequencies
	 */
	public Hashtable<String,Integer> NGramFrequency() {
		return m_NGramFrequency;
	}

	/**
     * Get the Unigram table
	 * @return Hashtable for which the keys are uni-gram and the values are frequencies
	 */
	public Hashtable<String,Integer> UNIGramFrequency() {
		return m_UNIGramFrequency;
	}
	
	// Test Routine
    /*
	private static void main(String[] args) {
		CharacterNGram cng = new CharacterNGram(3);
		cng.Feed("This document defines syntax for representing N-Gram (Markovian) stochastic grammars within the W3C Speech Interface Framework. The use of stochastic N-Gram models has a long and successful history in the research community and is now more and more effecting commercial systems, as the market asks for more robust and flexible solutions. The primary purpose of specifying a stochastic grammar format is to support large vocabulary and open vocabulary applications. In addition, stochastic grammars can be used to represent concepts or semantics. This specification defines the mechanism for combining stochastic and structured (in this case Context-Free) grammars as well as methods for combined semantic definitions.");
		Hashtable freq = cng.NGramFrequency();
		Enumeration keyEnum = freq.keys();
		while (keyEnum.hasMoreElements()) {
			String strNGram = (String)keyEnum.nextElement();
			System.out.print(strNGram + ": ");
			System.out.println(String.valueOf(freq.get(strNGram)));
		}
		String[] test = cng.SortNGrams();
		Double[] values = cng.Instance(test);
	}
    */
}
