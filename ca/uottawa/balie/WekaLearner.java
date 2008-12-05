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
 * Created on Apr 10, 2004
 */
package ca.uottawa.balie;

import java.io.Serializable;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


/**
 * Methods to create, train and test a classification algorithm.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class WekaLearner implements Serializable {

    private static final long serialVersionUID = 1L;

    // Local Constant
	private static final boolean DEBUG 		= false;
	private static final String CLASS_LABEL = "Class";

	/**
	 * Creates a new classification algorithm.
	 * 
	 * @param pi_Attributes			Array of attributes
	 * @param pi_ClassAttributes	Class attribute
	 */
	public WekaLearner(WekaAttribute[] pi_Attributes, String[] pi_ClassAttributes) {
		m_ClassAttributes 	= pi_ClassAttributes;
		m_AttributeLabels   = new String[pi_Attributes.length];
		
		// Create the Weka Attributes
		m_WekaAttributes = new FastVector(pi_Attributes.length+1);
		for (int i = 0; i != pi_Attributes.length; ++i) {
			if (pi_Attributes[i].Label().equals(CLASS_LABEL)) {
				throw new Error("Attribute cannot be named \"Class\" (reserved)");
			}
			m_AttributeLabels[i] = pi_Attributes[i].Label();
			if (pi_Attributes[i].IsNumeric()) {
				Attribute anAttribute = new Attribute(pi_Attributes[i].Label());
				m_WekaAttributes.addElement(anAttribute);	
			} else {
				Attribute anAttribute = new Attribute(pi_Attributes[i].Label(), pi_Attributes[i].Values());
				m_WekaAttributes.addElement(anAttribute);	
			}
		}
		
		// Create The class attribute 
		FastVector fvClass = new FastVector(pi_ClassAttributes.length);
		for (int i = 0; i != pi_ClassAttributes.length; ++i) {
			fvClass.addElement(pi_ClassAttributes[i]);
		}
		Attribute aClass = new Attribute(CLASS_LABEL, fvClass);
	
		m_WekaAttributes.addElement(aClass);	
		
		m_TrainingSet = new Instances("", m_WekaAttributes, 0);            
		m_TrainingSet.setClassIndex(aClass.index());
		
		m_TestingSet = new Instances("", m_WekaAttributes, 0);            
		m_TestingSet.setClassIndex(aClass.index());
		
		// create an empty confusion matrix.. will be populated at evaluation time
		m_ConfusionMatrix = new double[0][0];
        m_bDoubleOnly = false;
	}
	
    
	public WekaLearner(FastVector attrsMerged, String[] attrlblMerged, String[] classList, Instances trainMerged) {
        m_AttributeLabels = attrlblMerged;
        m_ClassAttributes = classList;
        m_WekaAttributes = attrsMerged;
        m_TrainingSet = trainMerged;
        m_TestingSet = new Instances("", m_WekaAttributes, 0);            
        m_TestingSet.setClassIndex(m_WekaAttributes.size()-1);
    }

    /**
	 * Adds an instance in the train set.
	 * 
	 * @param pi_Instance	The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @param pi_Class		The class of this instance
	 * @see WekaAttribute
	 */
	public void AddTrainInstance(Object[] pi_Instance, String pi_Class) {
		m_TrainingSet.add(CreateInstance(pi_Instance, pi_Class));
	}


	public void AddUnlabeledTrainInstance(Object[] pi_Instance) {
		m_TrainingSet.add(CreateUnlabeledInstance(pi_Instance));
	}
    
	/**
	 * Adds an instance in the test set.
	 * 
	 * @param pi_Instance 	The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @param pi_Class 		The class of this instance
	 * @see WekaAttribute
	 */
	public void AddTestInstance(Object[] pi_Instance, String pi_Class) {
		m_TestingSet.add(CreateInstance(pi_Instance, pi_Class));
	}


    private Instance CreateInstance(Object[] pi_Instance, String pi_Class) {
        if (m_bDoubleOnly) {
            return CreateDoubleInstance(pi_Instance, pi_Class);
        } else {
            return CreateInstance2(pi_Instance, pi_Class);
        }
    }
    
    public void SetDoubleOnly(boolean pi_Flag) {
        m_bDoubleOnly = pi_Flag; // mega-optimization
    }
    
    private Instance CreateInstance2(Object[] pi_Instance, String pi_Class) {
        Instance inst = new Instance(pi_Instance.length+1);
        for (int i = 0; i != pi_Instance.length; ++i) {
            if (pi_Instance[i] instanceof Double) {
                inst.setValue((Attribute)m_WekaAttributes.elementAt(i), ((Double)pi_Instance[i]).doubleValue());      
            } else if (pi_Instance[i] instanceof String) {
                try {
                    inst.setValue((Attribute)m_WekaAttributes.elementAt(i), (String)pi_Instance[i]);
                } catch (Exception e) {
                    if (DEBUG) DebugInfo.Out("Unknow attribute for learner: " + (String)pi_Instance[i]);
                    inst.setMissing((Attribute)m_WekaAttributes.elementAt(i));
                }
                
            }
        }
        inst.setValue((Attribute)m_WekaAttributes.lastElement(), pi_Class);
        return inst;
    }
    
    private Instance CreateDoubleInstance(Object[] pi_Instance, String pi_Class) {
            double[] instval = new double[pi_Instance.length];
            for (int i = 0; i != pi_Instance.length; ++i) {
                instval[i] = ((Double)pi_Instance[i]).doubleValue();
            }
            Instance inst = new Instance(1, instval);
            inst.insertAttributeAt(inst.numAttributes());
            inst.setValue((Attribute)m_WekaAttributes.lastElement(), pi_Class);
		return inst;
	}
	
	public Instance CreateUnlabeledInstance(Object[] pi_Instance) {
		Instance inst = new Instance(pi_Instance.length+1);
		for (int i = 0; i != pi_Instance.length; ++i) {
		    if (pi_Instance[i] instanceof Double) {
				inst.setValue((Attribute)m_WekaAttributes.elementAt(i), ((Double)pi_Instance[i]).doubleValue());      
		    } else if (pi_Instance[i] instanceof String) {
		        try {
		            inst.setValue((Attribute)m_WekaAttributes.elementAt(i), (String)pi_Instance[i]);
		        } catch (Exception e) {
		            if (DEBUG) DebugInfo.Out("Unknow attribute for learner: " + (String)pi_Instance[i]);
		            inst.setMissing((Attribute)m_WekaAttributes.elementAt(i));
		        }
		        
		    }
		}
		inst.setMissing((Attribute)m_WekaAttributes.lastElement());
		return inst;
	}	

	/**
	 * Creates the classification model by learning from the training set.
	 * 
	 * @param pi_Classifier	The classification algorithm (from the Weka library, ex.: NaiveBayes, J48, ...)
	 */	
	public void CreateModel(Classifier pi_Classifier) {
		if(DEBUG) DebugInfo.Out("Training on " + m_TrainingSet.numInstances() + " instances");
		try {
			m_Scheme = pi_Classifier;
			m_Scheme.buildClassifier(m_TrainingSet);
		} catch (Exception e) {
		  	System.out.println(e.getMessage());
		}
	}

	/**
	 * Creates the cluster model by learning from the training set.
	 * 
	 * @param pi_Clusterer	The clusterization algorithm (from the Weka library, ex.: k-Mean, ...)
	 */		
	public void CreateModel(Clusterer pi_Clusterer) {
		if (DEBUG) DebugInfo.Out("Clustering " + m_TrainingSet.numInstances() + " instances");
		try {
			m_ClusterScheme = pi_Clusterer;
			m_ClusterScheme.buildClusterer(m_TrainingSet);			
		} catch (Exception e) {
		  	System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Approximate training set error.
	 * 
	 * @return evaluation module from which many types of errors are exposed (e.g.: mean absolute error)
	 */
	public Evaluation EstimateConfidence() {
		Evaluation evaluation = null;
		try {
			evaluation = new Evaluation(m_TrainingSet);
			evaluation.crossValidateModel(m_Scheme, m_TrainingSet, 10, new Random());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// which error is the best? depends on the application.
		return evaluation;
	}
	
	
	/**
	 * Test the learned model.
	 * 
	 * @return A summary string of the performance of the classifier
	 */
	public String TestModel() {
		if(DEBUG) DebugInfo.Out("Testing on " + m_TestingSet.numInstances() + " instances");
		Evaluation evaluation = null;
		try {
			evaluation = new Evaluation(m_TrainingSet);
			evaluation.evaluateModel(m_Scheme, m_TestingSet);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String strSummary = evaluation.toSummaryString();
		strSummary += "\n\nConfusion Matrix: \n\n";
		m_ConfusionMatrix = evaluation.confusionMatrix();
		for (int i = 0; i != m_ConfusionMatrix.length; ++i) {
		    for (int j = 0; j != m_ConfusionMatrix[i].length; ++j) {
		        strSummary += String.valueOf(m_ConfusionMatrix[i][j]) + "\t";
		    }
		    strSummary += "\n";
		}
		return strSummary;
	}
	
	/**
	 * Gets the confusion matrix that plots precision and recall for each class.
	 * 
	 * @return 2-dimension array (X=Ground thruth, Y=Guesses)
	 */
	public double[][] ConfusionMatrix() {
		return m_ConfusionMatrix;
	}
	
	/**
	 * Classify an unseen instance using the learned classifier.
	 * Output the most probable class.
	 * 
	 * @param pi_Instance The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @return The index of the most probable class
	 * @see WekaAttribute
	 */
	public double Classify(Object[] pi_Instance) {
		Instance inst = CreateInstance(pi_Instance);
		inst.setDataset(m_TrainingSet);
		double out = 0;
		try {
			out = m_Scheme.classifyInstance(inst);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return out;
	}
	
	/**
	 * Cluster an unseen instance using the learned clusterizer.
	 * Output the most probable cluster.
	 * 
	 * @param pi_Instance The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @return The index of the most probable cluster
	 * @see WekaAttribute
	 */
	public double Cluster(Object[] pi_Instance) {
		Instance inst = CreateInstance(pi_Instance);
		inst.setDataset(m_TrainingSet);
		double out = 0;
		try {
			out = m_ClusterScheme.clusterInstance(inst);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return out;
	}
	
	private Instance CreateInstance(Object[] pi_Instance) {
		Instance inst = new Instance(pi_Instance.length+1);
		for (int i = 0; i != pi_Instance.length; ++i) {
		    if (pi_Instance[i] instanceof Double) {
				inst.setValue((Attribute)m_WekaAttributes.elementAt(i), ((Double)pi_Instance[i]).doubleValue());      
		    } else if (pi_Instance[i] instanceof String) {
		    	try {
			    	inst.setValue((Attribute)m_WekaAttributes.elementAt(i), (String)pi_Instance[i]);
		    	} catch (Exception e) {
		    		if (DEBUG) DebugInfo.Out("Unknow attribute for learner: " + (String)pi_Instance[i]);
		    		inst.setMissing((Attribute)m_WekaAttributes.elementAt(i));
		    	}
	        }
		}
		return inst;
	}
	

	/**
	 * Classify an unseen instance using the learned classifier.
	 * Ouput the likelihood of each class.
	 * 
	 * @param pi_Instance The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @return An array of probabilities, parallel to the possible classes
	 * @see WekaAttribute
	 */
	public double[] GetDistribution(Object[] pi_Instance) {
		Instance inst = new Instance(pi_Instance.length+1);
		for (int i = 0; i != pi_Instance.length; ++i) {
		    if (pi_Instance[i] instanceof Double) {
				inst.setValue((Attribute)m_WekaAttributes.elementAt(i), ((Double)pi_Instance[i]).doubleValue());      
		    } else if (pi_Instance[i] instanceof String) {
				try {
			    	inst.setValue((Attribute)m_WekaAttributes.elementAt(i), (String)pi_Instance[i]);      
				} catch (Exception e) {
		    		if (DEBUG) DebugInfo.Out("Unknow attribute for learner: " + (String)pi_Instance[i]);
		    		inst.setMissing((Attribute)m_WekaAttributes.elementAt(i));
		    	}
		    }		
		}
		inst.setDataset(m_TrainingSet);
		double fDistribution[] = null;
		try {
		    fDistribution = m_Scheme.distributionForInstance(inst);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fDistribution;
	}
	
	/**
	 * Classify an unseen instance using the learned classifier.
	 * Get the likelihood of a given class.
	 * 
	 * @param pi_Instance			The instance, an array of objects (can mix numeric and nominal attributes - see {@link WekaAttribute})
	 * @param pi_PositiveClassIndex	The index of a class
	 * @return the likelihood of the given class
	 * @see WekaAttribute
	 */
	public double Likelihood(Object[] pi_Instance, int pi_PositiveClassIndex) {
	    
	    double fDistribution[] = GetDistribution(pi_Instance);
		
		double fSum = 0.0;
		for (int i = 0; i != fDistribution.length; ++i) {
		    fSum += fDistribution[i];
		}
		
		return fDistribution[pi_PositiveClassIndex] / fSum;	    
	}

	/**
	 * Gets the list of attributes.
	 * 
	 * @return String array
	 */
	public String[] GetAttributeList() {
		return m_AttributeLabels;
	}
    public FastVector GetAttribute() {
        return m_WekaAttributes;
    }
	
	/**
	 * Gets the list of classes.
	 * 
	 * @return String array
	 */
	public String[] GetClassList() {
		return m_ClassAttributes;
	}	
	
	protected Instances GetTrainInstances() {
		return m_TrainingSet;
	}
	protected Instances GetTestInstances() {
		return m_TestingSet;
	}
	
	/**
     * Get the training instances
	 * @return training instances
	 */
	public Instances GetTrainingSet() {
		return m_TrainingSet;
	}
    /**
     * Get the training instances
     * @return training instances
     */
    public Instances GetTestingSet() {
        return m_TestingSet;
    }
	
	/**
     * Get the number of training instances
	 * @return training set size
	 */
	public int GetTrainingSetSize() {
	    return m_TrainingSet.numInstances();
	}
    
    public Instances GetClusterCentroid() {
        if (m_ClusterScheme instanceof SimpleKMeans) {
            return ((SimpleKMeans)m_ClusterScheme).getClusterCentroids();
        } else {
            throw new Error("Only available for SimpleKMeans clusterer");
        }
    }
    
	/**
	 * Reduces the size of a classifier by deleting the corpora.
	 * Only keep the learned model and information on classes and attributes.
	 */
	public void Shrink() {
	    m_TrainingSet.delete();
	    m_TestingSet.delete();
	}
    
	private String[] 	m_AttributeLabels;
	private String[]	m_ClassAttributes;
	private FastVector  m_WekaAttributes;
	private Instances	m_TrainingSet;
	private Instances	m_TestingSet;
	private Classifier	m_Scheme;
	private Clusterer 	m_ClusterScheme;
	private double[][]	m_ConfusionMatrix;
    private boolean     m_bDoubleOnly;

	/**
	 * Test routine
	 * 
	 * @param args
	 */
	public static void main(String[] args) {}




}
