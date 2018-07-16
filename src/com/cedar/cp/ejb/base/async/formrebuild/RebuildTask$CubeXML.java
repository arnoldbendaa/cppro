// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.formrebuild;

import com.cedar.cp.ejb.base.async.formrebuild.RebuildTask;
import com.cedar.cp.ejb.base.async.formrebuild.RebuildTask$1;
import java.text.MessageFormat;

class RebuildTask$CubeXML {

   int mXMLFormType;
   StringBuilder mData;
   String mHeader;
   String mFooter;
   // $FF: synthetic field
   final RebuildTask this$0;


   private RebuildTask$CubeXML(RebuildTask var1) {
      this.this$0 = var1;
      this.mData = new StringBuilder();
      this.mFooter = "</Cells></CubeUpdate>";
   }

   public int getXMLFormType() {
      return this.mXMLFormType;
   }

   public void setXMLFormType(int XMLFormType) {
      this.mXMLFormType = XMLFormType;
   }

   public String getData() {
      return this.mData.toString();
   }

   public String getPostingData() {
      StringBuilder data = new StringBuilder();
      data.append(this.mHeader).append("<Cells>").append(this.getData()).append(this.mFooter);
      return MessageFormat.format(data.toString(), new Object[]{String.valueOf(this.this$0.getCPConnection().getUserContext().getUserId())});
   }

   public void addPostings(String s) {
      String header = s.substring(0, s.indexOf("<Cells>"));
      String data = s.substring(s.indexOf("<Cells>") + 7, s.indexOf(this.mFooter));
      if(this.mHeader == null) {
         this.mHeader = header;
      }

      this.mData.append(data);
   }

   // $FF: synthetic method
   RebuildTask$CubeXML(RebuildTask x0, RebuildTask$1 x1) {
      this(x0);
   }
}
