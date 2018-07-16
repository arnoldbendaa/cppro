// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.NumberFormatter;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.ParseException;
import javax.swing.tree.DefaultMutableTreeNode;

public class InputColumnValue extends DefaultMutableTreeNode implements XMLWritable, Cloneable {

   private String mName;
   private String mValue = "";


   public InputColumnValue() {
      super("InputColumnValue");
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
      if(value == null) {
         value = "";
      }

      this.mValue = value;
      this.setUserObject(value);
   }

   public BigDecimal getBigDecimal() {
      double value = 0.0D;

      try {
         value = NumberFormatter.parseDouble(this.mValue);
      } catch (ParseException var4) {
         ;
      }

      return new BigDecimal(value);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<inputColumnValue ");
      if(this.mName != null) {
         out.write(" name=\"" + XmlUtils.escapeStringForXML(this.mName) + "\"");
      }

      if(this.mValue != null) {
         out.write(" value=\"" + XmlUtils.escapeStringForXML(this.mValue) + "\"");
      }

      out.write(" />");
   }

   public Object clone() {
      InputColumnValue newNode = new InputColumnValue();
      if(this.mName != null) {
         newNode.setName(new String(this.mName));
      }

      if(this.mValue != null) {
         newNode.setValue(new String(this.mValue));
      }

      return newNode;
   }
}
