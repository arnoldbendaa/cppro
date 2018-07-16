// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import java.io.IOException;
import java.io.Writer;

public class BoldFontProperty extends AbstractProperty {

   private boolean mBoldFont;


   public BoldFontProperty() {
      super("boldFont");
   }

   public BoldFontProperty(boolean boldFont) {
      this();
      this.mBoldFont = boldFont;
   }

   public void updateFormat(CellFormat format) {
      format.setBoldFont(this.mBoldFont);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Boolean.valueOf(this.mBoldFont));
   }

   public boolean isBoldFont() {
      return this.mBoldFont;
   }

   public void setBoldFont(boolean boldFont) {
      this.mBoldFont = boldFont;
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(obj instanceof BoldFontProperty) {
         BoldFontProperty other = (BoldFontProperty)obj;
         return this.mBoldFont == other.mBoldFont;
      } else {
         return super.equals(obj);
      }
   }

   public int hashCode() {
      return this.mBoldFont?1:0;
   }
}
