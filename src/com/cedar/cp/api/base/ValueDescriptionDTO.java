// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.EntityRef;
import java.io.Serializable;

public class ValueDescriptionDTO implements Serializable {

   private Object mObject;
   private String mName;
   private String mDescription;


   public ValueDescriptionDTO() {}

   public ValueDescriptionDTO(Object o) {
      this.mObject = o;
   }

   public ValueDescriptionDTO(Object o, String description) {
      this.mObject = o;
      this.mDescription = description;
   }

   public Object getObject() {
      return this.mObject;
   }

   public void setObject(Object object) {
      this.mObject = object;
   }

   public String getName() {
      return this.mName != null?this.mName:(this.mObject instanceof EntityRef?((EntityRef)this.mObject).getNarrative():(this.mObject != null?this.mObject.toString():""));
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getDescription() {
      return this.mDescription != null?this.mDescription:"";
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getName());
      if(sb.length() > 0) {
         sb.append(" - ");
      }

      sb.append(this.getDescription());
      return sb.toString();
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(!(o instanceof ValueDescriptionDTO)) {
         return false;
      } else {
         ValueDescriptionDTO that = (ValueDescriptionDTO)o;
         if(this.mObject != null) {
            if(!this.mObject.equals(that.mObject)) {
               return false;
            }
         } else if(that.mObject != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.mObject != null?this.mObject.hashCode():0;
      return result;
   }
}
