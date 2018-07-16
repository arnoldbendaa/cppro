// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Row;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class RowInput extends DefaultMutableTreeNode implements XMLWritable {

   private String mId;
   private List mRows = new ArrayList();


   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
      this.setUserObject("RowInput id=" + this.mId);
   }

   public void removeRow(Row row) {
      this.mRows.remove(row);
      this.remove(row);
   }

   public void addRow(Row row) {
      this.mRows.add(row);
      this.add(row);
   }

   public List getRows() {
      return Collections.unmodifiableList(this.mRows);
   }

   public List getOriginalRows() {
      return this.mRows;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<rowInput ");
      out.write(" id=\"" + this.mId + "\">");
      Iterator iter = this.mRows.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</rowInput>");
   }
}
