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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class that group routines for debugging.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */

public class DebugInfo {

    private DebugInfo() {}
    
    /**
     * Simple System.out.println wrapper with verbose flag parameter.
     * 
     * @param pi_Message 	Message to print
     */
	public static void Out(String pi_Message) {
		System.out.println("[" + pi_Message + "]");
	}

	/**
	 * Saves a {@link TokenList} to disk (for unit testing).
	 * 
	 * @param pi_TokenList	Tokenlist to save
	 * @param pi_FileName	Filename where to save the tokenlist
	 * @see TokenList
	 */
	public static void SaveTokenList(TokenList pi_TokenList, String pi_FileName) {
		try {
			FileOutputStream fos = new FileOutputStream(pi_FileName);
			ObjectOutputStream p = new ObjectOutputStream(fos);
			p.writeObject(pi_TokenList);
			p.flush();
			p.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Loads a {@link TokenList} from disk.
	 * 
	 * @param pi_FileName	Filename to load from
	 * @return The loaded TokenList
	 * @see TokenList
	 */
	public static TokenList LoadTokenList(String pi_FileName) {
		TokenList tokenList = null;
		try {
			FileInputStream in = new FileInputStream(pi_FileName);
			ObjectInputStream p = new ObjectInputStream(in);
			tokenList = (TokenList)p.readObject();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return tokenList;	
	}
}
