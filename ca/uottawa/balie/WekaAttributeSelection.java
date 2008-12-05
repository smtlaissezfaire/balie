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
 * Created on Apr 13, 2004
 */
package ca.uottawa.balie;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.ClassifierSubsetEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.SymmetricalUncertAttributeEval;
import weka.attributeSelection.SVMAttributeEval;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.FastVector;
import weka.core.Instances;

/**
 * Methods to select the top attributes from a given classification problem.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class WekaAttributeSelection {

	public static final int WEKA_CHI_SQUARE = 0;
	public static final int WEKA_INFO_GAIN = 1;
	public static final int WEKA_WRAPPER = 2;
	public static final int WEKA_SYM_UNCERT = 3;
	public static final int WEKA_SVM = 4;
	public static final int WEKA_RELIEF = 5;
	public static final int WEKA_ONER = 6;
	
	public static final int DEFAULT_NUM_ATTRIBUTES = 100;

	/**
	 * Creates an Attribute selection framework.
	 * 
	 * @param pi_Evaluator			Type of evaluation of attributes merit
	 * @param pi_Attributes			The attributes to evaluate
	 * @param pi_ClassAttributes	The class attribute
	 */
	public WekaAttributeSelection(int pi_Evaluator, WekaAttribute[] pi_Attributes, String[] pi_ClassAttributes) {
		
		// Create a dummy classifier
		m_DummyLearner = new WekaLearner(pi_Attributes, pi_ClassAttributes);
		m_Evaluator = pi_Evaluator;
		m_NumAttributes = DEFAULT_NUM_ATTRIBUTES;
		m_OriginalAttributes = m_DummyLearner.GetAttributeList();
		m_AttributeSelection = null;
	}

	/**
	 * Add an instance, that is a vector of value for all attributes.
	 * 
	 * @param pi_Instance	The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @param pi_Class		The class for this instance
	 * @see WekaAttribute
	 */
	public void AddInstance(Object[] pi_Instance, String pi_Class) {
		m_DummyLearner.AddTrainInstance(pi_Instance, pi_Class);
	}
	
	/**
	 * Sets the number of top attribute to select.
	 * 
	 * @param pi_Num	Num of attributes to select
	 */
	public void NumAttributes(int pi_Num) {
		m_NumAttributes = pi_Num;
	}
	
    public String[] GetAttributeList() {
        return m_OriginalAttributes;
    }
    public FastVector GetAttribute() {
        return m_DummyLearner.GetAttribute();
    }    
    public void SetDoubleOnly(boolean b) {
        m_DummyLearner.SetDoubleOnly(b);
    }
    
	/**
	 * Select the top attributes
	 */
	public void Select(boolean pi_Debug) {
		Instances insts = m_DummyLearner.GetTrainInstances();
		
		try {
			ASEvaluation eval = null;
			ASSearch search = null;
			
			if (m_Evaluator == WEKA_CHI_SQUARE) {
				eval = new ChiSquaredAttributeEval();
				search = new Ranker(); 
				((Ranker)search).setNumToSelect(m_NumAttributes);
			} else if (m_Evaluator == WEKA_INFO_GAIN) {
				eval = new InfoGainAttributeEval();
				search = new Ranker(); 
				((Ranker)search).setNumToSelect(m_NumAttributes);
			} else if (m_Evaluator == WEKA_WRAPPER) {
			    eval = new ClassifierSubsetEval();
			    ((ClassifierSubsetEval)eval).setClassifier(new NaiveBayes());
			    search = new Ranker(); // TODO: use something else than ranker
				((Ranker)search).setNumToSelect(m_NumAttributes);
			} else if (m_Evaluator == WEKA_SYM_UNCERT) {
				eval = new SymmetricalUncertAttributeEval();
				search = new Ranker(); 
				((Ranker)search).setNumToSelect(m_NumAttributes);
			} else if (m_Evaluator == WEKA_SVM) {
				eval = new SVMAttributeEval();
				search = new Ranker(); 
				((Ranker)search).setNumToSelect(m_NumAttributes);
			} else if (m_Evaluator == WEKA_RELIEF) {
				eval = new ReliefFAttributeEval();
				search = new Ranker(); 
				((Ranker)search).setNumToSelect(m_NumAttributes);
			} else if (m_Evaluator == WEKA_ONER) {
				eval = new OneRAttributeEval();
				search = new Ranker(); 
				((Ranker)search).setNumToSelect(m_NumAttributes);
			}
			
			m_AttributeSelection = new AttributeSelection();
			m_AttributeSelection.setEvaluator(eval);
			m_AttributeSelection.setSearch(search);
					
			m_AttributeSelection.SelectAttributes(insts);
			if (pi_Debug) System.out.println(m_AttributeSelection.toResultsString());
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * Gets the array of chosen attributes
	 * 
	 * @return A String array where each element is the label of a chosen attribute (see {@link WekaAttribute}).
	 * @see WekaAttribute
	 */
	public String[] ReduceDimentionality() {
		String[] out = null;
		try{
			int nNumSelected = m_AttributeSelection.numberAttributesSelected();
			out = new String[nNumSelected];
			int[] nSelections = m_AttributeSelection.selectedAttributes();

			for (int i = 0; i != nNumSelected; ++i) {
				out[i] = m_OriginalAttributes[nSelections[i]];
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}		
		return out;
	}
	
	private WekaLearner 	m_DummyLearner;
	private int 			m_Evaluator;
	private int 			m_NumAttributes;
	private String[]		m_OriginalAttributes;
	AttributeSelection 		m_AttributeSelection;
	
	/**
	 * Test routine.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
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

		WekaAttributeSelection was = new WekaAttributeSelection(WEKA_CHI_SQUARE, wekaAttr, strClass);
		
		was.AddInstance(new Double[]{new Double(0.0),new Double(1.2),new Double(2.2),new Double(4.4)}, "Positive");
		was.AddInstance(new Double[]{new Double(1.0),new Double(1.0),new Double(7.7),new Double(1.9)}, "Negative");
		was.AddInstance(new Double[]{new Double(0.1),new Double(0.0),new Double(0.0),new Double(0.0)}, "Positive");
		was.AddInstance(new Double[]{new Double(1.1),new Double(1.1),new Double(1.1),new Double(1.1)}, "Positive");
		was.AddInstance(new Double[]{new Double(3.4),new Double(9.9),new Double(0.2),new Double(1.0)}, "Positive");
		was.AddInstance(new Double[]{new Double(1.5),new Double(0.1),new Double(7.0),new Double(0.0)}, "Negative");
		
		was.Select(true);
	}

}
