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
		strTest =   "Kartengrundlage ist die DGKL (Luftbildkarte) im Ma�stab 1:5.000, in welche die\n"+
					"Stichprobenmittelpunkte und die Deutsche Grundkarte (Raster) einkopiert sind.\n"+
					"F�r jeden Stichprobenpunkt ist ein Kartenausschnitt als Lageskizze mit eingenordeter Rosette (Gon-Einteilung)\n"+
					"bereitzustellen. Er ist Bestandteil des Aufnahmebeleges �Lagebeschreibung� FoSI 1.";
		TestLanguage(strTest, Balie.LANGUAGE_GERMAN, 3, Balie.GERMAN_TOKEN_LIST_ON_DISK);

		System.out.println("[Testing French Tokenizer]");
		strTest = 	"D�termin� � faire mentir les sondages qui accordent depuis deux mois une majorit� des 75 si�ges du Qu�bec au Bloc qu�b�cois, le premier ministre Paul Martin fera un ultime blitz dans la Belle Province avant de d�clencher les hostilit�s en pr�vision d'un scrutin le 28 juin.\n"+
				  	"En tout, M. Martin effectuera quatre visites au Qu�bec d'ici le 17 mai et y prononcera deux discours majeurs, dont un portera sur le r�le du Qu�bec au sein de la f�d�ration canadienne.\n"+
				  	"Ce blitz vise � rappeler aux �lecteurs que le premier ministre est sensible aux aspirations des Qu�b�cois et qu'il est pr�f�rable pour le Qu�bec d'�lire des d�put�s lib�raux qui influenceront les d�cisions du gouvernement f�d�ral, selon certains strat�ges.\n"+
				  	"Ainsi, M. Martin se rendra � Saguenay aujourd'hui afin de confirmer � nouveau la participation d'Ottawa dans l'�largissement de l'autoroute 175 reliant cette r�gion � Qu�bec.";
		TestLanguage(strTest, Balie.LANGUAGE_FRENCH, 4, Balie.FRENCH_TOKEN_LIST_ON_DISK);

		System.out.println("[Testing Spanish Tokenizer]");
		strTest = 	"Washington, 6 may (EFE)- El presidente de EEUU, George W. Bush, sigue confiando en su secretario de Estado de Defensa, Donald Rumsfeld, a pesar de su disgusto por la forma en que est� afrontando el esc�ndalo del abuso a prisioneros iraqu�es, sobre el que el Alto Comisionado de las Naciones Unidas para los Derechos Humanos presentar� un informe el pr�ximo 31 de mayo.\n"+
					"El portavoz de la Casa Blanca, Scott McClellan, lo dej� hoy muy claro cuando respondi� con un tajante \"absolutamente\" a la pregunta de si Bush quiere que Rumsfeld contin�e al frente del Pent�gono.\n"+
					"\"El presidente aprecia mucho el trabajo que est� haciendo el secretario Rumsfeld\", a�adi�.\n"+
					"Sus declaraciones coinciden con una escalada de cr�ticas al Gobierno, especialmente al responsable de Defensa, y de las primeras peticiones para que dimita.";
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
