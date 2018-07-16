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

public class InputColumnKey extends DefaultMutableTreeNode implements XMLWritable {

   private String mName;
   private String mValue;


   public InputColumnKey() {
      super("InputColumnKey");
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<inputColumnKey ");
      if(this.mName != null) {
         out.write(" name=\"" + XmlUtils.escapeStringForXML(this.mName) + "\"");
      }

      if(this.mValue != null) {
         out.write(" value=\"" + XmlUtils.escapeStringForXML(this.mValue) + "\"");
      }

      out.write(" />");
   }
}
