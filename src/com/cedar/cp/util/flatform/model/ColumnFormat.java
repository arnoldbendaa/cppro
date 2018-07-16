// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class ColumnFormat implements XMLWritable, Serializable {

   private int mColumn;
   private int mWidth;


   public ColumnFormat() {}

   public ColumnFormat(int column) {
      this.mColumn = column;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public void setColumn(int column) {
      this.mColumn = column;
   }

   public int getWidth() {
      return this.mWidth;
   }

   public void setWidth(int width) {
      this.mWidth = width;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<columnFormat column=\"" + this.mColumn + "\" width=\"" + this.mWidth + "\" />");
   }
}
