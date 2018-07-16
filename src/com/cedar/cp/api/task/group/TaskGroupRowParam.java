// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task.group;

import com.cedar.cp.api.base.EntityRef;
import java.io.Serializable;

public class TaskGroupRowParam implements Serializable {

   private String mKey;
   private String mValue;
   private EntityRef mRef;
   private String mDisplayValue;


   public TaskGroupRowParam() {}

   public TaskGroupRowParam(String key, String value) {
      this.mKey = key;
      this.mValue = value;
   }

   public TaskGroupRowParam(EntityRef ref) {
      this.mKey = "ID";
      this.mRef = ref;
   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public EntityRef getRef() {
      return this.mRef;
   }

   public void setRef(EntityRef ref) {
      this.mRef = ref;
   }

   public void setDisplayValue(String displayValue) {
      this.mDisplayValue = displayValue;
   }

   public String getDisplayValue() {
      return this.mDisplayValue != null?this.mDisplayValue:(this.getValue() != null?this.mValue:(this.getRef() != null?this.getRef().getDisplayText():""));
   }

   public String getSaveVale() {
      return this.getValue() != null?this.getValue():(this.getRef() != null?this.getRef().getTokenizedKey():null);
   }

   public String toString() {
      return this.getDisplayValue();
   }
}
