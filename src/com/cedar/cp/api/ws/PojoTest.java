// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.ws;


public class PojoTest {

   private String mStringData;
   private int mIntData;
   private Integer mIntegerData;


   public String getStringData() {
      return this.mStringData;
   }

   public void setStringData(String stringData) {
      this.mStringData = stringData;
   }

   public int getIntData() {
      return this.mIntData;
   }

   public void setIntData(int intData) {
      this.mIntData = intData;
   }

   public Integer getIntegerData() {
      return this.mIntegerData;
   }

   public void setIntegerData(Integer integerData) {
      this.mIntegerData = integerData;
   }

   public String toString() {
      return "PojoTest{mStringData=\'" + this.mStringData + '\'' + ", mIntData=" + this.mIntData + ", mIntegerData=" + this.mIntegerData + '}';
   }
}
