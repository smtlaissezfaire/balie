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

import weka.classifiers.bayes.NaiveBayes;
import ca.uottawa.balie.WekaAttribute;
import ca.uottawa.balie.WekaLearner;
import ca.uottawa.balie.WekaPersistance;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TestWekaLearner {

	public static void Test() {
		System.out.println("************************************************************");	
		System.out.println("* Weka Learner testing.                                    *");	
		System.out.println("* - test machine learning capabilities                     *");	
		System.out.println("************************************************************");
		WekaAttribute[] wekaAttr = new WekaAttribute[]{
				new WekaAttribute("Double1"),
				new WekaAttribute("Double2"),
				new WekaAttribute("Double3"),
				new WekaAttribute("Double4")
		};
		String[] strClass = new String[]{
			"Positive",
			"Negative"		
		};

		WekaLearner wl = new WekaLearner(wekaAttr, strClass);
		
		wl.AddTrainInstance(new Double[]{new Double(0.0),new Double(1.2),new Double(2.2),new Double(4.4)}, "Positive");
		wl.AddTrainInstance(new Double[]{new Double(1.0),new Double(1.0),new Double(7.7),new Double(1.9)}, "Negative");
		wl.AddTrainInstance(new Double[]{new Double(0.1),new Double(0.0),new Double(0.0),new Double(0.0)}, "Positive");
		wl.AddTrainInstance(new Double[]{new Double(1.1),new Double(1.1),new Double(1.1),new Double(1.1)}, "Positive");
		wl.AddTrainInstance(new Double[]{new Double(3.4),new Double(9.9),new Double(0.2),new Double(1.0)}, "Positive");
		wl.AddTrainInstance(new Double[]{new Double(1.5),new Double(0.1),new Double(7.0),new Double(0.0)}, "Negative");
		
		wl.CreateModel(new NaiveBayes());
	
		wl.AddTestInstance(new Double[]{new Double(1.5),new Double(0.1),new Double(7.0),new Double(0.0)}, "Negative");
		wl.AddTestInstance(new Double[]{new Double(1.1),new Double(1.1),new Double(1.1),new Double(1.1)}, "Positive");
		
		System.out.println(wl.TestModel());
		
		double x = wl.Classify(new Double[]{new Double(3.4),new Double(1.0),new Double(9.9),new Double(0.0)});
		System.out.println(String.valueOf(x));
		
		WekaPersistance.Save(wl, "./WekaLearnerTest.sig");
		wl = WekaPersistance.Load("./WekaLearnerTest.sig");
		
		x = wl.Classify(new Double[]{new Double(3.4),new Double(1.0),new Double(9.9),new Double(0.0)});
		System.out.println(String.valueOf(x));

		System.out.println("[Success]");
		
	}

	public static void main(String[] args) {
		Test();
	}

}
