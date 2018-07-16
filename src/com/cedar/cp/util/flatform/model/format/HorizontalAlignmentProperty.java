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

public class HorizontalAlignmentProperty extends AbstractProperty implements FormatProperty {

   private int mHorizontalAlginment;


   public HorizontalAlignmentProperty() {
      super("horizontalAlignment");
   }

   public HorizontalAlignmentProperty(int horizontalAlginment) {
      this();
      this.mHorizontalAlginment = horizontalAlginment;
   }

   public int getHorizontalAlginment() {
      return this.mHorizontalAlginment;
   }

   public void setHorizontalAlginment(int horizontalAlginment) {
      this.mHorizontalAlginment = horizontalAlginment;
   }

   public void updateFormat(CellFormat format) {
      format.setHorizontalAlignment(this.mHorizontalAlginment);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Integer.valueOf(this.mHorizontalAlginment));
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof HorizontalAlignmentProperty?this.mHorizontalAlginment == ((HorizontalAlignmentProperty)obj).getHorizontalAlginment():false);
   }

   public int hashCode() {
      return this.mHorizontalAlginment;
   }
}
