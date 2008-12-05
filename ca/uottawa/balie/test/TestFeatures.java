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
 * Created on Apr 27, 2006
 */
package ca.uottawa.balie.test;

import ca.uottawa.balie.AccentLookup;
import ca.uottawa.balie.PunctLookup;
import ca.uottawa.balie.TokenFeature;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TestFeatures {

	public static void Test() {
		System.out.println("************************************************************");	
		System.out.println("* Test token features.                                     *");	
		System.out.println("* - test each feature one by one                           *");	
		System.out.println("************************************************************");	

		System.out.println("[Testing Word-level features related to casing]");
	
        TestWordLevel("Sirop d'érable", true, false, true, true, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false,
                false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("Apple", true, false, false, true, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false,
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("apple", false, false, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false,
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("applE", true, false, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false,
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("aPPle", true, false, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("aPP1e", true, true, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("appl-e", false, false, true, false, false, false, true, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("o'clock", false, false, true, false, true, true, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("I'm", true, false, true, true, true, true, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("won't", false, false, true, false, true, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("Hydro-Québec", true, false, true, true, false, false, true, false, false, false, true, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("I.B.M.", true, false, true, true, false, false, false, false, true, true, false, 
                true, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel(".", false, false, true, false, false, false, false, false, true, false, false, 
				true, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel("ProSys", true, false, false, true, false, false, false, false, false, false, true, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("1234567890", false, true, false, false, false, false, false, false, false, false, false, 
                false, true, 1234567890, 10, 10, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("1.2.3.4.5.6.7.8.9.0", false, true, true, false, false, false, false, false, true, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 1, 1, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel("888-8888", false, true, true, false, false, false, true, false, false, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 3, 4, false, 
				false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("eBay", true, false, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("W3C", true, true, false, true, false, false, false, false, false, true, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);

		System.out.println("[Testing Word-level features related to numeric expressions]");

		TestWordLevel("anticonstitutionnellement", false, false, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("3", false, true, false, false, false, false, false, false, false, false, false, 
                false, true, 3, 1, 1, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("33", false, true, false, false, false, false, false, false, false, false, false, 
                false, true, 33, 2, 2, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("3333", false, true, false, false, false, false, false, false, false, false, false, 
                false, true, 3333, 4, 4, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("33333333333333333333333333333333333333333333333333333333333333333333333333333333", false, true, false, false, false, false, false, false, false, false, false, 
                false, true, 3.333333333333333E79, 80, 80, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("99.99", false, true, true, false, false, false, false, false, true, false, false, 
                false, true, 99.99, 2, 2, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel("(888)888-8888", false, true, true, false, false, false, true, false, false, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 4, false, 
				false, false, false, false, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false);
		TestWordLevel("01-99", false, true, true, false, false, false, true, false, false, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 2, 2, false, 
				false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("12:00", false, true, true, false, false, false, false, false, false, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 2, 2, false, 
				false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("99/12/31", false, true, true, false, false, false, false, true, false, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 2, 2, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false);
		TestWordLevel("-3,000.14", false, true, true, false, false, false, true, false, true, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 2, false, 
				false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel("1.1.2", false, true, true, false, false, false, false, false, true, false, false, 
                false, true, TokenFeature.UNKNOWN_NUMERIC_VALUE, 1, 1, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel("1990s", false, true, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 4, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("cell3-", false, true, true, false, false, false, true, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("23rd", false, true, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 2, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("a33b44c55", false, true, false, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 2, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("XIV", true, false, false, true, false, false, false, false, false, true, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, true, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		
		System.out.println("[Testing Word-level features related to single chars]");
		
		TestWordLevel("&", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("'", false, false, true, false, true, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("\\", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("]", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel(")", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel(":", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel(",", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("@", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("©", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("$", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("-", false, false, true, false, false, false, true, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("!", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("?", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("¡", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("¿", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("\r", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("*", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("\n", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("[", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false);
		TestWordLevel("(", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false);
		TestWordLevel("%", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false);
		TestWordLevel(".", false, false, true, false, false, false, false, false, true, false, false, 
				true, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		TestWordLevel("|", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false);
		TestWordLevel("\"", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false);
		TestWordLevel(";", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false);
		TestWordLevel("/", false, false, true, false, false, false, false, true, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false);
		TestWordLevel("\t", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false);
		TestWordLevel("~", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false);
		TestWordLevel("_", false, false, true, false, false, false, false, false, false, false, false, 
                false, false, TokenFeature.UNKNOWN_NUMERIC_VALUE, 0, 0, false, 
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true);
		
		System.out.println("[Testing Functionnal-level features]");

		TestFunctionnalLevel("bonjour", "bonjour", "bonjour", "bonjour", "", "aaaaaaa", "a");
		TestFunctionnalLevel("G.M.", "G.M.", "GM", "GM", "..", "A-A-", "A-A-");
		TestFunctionnalLevel("Machine-223", "Machine-223", "Machine223", "Machine", "-223", "Aaaaaaa-000", "Aa-0");
		TestFunctionnalLevel("Hydro-Québec", "Hydro-Quebec", "HydroQuébec", "HydroQuébec", "-", "Aaaaa-Aaaaaa", "Aa-Aa");
		TestFunctionnalLevel("O'clock", "O'clock", "Oclock", "Oclock", "'", "A-aaaaa", "A-a");
		TestFunctionnalLevel("q11w22e33", "q11w22e33", "q11w22e33", "qwe", "112233", "a00a00a00", "a0a0a0");
		TestFunctionnalLevel("1-888-888-8888", "1-888-888-8888", "18888888888", "", "1-888-888-8888", "0-000-000-0000", "0-0-0-0");
        TestFunctionnalLevel("Sirop de poteau", "Sirop de poteau", "Sirop de poteau", "Siropdepoteau", "  ", "Aaaaa*aa*aaaaaa", "Aa*a*a");
		
		System.out.println("[Testing Morphological-level features]");
		
		TestMorphologicalLevel("David", new String[]{"D", "Da", "Dav"}, new String[]{"d", "id", "vid"});
		TestMorphologicalLevel("amalgame.", new String[]{"a", "am", "ama"}, new String[]{".", "e.", "me."});
		TestMorphologicalLevel("I", new String[]{"I", "I", "I"}, new String[]{"I", "I", "I"});
		TestMorphologicalLevel("I'm", new String[]{"I", "I'", "I'm"}, new String[]{"m", "'m", "I'm"});
		
		System.out.println("[Success]");
	}
	
	private static void TestWordLevel(String pi_Word, boolean pi_HasCapital, boolean pi_HasDigit, boolean pi_HasPunct, boolean pi_StartsCapital, boolean pi_HasApostrophe, boolean pi_ApostropheSecond, boolean pi_HasHyphen, boolean pi_HasSlash, boolean pi_HasPeriod, boolean pi_AllCapitalized, boolean pi_MixedCase,
									  boolean pi_EndsInPeriod, boolean pi_HasDigitsOnly, double pi_NumericValue, int pi_LeadingDigits, int pi_TrailingDigits, boolean pi_RomanDigit,
									  boolean pi_Ampersand, boolean pi_Apostrophe, boolean pi_BackSlash, boolean pi_CloseBracket, boolean pi_CloseParenthesis, boolean pi_Colon, boolean pi_Comma, boolean pi_CommercialAt, boolean pi_CopyrightPunct, boolean pi_Currency, boolean pi_Dash, boolean pi_Exclamation, boolean pi_Interrogation, boolean pi_InvertedExclamation, boolean pi_InvertedInterrogation, boolean pi_LineFeed, boolean pi_MiscArithmetic, boolean pi_Newline, boolean pi_OpenBracket, boolean pi_OpenParenthesis, boolean pi_Percent, boolean pi_PeriodPunct, boolean pi_Pipe, boolean pi_Quote, boolean pi_SemiColon, boolean pi_SlashPunct, boolean pi_Tabulation, boolean pi_Tilde, boolean pi_Underscore) {
		PunctLookup pl = new PunctLookup();
		TokenFeature tf = new TokenFeature(pi_Word, pl); 
		
		// casing
		Assert.Condition(TokenFeature.Feature.HasCapitalLetter.Mechanism().GetBooleanValue(tf) == pi_HasCapital, 				pi_Word + " has no capital letter");
		Assert.Condition(TokenFeature.Feature.HasDigit.Mechanism().GetBooleanValue(tf) == pi_HasDigit, 							pi_Word + " has no digits");
		Assert.Condition(TokenFeature.Feature.HasPunctuation.Mechanism().GetBooleanValue(tf) == pi_HasPunct, 					pi_Word + " has no punct");
		Assert.Condition(TokenFeature.Feature.StartsWithCapital.Mechanism().GetBooleanValue(tf) == pi_StartsCapital, 			pi_Word + " do not starts with a capital");
		Assert.Condition(TokenFeature.Feature.IsAllCapitalized.Mechanism().GetBooleanValue(tf) == pi_AllCapitalized, 			pi_Word + " is not all capitalized");
		Assert.Condition(TokenFeature.Feature.IsMixedCase.Mechanism().GetBooleanValue(tf) == pi_MixedCase, 						pi_Word + " is not mixed-case");
		
		// numeric
		Assert.Condition(TokenFeature.Feature.HasDigitsOnly.Mechanism().GetBooleanValue(tf) == pi_HasDigitsOnly, 				pi_Word + " do not have digits only");
		Assert.Condition((TokenFeature.Feature.NumericValue.Mechanism().GetNumericValue(tf) == TokenFeature.UNKNOWN_NUMERIC_VALUE && pi_NumericValue == TokenFeature.UNKNOWN_NUMERIC_VALUE) || (TokenFeature.Feature.NumericValue.Mechanism().GetNumericValue(tf) == pi_NumericValue), 	pi_Word + " invalid numeric value");
		Assert.Condition(TokenFeature.Feature.NumLeadingDigits.Mechanism().GetNumericValue(tf) == pi_LeadingDigits, 			pi_Word + " wrong number of leading digits");
		Assert.Condition(TokenFeature.Feature.NumTrailingDigits.Mechanism().GetNumericValue(tf) == pi_TrailingDigits, 			pi_Word + " wrong number of trailing digits");
		Assert.Condition(TokenFeature.Feature.IsRomanDigit.Mechanism().GetBooleanValue(tf) == pi_RomanDigit, 					pi_Word + " is not a roman digit");
		
		// punctuation
        Assert.Condition(TokenFeature.Feature.EndsInPeriod.Mechanism().GetBooleanValue(tf) == pi_EndsInPeriod,                          pi_Word + " don't ends in period");
        Assert.Condition(TokenFeature.Feature.ApostropheIsSecondChar.Mechanism().GetBooleanValue(tf) == pi_ApostropheSecond,            pi_Word + " has no apostrophe as second char");
		Assert.Condition(TokenFeature.Feature.HasAmpersandPunct.Mechanism().GetBooleanValue(tf) == pi_Ampersand, 						pi_Word + " has no Ampersand");
		Assert.Condition(TokenFeature.Feature.HasApostrophePunct.Mechanism().GetBooleanValue(tf) == pi_Apostrophe, 						pi_Word + " has no Apostrophe");
		Assert.Condition(TokenFeature.Feature.HasBackSlashPunct.Mechanism().GetBooleanValue(tf) == pi_BackSlash, 						pi_Word + " has no BackSlash");
		Assert.Condition(TokenFeature.Feature.HasCloseBracketPunct.Mechanism().GetBooleanValue(tf) == pi_CloseBracket, 					pi_Word + " has no CloseBracket");
		Assert.Condition(TokenFeature.Feature.HasCloseParenthesisPunct.Mechanism().GetBooleanValue(tf) == pi_CloseParenthesis, 			pi_Word + " has no CloseParenthesis");
		Assert.Condition(TokenFeature.Feature.HasColonPunct.Mechanism().GetBooleanValue(tf) == pi_Colon, 								pi_Word + " has no Colon");
		Assert.Condition(TokenFeature.Feature.HasCommaPunct.Mechanism().GetBooleanValue(tf) == pi_Comma, 								pi_Word + " has no Comma");
		Assert.Condition(TokenFeature.Feature.HasCommercialAtPunct.Mechanism().GetBooleanValue(tf) == pi_CommercialAt, 					pi_Word + " has no CommercialAt");
		Assert.Condition(TokenFeature.Feature.HasCopyrightPunct.Mechanism().GetBooleanValue(tf) == pi_CopyrightPunct, 					pi_Word + " has no Copyright");
		Assert.Condition(TokenFeature.Feature.HasCurrencyPunct.Mechanism().GetBooleanValue(tf) == pi_Currency, 							pi_Word + " has no Currency");
		Assert.Condition(TokenFeature.Feature.HasHyphenPunct.Mechanism().GetBooleanValue(tf) == pi_Dash, 								pi_Word + " has no Dash"); 
		Assert.Condition(TokenFeature.Feature.HasExclamationPunct.Mechanism().GetBooleanValue(tf) == pi_Exclamation, 					pi_Word + " has no Exclamation");
		Assert.Condition(TokenFeature.Feature.HasInterrogationPunct.Mechanism().GetBooleanValue(tf) == pi_Interrogation, 				pi_Word + " has no Interrogation");
		Assert.Condition(TokenFeature.Feature.HasInvertedExclamationPunct.Mechanism().GetBooleanValue(tf) == pi_InvertedExclamation,	pi_Word + " has no InvertedExclamation");
		Assert.Condition(TokenFeature.Feature.HasInvertedInterrogationPunct.Mechanism().GetBooleanValue(tf) == pi_InvertedInterrogation,pi_Word + " has no InvertedInterrogation");
		Assert.Condition(TokenFeature.Feature.HasLinefeedPunct.Mechanism().GetBooleanValue(tf) == pi_LineFeed, 							pi_Word + " has no LineFeed");
		Assert.Condition(TokenFeature.Feature.HasMiscArithmeticPunct.Mechanism().GetBooleanValue(tf) == pi_MiscArithmetic, 				pi_Word + " has no MiscArithmetic");
		Assert.Condition(TokenFeature.Feature.HasNewlinePunct.Mechanism().GetBooleanValue(tf) == pi_Newline, 							pi_Word + " has no Newline");
		Assert.Condition(TokenFeature.Feature.HasOpenBracketPunct.Mechanism().GetBooleanValue(tf) == pi_OpenBracket, 					pi_Word + " has no OpenBracket");
		Assert.Condition(TokenFeature.Feature.HasOpenParenthesisPunct.Mechanism().GetBooleanValue(tf) == pi_OpenParenthesis, 			pi_Word + " has no OpenParenthesis");
		Assert.Condition(TokenFeature.Feature.HasPercentPunct.Mechanism().GetBooleanValue(tf) == pi_Percent, 							pi_Word + " has no Percent");
		Assert.Condition(TokenFeature.Feature.HasPeriodPunct.Mechanism().GetBooleanValue(tf) == pi_PeriodPunct, 						pi_Word + " has no Period");
		Assert.Condition(TokenFeature.Feature.HasPipePunct.Mechanism().GetBooleanValue(tf) == pi_Pipe, 									pi_Word + " has no Pipe");
		Assert.Condition(TokenFeature.Feature.HasQuotePunct.Mechanism().GetBooleanValue(tf) == pi_Quote, 								pi_Word + " has no Quote");
		Assert.Condition(TokenFeature.Feature.HasSemiColonPunct.Mechanism().GetBooleanValue(tf) == pi_SemiColon, 						pi_Word + " has no SemiColon");
		Assert.Condition(TokenFeature.Feature.HasSlashPunct.Mechanism().GetBooleanValue(tf) == pi_SlashPunct, 							pi_Word + " has no Slash");
		Assert.Condition(TokenFeature.Feature.HasTabulationPunct.Mechanism().GetBooleanValue(tf) == pi_Tabulation, 						pi_Word + " has no Tabulation");
		Assert.Condition(TokenFeature.Feature.HasTildePunct.Mechanism().GetBooleanValue(tf) == pi_Tilde, 								pi_Word + " has no Tilde");
		Assert.Condition(TokenFeature.Feature.HasUnderscorePunct.Mechanism().GetBooleanValue(tf) == pi_Underscore, 						pi_Word + " has no Underscore");
	}
	
	private static void TestFunctionnalLevel(String pi_Word, String pi_NoAccentWord, String pi_NoPunct, String pi_Alpha, String pi_NonAlpha, String pi_Pattern, String pi_SummarizedPattern) {
		PunctLookup pl = new PunctLookup();
        AccentLookup al = new AccentLookup();
		TokenFeature tf = new TokenFeature(pi_Word, pl); 
		
		// morphology
        Assert.Condition(TokenFeature.Feature.GetStrippedAccent.Mechanism().GetNominalValue(tf, al).equals(pi_NoAccentWord),            pi_Word + " invalid no-accent version");
        Assert.Condition(TokenFeature.Feature.GetStrippedPunct.Mechanism().GetNominalValue(tf, al).equals(pi_NoPunct),                  pi_Word + " invalid no-punct version");
		Assert.Condition(TokenFeature.Feature.GetAlpha.Mechanism().GetNominalValue(tf, al).equals(pi_Alpha), 							pi_Word + " invalid alpha version");
		Assert.Condition(TokenFeature.Feature.GetNonAlpha.Mechanism().GetNominalValue(tf, al).equals(pi_NonAlpha), 						pi_Word + " invalid non-alpha version");
		Assert.Condition(TokenFeature.Feature.GetPattern.Mechanism().GetNominalValue(tf, al).equals(pi_Pattern), 						pi_Word + " invalid pattern version");
		Assert.Condition(TokenFeature.Feature.GetSummarizedPattern.Mechanism().GetNominalValue(tf, al).equals(pi_SummarizedPattern), 	pi_Word + " invalid summarized pattern version");
	}
	
	private static void TestMorphologicalLevel(String pi_Word, String[] pi_FirstThreePrefixes, String[] pi_FirstThreeSuffixes) {
		PunctLookup pl = new PunctLookup();
        AccentLookup al = new AccentLookup();
		TokenFeature tf = new TokenFeature(pi_Word, pl); 
		
		// morphology
		Assert.Condition(TokenFeature.Feature.GetPrefix1.Mechanism().GetNominalValue(tf, al).equals(pi_FirstThreePrefixes[0]), 					pi_Word + " invalid length 1 prefix");
		Assert.Condition(TokenFeature.Feature.GetPrefix2.Mechanism().GetNominalValue(tf, al).equals(pi_FirstThreePrefixes[1]), 					pi_Word + " invalid length 2 prefix");
		Assert.Condition(TokenFeature.Feature.GetPrefix3.Mechanism().GetNominalValue(tf, al).equals(pi_FirstThreePrefixes[2]), 					pi_Word + " invalid length 3 prefix");
		Assert.Condition(TokenFeature.Feature.GetSuffix1.Mechanism().GetNominalValue(tf, al).equals(pi_FirstThreeSuffixes[0]), 					pi_Word + " invalid length 1 suffix");
		Assert.Condition(TokenFeature.Feature.GetSuffix2.Mechanism().GetNominalValue(tf, al).equals(pi_FirstThreeSuffixes[1]), 					pi_Word + " invalid length 2 suffix");
		Assert.Condition(TokenFeature.Feature.GetSuffix3.Mechanism().GetNominalValue(tf, al).equals(pi_FirstThreeSuffixes[2]), 					pi_Word + " invalid length 3 suffix");
		
	}
	
	public static void main(String[] args) {
		Test();
	}
}
