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
 * Created on Nov 28, 2005
 */
package ca.uottawa.balie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Lexicon loader for baseline NER
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LexiconOnDisk implements LexiconOnDiskI, Serializable{

    private static final long serialVersionUID = 1L;
    
	/**
     * Choice of lexicons
     * 
	 * @author David Nadeau (pythonner@gmail.com)
	 */
	public static enum Lexicon {
        OPEN_SOURCE_LEXICON,      // Use Balie freely distributed lexicons 
        CLOSED_SOURCE_LEXICON     // Use closed-source lexicons covering all entity types
    }
	
	/**
	 * Read lexicons that reside in text files.
     * @param pi_LexisonSet
	 */
	public LexiconOnDisk(Lexicon pi_LexisonSet) {
		m_Entity = new Hashtable<String, Hashtable<String, NamedEntityType>>();
        m_Types = NamedEntityTypeEnum.values();
        m_PunctLookup = new PunctLookup();
        m_AccentLookup = new AccentLookup();
        
        ClassLoader cl = LexiconOnDisk.class.getClassLoader();
		
		if (pi_LexisonSet == Lexicon.OPEN_SOURCE_LEXICON) {
            ReadLexicon(cl, "lexicon/NERF-association.txt", NamedEntityTypeEnum.association);
            ReadLexicon(cl, "lexicon/NERF-celebrity.txt", NamedEntityTypeEnum.celebrity);
			ReadLexicon(cl, "lexicon/NERF-city.txt", NamedEntityTypeEnum.city);
			ReadLexicon(cl, "lexicon/NERF-company.txt", NamedEntityTypeEnum.company);
			ReadLexicon(cl, "lexicon/NERF-company_designator.txt", NamedEntityTypeEnum.company_designator);
			ReadLexicon(cl, "lexicon/NERF-country.txt", NamedEntityTypeEnum.country);
			ReadLexicon(cl, "lexicon/NERF-first_name.txt", NamedEntityTypeEnum.first_name);
			ReadLexicon(cl, "lexicon/NERF-government.txt", NamedEntityTypeEnum.government);
			ReadLexicon(cl, "lexicon/NERF-last_name.txt", NamedEntityTypeEnum.last_name);
			ReadLexicon(cl, "lexicon/NERF-military.txt", NamedEntityTypeEnum.military);
			ReadLexicon(cl, "lexicon/NERF-month.txt", NamedEntityTypeEnum.month);
			ReadLexicon(cl, "lexicon/NERF-person_title.txt", NamedEntityTypeEnum.person_title);
			ReadLexicon(cl, "lexicon/NERF-state_province.txt", NamedEntityTypeEnum.state_province);
		}
	}
	
	/**
     * Read lexicon from relative path, resolve through class loader
	 * @param pi_CL        class loader
	 * @param pi_Filename  relative path
	 * @param pi_Type      types
	 */
	protected void ReadLexicon(ClassLoader pi_CL, String pi_Filename, NamedEntityTypeEnumI pi_Type) {
        try {
        	InputStream in = pi_CL.getResourceAsStream(pi_Filename);
			BufferedReader inFile = new BufferedReader(new InputStreamReader(in));
            ReadLexicon(inFile, pi_Type);
        } catch (Exception e) {
            System.out.println(pi_Filename);
            e.printStackTrace();
        }		
	}	
	
    /**
     * Read lexicon from absolute path
     * @param pi_Filename  absolute path
     * @param pi_Type      types
     */
    protected void ReadLexicon(String pi_Filename, NamedEntityTypeEnumI pi_Type) {
        try {
            BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(pi_Filename)));
            ReadLexicon(inFile, pi_Type);
        } catch (Exception e) {
            System.out.println("NOT FOUND: " + pi_Filename);
        }       
    }
	
    private void ReadLexicon(BufferedReader pi_File, NamedEntityTypeEnumI pi_Type) {
        try {
            String line = pi_File.readLine();
            while (line != null) {
                if (!line.equals("\n") && !line.equals("\n\r") && !line.equals("p")&& !line.equals("/")) {
                    line = XmlUtil.Decode(line);
                    
                    AddEntityToLexicon(line, pi_Type);
                }
                line = pi_File.readLine();
            }
            pi_File.close();
        } catch (Exception e) {
            e.printStackTrace();
        }           
    }
    
    protected void AddEntityToLexicon(String pi_Line, NamedEntityTypeEnumI pi_Type) {
        Hashtable<String, NamedEntityType> subMap;
        String StrFirstLetter = Character.toString(pi_Line.charAt(0));
        if (m_Entity.containsKey(StrFirstLetter)) {
            subMap = m_Entity.get(StrFirstLetter);
        } else {
            subMap = new Hashtable<String, NamedEntityType>();
            m_Entity.put(StrFirstLetter, subMap);
        }
        
        if (subMap.containsKey(pi_Line)) {
            NamedEntityType curType = (NamedEntityType)subMap.get(pi_Line);
            curType.AddType(pi_Type, new NamedEntityExplanation(NamedEntityExplanation.ExplanationType.LEXICON_LOOKUP, new Object[]{pi_Line, pi_Type.Label()}));
            subMap.put(pi_Line, curType);
        } else {
            subMap.put(pi_Line, new NamedEntityType(pi_Type, m_Types.length, new NamedEntityExplanation(NamedEntityExplanation.ExplanationType.LEXICON_LOOKUP, new Object[]{pi_Line, pi_Type.Label()})));
        }
    }
    
    private boolean EntityIsInLexicon(String pi_Word) {
        if (pi_Word == null || pi_Word.length() == 0) return false;
        Hashtable<String, NamedEntityType> subMap;
        String StrFirstLetter = Character.toString(pi_Word.charAt(0));
        if (m_Entity.containsKey(StrFirstLetter)) {
            subMap = m_Entity.get(StrFirstLetter);
        } else return false;
        return subMap.containsKey(pi_Word);
    }
    
    private NamedEntityType GetTypeFromLexicon(String pi_Word) {
        Hashtable<String, NamedEntityType> subMap;
        String StrFirstLetter = Character.toString(pi_Word.charAt(0));
        if (m_Entity.containsKey(StrFirstLetter)) {
            subMap = m_Entity.get(StrFirstLetter);
        } else throw new Error("Cannnot get if the entity is not contained in the lexicon");
        return subMap.get(pi_Word);
    }
    
	/**
	 * Check if a given word is listed in a lexicon and return it (or its fuzzy variant found in the lexicon)
	 * 
	 * @param pi_Word	a word (can be a phase)
	 * @return the word found in the lexicon (either the input word or one of its fuzzy variant) returns NULL if not found!
	 */
	public String GetEntityInLexicon(String pi_Word, boolean pi_bFuzzy) {
		boolean bFound = EntityIsInLexicon(pi_Word);
        String strRet = null;

        // if found as is, return the word
        if (bFound) return pi_Word;
		// else try to fuzzy match
        else if (pi_bFuzzy) {
            ArrayList<String> alVariants = FuzzyVariants(pi_Word);
            Iterator<String> iCur = alVariants.iterator();
            while (iCur.hasNext()) {
                String strVar = iCur.next();
                bFound = EntityIsInLexicon(strVar);
                if (bFound) {
                    strRet = strVar;
                    break;
                }
            }
        }
        return strRet;
	}


	/**
	 * Get the entity type for this word.
	 * Entity type is a bit flag made of one or many entity types (see {@link TokenConsts} for type list)
	 * 
	 * @param pi_Word
	 * @return the type(s)
	 * @see TokenConsts
	 */
	//TODO: could reuse fuzzy match done in "GetEntityInLexicon" instead of recalculating it here
	public NamedEntityType GetEntityType(String pi_Word, boolean pi_bFuzzy) {
        boolean bFound = EntityIsInLexicon(pi_Word);
        String strMatchWord = pi_Word;
        if (pi_bFuzzy && !bFound) {
            ArrayList<String> alVariants = FuzzyVariants(pi_Word);
            Iterator<String> iCur = alVariants.iterator();
            while (iCur.hasNext()) {
                String strVar = iCur.next();
                bFound |= EntityIsInLexicon(strVar);
                strMatchWord = strVar;
                if (bFound) break;
            }
        }
        NamedEntityType net = null;
        if (!pi_Word.equals(strMatchWord)) {
            net = GetTypeFromLexicon(strMatchWord).clone(new NamedEntityExplanation(NamedEntityExplanation.ExplanationType.FUZZY_MATCH, new Object[]{pi_Word, strMatchWord}));
        } else {
            net = GetTypeFromLexicon(strMatchWord).clone(null);
        }
        return net;
	}
    
    /**
     * Get the entity type for this word and all its fuzzy variants.
     * @param pi_Word 
     * @return the type(s) of this words and all its variants.
     */
    public NamedEntityType GetEntityTypeForAllFuzzyVariants(String pi_Word) {
        NamedEntityType net = new NamedEntityType(m_Types.length, null);
        if (EntityIsInLexicon(pi_Word)) {
            net.MergeWith(GetTypeFromLexicon(pi_Word), null);
        }
        ArrayList<String> alVariants = FuzzyVariants(pi_Word);
        Iterator<String> iCur = alVariants.iterator();
        while (iCur.hasNext()) {
            String strVar = iCur.next();
            if (EntityIsInLexicon(strVar)) {
                net.MergeWith(GetTypeFromLexicon(strVar), null);
            }
        }
        return net;
    }
    
    private ArrayList<String> FuzzyVariants(String pi_Word) {
        
        ArrayList<String> alVariants = new ArrayList<String>();
        TokenFeature tf = new TokenFeature(pi_Word, m_PunctLookup);
        String strNoAccent = TokenFeature.Feature.GetStrippedAccent.Mechanism().GetNominalValue(tf, m_AccentLookup);
        if (!strNoAccent.equals(pi_Word)) alVariants.add(strNoAccent);
        
        if (pi_Word.indexOf(" '") != -1) {
            alVariants.add(pi_Word.replaceAll(" '", "'"));
        }
        if (pi_Word.indexOf(" .") != -1) {
            alVariants.add(pi_Word.replaceAll(" \\.", "\\."));
        }
        
        if (pi_Word.length() >= Balie.MIN_SIZE_FOR_FUZZY_MATCH) {
            String strTruncatedWord = pi_Word;
            for (int i = pi_Word.length()-1; i > 3; --i) {
                // do not accept punct truncation for last or second last chars
                if (i < pi_Word.length()-2 || !m_PunctLookup.IsPunctuation(pi_Word.charAt(i))) {
                    strTruncatedWord = pi_Word.substring(0, i) + pi_Word.substring(i+1, pi_Word.length());
                    alVariants.add(strTruncatedWord);
                    if (alVariants.size() >= Balie.MAX_NUMBER_FUZZY_VARIANTS) break;
                }
            }
        }
        
        return alVariants;
    }
    
    /* (non-Javadoc)
     * @see ca.uottawa.balie.LexiconOnDiskI#Types()
     */
    public NamedEntityTypeEnumI[] Types() {
        return m_Types;
    }

    protected Hashtable<String, Hashtable<String, NamedEntityType>>     m_Entity;
    protected NamedEntityTypeEnumI[]                                    m_Types;
    private   PunctLookup                                               m_PunctLookup;
    private   AccentLookup                                              m_AccentLookup;
    
	
}
