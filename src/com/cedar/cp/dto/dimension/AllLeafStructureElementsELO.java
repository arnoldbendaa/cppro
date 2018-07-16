// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllLeafStructureElementsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"StructureElement", "BudgetInstructionAssignment", "VirementAuthPoint", "ResponsibilityArea"};
   private transient StructureElementRef mStructureElementEntityRef;
   private transient int mParentId;
   private transient String mDescription;
   private transient int mDepth;
   private transient int mPosition;
   private transient int mEndPosition;
   private transient int mCalElemType;
   private transient boolean mDisabled;
   private transient boolean mNotPlannable;


   public AllLeafStructureElementsELO() {
      super(new String[]{"StructureElement", "ParentId", "Description", "Depth", "Position", "EndPosition", "CalElemType", "Disabled", "NotPlannable"});
   }

   public void add(StructureElementRef eRefStructureElement, int col1, String col2, int col3, int col4, int col5, int col6, boolean col7, boolean col8) {
      ArrayList l = new ArrayList();
      l.add(eRefStructureElement);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
      l.add(new Integer(col4));
      l.add(new Integer(col5));
      l.add(new Integer(col6));
      l.add(new Boolean(col7));
      l.add(new Boolean(col8));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mStructureElementEntityRef = (StructureElementRef)l.get(index);
      this.mParentId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mDepth = ((Integer)l.get(var4++)).intValue();
      this.mPosition = ((Integer)l.get(var4++)).intValue();
      this.mEndPosition = ((Integer)l.get(var4++)).intValue();
      this.mCalElemType = ((Integer)l.get(var4++)).intValue();
      this.mDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mNotPlannable = ((Boolean)l.get(var4++)).booleanValue();
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public int getEndPosition() {
      return this.mEndPosition;
   }

   public int getCalElemType() {
      return this.mCalElemType;
   }

   public boolean getDisabled() {
      return this.mDisabled;
   }

   public boolean getNotPlannable() {
      return this.mNotPlannable;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
