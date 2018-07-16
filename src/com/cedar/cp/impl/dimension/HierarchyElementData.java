// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;

public class HierarchyElementData {

   private HierarchyElement mElement;
   private Object mId;
   private String mVisId;
   private String mDescription;


   public HierarchyElementData(HierarchyElement de) {
      this.mElement = de;
      this.mId = de.getPrimaryKey();
      this.mVisId = de.getVisId();
      this.mDescription = de.getDescription();
   }

   public void updateElement(HierarchyElementImpl dei) {
      dei.setVisId(this.mVisId);
      dei.setDescription(this.mDescription);
   }

   public boolean hasNodeDataChanged() {
      return !this.mVisId.equals(this.mElement.getVisId()) || !this.mDescription.equals(this.mElement.getDescription());
   }

   public HierarchyElement getElement() {
      return this.mElement;
   }

   public Object getId() {
      return this.mId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
