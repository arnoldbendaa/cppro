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

public class StructureColumnValue extends DefaultMutableTreeNode implements XMLWritable {

   private int mDimension;
   private String mHier;


   public StructureColumnValue() {
      super("StructureColumnValue");
   }

   public int getDim() {
      return this.mDimension;
   }

   public void setDim(int dim) {
      this.mDimension = dim;
   }

   public String getHier() {
      return this.mHier;
   }

   public void setHier(String hier) {
      this.mHier = hier;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<structureColumnValue ");
      out.write(" dim=\"" + this.mDimension + "\"");
      out.write(" hier=\"" + XmlUtils.escapeStringForXML(this.mHier) + "\"");
      out.write(" />");
   }
}
