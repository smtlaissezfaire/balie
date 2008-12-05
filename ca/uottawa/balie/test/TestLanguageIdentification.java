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
import ca.uottawa.balie.LanguageIdentification;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TestLanguageIdentification {

	public static void Test() {
		System.out.println("************************************************************");	
		System.out.println("* Language ID testing.                                     *");	
		System.out.println("* - identify the language of a very small text             *");	
		System.out.println("* - (TODO)identify the language of a long text             *");	
		System.out.println("************************************************************");	

		LanguageIdentification li = new LanguageIdentification();
		
		String strTest = "NRC's Institute for Biodiagnostics (NRC-IBD) in Winnipeg recently welcomed a new group of students enrolled in an innovative training program for MRI (Magnetic Resonance Imaging) technologists. The students are the 18th such group to study at NRC-IBD's facilities since the program's launch in 1995 and evidence of NRC's commitment to providing high-impact training opportunities. So far, the program has a 98 per cent placement rate for graduates, and has attracted students from every province across Canada.";
		System.out.println(strTest);
		Assert.Condition(li.DetectLanguage(strTest).equals(Balie.LANGUAGE_ENGLISH), "Language Identification Fails.");
		
		strTest = "L'Institut du biodiagnostic du CNRC (IBD-CNRC) de Winnipeg a récemment souhaité la bienvenue à un nouveau groupe d'étudiants qui entame un programme de formation novateur en IRM (imagerie par résonance magnétique). Ce groupe est le 18e à venir étudier à l'IBD-CNRC depuis le lancement du programme en 1995 et il illustre bien la mesure dans laquelle le Conseil est déterminé à offrir des possibilités d'apprentissage dont l'impact ne passera pas inaperçu. Jusqu'à présent, 98 pour cent des diplômés de ce programme ont trouvé un emploi et des étudiants de chaque province s'y inscrivent.";
		System.out.println(strTest);
		Assert.Condition(li.DetectLanguage(strTest).equals(Balie.LANGUAGE_FRENCH), "Language Identification Fails.");

		strTest = "El instituto del NRC para Biodiagnostics (Nrc-ibd-ibd) en Winnipeg dio la bienvenida recientemente a un nuevo grupo de estudiantes alistados en un programa de entrenamiento innovador para los tecnólogos de MRI (proyección de imagen de resonancia magnética).  Los estudiantes son los décimo octavos tal grupo a estudiar en las instalaciones NRC-IBD's-IBD's desde el lanzamiento del programa en 1995 y la evidencia de la comisión del NRC a proporcionar oportunidades de entrenamiento de alto impacto.  Hasta ahora, el programa tiene una tarifa de la colocación de 98 por ciento para los graduados, y ha atraído a estudiantes de cada provincia a través de Canadá.";
		System.out.println(strTest);
		Assert.Condition(li.DetectLanguage(strTest).equals(Balie.LANGUAGE_SPANISH), "Language Identification Fails.");

        strTest = "Institut NRCs für Biodiagnostics (Nrc-ibd-ibd) in Winnipeg begrüßte vor kurzem eine neue Gruppe Kursteilnehmer, die in einem erfinderischen Trainingskurs für Technologen eingeschrieben wurden MRI (Kerspintomographie).  Die Kursteilnehmer sind die 18. solche Gruppe zum Studieren am Service NRC-IBD's-IBD's die Produkteinführung seit des Programms 1995 und Beweis der Verpflichtung NRCs zum Zur Verfügung stellen von high-impact Ausbildungsgelegenheiten.  Bis jetzt hat das Programm eine 98-Prozent-Plazierungsrate für Absolvent und hat Kursteilnehmer von jeder Provinz über Kanada angezogen.";
		System.out.println(strTest);
		Assert.Condition(li.DetectLanguage(strTest).equals(Balie.LANGUAGE_GERMAN), "Language Identification Fails.");
		
		strTest = "Institutul NRC pentru BioDiagnostic (Nrc-IBD) din Winnipeg ureaz? bun venit unui nou grup de studen?i implica?i  ?�ntr-un program de ?colarizare pentru tehnologiile MRI (Imagini Magnetice). Grupul e format din 18 studen?i pentru a studia facilita?ile NRC-IBD  �? ntrucat programul a fost lansat ? �n 1995 ?i eviden?e care s? exprime dorin?a de a oferi informa?ii este mare. P?�na acum, programul este 98 procente acoperit pentru studen?i cu diplom? , ?i a atras studen?i din toate provinciile din Canada.";
		System.out.println(strTest);
		Assert.Condition(li.DetectLanguage(strTest).equals(Balie.LANGUAGE_ROMANIAN), "Language Identification Fails.");

		System.out.println("[Success]");

	}
	
	public static void main(String[] args) {
		Test();
	}
}
