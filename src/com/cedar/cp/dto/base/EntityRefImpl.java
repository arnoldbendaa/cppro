// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class EntityRefImpl implements EntityRef, Comparable, Serializable {

   protected Object mKey;
   private String mNarrative;
   private static String sDelim = " : ";


   public EntityRefImpl(Object key, String narrative) {
      this.mKey = key;
      this.mNarrative = narrative;
   }

   public Object getPrimaryKey() {
      return this.mKey;
   }

   public String getNarrative() {
      return this.mNarrative;
   }
   
   public String toString() {
      return this.mNarrative;
   }

   public boolean equals(Object o) {
      return o instanceof EntityRef?this.mKey.equals(((EntityRef)o).getPrimaryKey()):(!(o instanceof PrimaryKey) && !(o instanceof CompositeKey)?(o instanceof String?this.mNarrative.equals(o):false):this.mKey.equals(o));
   }

   public int hashCode() {
      return this.mKey.hashCode();
   }

   public int compareTo(Object o) {
      return o instanceof EntityRef?this.mNarrative.compareTo(((EntityRef)o).getNarrative()):-1;
   }

   public String getTokenizedKey() {
      return this.mKey instanceof CompositeKey?((CompositeKey)this.mKey).toTokens():((PrimaryKey)this.mKey).toTokens();
   }

   public String getRefDelimiter() {
      return sDelim;
   }

   public String getDisplayText() {
      return this.toString();
   }

}
