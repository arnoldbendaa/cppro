// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.facades;


public class POIHyperlinkDTO {

   private String mAddress;
   private String mText;


   public String getText() {
      return this.mText;
   }

   public void setText(String text) {
      this.mText = text;
   }

   public String getAddress() {
      return this.mAddress;
   }

   public void setAddress(String address) {
      this.mAddress = address;
   }
}
