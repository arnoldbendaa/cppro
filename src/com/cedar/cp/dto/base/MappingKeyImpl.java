// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.api.base.MappingKey;

public class MappingKeyImpl implements MappingKey {

   private Object[] mKeys;


   public MappingKeyImpl(Object key) {
      this(new Object[]{key});
   }

   public MappingKeyImpl(Object seg0, Object seg1) {
      this(new Object[]{seg0, seg1});
   }

   public MappingKeyImpl(Object seg0, Object seg1, Object seg2) {
      this(new Object[]{seg0, seg1, seg2});
   }

   public MappingKeyImpl(Object[] keys) {
      this.mKeys = keys;
   }

   public int compareTo(Object o) {
      if(!(o instanceof MappingKeyImpl)) {
         return -1;
      } else {
         MappingKeyImpl otherKey = (MappingKeyImpl)o;
         int cmp = 0;

         for(int index = 0; index < this.mKeys.length && index < otherKey.mKeys.length && cmp == 0; ++index) {
            Comparable c1 = (Comparable)this.mKeys[index];
            Comparable c2 = (Comparable)otherKey.mKeys[index];
            if(c1 != null && c2 != null) {
               cmp = c1.compareTo(c2);
            } else if(c1 == null) {
               cmp = 1;
            } else {
               cmp = -1;
            }
         }

         return cmp;
      }
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof MappingKeyImpl)) {
         return false;
      } else {
         MappingKeyImpl otherKey = (MappingKeyImpl)obj;
         if(this.mKeys.length != otherKey.mKeys.length) {
            return false;
         } else {
            for(int index = 0; index < this.mKeys.length; ++index) {
               Object k1 = this.mKeys[index];
               Object k2 = otherKey.mKeys[index];
               if(k1 != null && k2 == null || k1 == null && k2 != null || k1 != null && k2 != null && !k1.equals(k2)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int length() {
      return this.mKeys.length;
   }

   public Object get(int index) {
      return this.mKeys[index];
   }

   public int hashCode() {
      return this.mKeys[0] != null?this.mKeys[0].hashCode():0;
   }
}
