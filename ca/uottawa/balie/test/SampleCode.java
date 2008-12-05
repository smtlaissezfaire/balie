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
 * File created on 07-07-20
 */
package ca.uottawa.balie.test;

import ca.uottawa.balie.Balie;
import ca.uottawa.balie.DisambiguationRulesNerf;
import ca.uottawa.balie.LanguageIdentification;
import ca.uottawa.balie.LexiconOnDisk;
import ca.uottawa.balie.LexiconOnDiskI;
import ca.uottawa.balie.NamedEntityRecognitionNerf;
import ca.uottawa.balie.NamedEntityTypeEnumMappingNerf;
import ca.uottawa.balie.PriorCorrectionNerf;
import ca.uottawa.balie.Token;
import ca.uottawa.balie.TokenList;
import ca.uottawa.balie.Tokenizer;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class SampleCode {
    
    public static void main(String[] args) {
        
        // Language identification
        LanguageIdentification li = new LanguageIdentification();
        String strTest = "Language identification can work " +
                         "on very small texts but it is " +
                         "only reliable on long texts.";
        String strLanguage = li.DetectLanguage(strTest);
        System.out.println(strLanguage);
        
        
        // Tokenization
        Tokenizer tokenizer = 
            new Tokenizer(Balie.LANGUAGE_ENGLISH, true);
        tokenizer.Tokenize(strTest);
        TokenList alTokenList = tokenizer.GetTokenList();
        for (int i = 0; i != alTokenList.Size(); ++i) {
            Token tok = alTokenList.Get(i);
            
            System.out.println(tok.Raw());
        }

        // Sentence boundary detection
        for (int i = 0; i != alTokenList.getSentenceCount(); ++i) {
            String strSentence = 
                alTokenList.SentenceText(i, true, true);
            System.out.print(strSentence);
        }       
        
        // Named entity recognition
        LexiconOnDiskI lexicon = new LexiconOnDisk(
            LexiconOnDisk.Lexicon.OPEN_SOURCE_LEXICON);
        DisambiguationRulesNerf disambiguationRules = 
            DisambiguationRulesNerf.Load();
        
        NamedEntityRecognitionNerf ner = 
            new NamedEntityRecognitionNerf(
                alTokenList, 
                lexicon, 
                disambiguationRules,
                new PriorCorrectionNerf(),
                NamedEntityTypeEnumMappingNerf.values(),
                true);
        
        ner.RecognizeEntities();
        alTokenList = ner.GetTokenList();       
        
    }
}
