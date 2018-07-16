// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.model.StartupDetailsForPickerLevel2ELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartupDetailsForPickerELO extends AbstractELO implements Serializable {

   private transient Integer mDimensionId;
   private transient String mVisId;
   private transient String mDescription;
   private transient Integer mCalElemType;
   private transient StartupDetailsForPickerLevel2ELO mHierarchy;
   private transient DimensionRef mDimensionRef;


   public StartupDetailsForPickerELO() {
      super(new String[]{"DimensionId", "VisId", "Description", "CalElemType", "Hierarchy", "Dimension"});
   }

   public void add(int dimId, String visId, String description, StartupDetailsForPickerLevel2ELO elo, DimensionRef dimensionRef) {
      ArrayList l = new ArrayList();
      l.add(new Integer(dimId));
      l.add(visId);
      l.add(description);
      l.add(new Integer(0));
      l.add(elo);
      l.add(dimensionRef);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mDimensionId = (Integer)l.get(index);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mCalElemType = (Integer)l.get(var4++);
      this.mHierarchy = (StartupDetailsForPickerLevel2ELO)l.get(var4++);
      this.mDimensionRef = (DimensionRef)l.get(var4++);
   }

   public Integer getDimensionId() {
      return this.mDimensionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getCalElemType() {
      return this.mCalElemType;
   }

   public void setCalElemType(Integer calElemType) {
      this.mCalElemType = calElemType;
   }

   public StartupDetailsForPickerLevel2ELO getHierarchy() {
      return this.mHierarchy;
   }

   public DimensionRef getDimensionRef() {
      return this.mDimensionRef;
   }
}
