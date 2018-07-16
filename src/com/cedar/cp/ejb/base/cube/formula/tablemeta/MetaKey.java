// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula.tablemeta;

import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaColumn;
import java.util.ArrayList;
import java.util.List;

public class MetaKey {

   private String mName;
   private List<MetaColumn> mSegments = new ArrayList();
   private boolean mPrimary;
   private boolean mUnique;


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public List<MetaColumn> getSegments() {
      return this.mSegments;
   }

   public void setSegments(List<MetaColumn> segments) {
      this.mSegments = segments;
   }

   public boolean isPrimary() {
      return this.mPrimary;
   }

   public void setPrimary(boolean primary) {
      this.mPrimary = primary;
   }

   public boolean isUnique() {
      return this.mUnique;
   }

   public void setUnique(boolean unique) {
      this.mUnique = unique;
   }
}
