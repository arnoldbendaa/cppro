// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNode;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;

public class ResponsibilityAreaNodeImpl extends StructureElementNodeImpl implements ResponsibilityAreaNode, Cloneable {

   private int mModelId;
   private int mResponsibilityAreaId;
   private Integer mAuthStatus;
   private int mVersionNum;


   public ResponsibilityAreaNodeImpl(CPConnection connection, EntityList pEntityList) {
      super(connection, pEntityList);
      this.setAuthStatus((Integer)pEntityList.getValueAt(0, "VirementAuthStatus"));
      this.setResponsibilityAreaId(((Integer)pEntityList.getValueAt(0, "ResponsibilityAreaId")).intValue());
      this.setVersionNum(((Integer)pEntityList.getValueAt(0, "VersionNum")).intValue());
   }

   public int queryAuthStatus() {
      return this.mAuthStatus != null?this.mAuthStatus.intValue():0;
   }

   public void setAuthStatus(Integer authStatus) {
      this.mAuthStatus = authStatus;
   }

   public Integer getAuthStatus() {
      return this.mAuthStatus;
   }

   public int getResponsibilityAreaId() {
      return this.mResponsibilityAreaId;
   }

   public void setResponsibilityAreaId(int responsibilityAreaId) {
      this.mResponsibilityAreaId = responsibilityAreaId;
   }

   public Object clone() throws CloneNotSupportedException {
      ResponsibilityAreaNodeImpl node = (ResponsibilityAreaNodeImpl)super.clone();
      node.mResponsibilityAreaId = this.mResponsibilityAreaId;
      node.mAuthStatus = this.mAuthStatus;
      return node;
   }

   public Object getRespAreaKey() {
      return this.mResponsibilityAreaId == 0?null:new ResponsibilityAreaCK(new ModelPK(this.getModelId()), new ResponsibilityAreaPK(this.mResponsibilityAreaId));
   }

   public ResponsibilityAreaPK getRespAreaPK() {
      return this.mResponsibilityAreaId == 0?null:new ResponsibilityAreaPK(this.mResponsibilityAreaId);
   }

   public ResponsibilityAreaImpl makeResponsibilityAreaImpl() {
      ResponsibilityAreaImpl ra = new ResponsibilityAreaImpl(this.getRespAreaPK());
      ra.setStructureElementId(this.getStructureElementId());
      ra.setStructureId(this.getStructureId());
      ra.setVirementAuthStatus(this.queryAuthStatus());
      ra.setVersionNum(this.getVersionNum());
      return ra;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVersionNum(int versionNum) {
      this.mVersionNum = versionNum;
   }
}
