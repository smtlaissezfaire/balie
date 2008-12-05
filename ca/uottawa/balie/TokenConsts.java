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
 * Static class containing enumeration of token type value.
 * Also contains the service function "Is".
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TokenConsts {
	
    protected TokenConsts() {}
    
	// Token Type
	public static final int TYPE_PUNCTUATION 			= 1;
	public static final int TYPE_WORD 					= 1 << 1;

	// Punctuations
	public static final int PUNCT_PERIOD				= 1;
	public static final int PUNCT_COMMA					= 1 << 1;
	public static final int PUNCT_EXCLAMATION			= 1 << 2;
	public static final int PUNCT_INTERROGATION			= 1 << 3;
	public static final int PUNCT_SEMI_COLON			= 1 << 4;
	public static final int PUNCT_COLON					= 1 << 5;
	public static final int PUNCT_OPEN_PARENTHESIS		= 1 << 6;
	public static final int PUNCT_CLOSE_PARENTHESIS		= 1 << 7;
	public static final int PUNCT_QUOTE					= 1 << 8;
	public static final int PUNCT_COMMERCIAL_AT			= 1 << 9;
	public static final int PUNCT_APOSTROPHE			= 1 << 10;
	public static final int PUNCT_DASH					= 1 << 11;
	public static final int PUNCT_SLASH					= 1 << 12;
	public static final int PUNCT_BACK_SLASH			= 1 << 13;
	public static final int PUNCT_MISC_ARITHMETIC		= 1 << 14;
	public static final int PUNCT_PERCENT				= 1 << 15;
	public static final int PUNCT_OPEN_BRACKET			= 1 << 16;
	public static final int PUNCT_CLOSE_BRACKET			= 1 << 17;
	public static final int PUNCT_INV_EXCLAMATION		= 1 << 18;
	public static final int PUNCT_INV_INTERROGATION		= 1 << 19;
	public static final int PUNCT_AMPERSAND				= 1 << 20;
	public static final int PUNCT_UNDERSCORE			= 1 << 21;
	public static final int PUNCT_TILDE					= 1 << 22;
	public static final int PUNCT_PIPE					= 1 << 23;
	public static final int PUNCT_CURRENCY				= 1 << 24;
	public static final int PUNCT_COPYRIGHT				= 1 << 25;
	
	public static final int PUNCT_TABULATION			= 1 << 28;
	public static final int PUNCT_LINEFEED				= 1 << 29;
	public static final int PUNCT_NEWLINE				= 1 << 30;
	public static final int PUNCT_UNKNOWN				= 1 << 31;

    // Balie does not handle token part-of-speech.
    // It must be performed by an external product (e.g., qTag)
    // Here is a placeholder POS tagset 
	public static final int POS_DETERMINER				= 1;
	public static final int POS_NOUN					= 1 << 1;
	public static final int POS_ADJECTIVE				= 1 << 2;
	public static final int POS_VERB					= 1 << 3;
	public static final int POS_ADVERB					= 1 << 4;
	public static final int POS_CONJUNCTION				= 1 << 5;
	public static final int POS_PREPOSITION				= 1 << 6;
	public static final int POS_PARTICLE				= 1 << 7;
	public static final int POS_PRONOUN					= 1 << 8;
	public static final int POS_POSSESSIVE				= 1 << 9;
	
	public static final int POS_PROPER_NAME_LIKE		= 1 << 28;
	public static final int POS_NUMBER					= 1 << 29;
	public static final int POS_NUMBER_LIKE				= 1 << 30;
	public static final int POS_UNKNOWN					= 1 << 31;

	/**
	 * Checks if an integer contains a reference value (using bitwise AND).
	 * For instance, if the integer is: 11000000000000010000000000000001
	 * and the reference value is:      01000000000000000000000000000000
	 * then the reference is contained in the integer to check.
	 * 
	 * Caution, pi_Reference must refer to a unique value (only one bit set to 1)!
	 * 
	 * @param pi_ToCheck	The integer value to check
	 * @param pi_Reference	The reference value
	 * @return True if the checked value contains the reference
	 */ 
	public static boolean Is(int pi_ToCheck, int pi_Reference) {	
	    /*
		if (bitcount(pi_Reference) != 1) {
	        bitcount(pi_Reference);
	        throw new Error("The reference integer cannot have multiple true bits.");
	    }
	    */
	    
		return (pi_ToCheck & pi_Reference) == pi_Reference;
	}
	
	// count the number of positive bits in an integer (optimized for sparse integers)
	protected static int bitcount(int n)  
	{  
	    int count=0;
	    
	    if (n < 0) {
	        ++count;
	        n &= ~(1<<31);
	    }
	    while (n != 0)
	    {
	        ++count;
	        n &= (n - 1);
	    }
	    return count;
	}

	
}
