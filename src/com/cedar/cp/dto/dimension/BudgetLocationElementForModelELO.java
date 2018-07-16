// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetLocationElementForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"StructureElement", "Model", "BudgetInstructionAssignment", "VirementAuthPoint", "ResponsibilityArea"};
   private transient StructureElementRef mStructureElementEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mStructureElementId;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mPosition;
   private transient int mEndPosition;
   private transient int mDepth;
   private transient boolean mLeaf;
   private transient boolean mDisabled;
   private transient boolean mNotPlannable;


   public BudgetLocationElementForModelELO() {
      super(new String[]{"StructureElement", "Model", "StructureElementId", "VisId", "Description", "Position", "EndPosition", "Depth", "Leaf", "Disabled", "NotPlannable"});
   }

   public void add(StructureElementRef eRefStructureElement, ModelRef eRefModel, int col1, String col2, String col3, int col4, int col5, int col6, boolean col7, boolean col8, boolean col9) {
      ArrayList l = new ArrayList();
      l.add(eRefStructureElement);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(new Integer(col4));
      l.add(new Integer(col5));
      l.add(new Integer(col6));
      l.add(new Boolean(col7));
      l.add(new Boolean(col8));
      l.add(new Boolean(col9));
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
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mPosition = ((Integer)l.get(var4++)).intValue();
      this.mEndPosition = ((Integer)l.get(var4++)).intValue();
      this.mDepth = ((Integer)l.get(var4++)).intValue();
      this.mLeaf = ((Boolean)l.get(var4++)).booleanValue();
      this.mDisabled = ((Boolean)l.get(var4++)).booleanValue();
      this.mNotPlannable = ((Boolean)l.get(var4++)).booleanValue();
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public int getEndPosition() {
      return this.mEndPosition;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public boolean getLeaf() {
      return this.mLeaf;
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
