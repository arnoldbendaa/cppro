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

public class FontNameProperty extends AbstractProperty implements FormatProperty {

   private String mFontName;


   public FontNameProperty() {
      super("fontName");
   }

   public FontNameProperty(String fontName) {
      this();
      this.mFontName = fontName;
   }

   public void updateFormat(CellFormat format) {
      format.setFontName(this.mFontName);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), this.mFontName);
   }

   public String getFontName() {
      return this.mFontName;
   }

   public void setFontName(String fontName) {
      this.mFontName = fontName;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof FontNameProperty?this.mFontName.equals(((FontNameProperty)obj).getFontName()):false);
   }

   public int hashCode() {
      return this.mFontName.hashCode();
   }
}
