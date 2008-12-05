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
 * This is the main entry point for training Balie.
 * Most constants are grouped in this class.
 * Verbose flags are grouped in this class.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class Balie {

	public static final String LANGUAGE_UNKNOWN 	 = "Unknown";
	public static final String LANGUAGE_ENGLISH 	 = "English";
	public static final String LANGUAGE_FRENCH		 = "French";
	public static final String LANGUAGE_SPANISH 	 = "Spanish";
	public static final String LANGUAGE_GERMAN  	 = "German";
	public static final String LANGUAGE_ROMANIAN 	 = "Romanian";
    public static final String LANGUAGE_ITALIAN      = "Italian";
	
	public static final String LANGUAGE_ID_TRAINING_CORPUS 	= "../BaLIECorpora/Corpus - Language Identification/Train";
	public static final String LANGUAGE_ID_TESTING_CORPUS  	= "../BaLIECorpora/Corpus - Language Identification/Test";
	public static final String OUT_LI_TRAIN_MODEL			= "../BaLIECorpora/Arff/LangIDTrainModel.arff";
	public static final String OUT_LI_TEST_MODEL  			= "../BaLIECorpora/Arff/LangIDTestModel.arff";
	
    // the small language model is distributed on sourceforge
    public static final String LANGUAGE_ID_MODEL		    = "LanguageIdentificationModel.sig";

	public static final String SBR_TRAINING_CORPUS_PC 	   	= "../BaLIECorpora/Corpus - Sentence Boundaries/train.xml";
	public static final String SBR_TESTING_CORPUS_PC  		= "../BaLIECorpora/Corpus - Sentence Boundaries/test.xml";
	public static final String OUT_SBD_TRAIN_MODEL			= "../BaLIECorpora/Arff/SBDTrainModel.arff";
	public static final String OUT_SBD_TEST_MODEL  			= "../BaLIECorpora/Arff/SBDTestModel.arff";
	public static final String SBR_MODEL  					= "SentenceBoundariesRecognition.sig";
	
	public static final String ENGLISH_TOKEN_LIST_ON_DISK 	= "TokenListEng.sig";
	public static final String GERMAN_TOKEN_LIST_ON_DISK  	= "TokenListGer.sig";
	public static final String FRENCH_TOKEN_LIST_ON_DISK  	= "TokenListFre.sig";
	public static final String SPANISH_TOKEN_LIST_ON_DISK 	= "TokenListSpa.sig";
	public static final String ROMANIAN_TOKEN_LIST_ON_DISK  = "TokenListRom.sig";
    public static final String ITALIAN_TOKEN_LIST_ON_DISK   = "TokenListIta.sig";
	public static final String UNBREAK_TOKEN_LIST_ON_DISK 	= "TokenListUnb.sig";
	
	public static final String ENCODING_DEFAULT				= "default";
	public static final String ENCODING_UTF8				= "utf8";
	public static final String ENCODING_LITTLE_INDIAN		= "UnicodeLittle";
	
    // fuzzy match parameters for named entities lexicon lookup 
    public static final boolean ALLOW_FUZZY_MATCH           = true; 
    public static final int MIN_SIZE_FOR_FUZZY_MATCH        = 8;
    public static final int MAX_NUMBER_FUZZY_VARIANTS       = 5;

    // Named entity recognition parameters
    public static final int MAX_TOKEN_PER_ENTITY            = 6; // max lenght optimization for entity lookup
    public static final int MIN_SIZE_ALIAS_MATCHING         = 4; // minimum size of a significant word
    
	// Debug flags (true = verbose, false = quiet)
	static public final boolean DEBUG_TOKENIZER 				    = false;
	static public final boolean DEBUG_TOKEN 					    = false;
	static public final boolean DEBUG_LANGUAGE_IDENTIFICATION 	    = false;
	static public final boolean DEBUG_PRINT_SBD_TEST_CORPUS 	    = false;
	static public final boolean DEBUG_PUNCT_LOOKUP 				    = false;
	static public final boolean DEBUG_UNBREAKABLE_LOOKUP 		    = false;
	static public final boolean DEBUG_ABBREVIATION_LOOKUP 		    = false;
	static public final boolean DEBUG_LIGATURE					    = false;
	static public final boolean DEBUG_NAMED_ENTITY_RECOGNITION	    = false;
    static public final boolean DEBUG_NAMED_ENTITY_RECOGNITION_FULL = false;

	private static void usage() {
		System.err.println("Usage: Balie <-TrainingMode>");
		System.err.println("");
		System.err.println("-trainLangId");
		System.err.println(" Train the Language Identification Model.");
		System.err.println("");
		System.err.println("-trainSBD");
		System.err.println(" Train the Sentence Boundaries Detection Model.");
		System.err.println("");
		System.err.println("-trainAll");
		System.err.println(" Train all of the above.");
		System.exit(1);
	}
	
	/**
	 * The main allows to execute Balie and request the training of individual or all modules.
	 * 
	 * @param args Either -trainlangid, -trainsbd or -trainall
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			usage();
		}
		else if (args[0].toLowerCase().equals("-trainlangid")) {
			LanguageIdentification.TrainLanguageIdentification();
		}
		else if (args[0].toLowerCase().equals("-trainsbd")) {
			SentenceBoundariesRecognition.TrainSentenceBoundariesRecognition();
		}
		else if (args[0].toLowerCase().equals("-trainall")) {
			LanguageIdentification.TrainLanguageIdentification();
			SentenceBoundariesRecognition.TrainSentenceBoundariesRecognition();
		} else {
			usage();
		}
	}

}
