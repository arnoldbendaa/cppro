// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.awt.ColorUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.awt.Color;
import java.io.IOException;
import java.io.Writer;

public class BackgroundColorProperty extends AbstractProperty implements FormatProperty {

   private Color mBackgroundColor;


   public BackgroundColorProperty() {
      super("backgroundColor");
   }

   public BackgroundColorProperty(String colorName) {
      this();
      this.mBackgroundColor = ColorUtils.getColorFromHexString(colorName);
   }

   public BackgroundColorProperty(Color color) {
      this();
      this.mBackgroundColor = color;
   }

   public Color getBackgroundColor() {
      return this.mBackgroundColor;
   }

   public void setBackgroundColor(Color backgroundColor) {
      this.mBackgroundColor = backgroundColor;
   }

   public void updateFormat(CellFormat cellFormat) {
      cellFormat.setBackgroundColor(this.mBackgroundColor);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), ColorUtils.getHexStringFromColor(this.mBackgroundColor));
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof BackgroundColorProperty?this.mBackgroundColor.equals(((BackgroundColorProperty)obj).getBackgroundColor()):false);
   }

   public int hashCode() {
      return this.mBackgroundColor.hashCode();
   }
}
