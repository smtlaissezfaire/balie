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
import java.util.Hashtable;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class NERServices {

    /**
     * Check if a named entity starts here
     * 
     * @param pi_Netype a named entity type information
     * @return true if starts here
     */
    public static boolean EntityStartsHere(NamedEntityType pi_Netype) {
        // entity starts
        return pi_Netype.IsStart();
    }
    
    /**
     * check if this named entity can occurs naturally in lowercased form
     * @param pi_Netype a named entity type information
     * @return true if can occur lowercased
     */
    public static boolean NaturallyLowercased(NamedEntityType pi_Netype) {
        // entity that can be lowercased naturally
        return pi_Netype.IsStart() && pi_Netype.Intersect(NamedEntityTypeEnum.NaturallyOccursLowercased());
    }
    
    /**
     * Check if the entity is made of one word only
     * 
     * @param pi_Netype a named entity type information
     * @return true if the entity is made of one word (this is not a multi-word entity)
     */
    public static boolean OneWordEntity(NamedEntityType pi_Netype) {
        // isolated token
        return pi_Netype.IsStart() && pi_Netype.IsEnd();
    }

    /**
     * Check if this entity is multiword and lowercased
     * 
     * @param pi_Token current token
     * @return true if the token is a the beginning of a multi-word, lowercased entity
     */
    public static boolean UncapitalizedMultiWordEntity(Token pi_Token) {
        // uncapitalized multi-word expression 
        return pi_Token.EntityType().IsStart() && !TokenFeature.Feature.StartsWithCapital.Mechanism().GetBooleanValue(pi_Token.Features());
    }

    /**
     * Get types of shared labels
     * 
     * @param pi_SharedLabs a map of shared labels associated with corresponding types
     * @param pi_TagSetSize the current tagset
     * @return named entity type containing all types of shared labels
     */
    public static NamedEntityType Labels2Types(Hashtable<String, NamedEntityTypeEnumI> pi_SharedLabs, int pi_TagSetSize) {
        NamedEntityType newNet = new NamedEntityType(pi_TagSetSize, new NamedEntityExplanation(NamedEntityExplanation.ExplanationType.ADJACENT_SHARE_TYPE, new Object[]{}));
        Enumeration<String> eCur = pi_SharedLabs.keys();
        while (eCur.hasMoreElements()) {
            String strKey = eCur.nextElement();
            NamedEntityTypeEnumI aType = pi_SharedLabs.get(strKey);
            newNet.AddType(aType, null);
        }
        return newNet;
    }

    /**
     * Get labels that are common to two entities (labels are the final tag associated with an entity)
     * 
     * @param pi_NetCur one entity type
     * @param pi_NetNext another entity type
     * @param pi_NETagSet the current tagset
     * @return a map of shared labels associated with corresponding types
     */
    public static Hashtable<String, NamedEntityTypeEnumI> SharedLabel(NamedEntityType pi_NetCur, NamedEntityType pi_NetNext, NamedEntityTypeEnumI[] pi_NETagSet) {
        // it is important to check the "label", not the type.
        // for instance, in MUC, the PERSON label allow to merge first_name, last_name, etc.
        
        // EXPLANATIONS of "David Riley bug". David is person>first name, org>food_brand, Riley is person>last name, org>cie.
        // entity were merged with shared = {person>first name, org>cie} by design.
        // prior, calculated on the first entity was 0 for prior(David,org>cie) because this case is not possible (not in prior cache) 
        
        ArrayList<String> alLabsCur =  pi_NetCur.GetAllLabels(pi_NETagSet);
        ArrayList<String> alLabsNext = pi_NetNext.GetAllLabels(pi_NETagSet);
        
        Hashtable<String, NamedEntityTypeEnumI> hShared = new Hashtable<String, NamedEntityTypeEnumI>();
        for (int i = 0; i != pi_NETagSet.length; ++i) {
            if (pi_NETagSet[i].Label() != null &&
                    !hShared.containsKey(pi_NETagSet[i].Label()) && 
                    alLabsCur.contains(pi_NETagSet[i].Label()) && 
                    pi_NetCur.IsA(pi_NETagSet[i]) && // force current entity to have the type (see David Riley bug above)
                    alLabsNext.contains(pi_NETagSet[i].Label())) {
                hShared.put(pi_NETagSet[i].Label(), pi_NETagSet[i]);
            }
        }
        return hShared;
    }
    
    /**
     * Add an element in a given object of map
     * 
     * @param pi_Hash the map
     * @param pi_Key the object in the map
     * @param i the element to add
     */
    public static void AddIndexExt(Hashtable<String,ArrayList<Integer>> pi_Hash, String pi_Key, int i) {
        if (pi_Hash.containsKey(pi_Key)) {
            ArrayList<Integer> alIdx = (ArrayList<Integer>)pi_Hash.get(pi_Key);
            alIdx.add(new Integer(i));
            pi_Hash.put(pi_Key, alIdx);
        } else {
            ArrayList<Integer> alNew = new ArrayList<Integer>();
            alNew.add(new Integer(i));
            pi_Hash.put(pi_Key, alNew);
        }
    }    

}
