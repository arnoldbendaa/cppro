// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.api.base.EntityList;
import javax.swing.AbstractListModel;

public class EntityListListModel extends AbstractListModel {

   protected String[] mToolTipColumnNames;
   protected String[] mColumnNames;
   protected EntityList mCollection;
   protected String mSeparator;


   public EntityListListModel(EntityList collection, String[] columnNames, String[] tooltipColumnNames, String separator) {
      this.mColumnNames = columnNames;
      this.mToolTipColumnNames = tooltipColumnNames;
      this.mCollection = collection;
      this.mSeparator = separator;
   }

   public EntityList getEntityList() {
      return this.mCollection;
   }

   public void setEntityList(EntityList newList) {
      this.mCollection = newList;
      this.fireContentsChanged(this, 0, newList.getNumRows());
   }

   public int getSize() {
      return this.mCollection == null?0:this.mCollection.getNumRows();
   }

   public Object getElementAt(int row) {
      StringBuffer b = new StringBuffer();

      for(int i = 0; i < this.mColumnNames.length; ++i) {
         if(i > 0) {
            b.append(this.mSeparator);
         }

         b.append(this.mCollection.getValueAt(row, this.mColumnNames[i]));
      }

      return b.toString();
   }

   public String getToolTipTextAt(int row) {
      StringBuffer b = new StringBuffer();

      for(int i = 0; i < this.mToolTipColumnNames.length; ++i) {
         if(i > 0) {
            b.append(this.mSeparator);
         }

         b.append(this.mCollection.getValueAt(row, this.mToolTipColumnNames[i]));
      }

      return b.toString();
   }
}
