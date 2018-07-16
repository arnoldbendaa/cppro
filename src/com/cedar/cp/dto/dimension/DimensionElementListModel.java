// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementRef;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;

public class DimensionElementListModel extends AbstractListModel implements ListModel {

   private List mDimensionElements;


   public DimensionElementListModel(List dimensionElements) {
      this.mDimensionElements = dimensionElements;
   }

   public Object getElementAt(int index) {
      return this.mDimensionElements.get(index);
   }

   public int getSize() {
      return this.mDimensionElements.size();
   }

   public void add(DimensionElement dimensionElement) {
      this.mDimensionElements.add(dimensionElement);
      this.fireIntervalAdded(this, this.getSize() - 1, this.getSize());
   }

   public void remove(DimensionElementRef dimensionElementRef) {
      DimensionElement targetDE = null;
      Iterator deIter = this.mDimensionElements.iterator();

      while(deIter.hasNext()) {
         DimensionElement index = (DimensionElement)deIter.next();
         if(index.getKey().equals(dimensionElementRef.getPrimaryKey())) {
            targetDE = index;
            break;
         }
      }

      int index1 = this.mDimensionElements.indexOf(targetDE);
      this.mDimensionElements.remove(index1);
      this.fireIntervalRemoved(this, index1, index1 + 1);
   }

   public void setDimensionElements(List dimensionElements) {
      int size = this.getSize();
      this.mDimensionElements = dimensionElements;
      this.fireContentsChanged(this, 0, size);
   }
}
