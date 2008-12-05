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
 * Created on May 6, 2004
 */
package ca.uottawa.balie;

import java.util.Hashtable;


/**
 * Routines specific to French language.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LanguageSpecificFrench extends LanguageSpecific {

    /**
     * Contruct library of French specific data and routines
     */
    public LanguageSpecificFrench() {
    }
    
	/* (non-Javadoc)
	 * @see ca.uottawa.balie.LanguageSpecific#Decompound(java.lang.String)
	 */
	public String[] Decompound(String pi_Composed) {

        pi_Composed = CommonFilter(pi_Composed);
        String[] parts = pi_Composed.split(" ");
        StringBuffer sb = new StringBuffer(); 
        // Iterate through decomposed
        for (int i = 0; i != parts.length; ++i) {
            // decompose apostrophe, except for the word aujourd'hui and proper names O'xxxx (e.g.: O'Connor)
            if (parts[i].indexOf("'") != -1 && !parts[i].equals("aujourd'hui") && !parts[i].startsWith("O'")) {
                sb.append(parts[i].replaceAll("'", " ' "));       
                sb.append(" ");
            } else {
                sb.append(parts[i]);
                sb.append(" ");
            }
        }
        return sb.toString().split(" "); // do not trim, it would removed important \n
	}

	/* (non-Javadoc)
	 * @see ca.uottawa.balie.LanguageSpecific#GetAbbreviations()
	 */
	public Hashtable<String, Integer> GetAbbreviations() {
		Hashtable<String, Integer> hAbbreviations = new Hashtable<String, Integer>();
		Integer i = new Integer(0);
		hAbbreviations.put("acad", 	    i);
		hAbbreviations.put("ad", 	    i);
		hAbbreviations.put("adm", 	    i);
		hAbbreviations.put("aka", 	    i);
		hAbbreviations.put("al", 	    i);
		hAbbreviations.put("am", 	    i);
		hAbbreviations.put("appt", 	    i);
		hAbbreviations.put("ardt", 	    i);
		hAbbreviations.put("av", 	    i);
		hAbbreviations.put("avr", 	    i);
		hAbbreviations.put("ba", 	    i);
		hAbbreviations.put("bc", 	    i);
		hAbbreviations.put("bd", 	    i);
		hAbbreviations.put("blvd", 	    i);
		hAbbreviations.put("bsc", 	    i);
		hAbbreviations.put("cap", 	    i);
		hAbbreviations.put("cdn", 	    i);
		hAbbreviations.put("ceo", 	    i);
		hAbbreviations.put("cf", 	    i);
		hAbbreviations.put("c", 	    i);
		hAbbreviations.put("cial", 	    i);
		hAbbreviations.put("cm", 	    i);
		hAbbreviations.put("cmdr", 	    i);
		hAbbreviations.put("c/o", 	    i);
		hAbbreviations.put("co", 	    i);
		hAbbreviations.put("col", 	    i);
		hAbbreviations.put("com", 	    i);
		hAbbreviations.put("comdr", 	    i);
		hAbbreviations.put("comdt", 	    i);
		hAbbreviations.put("corp", 	    i);
		hAbbreviations.put("dec", 	    i);
		hAbbreviations.put("déc", 	    i);
		hAbbreviations.put("deg", 	    i);
		hAbbreviations.put("dept", 	    i);
		hAbbreviations.put("depts", 	    i);
		hAbbreviations.put("desc", 	    i);
		hAbbreviations.put("dim", 	    i);
		hAbbreviations.put("dir", 	    i);
		hAbbreviations.put("dr", 	    i);
		hAbbreviations.put("drs", 	    i);
		hAbbreviations.put("ed", 	    i);
		hAbbreviations.put("eds", 	    i);
		hAbbreviations.put("edt", 	    i);
		hAbbreviations.put("edu", 	    i);
		hAbbreviations.put("eg", 	    i);
		hAbbreviations.put("eng", 	    i);
		hAbbreviations.put("engr", 	    i);
		hAbbreviations.put("est", 	    i);
		hAbbreviations.put("et", 	    i);
		hAbbreviations.put("etc", 	    i);
		hAbbreviations.put("ex", 	    i);
		hAbbreviations.put("ext", 	    i);
		hAbbreviations.put("f", 	    i);
		hAbbreviations.put("fax", 	    i);
		hAbbreviations.put("fév", 	    i);
		hAbbreviations.put("févr", 	    i);
		hAbbreviations.put("fig", 	    i);
		hAbbreviations.put("figs", 	    i);
		hAbbreviations.put("fwd", 	    i);
		hAbbreviations.put("fyi", 	    i);
		hAbbreviations.put("g", 	    i);
		hAbbreviations.put("gb/s", 	    i);
		hAbbreviations.put("gmt", 	    i);
		hAbbreviations.put("ha", 	    i);
		hAbbreviations.put("hon", 	    i);
		hAbbreviations.put("hr", 	    i);
		hAbbreviations.put("hrs", 	    i);
		hAbbreviations.put("i", 	    i);
		hAbbreviations.put("ie", 	    i);
		hAbbreviations.put("ii", 	    i);
		hAbbreviations.put("iii", 	    i);
		hAbbreviations.put("inc", 	    i);
		hAbbreviations.put("ind", 	    i);
		hAbbreviations.put("ing", 	    i);
		hAbbreviations.put("info", 	    i);
		hAbbreviations.put("isbn", 	    i);
		hAbbreviations.put("iv", 	    i);
		hAbbreviations.put("ix", 	    i);
		hAbbreviations.put("jan", 	    i);
		hAbbreviations.put("janv", 	    i);
		hAbbreviations.put("jeu", 	    i);
		hAbbreviations.put("juil", 	    i);
		hAbbreviations.put("kb/s", 	    i);
		hAbbreviations.put("kg", 	    i);
		hAbbreviations.put("kgs", 	    i);
		hAbbreviations.put("km", 	    i);
		hAbbreviations.put("ko", 	    i);
		hAbbreviations.put("lb", 	    i);
		hAbbreviations.put("lbs", 	    i);
		hAbbreviations.put("lieut", 	    i);
		hAbbreviations.put("lt", 	    i);
		hAbbreviations.put("ltd", 	    i);
		hAbbreviations.put("lun", 	    i);
		hAbbreviations.put("m", 	    i);
		hAbbreviations.put("ma", 	    i);
		hAbbreviations.put("mar", 	    i);
		hAbbreviations.put("max", 	    i);
		hAbbreviations.put("me", 	    i);
		hAbbreviations.put("meg", 	    i);
		hAbbreviations.put("merc", 	    i);
		hAbbreviations.put("messrs", 	    i);
		hAbbreviations.put("mg", 	    i);
		hAbbreviations.put("mgr", 	    i);
		hAbbreviations.put("mhz", 	    i);
		hAbbreviations.put("min", 	    i);
		hAbbreviations.put("mins", 	    i);
		hAbbreviations.put("mlle", 	    i);
		hAbbreviations.put("mlles", 	    i);
		hAbbreviations.put("mm", 	    i);
		hAbbreviations.put("mme", 	    i);
		hAbbreviations.put("mmes", 	    i);
		hAbbreviations.put("mr", 	    i);
		hAbbreviations.put("mrs", 	    i);
		hAbbreviations.put("ms", 	    i);
		hAbbreviations.put("msec", 	    i);
		hAbbreviations.put("msecs", 	    i);
		hAbbreviations.put("msc", 	    i);
		hAbbreviations.put("n/a", 	    i);
		hAbbreviations.put("natl", 	    i);
		hAbbreviations.put("nb", 	    i);
		hAbbreviations.put("no", 	    i);
		hAbbreviations.put("nov", 	    i);
		hAbbreviations.put("novem", 	    i);
		hAbbreviations.put("oct", 	    i);
		hAbbreviations.put("p", 	    i);
		hAbbreviations.put("pm", 	    i);
		hAbbreviations.put("pp", 	    i);
		hAbbreviations.put("pps", 	    i);
		hAbbreviations.put("phd", 	    i);
		hAbbreviations.put("pl", 	    i);
		hAbbreviations.put("pres", 	    i);
		hAbbreviations.put("prés", 	    i);
		hAbbreviations.put("prof", 	    i);
		hAbbreviations.put("profs", 	    i);
		hAbbreviations.put("ps", 	    i);
		hAbbreviations.put("pub", 	    i);
		hAbbreviations.put("r", 	    i);
		hAbbreviations.put("re", 	    i);
		hAbbreviations.put("rech", 	    i);
		hAbbreviations.put("ref", 	    i);
		hAbbreviations.put("rel", 	    i);
		hAbbreviations.put("rep", 	    i);
		hAbbreviations.put("rept", 	    i);
		hAbbreviations.put("rev", 	    i);
		hAbbreviations.put("revd", 	    i);
		hAbbreviations.put("revds", 	    i);
		hAbbreviations.put("rtfm", 	    i);
		hAbbreviations.put("rstp", 	    i);
		hAbbreviations.put("rsvp", 	    i);
		hAbbreviations.put("sam", 	    i);
		hAbbreviations.put("sec", 	    i);
		hAbbreviations.put("secs", 	    i);
		hAbbreviations.put("secy", 	    i);
		hAbbreviations.put("sen", 	    i);
		hAbbreviations.put("sens", 	    i);
		hAbbreviations.put("sep", 	    i);
		hAbbreviations.put("sept", 	    i);
		hAbbreviations.put("sgt", 	    i);
		hAbbreviations.put("st", 	    i);
		hAbbreviations.put("ste", 	    i);
		hAbbreviations.put("sté", 	    i);
		hAbbreviations.put("stp", 	    i);
		hAbbreviations.put("svp", 	    i);
		hAbbreviations.put("tbsp", 	    i);
		hAbbreviations.put("tbsps", 	    i);
		hAbbreviations.put("tech", 	    i);
		hAbbreviations.put("tel", 	    i);
		hAbbreviations.put("tél", 	    i);
		hAbbreviations.put("tko", 	    i);
		hAbbreviations.put("tsp", 	    i);
		hAbbreviations.put("tsps", 	    i);
		hAbbreviations.put("txt", 	    i);
		hAbbreviations.put("tv", 	    i);
		hAbbreviations.put("u", 	    i);
		hAbbreviations.put("univ", 	    i);
		hAbbreviations.put("usa", 	    i);
		hAbbreviations.put("v", 	    i);
		hAbbreviations.put("ven", 	    i);
		hAbbreviations.put("vend", 	    i);
		hAbbreviations.put("vi", 	    i);
		hAbbreviations.put("vii", 	    i);
		hAbbreviations.put("viii", 	    i);
		hAbbreviations.put("vol", 	    i);
		hAbbreviations.put("vols", 	    i);
		hAbbreviations.put("www", 	    i);
		hAbbreviations.put("x", 	    i);
		hAbbreviations.put("xi", 	    i);
		hAbbreviations.put("xii", 	    i);
		hAbbreviations.put("xiii", 	    i);
		hAbbreviations.put("xiv", 	    i);
		hAbbreviations.put("xv", 	    i);
		hAbbreviations.put("xx", 	    i);
		return hAbbreviations;
	}

	
}
