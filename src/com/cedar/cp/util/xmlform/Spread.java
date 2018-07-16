// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class Spread extends DefaultMutableTreeNode implements XMLWritable {

   private String mPeriod;
   private String mFormula;


   public String getPeriod() {
      return this.mPeriod;
   }

   public void setPeriod(String period) {
      this.mPeriod = period;
      this.setUserObject("Spread for period " + this.mPeriod);
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
   }

   public String getFormula() {
      return this.mFormula;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<spread ");
      out.write(" period=\"" + this.mPeriod + "\" >");
      if(this.mFormula != null && this.mFormula.trim().length() > 0) {
         out.write("<formula><![CDATA[" + this.mFormula + "]]></formula>");
      }

      out.write("</spread>");
   }
}
