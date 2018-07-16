// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.Serializable;

public class Pair<T extends Object, V extends Object> implements Serializable {

   private T mChild1;
   private V mChild2;


   public Pair() {}

   public Pair(T child1, V child2) {
      this.mChild1 = child1;
      this.mChild2 = child2;
   }

   public T getChild1() {
      return this.mChild1;
   }

   public V getChild2() {
      return this.mChild2;
   }

   public void setChild1(T child1) {
      this.mChild1 = child1;
   }

   public void setChild2(V child2) {
      this.mChild2 = child2;
   }

   public int hashCode() {
      return (this.mChild1 != null?this.mChild1.hashCode():0) + (this.mChild2 != null?this.mChild2.hashCode():0);
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(!(obj instanceof Pair)) {
         return false;
      } else {
         Pair other = (Pair)obj;
         return (this.mChild1 == other.mChild1 || this.mChild1 != null && other.mChild1 != null && this.mChild1.equals(other.mChild1)) && (this.mChild2 == other.mChild2 || this.mChild2 != null && other.mChild2 != null && this.mChild2.equals(other.mChild2));
      }
   }
}
