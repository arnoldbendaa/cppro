// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.recharge;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.Recharge;
import com.cedar.cp.api.model.recharge.RechargeCellDataVO;
import com.cedar.cp.api.model.recharge.RechargeEditor;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.recharge.RechargeEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.recharge.RechargeAdapter;
import com.cedar.cp.impl.model.recharge.RechargeEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class RechargeEditorImpl extends BusinessEditorImpl implements RechargeEditor {

   private RechargeEditorSessionSSO mServerSessionData;
   private RechargeImpl mEditorData;
   private RechargeAdapter mEditorDataAdapter;


   public RechargeEditorImpl(RechargeEditorSessionImpl session, RechargeEditorSessionSSO serverSessionData, RechargeImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(RechargeEditorSessionSSO serverSessionData, RechargeImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setManualRatios(boolean newManualRatios) throws ValidationException {
      this.validateManualRatios(newManualRatios);
      if(this.mEditorData.isManualRatios() != newManualRatios) {
         this.setContentModified();
         this.mEditorData.setManualRatios(newManualRatios);
      }
   }

   public void setAllocationDataTypeId(int newAllocationDataTypeId) throws ValidationException {
      this.validateAllocationDataTypeId(newAllocationDataTypeId);
      if(this.mEditorData.getAllocationDataTypeId() != newAllocationDataTypeId) {
         this.setContentModified();
         this.mEditorData.setAllocationDataTypeId(newAllocationDataTypeId);
      }
   }

   public void setDiffAccount(boolean newDiffAccount) throws ValidationException {
      this.validateDiffAccount(newDiffAccount);
      if(this.mEditorData.isDiffAccount() != newDiffAccount) {
         this.setContentModified();
         this.mEditorData.setDiffAccount(newDiffAccount);
      }
   }

   public void setAccountStructureId(int newAccountStructureId) throws ValidationException {
      this.validateAccountStructureId(newAccountStructureId);
      if(this.mEditorData.getAccountStructureId() != newAccountStructureId) {
         this.setContentModified();
         this.mEditorData.setAccountStructureId(newAccountStructureId);
      }
   }

   public void setAccountStructureElementId(int newAccountStructureElementId) throws ValidationException {
      this.validateAccountStructureElementId(newAccountStructureElementId);
      if(this.mEditorData.getAccountStructureElementId() != newAccountStructureElementId) {
         this.setContentModified();
         this.mEditorData.setAccountStructureElementId(newAccountStructureElementId);
      }
   }

   public void setRatioType(int newRatioType) throws ValidationException {
      this.validateRatioType(newRatioType);
      if(this.mEditorData.getRatioType() != newRatioType) {
         this.setContentModified();
         this.mEditorData.setRatioType(newRatioType);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void setReason(String newReason) throws ValidationException {
      if(newReason != null) {
         newReason = StringUtils.rtrim(newReason);
      }

      this.validateReason(newReason);
      if(this.mEditorData.getReason() == null || !this.mEditorData.getReason().equals(newReason)) {
         this.setContentModified();
         this.mEditorData.setReason(newReason);
      }
   }

   public void setReference(String newReference) throws ValidationException {
      if(newReference != null) {
         newReference = StringUtils.rtrim(newReference);
      }

      this.validateReference(newReference);
      if(this.mEditorData.getReference() == null || !this.mEditorData.getReference().equals(newReference)) {
         this.setContentModified();
         this.mEditorData.setReference(newReference);
      }
   }

   public void setAllocationPercentage(BigDecimal newAllocationPercentage) throws ValidationException {
      this.validateAllocationPercentage(newAllocationPercentage);
      if(this.mEditorData.getAllocationPercentage() == null || !this.mEditorData.getAllocationPercentage().equals(newAllocationPercentage)) {
         this.setContentModified();
         this.mEditorData.setAllocationPercentage(newAllocationPercentage);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a Recharge");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a Recharge");
      }
   }

   public void validateReason(String newReason) throws ValidationException {
      if(newReason != null && newReason.length() > 512) {
         throw new ValidationException("length (" + newReason.length() + ") of Reason must not exceed 512 on a Recharge");
      }
   }

   public void validateReference(String newReference) throws ValidationException {
      if(newReference != null && newReference.length() > 128) {
         throw new ValidationException("length (" + newReference.length() + ") of Reference must not exceed 128 on a Recharge");
      }
   }

   public void validateAllocationPercentage(BigDecimal newAllocationPercentage) throws ValidationException {}

   public void validateManualRatios(boolean newManualRatios) throws ValidationException {}

   public void validateAllocationDataTypeId(int newAllocationDataTypeId) throws ValidationException {}

   public void validateDiffAccount(boolean newDiffAccount) throws ValidationException {}

   public void validateAccountStructureId(int newAccountStructureId) throws ValidationException {}

   public void validateAccountStructureElementId(int newAccountStructureElementId) throws ValidationException {}

   public void validateRatioType(int newRatioType) throws ValidationException {}

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((RechargeEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public Recharge getRecharge() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new RechargeAdapter((RechargeEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void setAccountStructureElementRef(StructureElementRef ref) throws ValidationException {
      this.setAccountStructureId(((StructureElementPK)ref.getPrimaryKey()).getStructureId());
      this.setAccountStructureElementId(((StructureElementPK)ref.getPrimaryKey()).getStructureElementId());
      this.mEditorData.setAccountStructureElementRef(ref);
      this.setContentModified();
   }

   public void addSourceCell(EntityRef[] refs) throws ValidationException {
      this.mEditorData.addSourceCell(refs);
      this.setContentModified();
   }

   public void removeSourceCells(int[] rows) throws ValidationException {
      ArrayList l = new ArrayList(rows.length);
      int[] arr$ = rows;
      int len$ = rows.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int row = arr$[i$];
         l.add(this.mEditorData.getSelectedSourceCells().get(row));
      }

      this.mEditorData.getSelectedSourceCells().removeAll(l);
      this.setContentModified();
   }

   public void addTargetCell(EntityRef[] refs, BigDecimal ratio) throws ValidationException {
      this.mEditorData.addTargetCell(refs, ratio);
      this.setContentModified();
   }

   public void removeTargetCells(int[] rows) throws ValidationException {
      ArrayList l = new ArrayList(rows.length);
      int[] arr$ = rows;
      int len$ = rows.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int row = arr$[i$];
         l.add(this.mEditorData.getSelectedTargetCells().get(row));
      }

      this.mEditorData.getSelectedTargetCells().removeAll(l);
      this.setContentModified();
   }

   public void addOffsetCell(EntityRef[] refs) throws ValidationException {
      this.mEditorData.addOffsetCell(refs);
      this.setContentModified();
   }

   public void removeOffsetCells(int[] rows) throws ValidationException {
      ArrayList l = new ArrayList(rows.length);
      int[] arr$ = rows;
      int len$ = rows.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int row = arr$[i$];
         l.add(this.mEditorData.getSelectedOffsetCells().get(row));
      }

      this.mEditorData.getSelectedOffsetCells().removeAll(l);
      this.setContentModified();
   }

   public boolean ensureManualRatios() {
      Iterator i$ = this.mEditorData.getSelectedTargetCells().iterator();

      RechargeCellDataVO cell;
      do {
         if(!i$.hasNext()) {
            return true;
         }

         cell = (RechargeCellDataVO)i$.next();
      } while(cell.getRatio() != null);

      return false;
   }
}
