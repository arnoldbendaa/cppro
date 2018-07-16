// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import java.io.IOException;
import java.io.Writer;

public class ShowCommaProperty extends AbstractProperty {

   private boolean mShowComma;


   public ShowCommaProperty() {
      super("showComma");
   }

   public ShowCommaProperty(boolean showComma) {
      this();
      this.mShowComma = showComma;
   }

   public void updateFormat(CellFormat format) {
      format.setShowComma(this.mShowComma);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Boolean.valueOf(this.mShowComma));
   }

   public boolean isShowComma() {
      return this.mShowComma;
   }

   public void setShowComma(boolean showComma) {
      this.mShowComma = showComma;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof ShowCommaProperty?this.mShowComma == ((ShowCommaProperty)obj).isShowComma():false);
   }

   public int hashCode() {
      return this.mShowComma?1:0;
   }
}
