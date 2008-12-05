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
 * Created on Mar 11, 2005
 */
package ca.uottawa.balie;
import java.lang.reflect.Array;

/**
 * The following utility class allows simple construction of an effective hashCode method. 
 * It is based on the recommendations of Effective Java, by Joshua Bloch.
 * Source taken from: http://www.javapractices.com/Topic28.cjp
 * 
 * @author javapractices.com
 */

/**
* Collected methods which allow easy implementation of <code>hashCode</code>.
*
* Example use case:
* <pre>
*  public int hashCode(){
*    int result = HashCodeUtil.SEED;
*    //collect the contributions of various fields
*    result = HashCodeUtil.hash(result, fPrimitive);
*    result = HashCodeUtil.hash(result, fObject);
*    result = HashCodeUtil.hash(result, fArray);
*    return result;
*  }
* </pre>
*/

public class HashCodeUtil {

    /**
    * An initial value for a <code>hashCode</code>, to which is added contributions
    * from fields. Using a non-zero value decreases collisons of <code>hashCode</code>
    * values.
    */
    public static final int SEED = 23;

    /**
    * booleans.
    */
    public static int hash( int aSeed, boolean aBoolean ) {
      return firstTerm( aSeed ) + ( aBoolean ? 1 : 0 );
    }

    /**
    * chars.
    */
    public static int hash( int aSeed, char aChar ) {
      return firstTerm( aSeed ) + (int)aChar;
    }

    /**
    * ints.
    */
    public static int hash( int aSeed , int aInt ) {
      /*
      * Implementation Note
      * Note that byte and short are handled by this method, through
      * implicit conversion.
      */
      return firstTerm( aSeed ) + aInt;
    }

    /**
    * longs.
    */
    public static int hash( int aSeed , long aLong ) {
      return firstTerm(aSeed)  + (int)( aLong ^ (aLong >>> 32) );
    }

    /**
    * floats.
    */
    public static int hash( int aSeed , float aFloat ) {
      return hash( aSeed, Float.floatToIntBits(aFloat) );
    }

    /**
    * doubles.
    */
    public static int hash( int aSeed , double aDouble ) {
      return hash( aSeed, Double.doubleToLongBits(aDouble) );
    }

    /**
     * StringBuffer.
     */
     public static int hash( int aSeed , StringBuffer aSB ) {
       if (aSB == null) return hash(aSeed, 0);
       else             return hash( aSeed, aSB.toString().hashCode());
     }
    
    /**
    * <code>aObject</code> is a possibly-null object field, and possibly an array.
    *
    * If <code>aObject</code> is an array, then each element may be a primitive
    * or a possibly-null object.
    */
    public static int hash( int aSeed , Object aObject ) {
      int result = aSeed;
      if ( aObject == null) {
        result = hash(result, 0);
      }
      else if ( ! isArray(aObject) ) {
        result = hash(result, aObject.hashCode());
      }
      else {
        int length = Array.getLength(aObject);
        for ( int idx = 0; idx < length; ++idx ) {
          Object item = Array.get(aObject, idx);
          //recursive call!
          result = hash(result, item);
        }
      }
      return result;
    }


    /// PRIVATE ///
    private static final int fODD_PRIME_NUMBER = 37;

    private static int firstTerm( int aSeed ){
      return fODD_PRIME_NUMBER * aSeed;
    }

    private static boolean isArray(Object aObject){
      return aObject.getClass().isArray();
    }

}
