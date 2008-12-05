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
 * File created on 07-08-20
 */
package ca.uottawa.balie;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class NamedEntityAliasResolution {
    
    public NamedEntityAliasResolution(TokenList pi_TokenList, int pi_TagSetSize, NamedEntityType pi_Desired) {
        m_TokenList = pi_TokenList;
        m_TagSetSize = pi_TagSetSize;
        m_DesiredEntities = pi_Desired;
        
        m_Word2GroupId = new Hashtable<String, Integer>();
        m_GroupId2NamedEntityAlias = new Hashtable<Integer, NamedEntityAlias>();
    }

    public NamedEntityAlias GetAlias(Integer pi_GroupId) {
        return (NamedEntityAlias)m_GroupId2NamedEntityAlias.get(pi_GroupId);
    }
    
    
    public void ResolveAliasesNerf() {
        // each alias group is assigned an ID
        int nCurAliasGroupId = 0;

        // Loop through tokenlist and build a coreference network
        for (int i = 0; i != m_TokenList.Size()-1; ++i) {
            Token curTok  = m_TokenList.Get(i);
            
            if (ValidAliasToken(curTok)) {
                ArrayList<String> alBow = GetEntityBOWExt(i); 

                ArrayList<Integer> alGroups = GroupOverlapNerf(alBow);
                // add current entity to an existing group
                if (alGroups.size() > 0) {
                    // belongs to exactly one group
                    if (alGroups.size() == 1) {
                        NamedEntityAlias neAlias = (NamedEntityAlias)m_GroupId2NamedEntityAlias.get(alGroups.get(0));
                        neAlias.Add(alBow, i);
                        Iterator<String> iCur = alBow.iterator();
                        while (iCur.hasNext()) {
                            String strWord = iCur.next();
                            m_Word2GroupId.put(strWord, alGroups.get(0));
                        }
                    }
                    // belongs to many groups
                    else {
                        // merge groups
                        NamedEntityAlias neAlias = (NamedEntityAlias)m_GroupId2NamedEntityAlias.get(alGroups.get(0));
                        neAlias.Add(alBow, i);
                        for (int j = 1; j != alGroups.size(); ++j) {
                            neAlias.Merge((NamedEntityAlias)m_GroupId2NamedEntityAlias.get(alGroups.get(j)));
                            m_GroupId2NamedEntityAlias.remove(alGroups.get(j));
                            // remap words on this group
                            ArrayList<String> alWords = neAlias.Bow();
                            Iterator<String> iWCur = alWords.iterator();
                            while (iWCur.hasNext()) {
                                String strWord = iWCur.next();
                                m_Word2GroupId.put(strWord, alGroups.get(0));
                            }
                        }
                    }
                }
                // create a new group
                else {
                    NamedEntityAlias neAlias = new NamedEntityAlias(alBow, i, m_TagSetSize); 
                    Iterator<String> iCur = alBow.iterator();
                    while (iCur.hasNext()) {
                        String strWord = iCur.next();
                        m_Word2GroupId.put(strWord, new Integer(nCurAliasGroupId));
                    }
                    m_GroupId2NamedEntityAlias.put(new Integer(nCurAliasGroupId++), neAlias);
                }
            }
        }
        
        // create a reverse lookup for alias group
        Enumeration<Integer> eCur = m_GroupId2NamedEntityAlias.keys();
        while (eCur.hasMoreElements()) {
            Integer nGroup = (Integer)eCur.nextElement();
            NamedEntityAlias neAlias = (NamedEntityAlias)m_GroupId2NamedEntityAlias.get(nGroup);
            if (Balie.DEBUG_NAMED_ENTITY_RECOGNITION) DebugInfo.Out(neAlias.toString());
            Iterator<Integer> iCur = neAlias.Entities().iterator();
            while (iCur.hasNext()) {
                Integer nEnt = iCur.next();
                
                // set token information
                Token tok = m_TokenList.Get(nEnt);
                tok.NamedEntityAlias(nGroup);
            }
        }
    }
    
    
    public void IdentifyDominantAliases(boolean pi_FullDebug) {
        // try to identify a dominant type in each alias group
        Enumeration<Integer> eCur = m_GroupId2NamedEntityAlias.keys();
        while (eCur.hasMoreElements()) {
            
            // check all aliases
            Integer nGroupId = eCur.nextElement();
            NamedEntityAlias neAlias = (NamedEntityAlias)m_GroupId2NamedEntityAlias.get(nGroupId);

            if (pi_FullDebug) System.out.print("Alias group: " + neAlias.Bow() + "... ");
            
            // check for unambiguous alias
            ArrayList<Integer> alEntities = neAlias.Entities();
            Iterator<Integer> iCur = alEntities.iterator();
            Hashtable<NamedEntityType, Integer> hPureTypes = new Hashtable<NamedEntityType, Integer>(); 
            while (iCur.hasNext()) {
                Integer idx = iCur.next();
                Token localTok  = m_TokenList.Get(idx.intValue());
                if (localTok.EntityType() != null) {
                    NamedEntityType localType = NamedEntityType.Intersection(localTok.EntityType(), m_DesiredEntities, null);
                    
                    if (localType.TypeCount() <= 1) {
                        
                        // remove "explanation" field
                        localType.WipeInfo();
                        
                        if (hPureTypes.containsKey(localType)) {
                            int nFreq = hPureTypes.get(localType).intValue();
                            hPureTypes.put(localType, new Integer(nFreq+1));
                        } else {
                            hPureTypes.put(localType, new Integer(1));
                        }
                    }
                    
                }
            }

            if (hPureTypes.size() > 1) {
                Enumeration<NamedEntityType> eTCur = hPureTypes.keys();
                int nMaxFreq = 0;
                NamedEntityType maxType = new NamedEntityType(m_TagSetSize, null);
                while (eTCur.hasMoreElements()) {
                    NamedEntityType thisType = eTCur.nextElement();
                    int nThisFreq = hPureTypes.get(thisType).intValue();
                    
                    // find the dominant type but do not allows a typeless alias (e.g., unknown capitalized word)
                    if (thisType.TypeCount() !=0 && nThisFreq > nMaxFreq) {
                        nMaxFreq = nThisFreq;
                        maxType = thisType.clone(null);
                    }
                }
                
                neAlias.SetDominantType(maxType);
                m_GroupId2NamedEntityAlias.put(nGroupId, neAlias);
                if (pi_FullDebug) System.out.println(" dominant group: " + maxType.GetLabel(m_TokenList.NETagSet()));
            } else {
                if (pi_FullDebug) System.out.println(" no ambiguity.");
            }
            
        }    
    }
    
    private ArrayList<Integer> GroupOverlapNerf(ArrayList<String> pi_Bow) {
        ArrayList<Integer> alGroups = new ArrayList<Integer>();
        Iterator<String> iCur = pi_Bow.iterator();
        while (iCur.hasNext()) {
            String strWord = iCur.next();
            if (m_Word2GroupId.containsKey(strWord)) {
                Integer id = m_Word2GroupId.get(strWord);
                NamedEntityAlias nea = m_GroupId2NamedEntityAlias.get(id);
                if (nea.SignificantOverlap(pi_Bow)) {
                    alGroups.add(id);
                }
            }
        }
        alGroups = new ArrayList<Integer>(new HashSet<Integer>(alGroups)); //remove duplicates
        return alGroups;
    }
    
    private ArrayList<String> GetEntityBOWExt(int pi_Index) {
        
        // break condition: if inside entity break when END is met
        // if not inside an entity, break immediately
        boolean bInsideEntity = true;
        
        ArrayList<String> alBow = new ArrayList<String>();
        for (int i = pi_Index; i != m_TokenList.Size()-1; ++i) {

            Token curTok  = m_TokenList.Get(i);

            // check if inside entity at first iteration
            if (i == pi_Index) {
                bInsideEntity = curTok.EntityType().IsStart();
            }
            
            if ((TokenConsts.Is(curTok.Type(), TokenConsts.TYPE_WORD) &&
                curTok.Canon().length() >= Balie.MIN_SIZE_ALIAS_MATCHING)) {
                alBow.add(curTok.Canon());
            }
            
            if (bInsideEntity && curTok.EntityType().IsEnd()) {
                break;
            }
            
            if (!bInsideEntity) break;
        }
        return alBow;
    }

    protected ArrayList<String> GetEntityBOWExt(ArrayList<Token> pi_Toks) {
        ArrayList<String> alBow = new ArrayList<String>();
        Iterator<Token> iCur = pi_Toks.iterator();
        while (iCur.hasNext()) {
            Token curTok  = iCur.next();
            if ((TokenConsts.Is(curTok.Type(), TokenConsts.TYPE_WORD) &&
                curTok.Canon().length() >= Balie.MIN_SIZE_ALIAS_MATCHING)) {
                alBow.add(curTok.Canon());
            }
        }
        return alBow;
    }
    
    /**
     * Check if this token can be use in alias network
     * 
     * @param pi_Tok a token
     * @return true if this token can be the alias of an entity
     */
    public static boolean ValidAliasToken(Token pi_Tok) {
        // valid if is a named entity
        boolean bCondition1 = pi_Tok.EntityType().IsStart() && !pi_Tok.EntityType().HasNoTag() && !pi_Tok.EntityType().IsA(NamedEntityTypeEnum.NOTHING);
        // valid if not a named entity but is capitalized
        boolean bCondition2 = pi_Tok.EntityType().HasNoTag() && TokenFeature.Feature.StartsWithCapital.Mechanism().GetBooleanValue(pi_Tok.Features());
        
        return bCondition1 || bCondition2;
    }
    
    private TokenList                                       m_TokenList;
    private int                                             m_TagSetSize;
    protected NamedEntityType                               m_DesiredEntities;
    
    private Hashtable<String, Integer>                      m_Word2GroupId;          // alias group id given a word
    private Hashtable<Integer, NamedEntityAlias>            m_GroupId2NamedEntityAlias;  // Alias group lookup



}
