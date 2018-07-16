// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.awt.ColorUtils;
import com.cedar.cp.util.awt.LinesBorder;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.io.IOException;
import java.io.Writer;

public class BorderProperty extends AbstractProperty implements FormatProperty {

   private LinesBorder mBorder;


   public BorderProperty() {
      super("border");
   }

   public BorderProperty(LinesBorder border) {
      this();
      this.mBorder = border;
   }

   public void updateFormat(CellFormat format) {
      format.setBorder(this.mBorder);
   }

   private boolean xmlOutputRequired() {
      return this.mBorder.getNorthThickness() != 0 || this.mBorder.getSouthThickness() != 0 || this.mBorder.getWestThickness() != 0 || this.mBorder.getEastThickness() != 0;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<");
      out.write(this.getName());
      XmlUtils.outputAttribute(out, "northThickness", Integer.valueOf(this.mBorder.getNorthThickness()));
      if(this.mBorder.getNorthColor() != null) {
         XmlUtils.outputAttribute(out, "northColor", ColorUtils.getHexStringFromColor(this.mBorder.getNorthColor()));
      }

      XmlUtils.outputAttribute(out, "southThickness", Integer.valueOf(this.mBorder.getSouthThickness()));
      if(this.mBorder.getSouthColor() != null) {
         XmlUtils.outputAttribute(out, "southColor", ColorUtils.getHexStringFromColor(this.mBorder.getSouthColor()));
      }

      XmlUtils.outputAttribute(out, "westThickness", Integer.valueOf(this.mBorder.getWestThickness()));
      if(this.mBorder.getWestColor() != null) {
         XmlUtils.outputAttribute(out, "westColor", ColorUtils.getHexStringFromColor(this.mBorder.getWestColor()));
      }

      XmlUtils.outputAttribute(out, "eastThickness", Integer.valueOf(this.mBorder.getEastThickness()));
      if(this.mBorder.getEastColor() != null) {
         XmlUtils.outputAttribute(out, "eastColor", ColorUtils.getHexStringFromColor(this.mBorder.getEastColor()));
      }

      out.write("/>");
   }

   public LinesBorder getBorder() {
      return this.mBorder;
   }

   public void setBorder(LinesBorder border) {
      this.mBorder = border;
   }

   public boolean isXmlAttribute() {
      return false;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof BorderProperty?this.mBorder.equals(((BorderProperty)obj).getBorder()):false);
   }

   public int hashCode() {
      return this.mBorder.hashCode();
   }
}
