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

public class FontSizeProperty extends AbstractProperty implements FormatProperty {

   private int mFontSize;


   public FontSizeProperty() {
      super("fontSize");
   }

   public FontSizeProperty(int fontSize) {
      this();
      this.mFontSize = fontSize;
   }

   public void updateFormat(CellFormat format) {
      format.setFontSize(this.mFontSize);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Integer.valueOf(this.mFontSize));
   }

   public int getFontSize() {
      return this.mFontSize;
   }

   public void setFontSize(int fontSize) {
      this.mFontSize = fontSize;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof FontSizeProperty?this.mFontSize == ((FontSizeProperty)obj).getFontSize():false);
   }

   public int hashCode() {
      return this.mFontSize;
   }
}
