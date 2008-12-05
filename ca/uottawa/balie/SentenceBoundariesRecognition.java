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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import weka.core.FastVector;
import weka.classifiers.trees.J48;

/**
 * Methods for training, testing and using sentence boundary recognition.
 * This class needs to be redesigned to improve usability.
 * In its current form, it requires the following manipulations to be used correctly.
 * <ul>
 * <li>First, it operates on a TokenList</li>
 * <li>The caller module must obtain the model: <code>GetModel()</code></li>
 * <li>It must operated by examining every token and keeping a look ahead of 1 token</li>
 * <li>The method <code>IsSentenceBoundary()</code> must be called on each token</li>
 * <li>When a sentence break is found, the method <code>YieldSentenceBeginning()</code> must be called.</li>
 * </ul>
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class SentenceBoundariesRecognition {

	// Size of stop words that are allowed in all-capitalized sentences
	private static final int SIZE_OF_STOP_WORD  = 3;
		
	// Attributes:  
	private static final int NUM_ATTRIBUTES = 13;
	private static final String VAL_PERIOD 			= "P-Period";
	private static final String VAL_PERIOD_LIKE 	= "P-PeriodLike";
	private static final String VAL_QUOTE 			= "P-Quote";
	private static final String VAL_OPEN_BRACKET 	= "P-OpenBracket";
	private static final String VAL_CLOSE_BRACKET 	= "P-CloseBracket";
	private static final String VAL_PUNCT 			= "P-Other";
	private static final String VAL_NEW_LINE		= "P-NewLine";
    private static final String VAL_LINE_FEED       = "P-LineFeed";
    private static final String VAL_LF_IN_CAP       = "P-LineFeedAllCap";
	private static final String VAL_NL_IN_CAP		= "P-NewLineAllCap";
	private static final String VAL_CAPITAL 		= "W-Capital";
	private static final String VAL_DIGIT	 		= "W-Digit";
	private static final String VAL_ABBREVIATION	= "W-Abbreviation";
	private static final String VAL_OTHER 			= "W-Other";
	private static final String VAL_NULL 			= "Null";

	private static final int NUM_FEATURES = 6;
	
	// Class:  
	private static final String IS_SENTENCE_BOUNDARY     = "POSITIVE";
	private static final String IS_NOT_SENTENCE_BOUNDARY = "NEGATIVE";


	// Members  
	private boolean 			m_bCurrentSentenceIsAllCap;
	private AbbreviationLookup 	m_AbbreviationLookup;
	
	/**
	 * Initialize SBD algorithm.
	 * Can be use in training, testing or use modes.
	 * 
	 * @param pi_LanguageSpecific
	 */
	public SentenceBoundariesRecognition(LanguageSpecific pi_LanguageSpecific) {
		m_bCurrentSentenceIsAllCap = true;
		m_AbbreviationLookup = new AbbreviationLookup(pi_LanguageSpecific);
	}

    public void Reset() {
        m_bCurrentSentenceIsAllCap = true;
    }
    
	private WekaLearner TrainModel() {
		ArrayList<String> alSentence = null;
		
		try {
			alSentence = ReadCorpus(Balie.SBR_TRAINING_CORPUS_PC);
		} catch (Exception e) {
			throw new Error("SBD Training corpus was not found");
		}

		ArrayList<TokenList> alTokenLists 	= GetTokenLists(alSentence);

		// Let's prepare the classifier
		WekaAttribute[] wekaAttributes = new WekaAttribute[NUM_FEATURES];
		FastVector attrVal = new FastVector(NUM_ATTRIBUTES);
		attrVal.addElement(VAL_PERIOD);
		attrVal.addElement(VAL_PERIOD_LIKE);
		attrVal.addElement(VAL_OPEN_BRACKET);
		attrVal.addElement(VAL_CLOSE_BRACKET);
		attrVal.addElement(VAL_QUOTE);
		attrVal.addElement(VAL_PUNCT);
        attrVal.addElement(VAL_NEW_LINE);
		attrVal.addElement(VAL_LINE_FEED);
        attrVal.addElement(VAL_LF_IN_CAP);
		attrVal.addElement(VAL_NL_IN_CAP);
		attrVal.addElement(VAL_CAPITAL);
		attrVal.addElement(VAL_DIGIT);
		attrVal.addElement(VAL_ABBREVIATION);
		attrVal.addElement(VAL_OTHER);
		attrVal.addElement(VAL_NULL);
		
		wekaAttributes[0] = new WekaAttribute("SentenceBeginning", 	attrVal);
		wekaAttributes[1] = new WekaAttribute("LastToken", 			attrVal);
		wekaAttributes[2] = new WekaAttribute("Last2CurrentSpace");
		wekaAttributes[3] = new WekaAttribute("CurrentToken", 		attrVal);
		wekaAttributes[4] = new WekaAttribute("Current2NextSpace");
		wekaAttributes[5] = new WekaAttribute("NextToken", 			attrVal);
		String[] strClass = new String[]{IS_SENTENCE_BOUNDARY, IS_NOT_SENTENCE_BOUNDARY};
		WekaLearner wl = new WekaLearner(wekaAttributes, strClass);

		// Let's create an attribute for each token transition.
		for (int i = 0; i != alTokenLists.size(); ++i) {
			TokenList alCurrentTokenList = (TokenList)alTokenLists.get(i);
			TokenList alNextTokenList = null;
			if (i != alTokenLists.size()-1) {
				alNextTokenList = (TokenList)alTokenLists.get(i+1);
			}
			
			// Describe current attribute
			for (int j = 0; j != alCurrentTokenList.Size(); ++j) {
				Object[] strInstance = new Object[NUM_FEATURES];
				
				boolean bTrivialInstance = DescribeTrainTestInstance(alCurrentTokenList, alNextTokenList, j, strInstance);
				
				String curClass = IS_NOT_SENTENCE_BOUNDARY;
				if (j == alCurrentTokenList.Size()-1) {
					curClass = IS_SENTENCE_BOUNDARY;
					YieldSentenceBeginning();
				}
				
				// Do not add trivial examples
				if (!bTrivialInstance) {
					wl.AddTrainInstance(strInstance, curClass);
				}
			}
		}
		J48 j48 = new J48();
		wl.CreateModel(j48);
		
		return  wl;
	}

	private boolean DescribeTrainTestInstance(TokenList pi_CurTokenList, TokenList pi_NextTokenList, int pi_Pos, Object[] pi_Instance) {
				
		// sentence begining
		String strSentBegVal = VAL_NULL;
		if (pi_Pos != 0) {
			strSentBegVal = GetTokenValue(pi_CurTokenList.Get(0));
		}
		pi_Instance[0] = strSentBegVal;
				
		// last token
		String strLastVal = VAL_NULL;
		if (pi_Pos > 0) {
			strLastVal = GetTokenValue(pi_CurTokenList.Get(pi_Pos-1));
		}
		pi_Instance[1] = strLastVal;
				
		pi_Instance[2] = new Double(0);
		
		// current token
		if (pi_CurTokenList.Get(pi_Pos).NumWhiteBefore() > 0 ||
			(TokenConsts.Is(pi_CurTokenList.Get(pi_Pos).Type(), TokenConsts.TYPE_PUNCTUATION) &&
			 TokenConsts.Is(pi_CurTokenList.Get(pi_Pos).PartOfSpeech(), TokenConsts.PUNCT_NEWLINE))) {
			pi_Instance[2] = new Double(1);
		}
		String strCurVal = GetTokenValue(pi_CurTokenList.Get(pi_Pos));
		pi_Instance[3] = strCurVal;
				
		pi_Instance[4] = new Double(0);
		// next token
		String strNextVal = VAL_NULL;
		if (pi_Pos != pi_CurTokenList.Size()-1) {
			strNextVal = GetTokenValue(pi_CurTokenList.Get(pi_Pos+1));
			if (pi_CurTokenList.Get(pi_Pos+1).NumWhiteBefore() > 0 ||
				(TokenConsts.Is(pi_CurTokenList.Get(pi_Pos+1).Type(), TokenConsts.TYPE_PUNCTUATION) &&
				 TokenConsts.Is(pi_CurTokenList.Get(pi_Pos+1).PartOfSpeech(), TokenConsts.PUNCT_NEWLINE))) {
				pi_Instance[4] = new Double(1);
			}
		} else if (pi_NextTokenList != null) {
			strNextVal = GetTokenValue(pi_NextTokenList.Get(0));
			if (pi_NextTokenList.Get(0).NumWhiteBefore() > 0  ||
				 (TokenConsts.Is(pi_CurTokenList.Get(0).Type(), TokenConsts.TYPE_PUNCTUATION) &&
				  TokenConsts.Is(pi_CurTokenList.Get(0).PartOfSpeech(), TokenConsts.PUNCT_NEWLINE))) {
				pi_Instance[4] = new Double(1);
			}
		}
		pi_Instance[5] = strNextVal;
		
		return IsTrivialBoundary(pi_Instance);
	}

	private static boolean IsTrivialValue(String pi_Value, boolean pi_bSentBeg) {
		return pi_Value.equals(VAL_OTHER) || pi_Value.equals(VAL_NULL) || (pi_bSentBeg && pi_Value.equals(VAL_CAPITAL));
	}
	private static boolean IsTrivialBoundary(Object[] pi_Instance) {
		// Do not train or test on trivial examples
		// ~30% of instances are trivial (and class negative).
		// These are cases where all attributes are lowercased words and case where first token would be a weird punctuation
		if (pi_Instance.length != NUM_FEATURES) {
			throw new Error("Invalid instance");
		}
		return pi_Instance[5].equals(VAL_PUNCT) || (IsTrivialValue((String)pi_Instance[0], true) && IsTrivialValue((String)pi_Instance[1], false) && IsTrivialValue((String)pi_Instance[3], false) && IsTrivialValue((String)pi_Instance[5], false));
	}

	// TODO: Once a word is seen, try to re-use info.
	// example, a word at sentence beginning will be evaluated on each loop
	private String GetTokenValue(Token pi_Token) {
		String ret = VAL_OTHER;
		if (pi_Token == null) {
			ret = VAL_NULL;
		} else if (TokenConsts.Is(pi_Token.Type(), TokenConsts.TYPE_PUNCTUATION)) {
			if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_PERIOD)) {
				ret = VAL_PERIOD;
			} else if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_EXCLAMATION) ||
					   TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_INTERROGATION)) {
				ret = VAL_PERIOD_LIKE;
		    } else if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_NEWLINE)) {
			    if (m_bCurrentSentenceIsAllCap) {
					ret = VAL_NL_IN_CAP;
			    } else {
					ret = VAL_NEW_LINE;
			    }
            } else if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_LINEFEED)) {
                if (m_bCurrentSentenceIsAllCap) {
                    ret = VAL_LF_IN_CAP;
                } else {
                    ret = VAL_LINE_FEED;
                }
			} else if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_OPEN_PARENTHESIS) ||
					   TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_OPEN_BRACKET)) {
				ret = VAL_OPEN_BRACKET;
			} else if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_CLOSE_PARENTHESIS) ||
					   TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_CLOSE_BRACKET)) {
				ret = VAL_CLOSE_BRACKET;
			} else if (TokenConsts.Is(pi_Token.PartOfSpeech(), TokenConsts.PUNCT_QUOTE)) {
			    ret = VAL_QUOTE;
			} else {
				ret = VAL_PUNCT;
			}
		} else if (m_AbbreviationLookup.IsAbbreviation(pi_Token.Canon())) {
			ret = VAL_ABBREVIATION;
		} else if (TokenFeature.Feature.StartsWithCapital.Mechanism().GetBooleanValue(pi_Token.Features())) {
			ret = VAL_CAPITAL;
			
		} else if (Character.isDigit(pi_Token.Canon().charAt(0))) {
			ret = VAL_DIGIT;
		} else {
			// Looks like the token is a non-capitalized noun.
			// Sentence is not more an "All capitalized sentence" if such a word, with suffisent length, is found
			if (pi_Token.Length() > SIZE_OF_STOP_WORD) {
				m_bCurrentSentenceIsAllCap = false;
			}
		}
		return ret;
	}

	private void TestModel(WekaLearner pi_Model) {
		
		ArrayList<String> alSentence = null;
		
		try {
			alSentence = ReadCorpus(Balie.SBR_TESTING_CORPUS_PC);
		} catch (Exception e) {
			throw new Error("SBD Testing corpus was not found.");
		}
		
		ArrayList<TokenList> alTokenLists 	= GetTokenLists(alSentence);
		
		// Let's create an attribute for each token transition.
		for (int i = 0; i != alTokenLists.size(); ++i) {
			TokenList alCurrentTokenList = (TokenList)alTokenLists.get(i);
			TokenList alNextTokenList = null;
			if (i != alTokenLists.size()-1) {
				alNextTokenList = (TokenList)alTokenLists.get(i+1);
			}
			
			// Describe current attribute
			for (int j = 0; j != alCurrentTokenList.Size(); ++j) {
				Object[] strInstance = new Object[NUM_FEATURES];
				
				boolean bTrivialInstance = DescribeTrainTestInstance(alCurrentTokenList, alNextTokenList, j, strInstance);
								
				String curClass = IS_NOT_SENTENCE_BOUNDARY;
				if (j == alCurrentTokenList.Size()-1) {
					curClass = IS_SENTENCE_BOUNDARY;
					YieldSentenceBeginning();
				}
				
				if (!bTrivialInstance) {
					pi_Model.AddTestInstance(strInstance, curClass);
				}
			}
		}
		System.out.println(pi_Model.TestModel());		
	}

	private static ArrayList<TokenList> GetTokenLists(ArrayList<String> pi_Sentences) {
		ArrayList<TokenList> alTokenLists = new ArrayList<TokenList>();
		
		// Let's tokenize each sentence
		Iterator<String> iCur = pi_Sentences.iterator(); 
		Tokenizer tEng = new Tokenizer(Balie.LANGUAGE_ENGLISH, false);
		while (iCur.hasNext()) {
			DebugInfo.Out("Sentence read.");
			tEng.Tokenize((String)iCur.next());
			alTokenLists.add(tEng.GetTokenList());
			tEng.Reset();
		}
		return alTokenLists;
	}

	private static ArrayList<String> ReadCorpus(String pi_FileName) {
		// Create a JAXP SAXParserFactory and configure it
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(false);

		XMLReader xmlReader = null;
		try {
			// Create a JAXP SAXParser
			SAXParser saxParser = spf.newSAXParser();
			// Get the encapsulated SAX XMLReader
			xmlReader = saxParser.getXMLReader();
		} catch (Exception ex) {
			System.err.println(ex);
			System.exit(1);
		}

		// Set the ContentHandler of the XMLReader
		SBRCorpusHandler corpusHandler = new SBRCorpusHandler();
		
		xmlReader.setContentHandler(corpusHandler);

		try {
			// Tell the XMLReader to parse the XML document
			xmlReader.parse(ConvertToFileURL(pi_FileName));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return corpusHandler.GetSentences();
	}


	// Convert from a filename to a file URL.
	private static String ConvertToFileURL(String filename) {
		String path = new File(filename).getAbsolutePath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}
		if (path.charAt(0) != '/') {
			path = "/" + path;
		}
		return "file:" + path;
	}

	/**
	 * Gets the learned model for SBR.
	 * 
	 * @return the Model ({@link WekaLearner})
	 */
	public static WekaLearner GetModel() {
		return WekaPersistance.Load(Balie.SBR_MODEL);
	}

	/**
     * Check if a sentence is all capitalized
	 * @return true if all cap
	 */
	public boolean SentenceIsAllCapitalized() {
		return m_bCurrentSentenceIsAllCap;
	}
	
	/**
	 * Check if a given token sequence is a sentence break (located after the "current" token)
	 * 
	 * @param pi_Model				SBD model (use the GetModel() method)
	 * @param pi_SentenceBeginning	The first token of the sentence (use the first token of the text for the first sentence)
	 * @param pi_LastToken			The previous token (i-1)
	 * @param pi_CurrentToken		The current token under examination (i)
	 * @param pi_NextToken			The next token in the tokenlist (i+1)
	 * @return True if there is a sentence break after the "current" token
	 */
	public boolean IsSentenceBoundary(WekaLearner pi_Model, Token pi_SentenceBeginning, Token pi_LastToken, Token pi_CurrentToken, Token pi_NextToken) {

		Object[] strInstance = new Object[NUM_FEATURES];
		
		// Sentence Beginning
		strInstance[0] = GetTokenValue(pi_SentenceBeginning);
		
		// last token
		strInstance[1] = GetTokenValue(pi_LastToken);
		
		// current token
		strInstance[2] = new Double(0);
		if (pi_CurrentToken != null && pi_CurrentToken.NumWhiteBefore() > 0 ||
			(TokenConsts.Is(pi_CurrentToken.Type(), TokenConsts.TYPE_PUNCTUATION) &&
			 TokenConsts.Is(pi_CurrentToken.PartOfSpeech(), TokenConsts.PUNCT_NEWLINE)))  {
			strInstance[2] = new Double(1); 
		}
		strInstance[3] = GetTokenValue(pi_CurrentToken);
		
		// next token
		strInstance[4] = new Double(0);
		if (pi_NextToken != null && pi_NextToken.NumWhiteBefore() > 0 ||
			(TokenConsts.Is(pi_NextToken.Type(), TokenConsts.TYPE_PUNCTUATION) &&
			 TokenConsts.Is(pi_NextToken.PartOfSpeech(), TokenConsts.PUNCT_NEWLINE))) {
			strInstance[4] = new Double(1); 
		}
		strInstance[5] = GetTokenValue(pi_NextToken);
		
		// Trivial instances are default Negative
		boolean bIsSB = false;
		if (!IsTrivialBoundary(strInstance)) {
			String strBoundary = ((String[])pi_Model.GetClassList())[(int)pi_Model.Classify(strInstance)];
			bIsSB = strBoundary.equals(IS_SENTENCE_BOUNDARY);
		}
		
		return bIsSB;
	}
	
	/**
	 * This method should be called each time a sentence break is found
	 */
	public void YieldSentenceBeginning() {
		m_bCurrentSentenceIsAllCap = true;
	}

	/**
	 * Trains and Tests the sentence boundary recognition model
	 */
	public static void TrainSentenceBoundariesRecognition() {
		// Train and test the model
		SentenceBoundariesRecognition sbr = new SentenceBoundariesRecognition(new LanguageSpecificEnglish());
		WekaLearner wl = sbr.TrainModel();
		sbr.TestModel(wl);

		// output arff files for visualization & debugging
		WekaPersistance.PrintToArffFile(wl, Balie.OUT_SBD_TRAIN_MODEL, WekaPersistance.PRINT_TRAINING_SET);
		WekaPersistance.PrintToArffFile(wl, Balie.OUT_SBD_TEST_MODEL,  WekaPersistance.PRINT_TESTING_SET);

		// Shrink the model
		wl.Shrink();
		
		// Save to disk
		WekaPersistance.Save(wl, Balie.SBR_MODEL);
		
		// Print the test corpus for visualization
		if (Balie.DEBUG_PRINT_SBD_TEST_CORPUS) {
			try {
				String strContent = FileHandler.GetTextFileContent(Balie.SBR_TESTING_CORPUS_PC, Balie.ENCODING_UTF8);
				strContent = strContent.replaceAll("<[^>]*>","");
				LanguageIdentification li = new LanguageIdentification();
				Tokenizer tTest = new Tokenizer(li.DetectLanguage(strContent), true);
				tTest.Tokenize(strContent);
				TokenList alTokenList = tTest.GetTokenList();
			
				for (int i = 0; i != tTest.SentenceCount(); ++i) {
					System.out.print("("+i+")");
					System.out.println(alTokenList.SentenceText(i, false, true));	
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		TrainSentenceBoundariesRecognition();
	}
}
