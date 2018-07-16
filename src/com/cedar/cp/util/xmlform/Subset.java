// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class Subset extends DefaultMutableTreeNode implements XMLWritable {

   private String mColumn;
   private String mValue;


   public Subset() {
      this.setUserObject("Subset");
   }

   public String getColumn() {
      return this.mColumn;
   }

   public void setColumn(String column) {
      this.mColumn = column;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<subset ");
      out.write(" column=\"" + this.mColumn + "\"");
      out.write(" value=\"" + this.mValue + "\" />");
   }
}
