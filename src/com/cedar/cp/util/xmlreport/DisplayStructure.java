// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.Constraint;
import com.cedar.cp.util.xmlreport.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisplayStructure implements XMLWritable, Serializable {

   private int mIndex;
   private int mAutoExpandDepth;
   private List mConstaints = new ArrayList();


   public void addConstraint(Constraint constraint) {
      this.mConstaints.add(constraint);
   }

   public void setConstraints(List constraints) {
      this.mConstaints = constraints;
   }

   public List getConstraints() {
      return this.mConstaints;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public int getAutoExpandDepth() {
      return this.mAutoExpandDepth;
   }

   public void setAutoExpandDepth(int autoExpandDepth) {
      this.mAutoExpandDepth = autoExpandDepth;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<displayStructure index=\"" + this.mIndex + "\" autoExpandDepth=\"" + this.mAutoExpandDepth + "\" >");
      Iterator iter = this.mConstaints.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</displayStructure>");
   }
}
