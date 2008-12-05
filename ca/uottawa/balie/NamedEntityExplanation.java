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
 * File created on 07-05-15
 */
package ca.uottawa.balie;

import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class NamedEntityExplanation {

    public enum ExplanationType {
        LEXICON_LOOKUP(2),
        FUZZY_MATCH(2),
        ENTITY_ENTITY_MERGING(3),
        ENTITY_CAPITAL_MERGING(2),
        ENTITY_PUNCT_MERGING(2),
        UNKNOWN_CAPITAL(1),
        ADJACENT_SHARE_TYPE(0),
        ACRONYM(2),
        CLASSIFIER(4),
        CLASSIFIER_VOTE(5),
        ALIAS_DOMINANT_TYPE(1),
        NATURAL_LOWERCSE(0),
        REJECTED_LOWERCASED_OR_AMBIGUOUS(0),
        REJECTED_LOWERCASED_AND_UPPERCASED(0),
        REJECTED_VERY_AMBIGUOUS(0);
        
        private ExplanationType(int pi_NumArgs) {
            m_NumArgs = pi_NumArgs;
        }
        public int m_NumArgs;
    }
    public static class LexiconLookup {}
    public static class FuzzyMatch {}
    public static class EntityEntityMerging {}
    public static class EntityCapitalMerging {}
    public static class EntityPunctMerging {}
    public static class UnknownCapital {}
    public static class AdjacentShareType {}
    public static class Acronym {}
    public static class Classifier {}
    public static class ClassifierVote {}
    public static class AliasDominantType {}
    public static class NaturalLowercase {}
    public static class RejectedLowercasedOrAmbiguous {}
    public static class RejectedLowercasedAndUppercased {}
    public static class RejectedVeryAmbiguous {}
    
    public NamedEntityExplanation(ExplanationType pi_Type, Object[] pi_Args) {
        m_Type = pi_Type;
        m_Args = pi_Args;
        if (m_Args.length != m_Type.m_NumArgs) {
            throw new Error("Wrong number of arguments for explanation");
        }
    }
    /* CHANGED FOR MEMORY OPTIMIZATION
    public NamedEntityExplanation(LexiconLookup pi_Explain, String pi_Entity, String pi_Label) {
        m_Exp = "<i>" + pi_Entity + "</i> was found in lexicon with type <i>" + pi_Label + "</i>.";
    }
    public NamedEntityExplanation(FuzzyMatch pi_Explain, String pi_Original, String pi_Match) {
        m_Exp = "<i>" + pi_Original + "</i> is fuzzy matched against <i>" + pi_Match + "</i>.";
    }
    public NamedEntityExplanation(EntityEntityMerging pi_Explain, String pi_Ending, String pi_PreviousType, String pi_NextType) {
        m_Exp = "Entities ending with <i>" + pi_Ending + "</i> of type <i>" + pi_PreviousType + "</i> was merged with adjacent entity of type <i>" + pi_NextType + "</i>.";
    }
    public NamedEntityExplanation(EntityCapitalMerging pi_Explain, String pi_Ending, String pi_Capital) {
        m_Exp = "Entities ending with <i>" + pi_Ending + "</i> was merged with the capitalized word <i>" + pi_Capital + "</i>.";
    }
    public NamedEntityExplanation(EntityPunctMerging pi_Explain, String pi_Ending, String pi_Punct) {
        m_Exp = "Entities ending with <i>" + pi_Ending + "</i> was merged with non-terminal punctation <i>" + pi_Punct + "</i>.";
    }
    public NamedEntityExplanation(UnknownCapital pi_Explain, String pi_Capital) {
        m_Exp = "The unknown capitalized word <i>" + pi_Capital + "</i> inherits its type from an alias network.";
    }
    public NamedEntityExplanation(AdjacentShareType pi_Explain) {
        m_Exp = "Adjacent entities sharing a common type were merged.";
    }
    public NamedEntityExplanation(Acronym pi_Explain, String pi_Acronym, String pi_Expansion) {
        m_Exp = "Type is forced for acronym <i>" + pi_Acronym + "</i>: <i>" +pi_Expansion+"</i>.";
    }
    public NamedEntityExplanation(Classifier pi_Explain, String pi_Entity, String pi_Type, double pi_Likelyhood, Hashtable<String, Double> pi_hPriorMap) {
        DecimalFormat df = new DecimalFormat("#.##");
        m_Exp = "Entity starting with <i>" + pi_Entity + "</i> was classified as <i>" + pi_Type + "</i> using contextual cues (confidence = "+df.format(pi_Likelyhood)+") ";
        m_Exp += "with prior probabilities {";
        Enumeration<String> eCur = pi_hPriorMap.keys();
        while (eCur.hasMoreElements()) {
            String strClass = eCur.nextElement();
            m_Exp += strClass + ": " + df.format(pi_hPriorMap.get(strClass)).toString();
            if (eCur.hasMoreElements()) m_Exp += ", ";
        }
        m_Exp += "}.";
    }
    public NamedEntityExplanation(ClassifierVote pi_Explain, String pi_Entity, String pi_Type, double pi_LikelyhoodSum, double pi_TotalSum, Hashtable<String, Double> pi_hPriorMap) {
        DecimalFormat df = new DecimalFormat("#.##");
        m_Exp = "Entity starting with <i>" + pi_Entity + "</i> was classified as <i>" + pi_Type + "</i> using contextual cues (sum of votes = "+df.format(pi_LikelyhoodSum)+" out of "+df.format(pi_TotalSum)+") ";
        m_Exp += "with prior probabilities {";
        Enumeration<String> eCur = pi_hPriorMap.keys();
        while (eCur.hasMoreElements()) {
            String strClass = eCur.nextElement();
            m_Exp += strClass + ": " + df.format(pi_hPriorMap.get(strClass)).toString();
            if (eCur.hasMoreElements()) m_Exp += ", ";
        }
        m_Exp += "}.";
    }
    public NamedEntityExplanation(AliasDominantType pi_Explain, String pi_Type) {
        m_Exp = "Entity type was overriden by dominant type <i>" + pi_Type + "</i> in alias network.";
    }
    public NamedEntityExplanation(NaturalLowercase pi_Explain) {
        m_Exp = "Type that can naturally occurs in lowercase or ambiguous position was kept.";
    }
    public NamedEntityExplanation(RejectedLowercasedOrAmbiguous pi_Explain) {
        m_Exp = "Entity was not tagged because it has unexpected capitalization or is in very difficult sentence.";
    }
    public NamedEntityExplanation(RejectedLowercasedAndUppercased pi_Explain) {
        m_Exp = "Entity was not tagged because it sometimes appears lowercased and uppercased in text.";
    }
    public NamedEntityExplanation(RejectedVeryAmbiguous pi_Explain) {
        m_Exp = "Entity was not tagged because it is very ambiguous and lacks supporting evidences.";
    }
    
     */
    
    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuffer strExp = new StringBuffer();
        switch (m_Type) {
        case  LEXICON_LOOKUP:
            strExp.append("<i>" + m_Args[0] + "</i> was found in lexicon with type <i>" + m_Args[1] + "</i>.");
            break;
        case FUZZY_MATCH:
            strExp.append("<i>" + m_Args[0] + "</i> is fuzzy matched against <i>" + m_Args[1] + "</i>.");
            break;
        case ENTITY_ENTITY_MERGING:
            strExp.append("Entities ending with <i>" + m_Args[0] + "</i> of type <i>" + m_Args[1] + "</i> was merged with adjacent entity of type <i>" + m_Args[2] + "</i>.");
            break;
        case ENTITY_CAPITAL_MERGING:
            strExp.append("Entities ending with <i>" + m_Args[0] + "</i> was merged with the capitalized word <i>" + m_Args[1] + "</i>.");
            break;
        case ENTITY_PUNCT_MERGING:
            strExp.append("Entities ending with <i>" + m_Args[0] + "</i> was merged with non-terminal punctation <i>" + m_Args[1] + "</i>.");
            break;
        case UNKNOWN_CAPITAL:
            strExp.append("The unknown capitalized word <i>" + m_Args[0] + "</i> inherits its type from an alias network.");
            break;
        case ADJACENT_SHARE_TYPE:
            strExp.append("Adjacent entities sharing a common type were merged.");
            break;
        case ACRONYM:
            strExp.append("Type is forced for acronym <i>" + m_Args[0] + "</i>: <i>" + m_Args[1] + "</i>.");
            break;
        case CLASSIFIER:
            DecimalFormat df = new DecimalFormat("#.##");
            strExp.append("Entity starting with <i>" + m_Args[0] + "</i> was classified as <i>" + m_Args[1] + "</i> using contextual cues (confidence = "+df.format(m_Args[2])+") ");
            strExp.append("with prior probabilities {");
            Enumeration<String> eCur = ((Hashtable<String, Double>)m_Args[3]).keys();
            while (eCur.hasMoreElements()) {
                String strClass = eCur.nextElement();
                strExp.append(strClass + ": " + df.format(((Hashtable<String, Double>)m_Args[3]).get(strClass)).toString());
                if (eCur.hasMoreElements()) strExp.append(", ");
            }
            strExp.append("}.");
            break;
        case CLASSIFIER_VOTE:
            DecimalFormat df2 = new DecimalFormat("#.##");
            strExp.append("Entity starting with <i>" + m_Args[0] + "</i> was classified as <i>" + m_Args[1] + "</i> using contextual cues (sum of votes = "+df2.format(m_Args[2])+" out of "+df2.format(m_Args[3])+") ");
            strExp.append("with prior probabilities {");
            Enumeration<String> eCur2 = ((Hashtable<String, Double>)m_Args[4]).keys();
            while (eCur2.hasMoreElements()) {
                String strClass = eCur2.nextElement();
                strExp.append(strClass + ": " + df2.format(((Hashtable<String, Double>)m_Args[4]).get(strClass)).toString());
                if (eCur2.hasMoreElements()) strExp.append(", ");
            }
            strExp.append("}.");
            break;
        case ALIAS_DOMINANT_TYPE:
            strExp.append("Entity type was overriden by dominant type <i>" + m_Args[0] + "</i> in alias network.");
            break;
        case NATURAL_LOWERCSE:
            strExp.append("Type that can naturally occurs in lowercase or ambiguous position was kept.");
            break;
        case REJECTED_LOWERCASED_OR_AMBIGUOUS:
            strExp.append("Entity was not tagged because it has unexpected capitalization or is in very difficult sentence.");
            break;
        case REJECTED_LOWERCASED_AND_UPPERCASED:
            strExp.append("Entity was not tagged because it sometimes appears lowercased and uppercased in text.");
            break;
        case REJECTED_VERY_AMBIGUOUS:
            strExp.append("Entity was not tagged because it is very ambiguous and lacks supporting evidences.");
            break;
        }
        return strExp.toString();
    }

    private ExplanationType m_Type;
    private Object[]        m_Args;
    
}
