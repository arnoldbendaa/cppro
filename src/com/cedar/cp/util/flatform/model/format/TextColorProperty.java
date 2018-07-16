// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
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

public class TextColorProperty extends AbstractProperty implements FormatProperty {

   private Color mTextColor;


   public TextColorProperty() {
      super("textColor");
   }

   public TextColorProperty(String colorName) {
      this();
      this.mTextColor = ColorUtils.getColorFromHexString(colorName);
   }

   public TextColorProperty(Color color) {
      this();
      this.mTextColor = color;
   }

   public Color getTextColor() {
      return this.mTextColor;
   }

   public void setTextColor(Color ForegroundColor) {
      this.mTextColor = ForegroundColor;
   }

   public void updateFormat(CellFormat cellFormat) {
      cellFormat.setTextColor(this.mTextColor);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), ColorUtils.getHexStringFromColor(this.mTextColor));
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof TextColorProperty?this.mTextColor.equals(((TextColorProperty)obj).getTextColor()):false);
   }

   public int hashCode() {
      return this.mTextColor.hashCode();
   }
}
