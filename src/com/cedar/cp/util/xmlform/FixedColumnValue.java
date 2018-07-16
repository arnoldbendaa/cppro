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

public class FixedColumnValue extends DefaultMutableTreeNode implements XMLWritable {

   private int mDimension;
   private String mDimValue;


   public FixedColumnValue() {
      super("FixedColumnValue");
   }

   public int getDim() {
      return this.mDimension;
   }

   public void setDim(int dim) {
      this.mDimension = dim;
   }

   public String getDimValue() {
      return this.mDimValue;
   }

   public void setDimValue(String value) {
      this.mDimValue = value;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<fixedColumnValue ");
      out.write(" dim=\"" + this.mDimension + "\"");
      out.write(" dimValue=\"" + XmlUtils.escapeStringForXML(this.mDimValue) + "\"");
      out.write(" />");
   }
}
