// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.Serializable;

public class ServletInputParam implements Serializable {

   private String mPackageName;
   private String mClassName;
   private String mJavaText;
   private String mClassPath;


   public ServletInputParam(String packageName, String className, String javaText, String classPath) {
      this.mPackageName = packageName;
      this.mClassName = className;
      this.mJavaText = javaText;
      this.mClassPath = classPath;
   }

   public String getPackageName() {
      return this.mPackageName;
   }

   public String getClassName() {
      return this.mClassName;
   }

   public String getJavaText() {
      return this.mJavaText;
   }

   public String getClassPath() {
      return this.mClassPath;
   }
}
