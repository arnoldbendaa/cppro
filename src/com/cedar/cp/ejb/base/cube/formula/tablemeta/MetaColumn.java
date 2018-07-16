// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula.tablemeta;


public class MetaColumn {

   private String mName;
   private int mLength;
   private int mSQLType;


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public int getSQLType() {
      return this.mSQLType;
   }

   public void setSQLType(int SQLType) {
      this.mSQLType = SQLType;
   }

   public int getLength() {
      return this.mLength;
   }

   public void setLength(int length) {
      this.mLength = length;
   }
}
