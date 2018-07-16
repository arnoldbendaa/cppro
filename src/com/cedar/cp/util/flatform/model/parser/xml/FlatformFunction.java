// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.xml;

import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionCategory;
import java.util.ArrayList;
import java.util.List;

public class FlatformFunction {

   List<FlatformFunctionCategory> functionCategories = new ArrayList();


   public void addFunctionCategoryList(FlatformFunctionCategory functionCategory) {
      this.functionCategories.add(functionCategory);
   }

   public List<FlatformFunctionCategory> getFunctionCategories() {
      return this.functionCategories;
   }

   public void setFunctionCategories(List<FlatformFunctionCategory> functionCategories) {
      this.functionCategories = functionCategories;
   }
}
