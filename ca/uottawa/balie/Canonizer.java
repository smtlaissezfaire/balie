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

/**
 * Offers static function to convert a word into its canon form.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class Canonizer {

	/**
	 * Puts every letters in lowercase
	 */
	public static final int RULE_LOWERCASE 				= 1;
	
	/**
	 * Normalizes punctuation (e.g.: all unicode quotes are resolved to ")
	 */
	public static final int RULE_NORMALIZE_PUNCT 		= 1 << 1;
	
	/**
	 * Removes internal punctuation, that is punctuation after the first letter and before the last letter.
	 * Punctuations are defined in the {@link PunctLookup}.
	 * 
	 * @see PunctLookup
	 */
	public static final int RULE_REMOVE_INTERNAL_PUNCT 	= 1 << 2;
	
	/**
	 * Changes ligature character in their multi-letter equivalent.
	 */
	public static final int RULE_EXPAND_LIGATURES 		= 1 << 3;

    /**
     * Strip Accents.
     */
    public static final int RULE_STRIP_ACCENTS          = 1 << 4;
    
	/**
	 * Transforms a word in its canon version.
	 * 
	 * @param pi_Raw 			The raw version of the word
	 * @param pi_Rules			The rules to apply using a bitwise OR (ex.: RULE_EXPAND_LIGATURES | RULE_REMOVE_INTERNAL_PUNCT)
	 * @param pi_PunctLookup	The punctuation lookup
	 * @param pi_LigatureLookup	The ligature lookup
	 * @return The canon word, after applying requested rules
	 */
	public static String CanonForm(String pi_Raw, int pi_Rules, PunctLookup pi_PunctLookup, LigatureLookup pi_LigatureLookup, AccentLookup pi_AccentLookup) {

		char[] cRaw = pi_Raw.toCharArray();
		
		StringBuffer sb = new StringBuffer();
		for (int i=0; i != cRaw.length; ++i) {
			if (i == 0 || (!TokenConsts.Is(pi_Rules, RULE_REMOVE_INTERNAL_PUNCT) || !pi_PunctLookup.IsInternalPunct(cRaw[i]))) {
				String cCanon = String.valueOf(cRaw[i]);
				if (TokenConsts.Is(pi_Rules, RULE_LOWERCASE)) {
					cCanon = cCanon.toLowerCase();
				}
				if (TokenConsts.Is(pi_Rules, RULE_EXPAND_LIGATURES)) {
					if (pi_LigatureLookup.GetEquivalence(cCanon) != null) {
						cCanon = pi_LigatureLookup.GetEquivalence(cCanon);
					}
				}
				if (TokenConsts.Is(pi_Rules, RULE_NORMALIZE_PUNCT)) {
					if (pi_PunctLookup.GetPunctEquivalence(cCanon) != null) {
						cCanon = pi_PunctLookup.GetPunctEquivalence(cCanon);
					}
				}
                if (TokenConsts.Is(pi_Rules, RULE_STRIP_ACCENTS)) {
                    if (pi_AccentLookup.HasAccentEquivalence(cCanon)) {
                        cCanon = pi_AccentLookup.GetAccentEquivalence(cCanon);
                    }
                }
				sb.append(cCanon);
			}
		}
		
		return sb.toString();
	}
}
