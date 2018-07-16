// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementLocation;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.virement.VirementLocationPK;

public class VirementLocationImpl implements VirementLocation {

   private VirementLocationPK mKey;
   private String mVisId;
   private String mDescription;


   public VirementLocationImpl(VirementLocationPK key, String visId, String description) {
      this.mKey = key;
      this.mVisId = visId;
      this.mDescription = description;
   }

   public Object getPrimaryKey() {
      return this.mKey;
   }

   public VirementLocationPK getKey() {
      return this.mKey;
   }

   public void setKey(VirementLocationPK key) {
      this.mKey = key;
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

   public Object getStructureElementKey() {
      return new StructureElementPK(this.mKey.getStructureId(), this.mKey.getStructureElementId());
   }
}
