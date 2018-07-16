// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.SummarySpreads;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class Summary extends DefaultMutableTreeNode implements XMLWritable {

   private String mId;
   private String mLabel;
   private String mFormula;
   private SummarySpreads mSummarySpreads;
   private String mFormat;
   private boolean mOverridable;
   private boolean mHidden;


   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
   }

   public String getLabel() {
      return this.mLabel;
   }

   public void setLabel(String label) {
      this.mLabel = label;
      this.setUserObject(label);
   }

   public boolean isOverridden() {
      return this.mOverridable;
   }

   public void setOverridden(boolean state) {
      this.mOverridable = state;
   }

   public String getFormula() {
      return this.mFormula;
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
      if(this.mFormula != null && this.mFormula.trim().length() == 0) {
         this.mFormula = null;
      }

   }

   public void setSummarySpreads(SummarySpreads summarySpreads) {
      this.mSummarySpreads = summarySpreads;
      if(this.getChildCount() > 0) {
         this.remove(0);
      }

      if(summarySpreads != null) {
         this.add(summarySpreads);
      }

      this.mFormula = null;
   }

   public SummarySpreads getSummarySpreads() {
      return this.mSummarySpreads;
   }

   public String getFormat() {
      return this.mFormat == null?"":this.mFormat;
   }

   public void setFormat(String format) {
      this.mFormat = format;
   }

   public boolean isHidden() {
      return this.mHidden;
   }

   public void setHidden(boolean hidden) {
      this.mHidden = hidden;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<summary ");
      out.write(" id=\"" + this.mId + "\"");
      out.write(" overridden=\"" + this.mOverridable + "\"");
      out.write(" hidden=\"" + this.mHidden + "\"");
      if(this.mLabel != null) {
         out.write(" label=\"" + XmlUtils.escapeStringForXML(this.mLabel) + "\"");
      }

      if(this.mFormat != null && this.mFormat.trim().length() > 0) {
         out.write(" format=\"" + XmlUtils.escapeStringForXML(this.mFormat) + "\"");
      }

      out.write(">");
      if(this.mSummarySpreads != null) {
         this.mSummarySpreads.writeXml(out);
      } else {
         out.write("<formula><![CDATA[" + this.mFormula + "]]></formula>");
      }

      out.write("</summary>");
   }
}
