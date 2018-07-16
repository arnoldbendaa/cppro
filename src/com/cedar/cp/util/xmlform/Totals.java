// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.ColumnTotal;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class Totals extends DefaultMutableTreeNode implements XMLWritable {

   private List<ColumnTotal> mColumnTotals = new ArrayList();


   public Totals() {
      this.setUserObject("Totals");
   }

   public void addColumnTotal(ColumnTotal columnTotal) {
      this.mColumnTotals.add(columnTotal);
      super.add(columnTotal);
   }

   public void removeColumnTotal(ColumnTotal colTotal) {
      this.mColumnTotals.remove(colTotal);
      super.remove(colTotal);
   }

   public List<ColumnTotal> getColumnTotals() {
      return this.mColumnTotals;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<totals>");
      Iterator iter = this.mColumnTotals.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</totals>");
   }
}
