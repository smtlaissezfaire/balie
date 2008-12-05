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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import weka.classifiers.functions.SMO;


/**
 * Methods for training, testing and using language identification.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LanguageIdentification {

	/**
	 * The latest LangID model (if found) is loaded upon construction.
	 * Language guesser will only work if a model is found.
	 */
	public LanguageIdentification() {
		m_Model = null;
        
        m_NGramLength           = 2;
        m_NGramMaxNum           = 500;
        m_nGramFreqThreshold    = 100;
        
		try {
			m_Model = WekaPersistance.Load(Balie.LANGUAGE_ID_MODEL);
            
            if (m_Model == null) {
                throw new Error("Unable to find the language identification model :" + Balie.LANGUAGE_ID_MODEL);
            }
            
		} catch (Exception e) {
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("No LanguageIdentification model found.");
		}
	}

	private WekaLearner m_Model;
    private int         m_NGramLength;
    private int         m_NGramMaxNum;
    private int         m_nGramFreqThreshold;
	
	private WekaLearner TrainModel(ArrayList<String> pi_Languages) {
		
		// Extract N-Gram from every training text
		Iterator<String> iCur = pi_Languages.iterator();
		Hashtable<String,ArrayList<CharacterNGram>> Lan2NGrams = new Hashtable<String,ArrayList<CharacterNGram>>();
		Hashtable<String,Integer> hashAllNGrams = new Hashtable<String,Integer>();
		Hashtable<String,Integer> hashAllUNIGrams = new Hashtable<String,Integer>();
		
		if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("Reading files.");
		
		while (iCur.hasNext()) {
			String strCurLan = (String)iCur.next();
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(strCurLan);
			ArrayList<CharacterNGram> alCurNGrams = null;
			try {
				alCurNGrams = Files2NGram(Balie.LANGUAGE_ID_TRAINING_CORPUS, strCurLan, hashAllNGrams, hashAllUNIGrams);
			} catch (Exception e) {
				throw new Error("Training corpus was not found here: " + Balie.LANGUAGE_ID_TRAINING_CORPUS);
			}
			Lan2NGrams.put(strCurLan, alCurNGrams);
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("Done");
		}
		
		// Get a reasonable list of attributes (remove low freq) 
		ArrayList<String> alSelectedNgramAttributes 	= GetGlobalNGramList(hashAllNGrams);
		ArrayList<String> alSelectedUnigramAttributes 	= GetGlobalNGramList(hashAllUNIGrams);
		
		// Proceed to Attribute Selection only on the BiGRAMS
		if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("Attribute Selection...");
		WekaAttribute[] wekaAttr = new WekaAttribute[alSelectedNgramAttributes.size()];
		for (int i = 0; i != alSelectedNgramAttributes.size(); ++i) {
			wekaAttr[i] = new WekaAttribute((String)alSelectedNgramAttributes.get(i));
		}

		//String[] strAttr = (String[])alSelectedNgramAttributes.toArray(new String [alSelectedNgramAttributes.size()]);
		String[] strClass = (String[])pi_Languages.toArray(new String [pi_Languages.size()]);
		
		WekaAttributeSelection was = new WekaAttributeSelection(WekaAttributeSelection.WEKA_CHI_SQUARE, wekaAttr, strClass);
		
		iCur = pi_Languages.iterator();
		while (iCur.hasNext()) {
			String strCurLan = (String)iCur.next();
			ArrayList<CharacterNGram> alCurNGrams = Lan2NGrams.get(strCurLan);
			Iterator<CharacterNGram> iNCur = alCurNGrams.iterator();
			while (iNCur.hasNext()) {
				CharacterNGram cng = iNCur.next();
				Double[] nGram = cng.Instance((String[])alSelectedNgramAttributes.toArray(new String[0]));
				was.AddInstance(nGram, strCurLan);
			}
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(String.valueOf(alCurNGrams.size()) + " instances created for " + strCurLan);
		}		

		was.NumAttributes(m_NGramMaxNum);
		was.Select(true);
		String[] strReducedAttr 		= was.ReduceDimentionality();

		// Now, concat chosen nGram and unigram in the final list of attributes
		String[] strAllAttributes 		= new String[m_NGramMaxNum + alSelectedUnigramAttributes.size()];
		WekaAttribute[] wekaFinalAttr 	= new WekaAttribute[strAllAttributes.length];
		
		if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("Creating the classifier...");
		for (int i = 0; i !=strReducedAttr.length; ++i) {
			wekaFinalAttr[i] 	= new WekaAttribute(strReducedAttr[i]);
			strAllAttributes[i] = strReducedAttr[i];
		}
		
		// At the list of the Reduced Attributes(NGrams) add the list of UNIGrams 
		int nPos = strReducedAttr.length; 
		for (int i = 0; i != alSelectedUnigramAttributes.size(); ++i) {
			wekaFinalAttr[nPos] 	=  new WekaAttribute((String)alSelectedUnigramAttributes.get(i));
			strAllAttributes[nPos]	= (String)alSelectedUnigramAttributes.get(i);
			++nPos;
		}
		
		WekaLearner wl = new WekaLearner(wekaFinalAttr, strClass);

		iCur = pi_Languages.iterator();		
		while (iCur.hasNext()) {
			String strCurLan = (String)iCur.next();
			ArrayList<CharacterNGram> alCurNGrams = Lan2NGrams.get(strCurLan);
			Iterator<CharacterNGram> iNCur = alCurNGrams.iterator();
			while (iNCur.hasNext()) {
				CharacterNGram cng = iNCur.next();
				Double[] nGram = cng.Instance(strAllAttributes);
				wl.AddTrainInstance(nGram, strCurLan);
			}
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(String.valueOf(alCurNGrams.size()) + " TRAIN instances created for " + strCurLan);
		}
		
        SMO smo = new SMO();
        wl.CreateModel(smo);
        
        /*
        // Create a classifier
        if (MODE.equals(Balie.LANGUAGE_ID_MODEL_HUGE)) {
            SMO smo = new SMO();
            wl.CreateModel(smo);
        } else {
            NaiveBayes nb = new NaiveBayes();
            //nb.setUseKernelEstimator(true);
            wl.CreateModel(nb);
        }
         */		
		return wl;	
	}
	
	
	private void TestModel(WekaLearner pi_Model, ArrayList<String> pi_Languages) {
		
		// Extract N-Gram from every testing text
		Iterator<String> iCur = pi_Languages.iterator();
		Hashtable<String,ArrayList<CharacterNGram>> Lan2NGrams = new Hashtable<String,ArrayList<CharacterNGram>>();
		Hashtable<String, Integer> hashAllNGrams = new Hashtable<String, Integer>();
		Hashtable<String, Integer> hashAllUNIGrams = new Hashtable<String, Integer>();
		
		if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("Reading files.");
		while (iCur.hasNext()) {
			String strCurLan = (String)iCur.next();
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(strCurLan);
			ArrayList<CharacterNGram> alCurNGrams = null;
			try {
				alCurNGrams = Files2NGram(Balie.LANGUAGE_ID_TESTING_CORPUS, strCurLan, hashAllNGrams, hashAllUNIGrams);
			} catch (Exception e) {
				throw new Error("Testing corpus was not found here: " + Balie.LANGUAGE_ID_TESTING_CORPUS);
			}

			Lan2NGrams.put(strCurLan, alCurNGrams);
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out("Done");
		}
		
		// Get the list of attributes
		String[] strSelectedAttributes = pi_Model.GetAttributeList();
		
		iCur = pi_Languages.iterator();
		while (iCur.hasNext()) {
			String strCurLan = (String)iCur.next();
			ArrayList<CharacterNGram> alCurNGrams = Lan2NGrams.get(strCurLan);
			Iterator<CharacterNGram> iNCur = alCurNGrams.iterator();
			while (iNCur.hasNext()) {
				CharacterNGram cng = iNCur.next();
				Double[] nGram = cng.Instance(strSelectedAttributes);
				pi_Model.AddTestInstance(nGram, strCurLan);
			}
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(String.valueOf(alCurNGrams.size()) + " TEST instances created for " + strCurLan);
		}		
		
		System.out.println(pi_Model.TestModel());
	}
	
	private ArrayList<String> GetGlobalNGramList(Hashtable<String, Integer> pi_hashAllNGrams) {
		ArrayList<String> alSelectedAttributes = new ArrayList<String>();
		// Attribute Selection
		Enumeration<String> keyEnum = pi_hashAllNGrams.keys();
					
		while (keyEnum.hasMoreElements()) {
			String strNGram = keyEnum.nextElement();
			if (((Integer)pi_hashAllNGrams.get(strNGram)).intValue() > m_nGramFreqThreshold) {
				alSelectedAttributes.add(strNGram);
			}
		}
		if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(String.valueOf(alSelectedAttributes.size()) + " attributes found");
		return alSelectedAttributes;
	
	}
	
	private ArrayList<CharacterNGram> Files2NGram(String pi_Corpus, String pi_Language, Hashtable<String,Integer> pi_hashAllNGrams, Hashtable<String,Integer> pi_hashAllUNIGrams) {
		ArrayList<CharacterNGram> alCurNGrams = new ArrayList<CharacterNGram>();
			
		String strTextPath = pi_Corpus + "/" + pi_Language;
			
		File fBasePath = new File(strTextPath);
		String[] strTrainingFiles = fBasePath.list();
			
		for (int i = 0; i != strTrainingFiles.length; ++i) {
		    if (!strTrainingFiles[i].equals("CVS")) {
				try {
					// Language Identification corpus
				    String strContent = FileHandler.GetTextFileContent(fBasePath.getAbsolutePath() + "/" + strTrainingFiles[i], Balie.ENCODING_LITTLE_INDIAN);
					CharacterNGram cng = new CharacterNGram(m_NGramLength);
					cng.Feed(strContent);  
					
					Hashtable<String,Integer> NGR_freq = cng.NGramFrequency();
					Hashtable<String,Integer> UNIGR_freq = cng.UNIGramFrequency();
					
					Enumeration<String> N_keyEnum = NGR_freq.keys();
						
					while (N_keyEnum.hasMoreElements()) {
						String strNGram = N_keyEnum.nextElement();
						if (pi_hashAllNGrams.containsKey(strNGram)){
							int old = ((Integer)pi_hashAllNGrams.get(strNGram)).intValue();
							int cur = ((Integer)NGR_freq.get(strNGram)).intValue();
							pi_hashAllNGrams.put(strNGram, new Integer(old+cur));
						} else {
							pi_hashAllNGrams.put(strNGram, NGR_freq.get(strNGram));
						}
					}
					
					//We create a separate Hashtable for the UNI_Grams	
					Enumeration<String> UNI_keyEnum = UNIGR_freq.keys();
					
					while (UNI_keyEnum.hasMoreElements()) {
						String strUNIGram = (String)UNI_keyEnum.nextElement();
						if (pi_hashAllUNIGrams.containsKey(strUNIGram)){
							int old = ((Integer)pi_hashAllUNIGrams.get(strUNIGram)).intValue();
							int cur = ((Integer)UNIGR_freq.get(strUNIGram)).intValue();
							pi_hashAllUNIGrams.put(strUNIGram, new Integer(old+cur));
						} else {
							pi_hashAllUNIGrams.put(strUNIGram, UNIGR_freq.get(strUNIGram));
						}
					}
					
					alCurNGrams.add(cng);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}		        
		    }
			if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) System.out.print(".");
		}
		return alCurNGrams;
	}
	
	/**
	 * Gives the likelihood of every language given a text.
	 * 
	 * @param pi_Text	A text for which we want to guess the language
	 * @return Array of LanguageIdentificationGuess (one per supported language)
	 */
	public LanguageIdentificationGuess[] GetLanguageLikelihood(String pi_Text) {
		if (m_Model == null) {
			throw new Error("Model must be loaded at construction time.");
		}
		
		CharacterNGram cng = new CharacterNGram(m_NGramLength);
		cng.Feed(pi_Text);
		Double[] instance = cng.Instance(m_Model.GetAttributeList());
		
		String strLangs[] = (String[])m_Model.GetClassList();
		
		double fProb[] = m_Model.GetDistribution(instance);
		
		if (strLangs.length != fProb.length) {
		    throw new Error("Incompatible language list and probability distribution.");
		}
		
		LanguageIdentificationGuess guesses[] = new LanguageIdentificationGuess[strLangs.length];
		
		for (int i = 0; i != strLangs.length; ++i) {
		    guesses[i] = new LanguageIdentificationGuess(strLangs[i], new Double(fProb[i]));
		    if(Balie.DEBUG_LANGUAGE_IDENTIFICATION) DebugInfo.Out(strLangs[i] + "(" + fProb[i] + ")");
		}

		return guesses;
	}
	
	/**
	 * Gives the language with the highest likelihood.
	 * 
	 * @param pi_Text	A text for which we want to guess the language
	 * @return String that represent the most probable language (see {@link Balie} for enumeration)
	 * @see Balie
	 */
	public String DetectLanguage(String pi_Text) {
	    String strLanguage = Balie.LANGUAGE_UNKNOWN;
	    LanguageIdentificationGuess[] guesses = GetLanguageLikelihood(pi_Text);
	    
	    // sort language by probability
	    Arrays.sort(guesses);
	    
	    //TODO: implements better heuristic in case many language are probable..
	    strLanguage = guesses[0].Language();
	    
	    return strLanguage;
	}

	/**
	 * Trains and Tests the language identification model.
	 */
	public static void TrainLanguageIdentification() {
		ArrayList<String> alLang = new ArrayList<String>();
		alLang.add(Balie.LANGUAGE_ENGLISH);
		alLang.add(Balie.LANGUAGE_FRENCH);
		alLang.add(Balie.LANGUAGE_SPANISH);
		alLang.add(Balie.LANGUAGE_GERMAN);
		alLang.add(Balie.LANGUAGE_ROMANIAN);
		
		LanguageIdentification li = new LanguageIdentification();
		
		WekaLearner wl = li.TrainModel(alLang);
		li.TestModel(wl, alLang);
		
		// output arff file for visualization and debugging
		WekaPersistance.PrintToArffFile(wl, Balie.OUT_LI_TRAIN_MODEL, WekaPersistance.PRINT_TRAINING_SET);
		WekaPersistance.PrintToArffFile(wl, Balie.OUT_LI_TEST_MODEL,  WekaPersistance.PRINT_TESTING_SET);
		
		// reduce model size
		wl.Shrink();
		
		// save model
		WekaPersistance.Save(wl, Balie.LANGUAGE_ID_MODEL);

	}

	/**
	 * Execute TrainLanguageIdentification()
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TrainLanguageIdentification();		
	}
}
