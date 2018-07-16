// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.api.base.EntityList;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EntityListImpl implements EntityList {

   protected List mHeadings;
   protected List<List<Object>> mCollection;
   protected int mCurrRowIndex = -1;


   public EntityListImpl() {}

   public EntityListImpl(String[] headings, Object[][] coll) {
      this.mHeadings = new ArrayList(headings.length);

      int i;
      for(i = 0; i < headings.length; ++i) {
         this.mHeadings.add(i, headings[i]);
      }

      this.mCollection = new ArrayList(coll.length);

      for(i = 0; i < coll.length; ++i) {
         ArrayList row = new ArrayList(coll[i].length);

         for(int j = 0; j < coll[i].length; ++j) {
            row.add(j, coll[i][j]);
         }

         this.mCollection.add(i, row);
      }

   }

   public EntityListImpl(EntityList coll) {
      String[] headings = coll.getHeadings();
      this.mHeadings = new ArrayList(headings.length);

      int i;
      for(i = 0; i < headings.length; ++i) {
         this.mHeadings.add(i, new String(headings[i]));
      }

      List src = coll.getDataAsList();
      this.mCollection = new ArrayList(src.size());

      for(i = 0; i < src.size(); ++i) {
         List oldRow = (List)src.get(i);
         ArrayList newRow = new ArrayList(oldRow.size());

         for(int j = 0; j < oldRow.size(); ++j) {
            newRow.add(oldRow.get(j));
         }

         this.mCollection.add(newRow);
      }

   }

   public void add(List l) {
      this.mCollection.add(l);
   }

   public String[] getHeadings() {
      String[] ret = new String[this.mHeadings.size()];
      return (String[])((String[])this.mHeadings.toArray(ret));
   }

   public List getDataAsList() {
      return this.mCollection;
   }

   public Object[][] getDataAsArray() {
      Object[][] temp = new Object[this.getNumRows()][this.getNumColumns()];

      for(int i = 0; i < this.getNumRows(); ++i) {
         List row = (List)this.mCollection.get(i);

         for(int j = 0; j < this.getNumColumns(); ++j) {
            temp[i][j] = row.get(j);
         }
      }

      return temp;
   }

   public Object[] getValues(String columnName) {
      int colIndex = -1;

      for(int rets = 0; rets < this.mHeadings.size(); ++rets) {
         if(((String)this.mHeadings.get(rets)).equals(columnName)) {
            colIndex = rets;
            break;
         }
      }

      if(colIndex == -1) {
         throw new IllegalArgumentException("Column name " + columnName + " not found");
      } else {
         Object[] var5 = new Object[this.mCollection.size()];

         for(int result = 0; result < var5.length; ++result) {
            var5[result] = ((List)this.mCollection.get(result)).get(colIndex);
         }

         if(var5.length > 0) {
            Object[] var6 = (Object[])((Object[])Array.newInstance(var5[0].getClass(), var5.length));
            System.arraycopy(var5, 0, var6, 0, var5.length);
            var5 = var6;
         }

         return var5;
      }
   }

   public EntityList getRowData(String key, String columnName) {
      Object[] values = this.getValues(columnName);
      int row = -1;

      for(int i = 0; i < values.length; ++i) {
         Object value = values[i];
         if(value != null && key != null && value.equals(key) || value == null && value == null) {
            row = i;
            break;
         }
      }

      return this.getRowData(row);
   }

   public EntityList getRowData(Object key, String columnName) {
      Object[] values = this.getValues(columnName);
      int row = -1;

      for(int i = 0; i < values.length; ++i) {
         if(key.equals(values[i])) {
            row = i;
            break;
         }
      }

      return this.getRowData(row);
   }

   public Object getColumnAt(String columnName) {
      return this.getValueAt(this.mCurrRowIndex, columnName);
   }

   public Object getValueAt(int row, String columnName) {
      int colIndex = -1;

      for(int i = 0; i < this.mHeadings.size(); ++i) {
         if(((String)this.mHeadings.get(i)).equals(columnName)) {
            colIndex = i;
            break;
         }
      }

      if(colIndex == -1) {
         throw new IllegalArgumentException("Column name " + columnName + " not found");
      } else {
         return ((List)this.mCollection.get(row)).get(colIndex);
      }
   }

   public void setValueAt(int row, String columnName, Object value) {
      int colIndex = -1;

      for(int i = 0; i < this.mHeadings.size(); ++i) {
         if(((String)this.mHeadings.get(i)).equals(columnName)) {
            colIndex = i;
            break;
         }
      }

      if(colIndex == -1) {
         throw new IllegalArgumentException("Column name " + columnName + " not found");
      } else {
         ((List)this.mCollection.get(row)).set(colIndex, value);
      }
   }

   public EntityList getRowData(int row) {
      if(row == -1) {
         return null;
      } else {
         List list = (List)this.mCollection.get(row);
         Object[][] data = new Object[1][this.getNumColumns()];

         for(int i = 0; i < this.getNumColumns(); ++i) {
            data[0][i] = list.get(i);
         }

         return new EntityListImpl(this.getHeadings(), data);
      }
   }

   public void remove(int index) {
      this.mCollection.remove(index);
   }

   public int getNumRows() {
      return this.mCollection.size();
   }

   public int getNumColumns() {
      return this.mHeadings.size();
   }

   public boolean includesEntity(String name) {
      return false;
   }

   public static EntityList findRow(EntityList list, String colName, Object value) {
      for(int row = 0; row < list.getNumRows(); ++row) {
         Object o = list.getValueAt(row, colName);
         if(o != null && o.equals(value)) {
            return list.getRowData(row);
         }
      }

      return null;
   }

   public static boolean containsColumn(EntityList list, String columnName) {
      String[] arr$ = list.getHeadings();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String name = arr$[i$];
         if(columnName.equals(name)) {
            return true;
         }
      }

      return false;
   }
}
