// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.ColumnStructure;
import com.cedar.cp.util.xmlreport.FilterStructure;
import com.cedar.cp.util.xmlreport.RowStructure;
import com.cedar.cp.util.xmlreport.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Layout implements XMLWritable, Serializable {

   private List mFilters = new ArrayList();
   private List mColumns = new ArrayList();
   private List mRows = new ArrayList();


   public void addFilterStructure(FilterStructure filter) {
      this.mFilters.add(filter);
   }

   public void addColumnStructure(ColumnStructure column) {
      this.mColumns.add(column);
   }

   public void addRowStructure(RowStructure row) {
      this.mRows.add(row);
   }

   public List getFilterStructures() {
      return this.mFilters;
   }

   public List getColumnStructures() {
      return this.mColumns;
   }

   public List getRowStructures() {
      return this.mRows;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<layout>");
      Iterator iter = this.mFilters.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      iter = this.mColumns.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      iter = this.mRows.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</layout>");
   }
}
