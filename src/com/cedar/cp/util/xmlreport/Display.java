// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.DisplayStructure;
import com.cedar.cp.util.xmlreport.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Display implements XMLWritable, Serializable {

   private List mStructures = new ArrayList();


   public void addDisplayStructure(DisplayStructure displayStructure) {
      this.mStructures.add(displayStructure);
   }

   public List getDisplayStructures() {
      return this.mStructures;
   }

   public DisplayStructure getDisplayStructure(int index) {
      return (DisplayStructure)this.mStructures.get(index);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<display>");
      Iterator iter = this.mStructures.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</display>");
   }
}
