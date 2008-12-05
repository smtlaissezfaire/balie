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
 * Created on Apr 12, 2004
 */
package ca.uottawa.balie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Simple file loader
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class FileHandler {
	
    private FileHandler() {}
    
    private static final int UTF8_BOM = 0xFEFF;
    
    /**
     * Loads a file from disk with a specified charset.
     * 
     * @param pi_Filename	File to read
     * @param pi_Encoding	File encoding (See {@link Balie} class for encoding choices)
     * @return The file content (String)
     * @see Balie
     */
	public static String GetTextFileContent(String pi_Filename, String pi_Encoding) {
		File txt = new File(pi_Filename);
		StringBuffer txtStr = new StringBuffer();
		
		try {
            BufferedReader bis;
			
			if (!pi_Encoding.equals(Balie.ENCODING_DEFAULT)) {
			    bis = new BufferedReader(new InputStreamReader(new FileInputStream(txt), pi_Encoding));
			} else {
			    bis = new BufferedReader(new InputStreamReader(new FileInputStream(txt)));
			}
			// check if first char is BOM
			int c = bis.read();
			if (c != -1 && c != UTF8_BOM) {
			    txtStr.append((char)c);
			}
			if (c != -1) {
				while ((c = bis.read()) != -1) {
				    txtStr.append((char)c);
				}			    
			}
			bis.close();
		} catch (Exception e) {
		    throw new Error("Unable to read file " + pi_Filename);
		}

		return txtStr.toString();
	}
	
}
