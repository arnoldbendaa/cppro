// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class RowHeader extends DefaultMutableTreeNode implements XMLWritable {

   private int mMaxDepth = 9;
   private List mColumns = new ArrayList();


   public RowHeader() {
      this.setUserObject("RowHeader");
   }

   public int getMaxDepth() {
      return this.mMaxDepth;
   }

   public void setMaxDepth(int maxDepth) {
      this.mMaxDepth = maxDepth;
   }

   public void addColumn(Column column) {
      this.mColumns.add(column);
      super.add(column);
   }

   public void removeColumn(Column col) {
      this.mColumns.remove(col);
      super.remove(col);
   }

   public List getColumns() {
      return this.mColumns;
   }

   public void addColumnAtIndex(int index, Column column) {
      this.mColumns.add(index, column);
      super.insert(column, index);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<rowHeader ");
      out.write(" maxDepth=\"" + this.mMaxDepth + "\"");
      out.write(">");
      Iterator iter = this.mColumns.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</rowHeader>");
   }
}
