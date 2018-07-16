// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class RowStructure implements XMLWritable, Serializable {

   private int mIndex;


   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<rowStructure index=\"" + this.mIndex + "\" />");
   }
}
