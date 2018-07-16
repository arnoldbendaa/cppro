// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.DataTypeDTO;
import com.cedar.cp.utc.struts.virement.LazyList;
import com.cedar.cp.utc.struts.virement.VirementAuthPointDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$1;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$2;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$3;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class VirementDataEntryDTO implements Serializable {

   private String mOwner;
   private String mKey;
   private int mTransferId;
   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private int mBudgetCycleId;
   private String mBudgetCycleVisId;
   private String mReason;
   private String mReference;
   private Collection mGroups = new LazyList(new VirementDataEntryDTO$2(this));
   private Collection mAuthPoints = new LazyList(new VirementDataEntryDTO$3(this));
   private List mDataTypes = new LazyList(new VirementDataEntryDTO$1(this));
   private List mDimensionHeaders = new ArrayList();
   private boolean mReadOnly;
   private static final String sNewKey = "NewKey";


   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }

   public String getBudgetCycleVisId() {
      return this.mBudgetCycleVisId;
   }

   public void setBudgetCycleVisId(String budgetCycleVisId) {
      this.mBudgetCycleVisId = budgetCycleVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public void setGroups(Collection groups) {
      this.mGroups = groups;
   }

   public Collection getGroups() {
      return this.mGroups;
   }

   public void addGroup(VirementGroupDTO group) {
      this.mGroups.add(group);
   }

   public void removeGroup(VirementGroupDTO group) {
      this.mGroups.remove(group);
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public String getReason() {
      return this.mReason;
   }

   public void setReason(String reason) {
      this.mReason = reason;
   }

   public String getReference() {
      return this.mReference;
   }

   public void setReference(String reference) {
      this.mReference = reference;
   }

   public List getDimensionHeaders() {
      return this.mDimensionHeaders;
   }

   public void setDimensionHeaders(List dimensionHeaders) {
      this.mDimensionHeaders = dimensionHeaders;
   }

   public void setNewKey() {
      this.mKey = "NewKey";
   }

   public boolean isNewKey() {
      return this.mKey != null && this.mKey.equals("NewKey");
   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public int getNumDimsPlusTwo() {
      return this.getDimensionHeaders() != null?this.getDimensionHeaders().size() + 2:0;
   }

   public int getTransferId() {
      return this.mTransferId;
   }

   public void setTransferId(int transferId) {
      this.mTransferId = transferId;
   }

   public String getOwner() {
      return this.mOwner;
   }

   public void setOwner(String owner) {
      this.mOwner = owner;
   }

   public Collection getAuthPoints() {
      return this.mAuthPoints;
   }

   public void setAuthPoints(Collection authPoints) {
      this.mAuthPoints = authPoints;
   }

   public VirementLineDTO findLine(Object key) {
      Iterator gIter = this.mGroups.iterator();

      VirementLineDTO line;
      do {
         if(!gIter.hasNext()) {
            return null;
         }

         VirementGroupDTO groupDTO = (VirementGroupDTO)gIter.next();
         line = groupDTO.findLine(key);
      } while(line == null);

      return line;
   }

   public VirementAuthPointDTO findAuthPoint(Object key) {
      Iterator apIter = this.mAuthPoints.iterator();

      VirementAuthPointDTO authPointDTO;
      do {
         if(!apIter.hasNext()) {
            return null;
         }

         authPointDTO = (VirementAuthPointDTO)apIter.next();
      } while(authPointDTO.getKeyText() == null || key == null || !authPointDTO.getKeyText().equals(key));

      return authPointDTO;
   }

   public VirementGroupDTO findGroup(Object key) {
      Iterator gIter = this.mGroups.iterator();

      VirementGroupDTO groupDTO;
      do {
         if(!gIter.hasNext()) {
            return null;
         }

         groupDTO = (VirementGroupDTO)gIter.next();
      } while(groupDTO.getKey() == null || key == null || !groupDTO.getKey().equals(key));

      return groupDTO;
   }

   public DataTypeDTO getDataType(String key) {
      Iterator i$ = this.getDataTypes().iterator();

      DataTypeDTO dtDTO;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         dtDTO = (DataTypeDTO)i$.next();
      } while(!dtDTO.getKey().equals(key));

      return dtDTO;
   }

   public boolean isReadOnly() {
      return this.mReadOnly;
   }

   public void setReadOnly(boolean readOnly) {
      this.mReadOnly = readOnly;
   }

   public List getDataTypes() {
      return this.mDataTypes;
   }

   public void setDataTypes(List dataTypes) {
      this.mDataTypes = dataTypes;
   }

   public void update(VirementDataEntryDTO src) {
      this.mOwner = src.mOwner;
      this.mKey = src.mKey;
      this.mTransferId = src.mTransferId;
      this.mModelId = src.mModelId;
      this.mModelVisId = src.mModelVisId;
      this.mFinanceCubeId = src.mFinanceCubeId;
      this.mFinanceCubeVisId = src.mFinanceCubeVisId;
      this.mBudgetCycleId = src.mBudgetCycleId;
      this.mBudgetCycleVisId = src.mBudgetCycleVisId;
      this.mReason = src.mReason;
      this.mReference = src.mReference;
      this.updateGroups((List)src.mGroups);
      this.updateAuthPoints(src.mAuthPoints);
      this.mDataTypes = new ArrayList(src.mDataTypes);
      this.mDimensionHeaders = new ArrayList(src.mDimensionHeaders);
      this.mReadOnly = src.mReadOnly;
   }

   private void updateGroups(List<VirementGroupDTO> srcGroups) {
      Iterator i$ = srcGroups.iterator();

      while(i$.hasNext()) {
         VirementGroupDTO srcGroup = (VirementGroupDTO)i$.next();
         VirementGroupDTO thisGroup = this.findGroup(srcGroup.getKey());
         if(thisGroup != null) {
            thisGroup.update(srcGroup);
         } else if(srcGroup.getKey() == null || srcGroup.getKey().length() == 0) {
            thisGroup = (VirementGroupDTO)((List)this.mGroups).get(srcGroups.indexOf(srcGroup));
            thisGroup.update(srcGroup);
         }
      }

   }

   private void updateAuthPoints(Collection<VirementAuthPointDTO> authPoints) {
      Iterator i$ = authPoints.iterator();

      while(i$.hasNext()) {
         VirementAuthPointDTO srcAuthPoint = (VirementAuthPointDTO)i$.next();
         VirementAuthPointDTO thisAuthPoint = this.findAuthPoint(srcAuthPoint.getKeyText());
         if(thisAuthPoint != null) {
            thisAuthPoint.update(srcAuthPoint);
         }
      }

   }
}
