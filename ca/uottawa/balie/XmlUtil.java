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
 * Created on Dec 20, 2004
 */
package ca.uottawa.balie;

/**
 * Utility class for reading and writing XML files
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class XmlUtil {
	
    private XmlUtil() {}
    
    /**
     * Gets an XML header.
     * 
     * @return String
     */
	public static String Header() {
		return "<?xml version=\"1.0\"?>\n";
	}

	/**
	 * Gets an XML header with an Encoding attribute.
	 * 
	 * @param pi_encoding	The file encoding
	 * @return String
	 */
	public static String Header(String pi_encoding) {
		return "<?xml version=\"1.0\" encoding=\""+pi_encoding+"\"?>\n";
		
	}

	/**
	 * Gets the XML line that declares the stylesheet.
	 * 
	 * @param pi_Filename	Location of the stylesheet
	 * @return String
	 */
	public static String StyleSheet(String pi_Filename) {
		return "<?xml-stylesheet type=\"text/xsl\" href=\""+ pi_Filename +"\"?>\n";
	}
	
	/**
	 * Encode specials chars for XML representation
	 * 
	 * @param pi_text The raw text
	 * @return The encoded text
	 */
    // find range of invald chars or use existing method somewhere..
	public static String Encode(String pi_text) {
    	StringBuffer sb = new StringBuffer();
    	char[] cText = pi_text.toCharArray();
    	char cCur;
    	for (int i =0 ; i != cText.length; ++i) {
    		cCur = cText[i];
    	    if (cCur =='&'){
    	    	sb.append("&amp;");
        	} else if (cCur =='<'){
    	    	sb.append("&lt;");
        	} else if (cCur =='>'){
        	    sb.append("&gt;");
        	} else if (cCur =='"'){
        		sb.append("&quot;");
        	} else if (cCur =='\''){
    	    	sb.append("&apos;");
        	} else if (cCur =='’'){
    	    	sb.append("&apos;");
        	} else if (cCur =='`'){
    	    	sb.append("&apos;");
            } else if (cCur < 0x20 && cCur != '\n' && cCur != '\r'){
                sb.append(" ");
        	} else {
        		sb.append(cCur);
        	}
    	}
    	return sb.toString();
	}
	
	/**
	 * Decode specials chars for XML representation
	 * 
	 * @param pi_text The encoded text
	 * @return The decoded text
	 */
	public static String Decode(String pi_text) {
		pi_text = pi_text.replace("&amp;", "&");
        pi_text = pi_text.replace("&apos;", "'");
		return pi_text;
	}
}
