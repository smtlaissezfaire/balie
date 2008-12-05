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
 * File created on 07-04-25
 */
package ca.uottawa.balie;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class FeatureRepresentationVocabulary {

    public FeatureRepresentationVocabulary(Hashtable<String, Integer[]> pi_PosVoc, Hashtable<String, Integer[]> pi_NegVoc, Hashtable<String, Integer[]> pi_FullVoc) {
        m_PositiveVoc = pi_PosVoc;
        m_PositiveVocSize = CountVocabulary(pi_PosVoc);
        m_NegativeVoc = pi_NegVoc;
        m_NegativeVocSize = CountVocabulary(pi_NegVoc);
        m_FullVoc = pi_FullVoc;
        m_FullVocSize = CountVocabulary(pi_FullVoc);
    }

    public Hashtable<String, Integer[]> GetFullVoc() {
        return m_FullVoc;
    }

    public double LikelihoodRatio(String strWord) {
        int nPosFreq = 1;
        int nNegFreq = 1;
        if (m_PositiveVoc.containsKey(strWord)) {
            nPosFreq += WordFreqency(m_PositiveVoc.get(strWord));
        }
        if (m_NegativeVoc.containsKey(strWord)) {
            nNegFreq += WordFreqency(m_NegativeVoc.get(strWord));
        }
        double fPosRelFreq =  (double)nPosFreq / m_PositiveVocSize;
        double fNegRelFreq =  (double)nNegFreq / m_NegativeVocSize;
        
        if (fPosRelFreq >= fNegRelFreq) {
            return fPosRelFreq / fNegRelFreq;
        } else {
            return fNegRelFreq / fPosRelFreq;
        }
    }
    
    
    private int CountVocabulary(Hashtable<String, Integer[]> pi_Voc) {
        int nVocSize = 0;
        Enumeration<String> eCur = pi_Voc.keys();
        while (eCur.hasMoreElements()) {
            String strCur = eCur.nextElement();
            Integer[] nFreqs = pi_Voc.get(strCur);
            nVocSize += WordFreqency(nFreqs);
        }
        return nVocSize;
    }
    
    private int WordFreqency(Integer[] pi_Freqs) {
        int nWordFreq = 0;
        for (int i = 0; i!= pi_Freqs.length; ++i) {
            nWordFreq += pi_Freqs[i];
        }
        return nWordFreq;
    }
    
    Hashtable<String, Integer[]> m_PositiveVoc;
    int                          m_PositiveVocSize;
    
    Hashtable<String, Integer[]> m_NegativeVoc;
    int                          m_NegativeVocSize;
    
    Hashtable<String, Integer[]> m_FullVoc;
    int                          m_FullVocSize;
    
}
