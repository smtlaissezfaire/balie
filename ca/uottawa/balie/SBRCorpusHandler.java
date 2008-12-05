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
 * Created on May 23, 2004
 */
package ca.uottawa.balie;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX parser for the Sentence Boundary Recognition corpus.
 * Uses javadoc of the superclass {@link DefaultHandler}.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class SBRCorpusHandler extends DefaultHandler {

	private static final String SENTENCE = "S";

	private boolean 	      m_bInSentence;
	private String  	      m_strSentence;
	private ArrayList<String> m_alSentences;

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		m_bInSentence = false;
        m_strSentence = "";
        m_alSentences = new ArrayList<String>();
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (qName.equals(SENTENCE)) {
			m_bInSentence = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement (String uri, String name, String qName) throws SAXException {
		if (qName.equals(SENTENCE)) {
			m_bInSentence = false;
			// add the sentence and add a sentence in which \n are replaced by \r\n..
            m_alSentences.add(m_strSentence);
			if (m_strSentence.indexOf('\n') != -1) {
                m_alSentences.add(m_strSentence.replaceAll("\n", "\r\n"));
			}
            m_strSentence = "";
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters (char ch[], int start, int length)
	{
		for (int i = start; i < start + length; i++) {
			if (m_bInSentence) {
                m_strSentence += ch[i];
			}
		}
	}
	
	/**
	 * Gets the list of train/test sentences.
	 *  
	 * @return Arraylist of sentences
	 */
	public ArrayList<String> GetSentences() {
		return m_alSentences;
	}
	

}
