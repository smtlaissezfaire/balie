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
import ca.uottawa.balie.DebugInfo;
import ca.uottawa.balie.LanguageIdentification;
import ca.uottawa.balie.TokenList;
import ca.uottawa.balie.Tokenizer;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TestTokenization {

	// Test Routine
	private static final boolean OVERWRITE_VERSIONS_ON_DISK = false;
	
		public static void Test() {
		System.out.println("************************************************************");	
		System.out.println("* Tokenizer testing.                                       *");	
		System.out.println("* - tokenize a small text for each language                *");	
		System.out.println("* - assert the number of sentences                         *");	
		System.out.println("* - assert the token list is similar to a saved tokenlist  *");	
		System.out.println("************************************************************");	
		
		System.out.println("[Testing English Tokenizer]");
		String strTest = "1. Introduction\n"+
		 			"Information  Extraction (IE) is the name given to any process which selectively structures and\n"+
					"combines data which is found, explicitly stated or implied, in one or more texts. The final output\n"+
					"of the extraction process varies; in every case, however, it can be transformed so as to populate\n"+
					"some type of database. Information analysts working long term on specific tasks already carry\n"+
					"out information extraction manually with the express goal of database creation.";
		TestLanguage(strTest, Balie.LANGUAGE_ENGLISH, 4, Balie.ENGLISH_TOKEN_LIST_ON_DISK);
		
		System.out.println("[Testing German Tokenizer]");
		strTest =   "Kartengrundlage ist die DGKL (Luftbildkarte) im Maßstab 1:5.000, in welche die\n"+
					"Stichprobenmittelpunkte und die Deutsche Grundkarte (Raster) einkopiert sind.\n"+
					"Für jeden Stichprobenpunkt ist ein Kartenausschnitt als Lageskizze mit eingenordeter Rosette (Gon-Einteilung)\n"+
					"bereitzustellen. Er ist Bestandteil des Aufnahmebeleges “Lagebeschreibung” FoSI 1.";
		TestLanguage(strTest, Balie.LANGUAGE_GERMAN, 3, Balie.GERMAN_TOKEN_LIST_ON_DISK);

		System.out.println("[Testing French Tokenizer]");
		strTest = 	"Déterminé à faire mentir les sondages qui accordent depuis deux mois une majorité des 75 sièges du Québec au Bloc québécois, le premier ministre Paul Martin fera un ultime blitz dans la Belle Province avant de déclencher les hostilités en prévision d'un scrutin le 28 juin.\n"+
				  	"En tout, M. Martin effectuera quatre visites au Québec d'ici le 17 mai et y prononcera deux discours majeurs, dont un portera sur le rôle du Québec au sein de la fédération canadienne.\n"+
				  	"Ce blitz vise à rappeler aux électeurs que le premier ministre est sensible aux aspirations des Québécois et qu'il est préférable pour le Québec d'élire des députés libéraux qui influenceront les décisions du gouvernement fédéral, selon certains stratèges.\n"+
				  	"Ainsi, M. Martin se rendra à Saguenay aujourd'hui afin de confirmer à nouveau la participation d'Ottawa dans l'élargissement de l'autoroute 175 reliant cette région à Québec.";
		TestLanguage(strTest, Balie.LANGUAGE_FRENCH, 4, Balie.FRENCH_TOKEN_LIST_ON_DISK);

		System.out.println("[Testing Spanish Tokenizer]");
		strTest = 	"Washington, 6 may (EFE)- El presidente de EEUU, George W. Bush, sigue confiando en su secretario de Estado de Defensa, Donald Rumsfeld, a pesar de su disgusto por la forma en que está afrontando el escándalo del abuso a prisioneros iraquíes, sobre el que el Alto Comisionado de las Naciones Unidas para los Derechos Humanos presentará un informe el próximo 31 de mayo.\n"+
					"El portavoz de la Casa Blanca, Scott McClellan, lo dejó hoy muy claro cuando respondió con un tajante \"absolutamente\" a la pregunta de si Bush quiere que Rumsfeld continúe al frente del Pentágono.\n"+
					"\"El presidente aprecia mucho el trabajo que está haciendo el secretario Rumsfeld\", añadió.\n"+
					"Sus declaraciones coinciden con una escalada de críticas al Gobierno, especialmente al responsable de Defensa, y de las primeras peticiones para que dimita.";
		TestLanguage(strTest, Balie.LANGUAGE_SPANISH, 4, Balie.SPANISH_TOKEN_LIST_ON_DISK);

		System.out.println("[Testing Romanian Tokenizer]");
		strTest = 	"Peste 60 de companii au avut la dispozitie o suprafata expozitionala de 1500 metri patrati " +
				    "pentru a-si prezenta propriile solutii pentru confortul interior al casei sau al biroului. " +
				    "Au participat firme din domenii variate: mobilier, decoratiuni interioare, comunicatii fixe si mobile, tehnologia informatiei, proiectarea si furnizarea corpurilor pentru iluminatul ambiental, atat din Iasi, cat si din Bucuresti, Arad, Odorheiu Secuiesc, Medias, Pascani, Suceava.";
		TestLanguage(strTest, Balie.LANGUAGE_ROMANIAN, 2, Balie.ROMANIAN_TOKEN_LIST_ON_DISK);

		System.out.println("[Testing Unbreakable Tokenizer]");
		strTest = "This is the test for unbreakable tokens. I don't know if I should keep this test. C# is a programming language. Java too (what a great one, especially version 5)... C++ is another one. Finally, --c++-- is nothing but c++ surrounded by dashes. What a great test it was.";
		TestLanguage(strTest, Balie.LANGUAGE_ENGLISH, 7, Balie.UNBREAK_TOKEN_LIST_ON_DISK);
	}
	
	private static void TestLanguage(String pi_Text, String pi_Language, int pi_NumSentences, String pi_TokList) {

		LanguageIdentification li = new LanguageIdentification();
		String strLang = li.DetectLanguage(pi_Text);
		Assert.Condition(strLang.equals(pi_Language), "Language identification failed. Guess is "+strLang+ ", expected is " + pi_Language);

		Tokenizer tokenizer = new Tokenizer(strLang, true);
		tokenizer.Tokenize(pi_Text);
		TokenList alTokenList = tokenizer.GetTokenList();
		Assert.Condition(tokenizer.SentenceCount() == pi_NumSentences, "Expected sentence count is " + pi_NumSentences + ", actual is " + tokenizer.SentenceCount());
		
		System.out.println("[TokenCount: "+String.valueOf(tokenizer.TokenCount())+"]");
		
		for (int i = 0; i != tokenizer.SentenceCount(); ++i) {
			System.out.print("("+i+" Raw  )");
			System.out.println(alTokenList.SentenceText(i, false, false));	
			System.out.print("("+i+" Canon)");
			System.out.println(alTokenList.SentenceText(i, true, false));	
		}
		
		if (OVERWRITE_VERSIONS_ON_DISK) {
			DebugInfo.SaveTokenList(alTokenList, pi_TokList);
		}
		TokenList tlEngCheck = DebugInfo.LoadTokenList(pi_TokList);
		if (!alTokenList.equals(tlEngCheck)) {
			throw new Error(pi_Language + " tokenList mismatch");
		}
		System.out.println("");
		System.out.println("[Success]");
		
	}
	
	public static void main(String[] args) {
		Test();
	}
}
