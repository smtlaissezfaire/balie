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
 * Routines specific to German language.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class LanguageSpecificGerman extends LanguageSpecific {

    /**
     * Contruct library of German specific data and routines
     */
    public LanguageSpecificGerman() {
    }
    
    /* (non-Javadoc)
     * @see ca.uottawa.balie.LanguageSpecific#Decompound(java.lang.String)
     */
    public String[] Decompound(String pi_Composed) {
	    pi_Composed = CommonFilter(pi_Composed);	
       // TODO: should decompound German words...
		return pi_Composed.split(" ");
    }
    
	/* (non-Javadoc)
	 * @see ca.uottawa.balie.LanguageSpecific#GetAbbreviations()
	 */
	public Hashtable<String, Integer> GetAbbreviations() {
		Hashtable<String, Integer> hAbbreviations = new Hashtable<String, Integer>();
		Integer i = new Integer(0);
		hAbbreviations.put("abb", 	    i);
		hAbbreviations.put("abbn", 	    i);
		hAbbreviations.put("acad", 	    i);
		hAbbreviations.put("ad", 	    i);
		hAbbreviations.put("aeg", 	    i);
		hAbbreviations.put("ag", 	    i);
		hAbbreviations.put("aka", 	    i);
		hAbbreviations.put("akw", 	    i);
		hAbbreviations.put("al", 	    i);
		hAbbreviations.put("am", 	    i);
		hAbbreviations.put("aok", 	    i);
		hAbbreviations.put("apr", 	    i);
		hAbbreviations.put("apri", 	    i);
		hAbbreviations.put("ard", 	    i);
		hAbbreviations.put("aug", 	    i);
		hAbbreviations.put("augu", 	    i);
		hAbbreviations.put("ba", 	    i);
		hAbbreviations.put("bc", 	    i);
		hAbbreviations.put("bka", 	    i);
		hAbbreviations.put("blvd", 	    i);
		hAbbreviations.put("brd", 	    i);
		hAbbreviations.put("bsc", 	    i);
		hAbbreviations.put("btw", 	    i);
		hAbbreviations.put("bw", 	    i);
		hAbbreviations.put("bwl", 	    i);
		hAbbreviations.put("bzgl", 	    i);
		hAbbreviations.put("bzw", 	    i);
		hAbbreviations.put("cand", 	    i);
		hAbbreviations.put("cdn", 	    i);
		hAbbreviations.put("ceo", 	    i);
		hAbbreviations.put("cf", 	    i);
		hAbbreviations.put("c", 	    i);
		hAbbreviations.put("ca", 	    i);
		hAbbreviations.put("cm", 	    i);
		hAbbreviations.put("c/o", 	    i);
		hAbbreviations.put("co", 	    i);
		hAbbreviations.put("col", 	    i);
		hAbbreviations.put("com", 	    i);
		hAbbreviations.put("corp", 	    i);
		hAbbreviations.put("d", 	    i);
		hAbbreviations.put("ddr", 	    i);
		hAbbreviations.put("deg", 	    i);
		hAbbreviations.put("dept", 	    i);
		hAbbreviations.put("depts", 	    i);
		hAbbreviations.put("desc", 	    i);
		hAbbreviations.put("dez", 	    i);
		hAbbreviations.put("deze", 	    i);
		hAbbreviations.put("dh", 	    i);
		hAbbreviations.put("di", 	    i);
		hAbbreviations.put("dipl", 	    i);
		hAbbreviations.put("dipl-inf", 	    i);
		hAbbreviations.put("dipl-inform", 	    i);
		hAbbreviations.put("dipl-ing", 	    i);
		hAbbreviations.put("dir", 	    i);
		hAbbreviations.put("dpa", 	    i);
		hAbbreviations.put("dpd", 	    i);
		hAbbreviations.put("dr", 	    i);
		hAbbreviations.put("dr-ing", 	    i);
		hAbbreviations.put("drk", 	    i);
		hAbbreviations.put("drs", 	    i);
		hAbbreviations.put("dvu", 	    i);
		hAbbreviations.put("ed", 	    i);
		hAbbreviations.put("eds", 	    i);
		hAbbreviations.put("edt", 	    i);
		hAbbreviations.put("edu", 	    i);
		hAbbreviations.put("edv", 	    i);
		hAbbreviations.put("eg", 	    i);
		hAbbreviations.put("ekg", 	    i);
		hAbbreviations.put("ekz", 	    i);
		hAbbreviations.put("eng", 	    i);
		hAbbreviations.put("engr", 	    i);
		hAbbreviations.put("entw", 	    i);
		hAbbreviations.put("est", 	    i);
		hAbbreviations.put("et", 	    i);
		hAbbreviations.put("etc", 	    i);
		hAbbreviations.put("ev", 	    i);
		hAbbreviations.put("ext", 	    i);
		hAbbreviations.put("fax", 	    i);
		hAbbreviations.put("faz", 	    i);
		hAbbreviations.put("fdj", 	    i);
		hAbbreviations.put("feb", 	    i);
		hAbbreviations.put("febr", 	    i);
		hAbbreviations.put("fpd", 	    i);
		hAbbreviations.put("fig", 	    i);
		hAbbreviations.put("figs", 	    i);
		hAbbreviations.put("forsch", 	    i);
		hAbbreviations.put("fsk", 	    i);
		hAbbreviations.put("fwd", 	    i);
		hAbbreviations.put("fyi", 	    i);
		hAbbreviations.put("g", 	    i);
		hAbbreviations.put("gbr", 	    i);
		hAbbreviations.put("gb/s", 	    i);
		hAbbreviations.put("geb", 	    i);
		hAbbreviations.put("gl", 	    i);
		hAbbreviations.put("gln", 	    i);
		hAbbreviations.put("gmbh", 	    i);
		hAbbreviations.put("gmt", 	    i);
		hAbbreviations.put("h", 	    i);
		hAbbreviations.put("habil", 	    i);
		hAbbreviations.put("hno", 	    i);
		hAbbreviations.put("hr", 	    i);
		hAbbreviations.put("hrs", 	    i);
		hAbbreviations.put("i", 	    i);
		hAbbreviations.put("ice", 	    i);
		hAbbreviations.put("ie", 	    i);
		hAbbreviations.put("ihk", 	    i);
		hAbbreviations.put("ii", 	    i);
		hAbbreviations.put("iii", 	    i);
		hAbbreviations.put("inc", 	    i);
		hAbbreviations.put("inf", 	    i);
		hAbbreviations.put("info", 	    i);
		hAbbreviations.put("inform", 	    i);
		hAbbreviations.put("ing", 	    i);
		hAbbreviations.put("isbn", 	    i);
		hAbbreviations.put("iv", 	    i);
		hAbbreviations.put("ix", 	    i);
		hAbbreviations.put("jan", 	    i);
		hAbbreviations.put("janu", 	    i);
		hAbbreviations.put("jul", 	    i);
		hAbbreviations.put("jun", 	    i);
		hAbbreviations.put("kand", 	    i);
		hAbbreviations.put("kap", 	    i);
		hAbbreviations.put("kb/s", 	    i);
		hAbbreviations.put("kdw", 	    i);
		hAbbreviations.put("kg", 	    i);
		hAbbreviations.put("kgs", 	    i);
		hAbbreviations.put("km", 	    i);
		hAbbreviations.put("kmh", 	    i);
		hAbbreviations.put("lb", 	    i);
		hAbbreviations.put("lbs", 	    i);
		hAbbreviations.put("ltd", 	    i);
		hAbbreviations.put("ltu", 	    i);
		hAbbreviations.put("m", 	    i);
		hAbbreviations.put("ma", 	    i);
		hAbbreviations.put("mai", 	    i);
		hAbbreviations.put("mär", 	    i);
		hAbbreviations.put("max", 	    i);
		hAbbreviations.put("me", 	    i);
		hAbbreviations.put("mech", 	    i);
		hAbbreviations.put("med", 	    i);
		hAbbreviations.put("meg", 	    i);
		hAbbreviations.put("mfg", 	    i);
		hAbbreviations.put("mg", 	    i);
		hAbbreviations.put("mhz", 	    i);
		hAbbreviations.put("min", 	    i);
		hAbbreviations.put("mins", 	    i);
		hAbbreviations.put("mlle", 	    i);
		hAbbreviations.put("mme", 	    i);
		hAbbreviations.put("mr", 	    i);
		hAbbreviations.put("mrs", 	    i);
		hAbbreviations.put("msec", 	    i);
		hAbbreviations.put("msecs", 	    i);
		hAbbreviations.put("msc", 	    i);
		hAbbreviations.put("n/a", 	    i);
		hAbbreviations.put("nat", 	    i);
		hAbbreviations.put("natl", 	    i);
		hAbbreviations.put("nm", 	    i);
		hAbbreviations.put("no", 	    i);
		hAbbreviations.put("nov", 	    i);
		hAbbreviations.put("okt", 	    i);
		hAbbreviations.put("okto", 	    i);
		hAbbreviations.put("p", 	    i);
		hAbbreviations.put("phil", 	    i);
		hAbbreviations.put("pm", 	    i);
		hAbbreviations.put("pp", 	    i);
		hAbbreviations.put("pps", 	    i);
		hAbbreviations.put("phd", 	    i);
		hAbbreviations.put("phys", 	    i);
		hAbbreviations.put("pls", 	    i);
		hAbbreviations.put("prof", 	    i);
		hAbbreviations.put("ps", 	    i);
		hAbbreviations.put("pub", 	    i);
		hAbbreviations.put("re", 	    i);
		hAbbreviations.put("rel", 	    i);
		hAbbreviations.put("rep", 	    i);
		hAbbreviations.put("rept", 	    i);
		hAbbreviations.put("rer", 	    i);
		hAbbreviations.put("rev", 	    i);
		hAbbreviations.put("rtfm", 	    i);
		hAbbreviations.put("rsvp", 	    i);
		hAbbreviations.put("sc", 	    i);
		hAbbreviations.put("sec", 	    i);
		hAbbreviations.put("secs", 	    i);
		hAbbreviations.put("sep", 	    i);
		hAbbreviations.put("sept", 	    i);
		hAbbreviations.put("st", 	    i);
		hAbbreviations.put("tbsp", 	    i);
		hAbbreviations.put("tbsps", 	    i);
		hAbbreviations.put("tech", 	    i);
		hAbbreviations.put("techn", 	    i);
		hAbbreviations.put("tel", 	    i);
		hAbbreviations.put("tsp", 	    i);
		hAbbreviations.put("tsps", 	    i);
		hAbbreviations.put("txt", 	    i);
		hAbbreviations.put("tv", 	    i);
		hAbbreviations.put("u", 	    i);
		hAbbreviations.put("ua", 	    i);
		hAbbreviations.put("uam", 	    i);
		hAbbreviations.put("uawg", 	    i);
		hAbbreviations.put("univ", 	    i);
		hAbbreviations.put("usa", 	    i);
		hAbbreviations.put("usf", 	    i);
		hAbbreviations.put("usw", 	    i);
		hAbbreviations.put("uva", 	    i);
		hAbbreviations.put("uvam", 	    i);
		hAbbreviations.put("v", 	    i);
		hAbbreviations.put("vgl", 	    i);
		hAbbreviations.put("vh", 	    i);
		hAbbreviations.put("vi", 	    i);
		hAbbreviations.put("vii", 	    i);
		hAbbreviations.put("viii", 	    i);
		hAbbreviations.put("vol", 	    i);
		hAbbreviations.put("vols", 	    i);
		hAbbreviations.put("wrt", 	    i);
		hAbbreviations.put("www", 	    i);
		hAbbreviations.put("x", 	    i);
		hAbbreviations.put("xi", 	    i);
		hAbbreviations.put("xii", 	    i);
		hAbbreviations.put("xiii", 	    i);
		hAbbreviations.put("xiv", 	    i);
		hAbbreviations.put("xv", 	    i);
		hAbbreviations.put("xx", 	    i);
		hAbbreviations.put("yr", 	    i);
		hAbbreviations.put("yrs", 	    i);
		hAbbreviations.put("zb", 	    i);
		hAbbreviations.put("zbv", 	    i);
		hAbbreviations.put("zdf", 	    i);
		hAbbreviations.put("zt", 	    i);
		hAbbreviations.put("ztr", 	    i);
		return hAbbreviations;
	}
	
}
