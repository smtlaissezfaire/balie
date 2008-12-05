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
 * Created on Apr 26, 2006
 */
package ca.uottawa.balie;

import java.io.Serializable;
import java.util.BitSet;

/**
 * This class contains a wide range of features about a token.
 * It is designed to balance information calculated at construction time and at access time.
 * 
 * @author David Nadeau (pythonner@gmail.com)
 */
public class TokenFeature implements Serializable {

    private static final long serialVersionUID = 2L;

    public static final double UNKNOWN_NUMERIC_VALUE = -1.0f;

    // recognition of roman digits
	private static final char[] ROMAN_DIGITS = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
    
    /**
     * possible types for a token feature
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public enum FeatureType {
        BOOLEAN,
        NOMINAL,
        NUMERIC,
        MULTI_VALUE_NOMINAL
    }
    
    /**
     * a feature must have a type and a mechanism that compute its value
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public interface IFeature {
        public FeatureType FeatureType();
        public Mechanism Mechanism();
    }
    
    /**
     * lexical features
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public enum Feature implements IFeature {
        HasCapitalLetter            (FeatureType.BOOLEAN, new HasCapitalLetter()),
        HasDigit                    (FeatureType.BOOLEAN, new HasDigit()),
        StartsWithCapital           (FeatureType.BOOLEAN, new StartsWithCapital()),
        HasPunctuation              (FeatureType.BOOLEAN, new HasPunctuation()),
        IsAllCapitalized            (FeatureType.BOOLEAN, new IsAllCapitalized()),
        IsMixedCase                 (FeatureType.BOOLEAN, new IsMixedCase()),    
        HasDigitsOnly               (FeatureType.BOOLEAN, new HasDigitsOnly()),
        EndsInPeriod                (FeatureType.BOOLEAN, new EndsInPeriod()),
        ApostropheIsSecondChar      (FeatureType.BOOLEAN, new ApostropheIsSecondChar()),
        HasAmpersandPunct           (FeatureType.BOOLEAN, new HasAmpersandPunct()),
        HasApostrophePunct          (FeatureType.BOOLEAN, new HasApostrophePunct()),
        HasBackSlashPunct           (FeatureType.BOOLEAN, new HasBackSlashPunct()),
        HasCloseBracketPunct        (FeatureType.BOOLEAN, new HasCloseBracketPunct()),
        HasCloseParenthesisPunct    (FeatureType.BOOLEAN, new HasCloseParenthesisPunct()),
        HasColonPunct               (FeatureType.BOOLEAN, new HasColonPunct()),
        HasCommaPunct               (FeatureType.BOOLEAN, new HasCommaPunct()),
        HasCommercialAtPunct        (FeatureType.BOOLEAN, new HasCommercialAtPunct()),
        HasCopyrightPunct           (FeatureType.BOOLEAN, new HasCopyrightPunct()),
        HasCurrencyPunct            (FeatureType.BOOLEAN, new HasCurrencyPunct()),
        HasHyphenPunct              (FeatureType.BOOLEAN, new HasHyphenPunct()),
        HasExclamationPunct         (FeatureType.BOOLEAN, new HasExclamationPunct()),
        HasInterrogationPunct       (FeatureType.BOOLEAN, new HasInterrogationPunct()),
        HasInvertedExclamationPunct (FeatureType.BOOLEAN, new HasInvertedExclamationPunct()),
        HasInvertedInterrogationPunct (FeatureType.BOOLEAN, new HasInvertedInterrogationPunct()),
        HasLinefeedPunct            (FeatureType.BOOLEAN, new HasLinefeedPunct()),
        HasMiscArithmeticPunct      (FeatureType.BOOLEAN, new HasMiscArithmeticPunct()),
        HasNewlinePunct             (FeatureType.BOOLEAN, new HasNewlinePunct()),
        HasOpenBracketPunct         (FeatureType.BOOLEAN, new HasOpenBracketPunct()),
        HasOpenParenthesisPunct     (FeatureType.BOOLEAN, new HasOpenParenthesisPunct()),
        HasPercentPunct             (FeatureType.BOOLEAN, new HasPercentPunct()),
        HasPeriodPunct              (FeatureType.BOOLEAN, new HasPeriodPunct()),
        HasPipePunct                (FeatureType.BOOLEAN, new HasPipePunct()),
        HasQuotePunct               (FeatureType.BOOLEAN, new HasQuotePunct()),
        HasSemiColonPunct           (FeatureType.BOOLEAN, new HasSemiColonPunct()),
        HasSlashPunct               (FeatureType.BOOLEAN, new HasSlashPunct()),
        HasTabulationPunct          (FeatureType.BOOLEAN, new HasTabulationPunct()),
        HasTildePunct               (FeatureType.BOOLEAN, new HasTildePunct()),
        HasUnderscorePunct          (FeatureType.BOOLEAN, new HasUnderscorePunct()),
        HasUnknownPunct             (FeatureType.BOOLEAN, new HasUnknownPunct()),        
        IsRomanDigit                (FeatureType.BOOLEAN, new IsRomanDigit()),
        GetSummarizedPattern        (FeatureType.NOMINAL, new GetSummarizedPattern()),
        GetStrippedAccent           (FeatureType.NOMINAL, new GetStrippedAccent()),
        GetStrippedPunct            (FeatureType.NOMINAL, new GetStrippedPunct()),
        GetAlpha                    (FeatureType.NOMINAL, new GetAlpha()),
        GetNonAlpha                 (FeatureType.NOMINAL, new GetNonAlpha()),
        GetPattern                  (FeatureType.NOMINAL, new GetPattern()),
        GetPrefix1                  (FeatureType.NOMINAL, new GetPrefix1()),
        GetSuffix1                  (FeatureType.NOMINAL, new GetSuffix1()),
        GetPrefix2                  (FeatureType.NOMINAL, new GetPrefix2()),
        GetSuffix2                  (FeatureType.NOMINAL, new GetSuffix2()),
        GetPrefix3                  (FeatureType.NOMINAL, new GetPrefix3()),
        GetSuffix3                  (FeatureType.NOMINAL, new GetSuffix3()),        
        Length                      (FeatureType.NUMERIC, new Lenght()),
        NumSpace                    (FeatureType.NUMERIC, new NumSpace()),
        NumericValue                (FeatureType.NUMERIC, new NumericValue()),
        NumLeadingDigits            (FeatureType.NUMERIC, new NumLeadingDigits()),
        NumTrailingDigits           (FeatureType.NUMERIC, new NumTrailingDigits());
        
        private Feature(FeatureType pi_FT, Mechanism pi_M) {
            this.FT = pi_FT;
            this.M = pi_M;
        }
        
        private final FeatureType      FT;
        private final Mechanism        M;
        
        public FeatureType FeatureType() {
            return FT;
        }
        public Mechanism Mechanism() {
            return M;
        }

    }
    
	/**
     * Construct features for a token
     * 
	 * @param pi_RawToken      The raw version of the token
	 * @param pi_PunctLookup   lookup for punctuations
	 */
	public TokenFeature(String pi_RawToken, PunctLookup pi_PunctLookup) {
		m_CharList = pi_RawToken.toCharArray();
		m_PunctTypes = new int[m_CharList.length];
		m_PunctsSum = 0;
		m_Digits = new BitSet(m_CharList.length);
		m_Uppercased = new BitSet(m_CharList.length);
		m_Lowercased = new BitSet(m_CharList.length);
		m_Punctuation = new BitSet(m_CharList.length);
        m_NumSpace = 0;
		for (int i = 0; i != pi_RawToken.length(); ++i) {
			if (Character.isDigit(m_CharList[i])) 			m_Digits.set(i);
			if (Character.isUpperCase(m_CharList[i])) 		m_Uppercased.set(i);
			if (Character.isLowerCase(m_CharList[i])) 		m_Lowercased.set(i);
			if (pi_PunctLookup.IsPunctuation(m_CharList[i])) {
				m_Punctuation.set(i);
				Character c = new  Character(m_CharList[i]);
				m_PunctTypes[i] = pi_PunctLookup.GetPunctType(c.toString());
				m_PunctsSum |= m_PunctTypes[i];
			}
            if (m_CharList[i] == ' ') ++m_NumSpace;
		}
		try {m_NumericValue = Double.parseDouble(pi_RawToken);} catch (Exception nfe) {m_NumericValue = UNKNOWN_NUMERIC_VALUE;}
	}
	
