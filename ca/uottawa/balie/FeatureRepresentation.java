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
 * File created on 07-03-20
 */
package ca.uottawa.balie;

import java.io.Serializable;
import weka.core.FastVector;
import ca.uottawa.balie.WekaAttribute;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public interface FeatureRepresentation extends Serializable {
    
    public WekaAttribute[] GetWekaAttributes(FeatureRepresentationVocabulary pi_Vocabulary, int pi_MinFreq, int pi_ContextSize);

    public Object[] GetWekaInstance(String[] pi_Inst, FastVector pi_Attrbutes, int pi_MinFreq);

    public String Name();

    public boolean DoubleOnly();
    
}
