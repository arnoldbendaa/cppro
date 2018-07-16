// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.ra;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ra.ResponsibilityArea;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponsibilityAreaImpl implements ResponsibilityArea, Serializable, Cloneable {

   private Map mUpdatedNodes = new HashMap();
   private Object mPrimaryKey;
   private int mModelId;
   private int mStructureId;
   private int mStructureElementId;
   private int mVirementAuthStatus;
   private int mVersionNum;
   private ModelRef mModelRef;
   private StructureElementRef mOwningStructureElementRef;


   public ResponsibilityAreaImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mStructureId = 0;
      this.mStructureElementId = 0;
      this.mVirementAuthStatus = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ResponsibilityAreaPK)paramKey;
   }

   public void setPrimaryKey(ResponsibilityAreaCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getVirementAuthStatus() {
      return this.mVirementAuthStatus;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public StructureElementRef getOwningStructureElementRef() {
      return this.mOwningStructureElementRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setOwningStructureElementRef(StructureElementRef ref) {
      this.mOwningStructureElementRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setModelId(int paramModelId) {
      this.mModelId = paramModelId;
   }

   public void setStructureId(int paramStructureId) {
      this.mStructureId = paramStructureId;
   }

   public void setStructureElementId(int paramStructureElementId) {
      this.mStructureElementId = paramStructureElementId;
   }

   public void setVirementAuthStatus(int paramVirementAuthStatus) {
      this.mVirementAuthStatus = paramVirementAuthStatus;
   }

   public Map getUpdatedNodes() {
      return this.mUpdatedNodes;
   }

   public void setUpdatedNodes(Map updatedNodes) {
      this.mUpdatedNodes = updatedNodes;
   }
}
