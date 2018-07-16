// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.ColumnFormat;
import com.cedar.cp.util.xmlform.ColumnModelData;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class ColumnTotal extends DefaultMutableTreeNode implements XMLWritable {

   private String mId;
   private String mColumnId;
   private String mText;
   private String mFormula;
   private ColumnModelData mColumnModelData;
   private String mToolTipText;
   private ColumnFormat mColumnFormat = new ColumnFormat();


   public String getText() {
      return this.mText;
   }

   public void setText(String text) {
      this.mText = text;
      if(text != null && text.trim().length() == 0) {
         this.mText = null;
      }

   }

   public String getColumnId() {
      return this.mColumnId;
   }

   public void setColumnId(String colId) {
      this.mColumnId = colId;
   }

   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
      this.setUserObject(this.mId);
   }

   public void setToolTipText(String toolTip) {
      this.mToolTipText = toolTip;
   }

   public String getToolTipText() {
      return this.mToolTipText;
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
   }

   public String getFormula() {
      return this.mFormula;
   }

   public ColumnModelData getColumnModelData() {
      return this.mColumnModelData;
   }

   public void setColumnModelData(ColumnModelData data) {
      this.mColumnModelData = data;
   }

   public boolean isBlankWhenZero() {
      return this.mColumnFormat.isBlankWhenZero();
   }

   public void setBlankWhenZero(boolean blankWhenZero) {
      this.mColumnFormat.setBlankWhenZero(blankWhenZero);
   }

   public String getFormat() {
      String format = this.mColumnFormat.getPattern();
      return format == null?"":format;
   }

   public void setFormat(String format) {
      this.mColumnFormat.setPattern(format);
   }

   public int getIntAlignment() {
      this.checkAlignment();
      return this.mColumnFormat.getAlignment();
   }

   private void checkAlignment() {
      int alignment = this.mColumnFormat.getAlignment();
      if(alignment < 0 || alignment >= ColumnFormat.sAlignments.length) {
         byte alignment1;
         if(this.getText() != null && this.getText().trim().length() > 0) {
            alignment1 = 0;
         } else {
            alignment1 = 2;
         }

         this.mColumnFormat.setAlignment(alignment1);
      }

   }

   public void setAlignment(String alignment) {
      if(alignment != null && alignment.length() != 0) {
         if(alignment.equalsIgnoreCase("left")) {
            this.mColumnFormat.setAlignment(0);
         } else if(alignment.equalsIgnoreCase("center")) {
            this.mColumnFormat.setAlignment(1);
         } else {
            this.mColumnFormat.setAlignment(2);
         }
      } else {
         this.mColumnFormat.setAlignment(2);
      }

   }

   public String getAlignment() {
      this.checkAlignment();
      return ColumnFormat.sAlignments[this.mColumnFormat.getAlignment()];
   }

   public String getBackground() {
      return this.mColumnFormat.getBackground();
   }

   public void setBackground(String background) {
      this.mColumnFormat.setBackground(background);
   }

   public ColumnFormat getColumnFormat() {
      return this.mColumnFormat;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<columnTotal ");
      out.write(" id=\"" + this.mId + "\"");
      out.write(" columnId=\"" + this.mColumnId + "\"");
      if(this.mText != null) {
         out.write(" text=\"" + XmlUtils.escapeStringForXML(this.mText) + "\"");
      }

      if(this.mToolTipText != null && this.mToolTipText.trim().length() > 0) {
         out.write(" toolTipText=\"" + XmlUtils.escapeStringForXML(this.mToolTipText) + "\"");
      }

      String format = this.mColumnFormat.getPattern();
      if(format != null && format.trim().length() > 0) {
         out.write(" format=\"" + XmlUtils.escapeStringForXML(format) + "\"");
      }

      String background = this.mColumnFormat.getBackground();
      if(background != null) {
         out.write(" background=\"" + background + "\"");
      }

      out.write(" blankWhenZero=\"" + this.mColumnFormat.isBlankWhenZero() + "\"");
      out.write(" alignment=\"" + this.getAlignment() + "\"");
      out.write(" >");
      if(this.mFormula != null && this.mFormula.trim().length() > 0) {
         out.write("<formula><![CDATA[" + this.mFormula + "]]></formula>");
      }

      out.write("</columnTotal>");
   }
}
