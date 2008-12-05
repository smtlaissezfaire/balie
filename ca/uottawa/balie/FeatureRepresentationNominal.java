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
 * File created on 07-04-03
 */
package ca.uottawa.balie;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import weka.core.Attribute;
import weka.core.FastVector;

import ca.uottawa.balie.WekaAttribute;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class FeatureRepresentationNominal implements FeatureRepresentation {
    
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unchecked")
    public WekaAttribute[] GetWekaAttributes(FeatureRepresentationVocabulary pi_Vocabulary, int pi_MinFreq, int pi_ContextSize) {
        WekaAttribute[] attrs = new WekaAttribute[pi_ContextSize * 2];
        
        ArrayList[] als = new ArrayList[pi_ContextSize * 2];
        for (int i = 0; i != pi_ContextSize * 2; ++i) {
            ArrayList<String> al = new ArrayList<String>();
            als[i] = al;
        }
        
        Enumeration<String> eCur = pi_Vocabulary.GetFullVoc().keys();
        while (eCur.hasMoreElements()) {
            String strWord  = eCur.nextElement();
            Integer[] ctxfreq = pi_Vocabulary.GetFullVoc().get(strWord);
            for (int i = 0; i != pi_ContextSize * 2; ++i) {
                if (ctxfreq[i] > pi_MinFreq) {
                    als[i].add(strWord);
                }
            }
        }

        // turn arraylist in fastvector
        FastVector[] fvs = new FastVector[pi_ContextSize * 2];
        for (int i = 0; i != pi_ContextSize * 2; ++i) {
            FastVector fv = new FastVector(als[i].size()+1);
            Iterator<String> iCur = als[i].iterator();
            while (iCur.hasNext()) {
                String strWord = iCur.next();
                fv.addElement(strWord);
            }
            fvs[i] = fv;
        }
        
        for (int i = 0; i != pi_ContextSize * 2; ++i) {
            attrs[i] = new WekaAttribute("Context"+i, fvs[i]);
        }
        
        
        return attrs;
    }

    public Object[] GetWekaInstance(String[] pi_Inst, FastVector pi_Attributes, int pi_MinFreq) {
        Object[] inst = new Object[pi_Attributes.size()-1];
        
        for (int i = 0; i != pi_Attributes.size()-1; ++i) {
            Attribute attr = (Attribute)pi_Attributes.elementAt(i);
            if (pi_Inst[i] != null && attr.indexOfValue(pi_Inst[i]) != -1) {
                inst[i] = pi_Inst[i];
            } else {
                inst[i] = null;
            }
        }
        
        return inst;
    }

    public boolean DoubleOnly() {
        return false;
    }
    
    public String Name() {
        return "Nominal";
    }    
}
