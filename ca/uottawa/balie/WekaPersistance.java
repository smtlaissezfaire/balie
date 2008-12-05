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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;

import weka.core.converters.ArffSaver;

/**
 * Service class to load and save Weka models.
 * Also offers a method to ouput training and testing corpus in proprietary Weka format (.arff)
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class WekaPersistance {
	
    private WekaPersistance() {}
    
    /**
     * Saves a model to disk.
     * 	
     * @param pi_Model		The model
     * @param pi_FileName	The file where to save
     */
	public static void Save(WekaLearner pi_Model, String pi_FileName) {
		try {  
			FileOutputStream fos = new FileOutputStream(pi_FileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos); 
			ObjectOutputStream p = new ObjectOutputStream(bos);
			p.writeObject(pi_Model);
			p.flush();
			p.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Loads a model from disk.
	 * 
	 * @param pi_FileName	The file to load from
	 * @return	The model
	 */
	 public static WekaLearner Load(String pi_FileName) {  
		WekaLearner wl = null;
		if (pi_FileName != null) {
			try {  
				InputStream in = WekaPersistance.class.getClassLoader().getResourceAsStream(pi_FileName);
				wl = Load(in);
			} catch (Exception e) {	
				System.out.println(e.getMessage());
			}
		}
		return wl;
	 }
     
     public static WekaLearner LoadAbs(String pi_FileName) {  
         WekaLearner wl = null;
         if (pi_FileName != null) {
             try {  
                 InputStream in = new FileInputStream(pi_FileName);
                 wl = Load(in);
             } catch (Exception e) { 
                 System.out.println(e.getMessage());
             }
         }
         return wl;
    }    
	
   /**
    * Loads a model from disk.
    * 
    * @param pi_InputStream The input tream to load from
    * @return  The model
    */
	private static WekaLearner Load(InputStream pi_InputStream) {  
		WekaLearner model = null;
		if (pi_InputStream != null) {
			try {  
                BufferedInputStream bis = new BufferedInputStream(pi_InputStream); 
				ObjectInputStream p = new ObjectInputStream(bis);
				model = (WekaLearner)p.readObject();
			} catch (Exception e) {  
				System.out.println(e.getMessage());
			}
		}
		return model;
	}

   
    public static final int PRINT_TRAINING_SET = 0;
	public static final int PRINT_TESTING_SET  = 1;
	
	/**
	 * Write an .arff file to disk
	 * 
	 * @param pi_wl			The model
	 * @param pi_filename	The file where to save
	 * @param pi_Set		Flag indicating which set to save (training or testing)
	 */
	public static void PrintToArffFile(WekaLearner pi_wl, String pi_filename, int pi_Set) {
		ArffSaver as = new ArffSaver();
		try {
			File fOut = new File(pi_filename);
			as.setFile(fOut);
			if (pi_Set == PRINT_TRAINING_SET) {
				as.setInstances(pi_wl.GetTrainInstances());
			} else if (pi_Set == PRINT_TESTING_SET) {
				as.setInstances(pi_wl.GetTestInstances());
			} else {
				throw new Error("You must specify whether to output training or testing set.");
			}
			as.writeBatch();
		} catch (Exception e) {
			throw new Error("Unable to output the weka' arff file");
		}		
	}
}
