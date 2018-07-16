// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.awt.EntityListListModel;
import javax.swing.ComboBoxModel;

public class EntityListComboBoxModel extends EntityListListModel implements ComboBoxModel {

   private Object mSelectedItem;


   public EntityListComboBoxModel(EntityList collection, String[] columnNames, String[] tooltipColumnNames, String separator) {
      super(collection, columnNames, tooltipColumnNames, separator);
   }

   public int findRowForColumn(String colName, Object value) {
      int index = -1;

      for(int i = 0; index == -1 && i < this.mCollection.getNumRows(); ++i) {
         Object v = this.mCollection.getValueAt(i, colName);
         if(value.equals(v)) {
            index = i;
         }
      }

      return index;
   }

   public Object getSelectedItem() {
      return this.mSelectedItem;
   }

   public void setSelectedItem(Object anItem) {
      this.mSelectedItem = anItem;
   }

   public void setEntityList(EntityList newList) {
      this.mSelectedItem = null;
      super.setEntityList(newList);
   }
}
