// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;


public class ResourceLock {

   private String mOwner;
   private String mResource;


   public ResourceLock(String owner, String resource) {
      this.mOwner = owner;
      this.mResource = resource;
   }

   public String getOwner() {
      return this.mOwner;
   }

   public String getResource() {
      return this.mResource;
   }

   public String toString() {
      return "ResourceLock <" + this.mOwner + "> owns <" + this.mResource + '>';
   }
}
