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

public class NegativeColorProperty extends AbstractProperty implements FormatProperty {

   private Color mNegativeColor;


   public NegativeColorProperty() {
      super("negativeColor");
   }

   public NegativeColorProperty(String colorName) {
      this();
      this.mNegativeColor = ColorUtils.getColorFromHexString(colorName);
   }

   public NegativeColorProperty(Color color) {
      this();
      this.mNegativeColor = color;
   }

   public Color getNegativeColor() {
      return this.mNegativeColor;
   }

   public void setNegativeColor(Color ForegroundColor) {
      this.mNegativeColor = ForegroundColor;
   }

   public void updateFormat(CellFormat cellFormat) {
      cellFormat.setNegativeColor(this.mNegativeColor);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), ColorUtils.getHexStringFromColor(this.mNegativeColor));
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof NegativeColorProperty?this.mNegativeColor.equals(((NegativeColorProperty)obj).getNegativeColor()):false);
   }

   public int hashCode() {
      return this.mNegativeColor.hashCode();
   }
}
