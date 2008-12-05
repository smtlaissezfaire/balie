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
 * File created on 07-07-18
 */
package ca.uottawa.balie;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class DisambiguationRulesNerf implements Serializable, DisambiguationRulesI {

    private static final long serialVersionUID  = 1L;

    public DisambiguationRulesNerf(int pi_MinFreq, int pi_ContextSize, FeatureRepresentation[] pi_Fr) {
        m_MinFreq = pi_MinFreq;
        m_ContextSize = pi_ContextSize;
        m_Fr = pi_Fr;
        m_Cache = new Hashtable<DisambiguationRulesKey, WekaLearner>();
    }
    
    public DisambiguationRulesOutcome Classify(DisambiguationRulesKey pi_Key, String[] pi_Context, Hashtable<String, Double> pi_hPriorMap) {

        WekaLearner wl = null;
        if (m_Cache.containsKey(pi_Key)) {
            wl = m_Cache.get(pi_Key);
        } else {
            wl = WekaPersistance.Load("rulesystem/" + pi_Key.toString());
            m_Cache.put(pi_Key, wl);
        }

        Object[] inst = new Object[0];
        if (m_Fr.length == 1) {
            inst = m_Fr[0].GetWekaInstance(pi_Context, wl.GetAttribute(), m_MinFreq);
        } else {
            for (int i = 0; i != m_Fr.length; ++i) {
                inst = MergeInst(inst, m_Fr[i].GetWekaInstance(pi_Context, wl.GetAttribute(), m_MinFreq));
            }
        }
        
        String[] strClasses = wl.GetClassList();
        double likelihood1 = wl.Likelihood(inst, 0);
        double likelihood2 = wl.Likelihood(inst, 1);
        
        // apply prior correction here
        double prob1 = likelihood1 * pi_hPriorMap.get(strClasses[0]).doubleValue() / (likelihood1 * pi_hPriorMap.get(strClasses[0]).doubleValue() + likelihood2 * pi_hPriorMap.get(strClasses[1]).doubleValue()); 
        double prob2 = likelihood2 * pi_hPriorMap.get(strClasses[1]).doubleValue() / (likelihood1 * pi_hPriorMap.get(strClasses[0]).doubleValue() + likelihood2 * pi_hPriorMap.get(strClasses[1]).doubleValue()); 
        
        int nClass = prob1>prob2?0:1;

        DisambiguationRulesOutcome outcome = new DisambiguationRulesOutcome(strClasses[nClass], Math.max(prob1, prob2));
        
        return outcome;
    }

    private Object[] MergeInst(Object[] inst, Object[] objects) {
        Object[] merg = new Object[inst.length + objects.length];
        for (int i = 0; i != inst.length; ++i) {
            merg[i] = inst[i];
        }
        for (int i = 0; i != objects.length; ++i) {
            merg[inst.length+i] = objects[i];
        }
        return merg;
    }
    
    public int ContextSize() {
        return m_ContextSize;
    }

    private void ResetCache() {
        m_Cache = new Hashtable<DisambiguationRulesKey, WekaLearner>();
    }
    
    public static DisambiguationRulesNerf Load() {
        DisambiguationRulesNerf rules = null;
        ClassLoader cl = LexiconOnDisk.class.getClassLoader();
        try {
            InputStream in = cl.getResourceAsStream("rulesystem/rules.index");
            BufferedInputStream bis = new BufferedInputStream(in);
            ObjectInputStream p = new ObjectInputStream(bis);
            rules = ((DisambiguationRulesNerf)p.readObject());
            p.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // OEF exception may means that the serialized Rule has a new "non-serialized" component...
        }
        rules.ResetCache();
        return rules;
    }
    

    private int                                             m_MinFreq;
    private int                                             m_ContextSize;
    private FeatureRepresentation[]                         m_Fr;
    private Hashtable<DisambiguationRulesKey, WekaLearner>  m_Cache;

    
}
