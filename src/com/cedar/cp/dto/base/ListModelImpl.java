// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import java.util.List;
import javax.swing.AbstractListModel;

public class ListModelImpl extends AbstractListModel {

   private List mList;


   public ListModelImpl(List list) {
      this.mList = list;
   }

   public int getSize() {
      return this.mList.size();
   }

   public Object getElementAt(int i) {
      return this.mList.get(i);
   }

   public void add(Object i) {
      this.mList.add(i);
      this.fireIntervalAdded(this, this.getSize() - 1, this.getSize());
   }

   public void remove(Object o) {
      int index = this.mList.indexOf(o);
      if(index != -1) {
         this.mList.remove(index);
         this.fireIntervalRemoved(this, index, index + 1);
      }

   }

   public void fireItemChanged(Object o) {
      int index = this.mList.indexOf(o);
      if(index != -1) {
         this.fireContentsChanged(this, index, index + 1);
      }

   }

   public void setList(List l) {
      this.mList = l;
      this.fireContentsChanged(this, 0, this.getSize());
   }

   public List getList() {
      return this.mList;
   }
}
