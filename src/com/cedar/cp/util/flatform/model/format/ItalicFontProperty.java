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

public class ItalicFontProperty extends AbstractProperty implements FormatProperty {

   private boolean mItalicFont;


   public ItalicFontProperty() {
      super("italicFont");
   }

   public ItalicFontProperty(boolean italicFont) {
      this();
      this.mItalicFont = italicFont;
   }

   public void updateFormat(CellFormat format) {
      format.setItalicFont(this.mItalicFont);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Boolean.valueOf(this.mItalicFont));
   }

   public boolean isItalicFont() {
      return this.mItalicFont;
   }

   public void setItalicFont(boolean italicFont) {
      this.mItalicFont = italicFont;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof ItalicFontProperty?this.mItalicFont == ((ItalicFontProperty)obj).isItalicFont():false);
   }

   public int hashCode() {
      return this.mItalicFont?1:0;
   }
}
