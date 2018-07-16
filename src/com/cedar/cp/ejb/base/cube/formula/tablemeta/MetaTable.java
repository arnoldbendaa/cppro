// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula.tablemeta;

import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaColumn;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MetaTable {

   private String mName;
   private boolean mView;
   private List<MetaColumn> mColumns = new ArrayList();
   private List<MetaKey> mKeys = new ArrayList();


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public List<MetaColumn> getColumns() {
      return this.mColumns;
   }

   public void setColumns(List<MetaColumn> columns) {
      this.mColumns = columns;
   }

   public MetaColumn getColumn(String name) {
      Iterator i$ = this.mColumns.iterator();

      MetaColumn c;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         c = (MetaColumn)i$.next();
      } while(!c.getName().equals(name.toUpperCase()));

      return c;
   }

   public List<MetaKey> getKeys() {
      return this.mKeys;
   }

   public void setKeys(List<MetaKey> keys) {
      this.mKeys = keys;
   }

   public boolean isView() {
      return this.mView;
   }

   public void setView(boolean view) {
      this.mView = view;
   }
}
