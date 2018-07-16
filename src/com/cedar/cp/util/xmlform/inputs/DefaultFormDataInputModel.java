// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.Body;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultFormDataInputModel implements FormDataInputModel {

   private List mData = new ArrayList();
   private List mColumnIds = new ArrayList();
   private Map<String, Column> mColumnMappings = new HashMap();


   public DefaultFormDataInputModel(FormConfig config) {
      Body body = config.getBody();
      this.recursivelyProcessColumns(body.getColumns(), 0);
   }

   protected int recursivelyProcessColumns(List cols, int index) {
      Iterator iter = cols.iterator();

      while(iter.hasNext()) {
         Object o = iter.next();
         if(o instanceof Column) {
            Column group = (Column)o;
            this.mColumnIds.add(group.getId());
            this.mColumnMappings.put(group.getId(), group);
         } else if(o instanceof ColumnGroup) {
            ColumnGroup group1 = (ColumnGroup)o;
            cols = group1.getColumns();
            index = this.recursivelyProcessColumns(cols, index);
         }
      }

      return index;
   }

   public int addRow() {
      int index = this.mData.size();
      this.mData.add(new HashMap());
      return index;
   }

   public void deleteRow(int row) {
      this.mData.remove(row);
   }

   public void setData(List data) {
      this.mData = data;
   }

   public Column getColumnMapping(String colId) {
      return (Column)this.mColumnMappings.get(colId);
   }

   public String getColumnId(int colIndex) {
      return (String)this.mColumnIds.get(colIndex);
   }

   public int getColumnIndex(Column column) {
      return this.mColumnIds.indexOf(column.getId());
   }

   public int getRowCount() {
      return this.mData.size();
   }

   public int getColumnCount() {
      return this.mColumnIds.size();
   }

   public Object getValueAt(int row, int col) {
      Map m = (Map)this.mData.get(row);
      return m.get(this.mColumnIds.get(col));
   }

   public void setValueAt(Object value, int row, int col) {
      Map m = (Map)this.mData.get(row);
      m.put(this.mColumnIds.get(col), value);
   }

   public int getSheetProtectionLevel() {
      return 0;
   }
}
