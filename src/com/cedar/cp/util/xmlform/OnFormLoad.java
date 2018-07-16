// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class OnFormLoad extends DefaultMutableTreeNode implements XMLWritable {

   private String mFormula;


   public OnFormLoad() {
      this.setUserObject("OnFormLoad: ");
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
   }

   public String getFormula() {
      return this.mFormula;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<onFormLoad>");
      if(this.mFormula != null && this.mFormula.trim().length() > 0) {
         out.write("<formula><![CDATA[" + this.mFormula + "]]></formula>");
      }

      out.write("</onFormLoad>");
   }
}
