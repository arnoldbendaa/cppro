// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.Recharge;
import com.cedar.cp.api.model.recharge.RechargeCellDataVO;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.TreeModel;

public class RechargeImpl implements Recharge, Serializable, Cloneable {

   private transient TreeModel mAccountModel;
   private Object mAccountStructureElementRef;
   private EntityList mTableModelHeadings;
   private List<RechargeCellDataVO> mSelectedSourceCells;
   private List<RechargeCellDataVO> mSelectedTargetCells;
   private List<RechargeCellDataVO> mSelectedOffSetCells;
   private transient List mStructureElementModel;
   private transient List mDataTypeModel;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private String mReason;
   private String mReference;
   private BigDecimal mAllocationPercentage;
   private boolean mManualRatios;
   private int mAllocationDataTypeId;
   private boolean mDiffAccount;
   private int mAccountStructureId;
   private int mAccountStructureElementId;
   private int mRatioType;
   private int mVersionNum;
   private ModelRef mModelRef;


   public RechargeImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mReason = "";
      this.mReference = "";
      this.mAllocationPercentage = null;
      this.mManualRatios = false;
      this.mAllocationDataTypeId = 0;
      this.mDiffAccount = false;
      this.mAccountStructureId = 0;
      this.mAccountStructureElementId = 0;
      this.mRatioType = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (RechargePK)paramKey;
   }

   public void setPrimaryKey(RechargeCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getReason() {
      return this.mReason;
   }

   public String getReference() {
      return this.mReference;
   }

   public BigDecimal getAllocationPercentage() {
      return this.mAllocationPercentage;
   }

   public boolean isManualRatios() {
      return this.mManualRatios;
   }

   public int getAllocationDataTypeId() {
      return this.mAllocationDataTypeId;
   }

   public boolean isDiffAccount() {
      return this.mDiffAccount;
   }

   public int getAccountStructureId() {
      return this.mAccountStructureId;
   }

   public int getAccountStructureElementId() {
      return this.mAccountStructureElementId;
   }

   public int getRatioType() {
      return this.mRatioType;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setReason(String paramReason) {
      this.mReason = paramReason;
   }

   public void setReference(String paramReference) {
      this.mReference = paramReference;
   }

   public void setAllocationPercentage(BigDecimal paramAllocationPercentage) {
      this.mAllocationPercentage = paramAllocationPercentage;
   }

   public void setManualRatios(boolean paramManualRatios) {
      this.mManualRatios = paramManualRatios;
   }

   public void setAllocationDataTypeId(int paramAllocationDataTypeId) {
      this.mAllocationDataTypeId = paramAllocationDataTypeId;
   }

   public void setDiffAccount(boolean paramDiffAccount) {
      this.mDiffAccount = paramDiffAccount;
   }

   public void setAccountStructureId(int paramAccountStructureId) {
      this.mAccountStructureId = paramAccountStructureId;
   }

   public void setAccountStructureElementId(int paramAccountStructureElementId) {
      this.mAccountStructureElementId = paramAccountStructureElementId;
   }

   public void setRatioType(int paramRatioType) {
      this.mRatioType = paramRatioType;
   }

   public TreeModel getAccountTree() {
      return this.mAccountModel;
   }

   public void setAccountModel(TreeModel accountModel) {
      this.mAccountModel = accountModel;
   }

   public Object getAccountStructureElementRef() {
      return this.mAccountStructureElementRef;
   }

   public void setAccountStructureElementRef(Object accountStructureElementRef) {
      this.mAccountStructureElementRef = accountStructureElementRef;
   }

   public EntityList getTableModelHeadings() {
      return this.mTableModelHeadings;
   }

   public void setTableModelHeadings(EntityList tableModelHeadings) {
      this.mTableModelHeadings = tableModelHeadings;
   }

   public List<RechargeCellDataVO> getSelectedSourceCells() {
      if(this.mSelectedSourceCells == null) {
         this.mSelectedSourceCells = new ArrayList();
      }

      return this.mSelectedSourceCells;
   }

   public void addSourceCell(EntityRef[] refs) {
      if(this.mSelectedSourceCells == null) {
         this.mSelectedSourceCells = new ArrayList();
      }

      this.mSelectedSourceCells.add(new RechargeCellDataVO(refs));
   }

   public void setSelectedSourceCells(List<RechargeCellDataVO> selectedSourceCells) {
      this.mSelectedSourceCells = selectedSourceCells;
   }

   public List<RechargeCellDataVO> getSelectedTargetCells() {
      if(this.mSelectedTargetCells == null) {
         this.mSelectedTargetCells = new ArrayList();
      }

      return this.mSelectedTargetCells;
   }

   public void addTargetCell(EntityRef[] refs, BigDecimal ratio) {
      if(this.mSelectedTargetCells == null) {
         this.mSelectedTargetCells = new ArrayList();
      }

      this.mSelectedTargetCells.add(new RechargeCellDataVO(refs, ratio));
   }

   public void setSelectedTargetCells(List<RechargeCellDataVO> selectedTargetCells) {
      this.mSelectedTargetCells = selectedTargetCells;
   }

   public List<RechargeCellDataVO> getSelectedOffsetCells() {
      if(this.mSelectedOffSetCells == null) {
         this.mSelectedOffSetCells = new ArrayList();
      }

      return this.mSelectedOffSetCells;
   }

   public void addOffsetCell(EntityRef[] refs) {
      if(this.mSelectedOffSetCells == null) {
         this.mSelectedOffSetCells = new ArrayList();
      }

      this.mSelectedOffSetCells.add(new RechargeCellDataVO(refs));
   }

   public void setSelectedOffSetCells(List<RechargeCellDataVO> selectedOffSetCells) {
      this.mSelectedOffSetCells = selectedOffSetCells;
   }

   public TreeModel[] getCellPickerModel(boolean incDataType) {
      return null;
   }

   public TreeModel[] getCellPickerModel(boolean incDataType, boolean writeableDataTypes) {
      return null;
   }

   public List getStructureElementModel() {
      return this.mStructureElementModel;
   }

   public void setStructureElementModel(List structureElementModel) {
      this.mStructureElementModel = structureElementModel;
   }

   public List getDataTypeModel() {
      return this.mDataTypeModel;
   }

   public void setDataTypeModel(List dataTypeModel) {
      this.mDataTypeModel = dataTypeModel;
   }
}
