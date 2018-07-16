// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.model.StartupDetailsForPickerLevel3ELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartupDetailsForPickerLevel2ELO extends AbstractELO implements Serializable {

   private transient Integer mHierarchyId;
   private transient String mHierarchyVisId;
   private transient String mHierarchyDescription;
   private transient Integer mCalElemType;
   private transient StartupDetailsForPickerLevel3ELO mStructureElements;


   public StartupDetailsForPickerLevel2ELO() {
      super(new String[]{"HierarchyId", "HierarchyVisId", "HierarchyDescription", "CalElemType", "StructureElement"});
   }

   public void add(int hId, String hvisId, String hdescription, StartupDetailsForPickerLevel3ELO elo) {
      ArrayList l = new ArrayList();
      l.add(new Integer(hId));
      l.add(hvisId);
      l.add(hdescription);
      l.add(new Integer(0));
      l.add(elo);
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
      this.mHierarchyId = (Integer)l.get(index);
      this.mHierarchyVisId = (String)l.get(var4++);
      this.mHierarchyDescription = (String)l.get(var4++);
      this.mCalElemType = (Integer)l.get(var4++);
      this.mStructureElements = (StartupDetailsForPickerLevel3ELO)l.get(var4++);
   }

   public Integer getHierarchyId() {
      return this.mHierarchyId;
   }

   public String getHierarchyVisId() {
      return this.mHierarchyVisId;
   }

   public String getHierarchyDescription() {
      return this.mHierarchyDescription;
   }

   public Integer getCalElemType() {
      return this.mCalElemType;
   }

   public void setCalElemType(Integer calElemType) {
      this.mCalElemType = calElemType;
   }

   public StartupDetailsForPickerLevel3ELO getStructureElements() {
      return this.mStructureElements;
   }
}
