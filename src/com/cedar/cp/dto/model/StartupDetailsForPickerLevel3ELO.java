// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartupDetailsForPickerLevel3ELO extends AbstractELO implements Serializable {

   private transient EntityRef mStructureElementRef;
   private transient Integer mStructureId;
   private transient Integer mStructureElementId;
   private transient String mStructureElementVisId;
   private transient String mStructureElementDescription;
   private transient Boolean mLeaf;
   private transient Integer mCalElemType;
   private transient Integer mPosition;


   public StartupDetailsForPickerLevel3ELO() {
      super(new String[]{"StructureElementRef", "StructureId", "StructureElementId", "StructureElementVisId", "StructureElementDescription", "Leaf", "CalElemType", "Position"});
   }

   public void add(EntityRef seRef, int sId, int seId, String sevisId, String sedescription, boolean leaf, int calType, int position) {
      ArrayList l = new ArrayList();
      l.add(seRef);
      l.add(new Integer(sId));
      l.add(new Integer(seId));
      l.add(sevisId);
      l.add(sedescription);
      l.add(new Boolean(leaf));
      l.add(new Integer(calType));
      l.add(new Integer(position));
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
      this.mStructureElementRef = (EntityRef)l.get(index);
      this.mStructureId = (Integer)l.get(var4++);
      this.mStructureElementId = (Integer)l.get(var4++);
      this.mStructureElementVisId = (String)l.get(var4++);
      this.mStructureElementDescription = (String)l.get(var4++);
      this.mLeaf = (Boolean)l.get(var4++);
      this.mCalElemType = (Integer)l.get(var4++);
      this.mPosition = (Integer)l.get(var4++);
   }

   public EntityRef getStructureElementRef() {
      return this.mStructureElementRef;
   }

   public Integer getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getStructureElementVisId() {
      return this.mStructureElementVisId;
   }

   public String getStructureElementDescription() {
      return this.mStructureElementDescription;
   }

   public Boolean getLeaf() {
      return this.mLeaf;
   }

   public Integer getStructureId() {
      return this.mStructureId;
   }

   public Integer getCalElemType() {
      return this.mCalElemType;
   }

   public void setCalElemType(Integer calElemType) {
      this.mCalElemType = calElemType;
   }

   public Integer getPosition() {
      return this.mPosition;
   }
}
