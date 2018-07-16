// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.io.IOException;
import java.io.Writer;

public class UnderlineFontProperty extends AbstractProperty implements FormatProperty {

   private boolean mUnderlineFont;


   public UnderlineFontProperty() {
      super("underlineFont");
   }

   public UnderlineFontProperty(boolean underlineFont) {
      this();
      this.mUnderlineFont = underlineFont;
   }

   public void updateFormat(CellFormat format) {
      format.setUnderlineFont(this.mUnderlineFont);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Boolean.valueOf(this.mUnderlineFont));
   }

   public boolean isUnderlineFont() {
      return this.mUnderlineFont;
   }

   public void setUnderlineFont(boolean underlineFont) {
      this.mUnderlineFont = underlineFont;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof UnderlineFontProperty?this.mUnderlineFont == ((UnderlineFontProperty)obj).isUnderlineFont():false);
   }

   public int hashCode() {
      return this.mUnderlineFont?1:0;
   }
}