    /**
     * Each feature must implements one mechanism
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public interface Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf);
        public String  GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup);
        public Double  GetNumericValue(TokenFeature tf);
    }
    
    /**
     * Mechanism for a boolean feature
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public static class BooleanMechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {return null;}
        public Double GetNumericValue(TokenFeature tf) {return null;}
    }
    /**
     * Mechanism for a nominal feature
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public static class NominalMechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {return null;}
        public Double GetNumericValue(TokenFeature tf) {return null;}
    }
    /**
     * Mechanism for a numeric feature
     * 
     * @author David Nadeau (pythonner@gmail.com)
     */
    public static class NumericMechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {return null;}
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {return null;}
    }
    
    private static class HasCapitalLetter extends BooleanMechanism implements Mechanism  {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return !tf.m_Uppercased.isEmpty();
        }
    }
    private static class HasDigit extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return !tf.m_Digits.isEmpty();
        }
    }
    private static class StartsWithCapital extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return tf.m_Uppercased.get(0);
        }
    }
    private static class IsMixedCase extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return new StartsWithCapital().GetBooleanValue(tf) && !tf.m_Lowercased.isEmpty() && tf.m_Uppercased.cardinality() > 1;
        }
    }    
    private static class Lenght extends NumericMechanism implements Mechanism {
        public Double GetNumericValue(TokenFeature tf) {
            return new Double(tf.m_CharList.length);
        }
    }
    private static class NumSpace extends NumericMechanism implements Mechanism {
        public Double GetNumericValue(TokenFeature tf) {
            return new Double(tf.m_NumSpace);
        }
    }
    private static class HasPunctuation extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return !tf.m_Punctuation.isEmpty();
        }
    }    
    private static class IsAllCapitalized extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return tf.m_Lowercased.isEmpty() && !tf.m_Uppercased.isEmpty();
        }
    }    
    private static class EndsInPeriod extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return tf.m_Punctuation.get(tf.m_CharList.length-1) && TokenConsts.Is(tf.m_PunctTypes[tf.m_CharList.length-1], TokenConsts.PUNCT_PERIOD);
        }
    }
    private static class ApostropheIsSecondChar extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return tf.m_Punctuation.get(1) && TokenConsts.Is(tf.m_PunctTypes[1], TokenConsts.PUNCT_APOSTROPHE);
        }   
    }
    private static class HasAmpersandPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_AMPERSAND) != 0;
        }
    }
    private static class HasApostrophePunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_APOSTROPHE) != 0;
        }
    }
    private static class HasBackSlashPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_BACK_SLASH) != 0;
        }
    }
    private static class HasCloseBracketPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_CLOSE_BRACKET) != 0;
        }
    }
    private static class HasCloseParenthesisPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_CLOSE_PARENTHESIS) != 0;
        }
    }
    private static class HasColonPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_COLON) != 0;
        }
    }
    private static class HasCommaPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_COMMA) != 0;
        }
    }
    private static class HasCommercialAtPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_COMMERCIAL_AT) != 0;
        }
    }
    private static class HasCopyrightPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_COPYRIGHT) != 0;
        }
    }
    private static class HasCurrencyPunct extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_CURRENCY) != 0;
        }
    }
    private static class HasHyphenPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_DASH) != 0;
        }
    }
    private static class HasExclamationPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_EXCLAMATION) != 0;
        }
    }
    private static class HasInterrogationPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_INTERROGATION) != 0;
        }
    }
    private static class HasInvertedExclamationPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_INV_EXCLAMATION) != 0;
        }
    }
    private static class HasInvertedInterrogationPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_INV_INTERROGATION) != 0;
        }
    }
    private static class HasLinefeedPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_LINEFEED) != 0;
        }
    }
    private static class HasMiscArithmeticPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_MISC_ARITHMETIC) != 0;
        }
    }
    private static class HasNewlinePunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_NEWLINE) != 0;
        }
    }
    private static class HasOpenBracketPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_OPEN_BRACKET) != 0;
        }
    }
    private static class HasOpenParenthesisPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_OPEN_PARENTHESIS) != 0;
        }
    }
    private static class HasPercentPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_PERCENT) != 0;
        }
    }
    private static class HasPeriodPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_PERIOD) != 0;
        }
    }
    private static class HasPipePunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_PIPE) != 0;
        }
    }
    private static class HasQuotePunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_QUOTE) != 0;
        }
    }
    private static class HasSemiColonPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_SEMI_COLON) != 0;
        }
    }
    private static class HasSlashPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_SLASH) != 0;
        }
    }
    private static class HasTabulationPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_TABULATION) != 0;
        }
    }
    private static class HasTildePunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_TILDE) != 0;
        }
    }
    private static class HasUnderscorePunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_UNDERSCORE) != 0;
        }
    }
    private static class HasUnknownPunct extends BooleanMechanism implements Mechanism {
            public Boolean GetBooleanValue(TokenFeature tf) {
            return (tf.m_PunctsSum & TokenConsts.PUNCT_UNKNOWN) != 0;
        }
    }
    
    private static class HasDigitsOnly extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            return tf.m_Uppercased.isEmpty() && tf.m_Lowercased.isEmpty() && !tf.m_Digits.isEmpty();
        }
    }
    private static class IsRomanDigit extends BooleanMechanism implements Mechanism {
        public Boolean GetBooleanValue(TokenFeature tf) {
            boolean bIsARomanDigit = false;
            for (int i =0; i != tf.m_CharList.length; ++i) {
                bIsARomanDigit = false;
                for (int j =0; j != ROMAN_DIGITS.length; ++j) {
                    if (tf.m_CharList[i] == ROMAN_DIGITS[j]) {
                        bIsARomanDigit=true;
                        break;
                    }
                }
                if (!bIsARomanDigit) break;
            }
            return bIsARomanDigit;        
        }
    }
    private static class NumericValue extends NumericMechanism implements Mechanism {
        public Double GetNumericValue(TokenFeature tf) {
            return new Double(tf.m_NumericValue);
        }
    }
    private static class NumLeadingDigits extends NumericMechanism implements Mechanism {
        public Double GetNumericValue(TokenFeature tf) {
            int n=0; 
            while(tf.m_Digits.get(n)) {
                n++;
                if(n==tf.m_Digits.length())break;
            } 
            return new Double(n);
        }
    }
    private static class NumTrailingDigits extends NumericMechanism implements Mechanism {
        public Double GetNumericValue(TokenFeature tf) {
            int n=tf.m_CharList.length-1,count=0;
            while(tf.m_Digits.get(n)) {
                ++count;
                --n; 
                if(n==-1) break;
            }
            return new Double(count);        
        }
    }

    private static class GetStrippedAccent extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            if (pi_AccentLookup == null) throw new Error("Accent lookup needed here!");
            StringBuffer strStripped = new StringBuffer(); 
            for (int i = 0; i != tf.m_CharList.length; ++i) {
                if (pi_AccentLookup.HasAccentEquivalence(String.valueOf(tf.m_CharList[i]))) {
                    strStripped.append(pi_AccentLookup.GetAccentEquivalence(String.valueOf(tf.m_CharList[i])));
                } else {
                    strStripped.append(tf.m_CharList[i]);
                } 
            }
            return strStripped.toString();
        }
    }

    private static class GetStrippedPunct extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            StringBuffer strStripped = new StringBuffer(); 
            for (int i = 0; i != tf.m_CharList.length; ++i) {
                if (!tf.m_Punctuation.get(i)) {
                    strStripped.append(tf.m_CharList[i]);
                } 
            }
            return strStripped.toString();
        }
    }
    
    private static class GetAlpha extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            StringBuffer strAlpha = new StringBuffer(); 
            for (int i = 0; i != tf.m_CharList.length; ++i) {
                if (!tf.m_Digits.get(i) && !tf.m_Punctuation.get(i) && tf.m_CharList[i] != ' ') strAlpha.append(tf.m_CharList[i]);
            } 
            return strAlpha.toString();
        }
    }
    
    private static class GetNonAlpha extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            StringBuffer strNonAlpha = new StringBuffer(); 
            for (int i = 0; i != tf.m_CharList.length; ++i) {
                if (tf.m_Digits.get(i) || tf.m_Punctuation.get(i) || tf.m_CharList[i] == ' ') strNonAlpha.append(tf.m_CharList[i]);
            } 
            return strNonAlpha.toString();
        }
    }
    
    private static class GetPattern extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            StringBuffer strPattern = new StringBuffer(); 
            for (int i = 0; i != tf.m_CharList.length; ++i) {
                if (tf.m_Digits.get(i)) {
                    strPattern.append("0");
                } else if (tf.m_Punctuation.get(i)) {
                    strPattern.append("-");
                } else if (tf.m_Uppercased.get(i)) {
                    strPattern.append("A");
                } else if (tf.m_Lowercased.get(i)) {
                    strPattern.append("a");
                } else {
                    strPattern.append("*");
                }
            } 
            return strPattern.toString();
        }
    }
    
    private static class GetSummarizedPattern extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            StringBuffer strSummarizedPattern = new StringBuffer(); 
            for (int i = 0; i != tf.m_CharList.length; ++i) {
                int lastidx = strSummarizedPattern.length()-1;
                if (tf.m_Digits.get(i)) {
                    if (lastidx == -1 || strSummarizedPattern.charAt(lastidx) != '0') strSummarizedPattern.append("0");
                } else if (tf.m_Punctuation.get(i)) {
                    if (lastidx == -1 || strSummarizedPattern.charAt(lastidx) != '-') strSummarizedPattern.append("-");
                } else if (tf.m_Uppercased.get(i)) {
                    if (lastidx == -1 || strSummarizedPattern.charAt(lastidx) != 'A') strSummarizedPattern.append("A");
                } else if (tf.m_Lowercased.get(i)) {
                    if (lastidx == -1 || strSummarizedPattern.charAt(lastidx) != 'a') strSummarizedPattern.append("a");
                } else {
                    if (lastidx == -1 || strSummarizedPattern.charAt(lastidx) != '*') strSummarizedPattern.append("*");
                }
            } 
            return strSummarizedPattern.toString();
        }
    }
    
    private static class GetPrefix1 extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            String strPrefix = ""; 
            for (int i = 0; i != 1 && i != tf.m_CharList.length; ++i) {
                strPrefix += tf.m_CharList[i];
            } 
            return strPrefix;        
        }
    }
    
    private static class GetPrefix2 extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            String strPrefix = ""; 
            for (int i = 0; i != 2 && i != tf.m_CharList.length; ++i) {
                strPrefix += tf.m_CharList[i];
            } 
            return strPrefix;        
        }
    }
    
    private static class GetPrefix3 extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            String strPrefix = ""; 
            for (int i = 0; i != 3 && i != tf.m_CharList.length; ++i) {
                strPrefix += tf.m_CharList[i];
            } 
            return strPrefix;        
        }
    }
    private static class GetSuffix1 extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            String strSuffix = ""; 
            for (int i = 0; i != 1 && i != tf.m_CharList.length; ++i) {
                strSuffix = tf.m_CharList[tf.m_CharList.length - i - 1] + strSuffix;
            } 
            return strSuffix;        
        }
    }
    private static class GetSuffix2 extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            String strSuffix = ""; 
            for (int i = 0; i != 2 && i != tf.m_CharList.length; ++i) {
                strSuffix = tf.m_CharList[tf.m_CharList.length - i - 1] + strSuffix;
            } 
            return strSuffix;        
        }
    }
    private static class GetSuffix3 extends NominalMechanism implements Mechanism {
        public String GetNominalValue(TokenFeature tf, AccentLookup pi_AccentLookup) {
            String strSuffix = ""; 
            for (int i = 0; i != 3 && i != tf.m_CharList.length; ++i) {
                strSuffix = tf.m_CharList[tf.m_CharList.length - i - 1] + strSuffix;
            } 
            return strSuffix;        
        }
    }
    
	// internal data structure for fast feature verification	
	private char[] 		m_CharList;		// ex.: "Hello" = {'"', 'H', 'e', 'l', 'l', 'o', '"'}
	private int[] 		m_PunctTypes;	// ex.:           {64,   0,   0,   0,   0,   0,   64}
	private int 		m_PunctsSum;	// ex.:			   64 (bitwise OR of all punctuations)
	private BitSet 		m_Digits; 		// ex.: W3C = 010 (bitmask over the token)
	private BitSet 		m_Uppercased; 	// ex.: "Hello" = 0100000 (bitmask over the token)	
	private BitSet 		m_Lowercased; 	// ex.: "Hello" = 0011110 (bitmask over the token)
	private BitSet 		m_Punctuation;	// ex.: "Hello" = 1000001 (bitmask over the token)
	private double		m_NumericValue; // ex.: "Hello" = Not a Number; 10(string) = 10f(double) 
    private int         m_NumSpace;     // ex.: "Tim Horton" = 1

}
