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
 * Created on Dec 16, 2005
 */
package ca.uottawa.balie.test;

import ca.uottawa.balie.Balie;
import ca.uottawa.balie.DisambiguationRulesNerf;
import ca.uottawa.balie.LexiconOnDisk;
import ca.uottawa.balie.LexiconOnDiskI;
import ca.uottawa.balie.NamedEntityRecognitionNerf;
import ca.uottawa.balie.NamedEntityTypeEnumMappingNerf;
import ca.uottawa.balie.PriorCorrectionNerf;
import ca.uottawa.balie.TokenList;
import ca.uottawa.balie.Tokenizer;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TestNamedEntityRecognition {

	public static void Test() {
		System.out.println("************************************************************");	
		System.out.println("* Named Entity Recognition testing.                        *");	
		System.out.println("* - test recognition of an entity of each type             *");	
		System.out.println("************************************************************");
		
        {
		String strText = "VICTORIA -- Canadian scientists are part of a global effort to conduct a census of all the marine life in the world's oceans by 2010 that, at the halfway point, has discovered hundreds of unknown species in the deeper ocean waters.\n" + 
					"Canadians involved in the Census of Marine Life are also pioneering the use of a tracking device that can follow salmon more than 1,500 kilometres in the ocean, allowing us to learn more about the species than was known before.\n" + 
					"\"New species continue to appear on an average of 150 new species of fish in a year,\" says Ron O'Door, who is on leave from Dalhousie University in Halifax to work on the census project.\n" +
					"\"We estimate we could add as many as a million of all species of life forms by the end of the census in 2010.\"\n" +  
					"The Canadian Pacific Ocean Shelf Tracking Project, meanwhile, is a pilot program revealing the coastal migrations of endangered British Columbia salmon, said David Welch, a scientist and chief census researcher from Nanaimo, B.C.\n" + 
					"Using technology developed in Halifax, salmon are implanted with chips that can monitor their movements for years.\n" + 
					"\"We've put out a listening array that stretches 1,500 kilometres north to south and it's allowing us to track salmon for the first time over vast distances along the ocean shelf,\" Welch said.\n"+ 
					"\"We're measuring survival for the first time,\" he said. \"That's a huge step forward because we've never been able to do that.\"\n"+ 
					"Scientists are watching where the salmon go when they reach the ocean, Welch said. They, and ultimately fishermen, will soon discover where the salmon travel and perhaps why their stocks are declining during their years in the ocean, he added.\n" + 
					"The researchers are tracking salmon in an area that stretches more than 1,550 kilometres, from Washington State, through British Columbia to north of the Alaskan panhandle, Welch said. \n"+ 
					"By 2010, the project is aiming to cover the entire western coast of North America, with a goal to replicate the network on continental shelves worldwide, he said. \n"+
					"So far, census scientists have discovered 78 new fish species this year. The census now has identified 15,717 fish species and has an inventory of more than more than 40,000 marine species.\n"+ 
					"Most of the new marine species are being found in the deep ocean waters as scientists use new sensitive tracking technology to make observations in previously unknown waters, O'Door said. \n"+
					"The Norwegians have developed an ultra-sensitive sonar that can locate a tiny shrimp three kilometres below the surface, he said. \n"+
					"The census is also confirming previously held theories that many fish species, especially large predators, are disappearing, O'Door said.\n"+ 
					"\"Part of the importance of the census is raising the awareness of people of what's happening in the world's oceans,\" he said. \n"+
					"Some 1,700 experts from 73 countries are working on the census, which in 2010 is destined to become a public document used by fishermen, scientists and students.\n"; 

        
            Tokenizer tokenizer = new Tokenizer(Balie.LANGUAGE_ENGLISH, true);
        
            LexiconOnDiskI lexicon = new LexiconOnDisk(LexiconOnDisk.Lexicon.OPEN_SOURCE_LEXICON);
            DisambiguationRulesNerf disambiguationRules = DisambiguationRulesNerf.Load();
            
            tokenizer.Reset();
            tokenizer.Tokenize(strText);
            TokenList alTokenList = tokenizer.GetTokenList();

            NamedEntityRecognitionNerf ner = new NamedEntityRecognitionNerf(
                    alTokenList, 
                    lexicon, 
                    disambiguationRules,
                    new PriorCorrectionNerf(),
                    NamedEntityTypeEnumMappingNerf.values(),
                    true);
            ner.RecognizeEntities();
            
            alTokenList = ner.GetTokenList();
    		String strAnnotated = alTokenList.TokenRangeText(0, alTokenList.Size(), false, true, true, true, false, true);
    		
    		System.out.println(strAnnotated);
    		System.out.println("");
    	
            
    		System.out.println("LOCATION: Victoria");
            String strLabel = alTokenList.Get(0).EntityType().GetLabel(NamedEntityTypeEnumMappingNerf.values());
    		Assert.Condition(strLabel.equals("LOCATION"), "Failed.");

            System.out.println("PERSON: Ron O'Door");
    		Assert.Condition(alTokenList.Get(112).EntityType().GetLabel(NamedEntityTypeEnumMappingNerf.values()).equals("PERSON"), "Failed.");
    		Assert.Condition(alTokenList.Get(113).EntityType().GetLabel(NamedEntityTypeEnumMappingNerf.values()).equals("PERSON"), "Failed.");
    
        }
               
		System.out.println("[Success]");
	}

	public static void main(String[] args) {
		Test();
	}

}
