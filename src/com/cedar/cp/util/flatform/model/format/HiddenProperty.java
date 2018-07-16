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

public class HiddenProperty extends AbstractProperty {

   private boolean mHidden;


   public HiddenProperty() {
      super("hidden");
   }

   public HiddenProperty(boolean hidden) {
      this();
      this.mHidden = hidden;
   }

   public void updateFormat(CellFormat format) {
      format.setHidden(this.mHidden);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Boolean.valueOf(this.mHidden));
   }

   public boolean isHidden() {
      return this.mHidden;
   }

   public void setHidden(boolean hidden) {
      this.mHidden = hidden;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof HiddenProperty?this.mHidden == ((HiddenProperty)obj).isHidden():false);
   }

   public int hashCode() {
      return this.mHidden?1:0;
   }
}
