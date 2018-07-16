// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.xml;

import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionDetail;
import java.util.ArrayList;
import java.util.List;

public class FlatformFunctionCategory {

   private String mName = null;
   private List<FlatformFunctionDetail> mFunctionList = new ArrayList();


   public void addFunction(FlatformFunctionDetail functionDetail) {
      this.mFunctionList.add(functionDetail);
   }

   public List<FlatformFunctionDetail> getFunctionList() {
      return this.mFunctionList;
   }

   public void setFunctionList(List<FlatformFunctionDetail> functionList) {
      this.mFunctionList = functionList;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String toString() {
      return this.mName;
   }
}
