// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PropertiesMap {

   private String[] mPropertyNames;
   private Map<Object, Object[]> mValues;


   public PropertiesMap(String ... propertyNames) {
      if(propertyNames.length == 0) {
         throw new IllegalArgumentException("You must define at least some properties");
      } else {
         this.mPropertyNames = propertyNames;
         this.mValues = new HashMap();
      }
   }

   public void put(Object key, Object ... values) {
      if(this.mPropertyNames.length > values.length) {
         throw new IllegalArgumentException("The number of values is less than required");
      } else if(this.mPropertyNames.length < values.length) {
         throw new IllegalArgumentException("The number of values is more than required");
      } else {
         this.mValues.put(key, values);
      }
   }

   public double sum(String resultPropertyName, Object ... pairs) {
      if(resultPropertyName == null) {
         throw new IllegalArgumentException("Result property name not specified");
      } else if(pairs.length % 2 != 0) {
         throw new IllegalArgumentException("Uneven number of property/values");
      } else {
         int resultPropertyIndex = this.getPropertyIndex(resultPropertyName);
         int[] propertyIndexes = new int[pairs.length / 2];
         Object[] criteriaValues = new Object[pairs.length / 2];
         int result = 0;

         Object value;
         for(int checkIndex = 0; result < pairs.length - 1; ++checkIndex) {
            Object i$ = pairs[result];
            if(i$ == null) {
               throw new IllegalArgumentException("Property Name cannot be null");
            }

            int tuple = this.getPropertyIndex(i$.toString());
            if(tuple < 0) {
               throw new IllegalArgumentException("Unknown Property Name");
            }

            value = pairs[result + 1];
            if(value == null) {
               throw new IllegalArgumentException("No value passed for property " + i$);
            }

            propertyIndexes[checkIndex] = tuple;
            criteriaValues[checkIndex] = value;
            result += 2;
         }

         double var11 = 0.0D;
         Iterator var12 = this.mValues.values().iterator();

         while(var12.hasNext()) {
            Object[] var13 = (Object[])var12.next();
            if(this.tupleMatchesCriteria(var13, propertyIndexes, criteriaValues)) {
               value = var13[resultPropertyIndex];
               if(value instanceof Number) {
                  var11 += ((Number)value).doubleValue();
               }
            }
         }

         return var11;
      }
   }

   private int getPropertyIndex(String key) {
      for(int i = 0; i < this.mPropertyNames.length; ++i) {
         if(this.mPropertyNames[i].equals(key)) {
            return i;
         }
      }

      return -1;
   }

   private boolean tupleMatchesCriteria(Object[] tuple, int[] indexes, Object[] criteria) {
      for(int i = 0; i < indexes.length; ++i) {
         Object value = tuple[indexes[i]];
         if(!criteria[i].equals(value)) {
            return false;
         }
      }

      return true;
   }
}
