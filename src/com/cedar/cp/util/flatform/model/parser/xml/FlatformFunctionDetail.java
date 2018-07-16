// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.xml;


public class FlatformFunctionDetail implements Comparable<FlatformFunctionDetail> {

   private String mName = null;
   private String mShortDesc = null;
   private String mDetailedDesc = null;
   private String mParameter = null;
   private String mFunctionClass = null;


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getShortDesc() {
      return this.mShortDesc;
   }

   public void setShortDesc(String shortDesc) {
      this.mShortDesc = shortDesc;
   }

   public String getDetailedDesc() {
      return this.mDetailedDesc;
   }

   public void setDetailedDesc(String detailedDesc) {
      this.mDetailedDesc = detailedDesc;
   }

   public String getParameter() {
      return this.mParameter;
   }

   public void setParameter(String parameter) {
      this.mParameter = parameter;
   }

   public String getFunctionClass() {
      return this.mFunctionClass;
   }

   public void setFunctionClass(String functionClass) {
      this.mFunctionClass = functionClass;
   }

   public String toString() {
      return this.mName + " - " + this.mShortDesc;
   }

   public int compareTo(FlatformFunctionDetail arg0) {
      return this.mName.compareTo(arg0.getName());
   }
}
