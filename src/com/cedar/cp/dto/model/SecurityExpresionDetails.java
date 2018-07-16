// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import java.util.List;

public class SecurityExpresionDetails {

   private int groupId;
   private String expression;
   private int mode;
   private List range;


   public int getGroupId() {
      return this.groupId;
   }

   public void setGroupId(int groupId) {
      this.groupId = groupId;
   }

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String expression) {
      this.expression = expression;
   }

   public int getMode() {
      return this.mode;
   }

   public void setMode(int mode) {
      this.mode = mode;
   }

   public List getRange() {
      return this.range;
   }

   public int getRangeSize() {
      return this.range != null?this.range.size():0;
   }

   public void setRange(List range) {
      this.range = range;
   }
}
