// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.props;

import com.cedar.cp.api.base.EntityRef;

public class PropertyDTO {

   private int mId;
   private EntityRef mPropertyRef;
   private String mProperty;
   private String mValue;
   private String mDescription;


   public PropertyDTO() {}

   public PropertyDTO(Object ref, String value) {
      this(ref, value, "");
   }

   public PropertyDTO(Object ref, String value, String description) {
      EntityRef eRef = (EntityRef)ref;
      this.setPropertyRef(eRef);
      this.setProperty(eRef.getNarrative());
      this.setValue(value);
      this.setDescription(description);
   }

   public int getId() {
      return this.mId;
   }

   public void setId(int id) {
      this.mId = id;
   }

   public EntityRef getPropertyRef() {
      return this.mPropertyRef;
   }

   public void setPropertyRef(EntityRef propertyRef) {
      this.mPropertyRef = propertyRef;
   }

   public String getProperty() {
      return this.mProperty;
   }

   public void setProperty(String property) {
      this.mProperty = property;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
