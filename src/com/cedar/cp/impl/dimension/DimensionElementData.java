// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.dto.dimension.DimensionElementImpl;

public class DimensionElementData {

   private DimensionElement mElement;
   private Object mId;
   private String mVisId;
   private String mDescription;
   private int mCreditDebit;
   private boolean mDisabled;


   public DimensionElementData(DimensionElement de) {
      this.mElement = de;
      this.mId = de.getKey();
      this.mVisId = de.getVisId();
      this.mDescription = de.getDescription();
      this.mCreditDebit = de.getCreditDebit();
      this.mDisabled = de.isDisabled();
   }

   public void updateElement(DimensionElementImpl dei) {
      dei.setVisId(this.mVisId);
      dei.setDescription(this.mDescription);
      dei.setCreditDebit(this.mCreditDebit);
      dei.setDisabled(this.mDisabled);
   }

   public boolean hasNodeDataChanged() {
      DimensionElement de = this.mElement;
      return !this.mVisId.equals(de.getVisId()) || !this.mDescription.equals(de.getDescription()) || this.mCreditDebit != de.getCreditDebit() || this.mDisabled != de.isDisabled();
   }

   public DimensionElement getElement() {
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

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public void setCreditDebit(int creditDebit) {
      this.mCreditDebit = creditDebit;
   }

   public boolean isDisabled() {
      return this.mDisabled;
   }

   public void setDisabled(boolean disabled) {
      this.mDisabled = disabled;
   }
}
