// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ReviewBudgetHeaderValue;
import java.io.Serializable;

public class ReviewBudgetHeaderValueImpl implements ReviewBudgetHeaderValue, Serializable {

   private Object mLink;
   private String mHeading;
   private int mColumnSpan;
   private int mRowSpan;


   public ReviewBudgetHeaderValueImpl(Object link, String heading, int rowSpan, int colSpan) {
      this.mLink = link;
      this.mHeading = heading;
      this.mRowSpan = rowSpan;
      this.mColumnSpan = colSpan;
   }

   public void setObject(Object link) {
      this.mLink = link;
   }

   public void setHeading(String heading) {
      this.mHeading = heading;
   }

   public void setColumnSpan(int span) {
      this.mColumnSpan = span;
   }

   public void setRowSpan(int span) {
      this.mRowSpan = span;
   }

   public Object getObject() {
      return this.mLink;
   }

   public String getHeading() {
      return this.mHeading;
   }

   public int getColumnSpan() {
      return this.mColumnSpan;
   }

   public int getRowSpan() {
      return this.mRowSpan;
   }
}
