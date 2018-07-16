// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.ValueMapping;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DefaultValueMapping implements ValueMapping, Serializable {

   private List mLiterals;
   private List mValues;


   public DefaultValueMapping(String[] literals, Object[] values) {
      this.mLiterals = Arrays.asList(literals);
      this.mValues = Arrays.asList(values);
   }

   public Object getValue(Object literal) {
      int index = this.mLiterals.indexOf(literal);
      return index == -1?null:this.mValues.get(index);
   }

   public Object getLiteral(Object value) {
      int index = this.mValues.indexOf(value);
      return index == -1?null:this.mLiterals.get(index);
   }

   public List getLiterals() {
      return this.mLiterals;
   }

   public List getValues() {
      return this.mValues;
   }
}
