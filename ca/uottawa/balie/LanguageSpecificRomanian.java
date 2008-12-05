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
 * Created on Dec 29, 2004
 */
package ca.uottawa.balie;

import java.util.Hashtable;

/**
 * Routines specific to Romanian language.
 * 
 * @author Onutz
 */

public class LanguageSpecificRomanian extends LanguageSpecific {

    /**
     * Contruct library of Romanian specific data and routines
     */
    public LanguageSpecificRomanian() {
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
            //decompose everything on "/"
            if (parts[i].indexOf("/") != -1) {
                sb.append(parts[i].replaceAll("\\/", " \\/ "));
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
		hAbbreviations.put("a", i);
		hAbbreviations.put("ab", i);
		hAbbreviations.put("abr", i);
		hAbbreviations.put("absol", i);
		hAbbreviations.put("ac", i);
		hAbbreviations.put("acc", i);
		hAbbreviations.put("a.c", i);
		hAbbreviations.put("acad", i);
		hAbbreviations.put("ag", i);
		hAbbreviations.put("agr", i);
		hAbbreviations.put("ah", i);
		hAbbreviations.put("al", i);
		hAbbreviations.put("abs", i);
		hAbbreviations.put("ac", i);
		hAbbreviations.put("acuz", i);
		hAbbreviations.put("act", i);
		hAbbreviations.put("adj", i);
		hAbbreviations.put("adv", i);
		hAbbreviations.put("aero", i);
		hAbbreviations.put("afirm", i);
		hAbbreviations.put("a.m", i);
		hAbbreviations.put("anat", i);
		hAbbreviations.put("ap", i);
		hAbbreviations.put("api", i);
		hAbbreviations.put("apr", i);
		hAbbreviations.put("apoz", i);
		hAbbreviations.put("aprop", i);
		hAbbreviations.put("ar", i);
		hAbbreviations.put("arh", i);
		hAbbreviations.put("arhit", i);
		hAbbreviations.put("art", i);
		hAbbreviations.put("alin", i);
		hAbbreviations.put("atr", i);
		hAbbreviations.put("asist", i);
		hAbbreviations.put("astro", i);
		hAbbreviations.put("aug", i);
		hAbbreviations.put("bc", i);
		hAbbreviations.put("bd", i);
		hAbbreviations.put("bh", i);
		hAbbreviations.put("bio", i);
		hAbbreviations.put("bis", i);
		hAbbreviations.put("bot", i);
		hAbbreviations.put("bn", i);
		hAbbreviations.put("bt", i);
		hAbbreviations.put("bv", i);
		hAbbreviations.put("bu", i);
		hAbbreviations.put("br", i);
		hAbbreviations.put("bz", i);
		hAbbreviations.put("card", i);
		hAbbreviations.put("c.ag", i);
		hAbbreviations.put("cap", i);
		hAbbreviations.put("cca", i);
		hAbbreviations.put("c.c", i);
		hAbbreviations.put("c.c.cz", i);
		hAbbreviations.put("ccumul", i);
		hAbbreviations.put("ccumul", i);
		hAbbreviations.put("c.dir", i);
		hAbbreviations.put("cd", i);
		hAbbreviations.put("c.c", i);
		hAbbreviations.put("cexc", i);
		hAbbreviations.put("cal", i);
		hAbbreviations.put("cf", i);
		hAbbreviations.put("cg", i);
		hAbbreviations.put("chim", i);
		hAbbreviations.put("c.ind", i);
		hAbbreviations.put("cine", i);
		hAbbreviations.put("ci", i);
		hAbbreviations.put("cit", i);
		hAbbreviations.put("cinstr", i);
		hAbbreviations.put("cj", i);
		hAbbreviations.put("cl", i);
		hAbbreviations.put("cm", i);
		hAbbreviations.put("cm2", i);
		hAbbreviations.put("cm3", i);
		hAbbreviations.put("cns", i);
		hAbbreviations.put("copoz", i);
		hAbbreviations.put("co", i);
		hAbbreviations.put("com", i);
		hAbbreviations.put("comp", i);
		hAbbreviations.put("concr", i);
		hAbbreviations.put("conf", i);
		hAbbreviations.put("conj", i);
		hAbbreviations.put("cont", i);
		hAbbreviations.put("constr", i);
		hAbbreviations.put("col", i);
		hAbbreviations.put("cor", i);
		hAbbreviations.put("cores", i);
		hAbbreviations.put("cos", i);
		hAbbreviations.put("cosec", i);
		hAbbreviations.put("cosmo", i);
		hAbbreviations.put("cot", i);
		hAbbreviations.put("cotg", i);
		hAbbreviations.put("cp", i);
		hAbbreviations.put("cpt", i);
		hAbbreviations.put("cr", i);
		hAbbreviations.put("cresc", i);
		hAbbreviations.put("c.c.conces", i);
		hAbbreviations.put("conjug", i);
		hAbbreviations.put("conj", i);
		hAbbreviations.put("conjunct", i);
		hAbbreviations.put("cond", i);
		hAbbreviations.put("cond-opt", i);
		hAbbreviations.put("consec", i);
		hAbbreviations.put("contrag", i);
		hAbbreviations.put("cl", i);
		hAbbreviations.put("crel", i);
		hAbbreviations.put("cv", i);
		hAbbreviations.put("cvs", i);
		hAbbreviations.put("cuv", i);
		hAbbreviations.put("c.c.sociat", i);
		hAbbreviations.put("csociat", i);
		hAbbreviations.put("c.c.scop", i);
		hAbbreviations.put("c.c.timp", i);
		hAbbreviations.put("cs", i);
		hAbbreviations.put("ct", i);
		hAbbreviations.put("cz", i);
		hAbbreviations.put("d", i);
		hAbbreviations.put("d.a", i);
		hAbbreviations.put("dag", i);
		hAbbreviations.put("dal", i);
		hAbbreviations.put("dam", i);
		hAbbreviations.put("dat", i);
		hAbbreviations.put("das", i);
		hAbbreviations.put("db", i);
		hAbbreviations.put("d.c", i);
		hAbbreviations.put("d.e", i);
		hAbbreviations.put("dec", i);
		hAbbreviations.put("depr", i);
		hAbbreviations.put("dial", i);
		hAbbreviations.put("dj", i);
		hAbbreviations.put("d-ei", i);
		hAbbreviations.put("ex", i);
		hAbbreviations.put("den", i);
		hAbbreviations.put("dh", i);
		hAbbreviations.put("dim", i);
		hAbbreviations.put("dl", i);
		hAbbreviations.put("dlor", i);
		hAbbreviations.put("d-lor", i);
		hAbbreviations.put("dlui", i);
		hAbbreviations.put("d-lui", i);
		hAbbreviations.put("d-ul", i);
		hAbbreviations.put("d-nei", i);
		hAbbreviations.put("dm", i);
		hAbbreviations.put("d-na", i);
		hAbbreviations.put("dna", i);
		hAbbreviations.put("dnei", i);
		hAbbreviations.put("doc", i);
		hAbbreviations.put("dr", i);
		hAbbreviations.put("dra", i);
		hAbbreviations.put("drd", i);
		hAbbreviations.put("d.s", i);
		hAbbreviations.put("d-sa", i);
		hAbbreviations.put("d-sale", i);
		hAbbreviations.put("d-ta", i);
		hAbbreviations.put("d-tale", i);
		hAbbreviations.put("dv", i);
		hAbbreviations.put("d-voastră", i);
		hAbbreviations.put("d-voastra", i);
		hAbbreviations.put("dvs", i);
		hAbbreviations.put("dupl", i);
		hAbbreviations.put("d-soara", i);
		hAbbreviations.put("d-soară", i);
		hAbbreviations.put("defect", i);
		hAbbreviations.put("dem", i);
		hAbbreviations.put("depart", i);
		hAbbreviations.put("dex", i);
		hAbbreviations.put("dezv", i);
		hAbbreviations.put("diat", i);
		hAbbreviations.put("dial", i);
		hAbbreviations.put("distrib", i);
		hAbbreviations.put("e", i);
		hAbbreviations.put("ec", i);
		hAbbreviations.put("ec.-cont", i);
		hAbbreviations.put("e.n", i);
		hAbbreviations.put("egalit", i);
		hAbbreviations.put("electro", i);
		hAbbreviations.put("etc", i);
		hAbbreviations.put("eps", i);
		hAbbreviations.put("ev", i);
		hAbbreviations.put("enclit", i);
		hAbbreviations.put("eng", i);
		hAbbreviations.put("ex", i);
		hAbbreviations.put("expr", i);
		hAbbreviations.put("f", i);
		hAbbreviations.put("fact", i);
		hAbbreviations.put("fasc", i);
		hAbbreviations.put("febr", i);
		hAbbreviations.put("fig", i);
		hAbbreviations.put("fam", i);
		hAbbreviations.put("fax", i);
		hAbbreviations.put("fem", i);
		hAbbreviations.put("fig", i);
		hAbbreviations.put("filol", i);
		hAbbreviations.put("filoz", i);
		hAbbreviations.put("fin", i);
		hAbbreviations.put("fiz", i);
		hAbbreviations.put("fizio", i);
		hAbbreviations.put("fon", i);
		hAbbreviations.put("foto", i);
		hAbbreviations.put("fra", i);
		hAbbreviations.put("g", i);
		hAbbreviations.put("gal", i);
		hAbbreviations.put("gen", i);
		hAbbreviations.put("geo", i);
		hAbbreviations.put("geom", i);
		hAbbreviations.put("gerun", i);
		hAbbreviations.put("ger", i);
		hAbbreviations.put("gf", i);
		hAbbreviations.put("gj", i);
		hAbbreviations.put("gl", i);
		hAbbreviations.put("gmt", i);
		hAbbreviations.put("gr", i);
		hAbbreviations.put("gre", i);
		hAbbreviations.put("gram", i);
		hAbbreviations.put("gs", i);
		hAbbreviations.put("h", i);
		hAbbreviations.put("ha", i);
		hAbbreviations.put("hd", i);
		hAbbreviations.put("hm", i);
		hAbbreviations.put("hort", i);
		hAbbreviations.put("hp", i);
		hAbbreviations.put("hw", i);
		hAbbreviations.put("hz", i);
		hAbbreviations.put("hot", i);
		hAbbreviations.put("ian", i);
		hAbbreviations.put("ib", i);
		hAbbreviations.put("ibid", i);
		hAbbreviations.put("id", i);
		hAbbreviations.put("ident", i);
		hAbbreviations.put("iht", i);
		hAbbreviations.put("il", i);
		hAbbreviations.put("imp", i);
		hAbbreviations.put("imper", i);
		hAbbreviations.put("imperf", i);
		hAbbreviations.put("impers", i);
		hAbbreviations.put("impr", i);
		hAbbreviations.put("ind", i);
		hAbbreviations.put("inf", i);
		hAbbreviations.put("ing", i);
		hAbbreviations.put("infer", i);
		hAbbreviations.put("infor", i);
		hAbbreviations.put("int", i);
		hAbbreviations.put("interj", i);
		hAbbreviations.put("intr", i);
		hAbbreviations.put("intranz", i);
		hAbbreviations.put("instr", i);
		hAbbreviations.put("invar", i);
		hAbbreviations.put("iul", i);
		hAbbreviations.put("iun", i);
		hAbbreviations.put("iron", i);
		hAbbreviations.put("is", i);
		hAbbreviations.put("ist", i);
		hAbbreviations.put("ita", i);
		hAbbreviations.put("�î.a", i);
		hAbbreviations.put("î�.e.n", i);
		hAbbreviations.put("�i.a", i);
		hAbbreviations.put("�înv", i);
		hAbbreviations.put("i.e.n", i);
		hAbbreviations.put("jr", i);
		hAbbreviations.put("�jap", i);
		hAbbreviations.put("�jur", i);
		hAbbreviations.put("ke", i);
		hAbbreviations.put("kcal", i);
		hAbbreviations.put("k.d", i);
		hAbbreviations.put("kg", i);
		hAbbreviations.put("kgf", i);
		hAbbreviations.put("kgf.m", i);
		hAbbreviations.put("kgf.m.s", i);
		hAbbreviations.put("kgm", i);
		hAbbreviations.put("khz", i);
		hAbbreviations.put("kj", i);
		hAbbreviations.put("kl", i);
		hAbbreviations.put("km", i);
		hAbbreviations.put("km2", i);
		hAbbreviations.put("km/h", i);
		hAbbreviations.put("k.o", i);
		hAbbreviations.put("kt", i);
		hAbbreviations.put("kv", i);
		hAbbreviations.put("kva", i);
		hAbbreviations.put("kwh", i);
		hAbbreviations.put("l", i);
		hAbbreviations.put("la", i);
		hAbbreviations.put("l.c", i);
		hAbbreviations.put("lect", i);
		hAbbreviations.put("lit", i);
		hAbbreviations.put("lm", i);
		hAbbreviations.put("loc", i);
		hAbbreviations.put("log", i);
		hAbbreviations.put("lt", i);
		hAbbreviations.put("lt.-col", i);
		hAbbreviations.put("lt.-maj", i);
		hAbbreviations.put("lucr.cit", i);
		hAbbreviations.put("�lat", i);
		hAbbreviations.put("�lat.med", i);
		hAbbreviations.put("�lat.pop", i);
		hAbbreviations.put("�lingv", i);
		hAbbreviations.put("�lit", i);
		hAbbreviations.put("�livr", i);
		hAbbreviations.put("�loc.adj", i);
		hAbbreviations.put("�loc.adv", i);
		hAbbreviations.put("�loc.conj", i);
		hAbbreviations.put("�loc.prep", i);
		hAbbreviations.put("�loc.vb", i);
		hAbbreviations.put("�log", i);
		hAbbreviations.put("lx", i);
		hAbbreviations.put("m", i);
		hAbbreviations.put("m2�", i);
		hAbbreviations.put("m3", i);
		hAbbreviations.put("ma", i);
		hAbbreviations.put("mag", i);
		hAbbreviations.put("mar", i);
		hAbbreviations.put("mat", i);
		hAbbreviations.put("mart", i);
		hAbbreviations.put("md", i);
		hAbbreviations.put("mec", i);
		hAbbreviations.put("med", i);
		hAbbreviations.put("med.vet", i);
		hAbbreviations.put("meteo", i);
		hAbbreviations.put("mev", i);
		hAbbreviations.put("masc", i);
		hAbbreviations.put("mf", i);
		hAbbreviations.put("mg", i);
		hAbbreviations.put("mgr", i);
		hAbbreviations.put("mr", i);
		hAbbreviations.put("mh", i);
		hAbbreviations.put("mhz", i);
		hAbbreviations.put("mil", i);
		hAbbreviations.put("min", i);
		hAbbreviations.put("mitol", i);
		hAbbreviations.put("mks", i);
		hAbbreviations.put("ml", i);
		hAbbreviations.put("mld", i);
		hAbbreviations.put("mm", i);
		hAbbreviations.put("mmp", i);
		hAbbreviations.put("mp", i);
		hAbbreviations.put("mr", i);
		hAbbreviations.put("mrs", i);
		hAbbreviations.put("ms", i);
		hAbbreviations.put("mv", i);
		hAbbreviations.put("mw", i);
		hAbbreviations.put("mx", i);
		hAbbreviations.put("mod", i);
		hAbbreviations.put("m.m", i);
		hAbbreviations.put("mult", i);
		hAbbreviations.put("multiplcat", i);
		hAbbreviations.put("muz", i);
		hAbbreviations.put("n", i);
		hAbbreviations.put("n.b", i);
		hAbbreviations.put("ngr", i);
		hAbbreviations.put("nom", i);
		hAbbreviations.put("n.pr", i);
		hAbbreviations.put("num", i);
		hAbbreviations.put("num.adv", i);
		hAbbreviations.put("num.card", i);
		hAbbreviations.put("num.col", i);
		hAbbreviations.put("num.mult", i);
		hAbbreviations.put("num.neh", i);
		hAbbreviations.put("num.ord", i);
		hAbbreviations.put("ns", i);
		hAbbreviations.put("nt", i);
		hAbbreviations.put("n-v", i);
		hAbbreviations.put("ob", i);
		hAbbreviations.put("obs", i);
		hAbbreviations.put("oct", i);
		hAbbreviations.put("o.k", i);
		hAbbreviations.put("onor", i);
		hAbbreviations.put("oz", i);
		hAbbreviations.put("op", i);
		hAbbreviations.put("opt", i);
		hAbbreviations.put("opoz", i);
		hAbbreviations.put("ot", i);
		hAbbreviations.put("orni", i);
		hAbbreviations.put("neacc", i);
		hAbbreviations.put("neart", i);
		hAbbreviations.put("neg", i);
		hAbbreviations.put("nehot", i);
		hAbbreviations.put("nepers", i);
		hAbbreviations.put("nereg", i);
		hAbbreviations.put("nr", i);
		hAbbreviations.put("numer", i);
		hAbbreviations.put("ord", i);
		hAbbreviations.put("p", i);
		hAbbreviations.put("p.ana", i);
		hAbbreviations.put("pag", i);
		hAbbreviations.put("part", i);
		hAbbreviations.put("pass", i);
		hAbbreviations.put("peior", i);
		hAbbreviations.put("perf.c", i);
		hAbbreviations.put("perf.s", i);
		hAbbreviations.put("pf", i);
		hAbbreviations.put("pizz", i);
		hAbbreviations.put("pl", i);
		hAbbreviations.put("plut", i);
		hAbbreviations.put("plut.adj", i);
		hAbbreviations.put("plut.-maj", i);
		hAbbreviations.put("p.m", i);
		hAbbreviations.put("porno", i);
		hAbbreviations.put("pos", i);
		hAbbreviations.put("posed", i);
		hAbbreviations.put("pp", i);
		hAbbreviations.put("pred", i);
		hAbbreviations.put("prof", i);
		hAbbreviations.put("pron", i);
		hAbbreviations.put("p.s", i);
		hAbbreviations.put("p.s.s", i);
		hAbbreviations.put("pva", i);
		hAbbreviations.put("pz", i);
		hAbbreviations.put("part", i);
		hAbbreviations.put("pas", i);
		hAbbreviations.put("perf", i);
		hAbbreviations.put("pers", i);
		hAbbreviations.put("pl", i);
		hAbbreviations.put("polit", i);
		hAbbreviations.put("pos", i);
		hAbbreviations.put("pop", i);
		hAbbreviations.put("genit", i);
		hAbbreviations.put("poz", i);
		hAbbreviations.put("pred", i);
		hAbbreviations.put("nomin", i);
		hAbbreviations.put("pr", i);
		hAbbreviations.put("ps", i);
		hAbbreviations.put("pct", i);
		hAbbreviations.put("pref", i);
		hAbbreviations.put("prepoz", i);
		hAbbreviations.put("prez", i);
		hAbbreviations.put("proclit", i);
		hAbbreviations.put("pron", i);
		hAbbreviations.put("propoz", i);
		hAbbreviations.put("proven", i);
		hAbbreviations.put("p.s", i);
		hAbbreviations.put("pict", i);
		hAbbreviations.put("r", i);
		hAbbreviations.put("rall", i);
		hAbbreviations.put("reflex", i);
		hAbbreviations.put("reg", i);
		hAbbreviations.put("region", i);
		hAbbreviations.put("rel", i);
		hAbbreviations.put("rh", i);
		hAbbreviations.put("rom", i);
		hAbbreviations.put("rus", i);
		hAbbreviations.put("s", i);
		hAbbreviations.put("sb", i);
		hAbbreviations.put("sas", i);
		hAbbreviations.put("s-e", i);
		hAbbreviations.put("sec", i);
		hAbbreviations.put("sect", i);
		hAbbreviations.put("sept", i);
		hAbbreviations.put("serg", i);
		hAbbreviations.put("serg.-maj", i);
		hAbbreviations.put("sf", i);
		hAbbreviations.put("sg", i);
		hAbbreviations.put("si", i);
		hAbbreviations.put("sida", i);
		hAbbreviations.put("sin", i);
		hAbbreviations.put("sj", i);
		hAbbreviations.put("sla", i);
		hAbbreviations.put("slo", i);
		hAbbreviations.put("silv", i);
		hAbbreviations.put("slt", i);
		hAbbreviations.put("sm", i);
		hAbbreviations.put("sn", i);
		hAbbreviations.put("sb", i);
		hAbbreviations.put("sg", i);
		hAbbreviations.put("subst", i);
		hAbbreviations.put("suf", i);
		hAbbreviations.put("super", i);
		hAbbreviations.put("superl", i);
		hAbbreviations.put("str", i);
		hAbbreviations.put("str", i);
		hAbbreviations.put("spa", i);
		hAbbreviations.put("s-v", i);
		hAbbreviations.put("s.v", i);
		hAbbreviations.put("sv", i);
		hAbbreviations.put("ş.a", i);
		hAbbreviations.put("ş.a.m.d", i);
		hAbbreviations.put("t", i);
		hAbbreviations.put("tab", i);
		hAbbreviations.put("tat", i);
		hAbbreviations.put("tdw", i);
		hAbbreviations.put("tf", i);
		hAbbreviations.put("tg", i);
		hAbbreviations.put("teh", i);
		hAbbreviations.put("tel", i);
		hAbbreviations.put("termin", i);
		hAbbreviations.put("text", i);
		hAbbreviations.put("tipo", i);
		hAbbreviations.put("topo", i);
		hAbbreviations.put("tl", i);
		hAbbreviations.put("tr", i);
		hAbbreviations.put("tranz", i);
		hAbbreviations.put("trec", i);
		hAbbreviations.put("tur", i);
		hAbbreviations.put("tm", i);
		hAbbreviations.put("urm", i);
		hAbbreviations.put("urb", i);
		hAbbreviations.put("v", i);
		hAbbreviations.put("va", i);
		hAbbreviations.put("var", i);
		hAbbreviations.put("vol", i);
		hAbbreviations.put("var", i);
		hAbbreviations.put("verb", i);
		hAbbreviations.put("viit", i);
		hAbbreviations.put("viit.I", i);
		hAbbreviations.put("viit.II", i);
		hAbbreviations.put("voc", i);
		hAbbreviations.put("vl", i);
		hAbbreviations.put("vn", i);
		hAbbreviations.put("w", i);
		hAbbreviations.put("wh", i);
		hAbbreviations.put("zool", i);
		hAbbreviations.put("xe", i);
		return hAbbreviations;
	}

}