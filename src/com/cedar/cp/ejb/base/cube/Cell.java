// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import java.io.Serializable;

public class Cell implements Serializable {

   private String mAddr;
   private String mDataType;


   public String getAddr() {
      return this.mAddr;
   }

   public void setAddr(String addr) {
      this.mAddr = addr;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dt) {
      this.mDataType = dt;
   }

   public boolean equals(Object o) {
      if(!(o instanceof Cell)) {
         return false;
      } else {
         Cell other = (Cell)o;
         return (other.mAddr == null && this.mAddr == null || other.mAddr != null && this.mAddr != null && other.mAddr.equals(this.mAddr)) && (other.mDataType == null && this.mDataType == null || other.mDataType != null && this.mDataType != null && other.mDataType.equals(this.mDataType));
      }
   }

   public int hashCode() {
      return (this.mAddr != null?this.mAddr.hashCode():0) + (this.mDataType != null?this.mDataType.hashCode():0);
   }

   public String getPrimaryKey() {
      return this.mAddr + this.mDataType;
   }
}
