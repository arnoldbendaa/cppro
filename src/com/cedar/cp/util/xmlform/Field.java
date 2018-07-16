// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class Field extends DefaultMutableTreeNode implements XMLWritable {

   private String mLabel;
   private String mValue;


   public void setLabel(String label) {
      this.mLabel = label;
   }

   public String getLabel() {
      return this.mLabel;
   }

   public void setValue(String value) {
      this.mValue = value;
      this.setUserObject("Field: " + value);
   }

   public String getValue() {
      return this.mValue;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<field ");
      if(this.mLabel != null) {
         out.write("label=\"" + XmlUtils.escapeStringForXML(this.mLabel) + "\"");
      }

      out.write(" value=\"" + XmlUtils.escapeStringForXML(this.mValue) + "\"");
      out.write(" />");
   }
}
