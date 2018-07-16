// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;


public class CrumbDTO {

   private String mVisId;
   private String mStructureElementId;
   private int mOldDepth;
   private int mOldUserCount;
   private String mDescription;


   public CrumbDTO() {}

   public CrumbDTO(String visId, String structureElementId) {
      this.mVisId = visId;
      this.mStructureElementId = structureElementId;
   }

   public CrumbDTO(String visId, String structureElementId, int oldDepth, int oldUserCount) {
      this.mVisId = visId;
      this.mStructureElementId = structureElementId;
      this.mOldDepth = oldDepth;
      this.mOldUserCount = oldUserCount;
   }

   public CrumbDTO(String visId, String structureElementId, String oldDepth, String oldUserCount) {
      this.mVisId = visId;
      this.mStructureElementId = structureElementId;
      this.mOldDepth = Integer.parseInt(oldDepth);
      this.mOldUserCount = Integer.parseInt(oldUserCount);
   }

   public CrumbDTO(String visId, String structureElementId, int oldDepth, int oldUserCount, String description) {
      this.mVisId = visId;
      this.mStructureElementId = structureElementId;
      this.mOldDepth = oldDepth;
      this.mOldUserCount = oldUserCount;
      this.mDescription = description;
   }

   public CrumbDTO(String visId, String structureElementId, String oldDepth, String oldUserCount, String description) {
      this.mVisId = visId;
      this.mStructureElementId = structureElementId;
      this.mOldDepth = Integer.parseInt(oldDepth);
      this.mOldUserCount = Integer.parseInt(oldUserCount);
      this.mDescription = description;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(String structureElementId) {
      this.mStructureElementId = structureElementId;
   }

   public int getOldDepth() {
      return this.mOldDepth;
   }

   public void setOldDepth(int oldDepth) {
      this.mOldDepth = oldDepth;
   }

   public int getOldUserCount() {
      return this.mOldUserCount;
   }

   public void setOldUserCount(int oldUserCount) {
      this.mOldUserCount = oldUserCount;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }
}
