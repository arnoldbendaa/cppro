// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuild;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.swing.tree.TreeModel;

public class FormRebuildImpl implements FormRebuild, Serializable, Cloneable {

   private BudgetCycleRef mBudgetCycleRef;
   private XmlFormRef mXmlFormRef;
   private EntityRef[] mSelection;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private Timestamp mLastSubmit;
   private int mXmlformId;
   private int mBudgetCycleId;
   private int mStructureId0;
   private int mStructureId1;
   private int mStructureId2;
   private int mStructureId3;
   private int mStructureId4;
   private int mStructureId5;
   private int mStructureId6;
   private int mStructureId7;
   private int mStructureId8;
   private int mStructureElementId0;
   private int mStructureElementId1;
   private int mStructureElementId2;
   private int mStructureElementId3;
   private int mStructureElementId4;
   private int mStructureElementId5;
   private int mStructureElementId6;
   private int mStructureElementId7;
   private int mStructureElementId8;
   private String mDataType;
   private ModelRef mModelRef;


   public FormRebuildImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mLastSubmit = null;
      this.mXmlformId = 0;
      this.mBudgetCycleId = 0;
      this.mStructureId0 = 0;
      this.mStructureId1 = 0;
      this.mStructureId2 = 0;
      this.mStructureId3 = 0;
      this.mStructureId4 = 0;
      this.mStructureId5 = 0;
      this.mStructureId6 = 0;
      this.mStructureId7 = 0;
      this.mStructureId8 = 0;
      this.mStructureElementId0 = 0;
      this.mStructureElementId1 = 0;
      this.mStructureElementId2 = 0;
      this.mStructureElementId3 = 0;
      this.mStructureElementId4 = 0;
      this.mStructureElementId5 = 0;
      this.mStructureElementId6 = 0;
      this.mStructureElementId7 = 0;
      this.mStructureElementId8 = 0;
      this.mDataType = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (FormRebuildPK)paramKey;
   }

   public void setPrimaryKey(FormRebuildCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getStructureId0() {
      return this.mStructureId0;
   }

   public int getStructureId1() {
      return this.mStructureId1;
   }

   public int getStructureId2() {
      return this.mStructureId2;
   }

   public int getStructureId3() {
      return this.mStructureId3;
   }

   public int getStructureId4() {
      return this.mStructureId4;
   }

   public int getStructureId5() {
      return this.mStructureId5;
   }

   public int getStructureId6() {
      return this.mStructureId6;
   }

   public int getStructureId7() {
      return this.mStructureId7;
   }

   public int getStructureId8() {
      return this.mStructureId8;
   }

   public int getStructureElementId0() {
      return this.mStructureElementId0;
   }

   public int getStructureElementId1() {
      return this.mStructureElementId1;
   }

   public int getStructureElementId2() {
      return this.mStructureElementId2;
   }

   public int getStructureElementId3() {
      return this.mStructureElementId3;
   }

   public int getStructureElementId4() {
      return this.mStructureElementId4;
   }

   public int getStructureElementId5() {
      return this.mStructureElementId5;
   }

   public int getStructureElementId6() {
      return this.mStructureElementId6;
   }

   public int getStructureElementId7() {
      return this.mStructureElementId7;
   }

   public int getStructureElementId8() {
      return this.mStructureElementId8;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setLastSubmit(Timestamp paramLastSubmit) {
      this.mLastSubmit = paramLastSubmit;
   }

   public void setXmlformId(int paramXmlformId) {
      this.mXmlformId = paramXmlformId;
   }

   public void setBudgetCycleId(int paramBudgetCycleId) {
      this.mBudgetCycleId = paramBudgetCycleId;
   }

   public int[] getStructureIdArray() {
      return new int[]{this.getStructureId0(), this.getStructureId1(), this.getStructureId2(), this.getStructureId3(), this.getStructureId4(), this.getStructureId5(), this.getStructureId6(), this.getStructureId7(), this.getStructureId8()};
   }

   public void setStructureIdArray(int[] p) {
      this.setStructureId0(p[0]);
      this.setStructureId1(p[1]);
      this.setStructureId2(p[2]);
      this.setStructureId3(p[3]);
      this.setStructureId4(p[4]);
      this.setStructureId5(p[5]);
      this.setStructureId6(p[6]);
      this.setStructureId7(p[7]);
      this.setStructureId8(p[8]);
   }

   public void setStructureId0(int paramStructureId0) {
      this.mStructureId0 = paramStructureId0;
   }

   public void setStructureId1(int paramStructureId1) {
      this.mStructureId1 = paramStructureId1;
   }

   public void setStructureId2(int paramStructureId2) {
      this.mStructureId2 = paramStructureId2;
   }

   public void setStructureId3(int paramStructureId3) {
      this.mStructureId3 = paramStructureId3;
   }

   public void setStructureId4(int paramStructureId4) {
      this.mStructureId4 = paramStructureId4;
   }

   public void setStructureId5(int paramStructureId5) {
      this.mStructureId5 = paramStructureId5;
   }

   public void setStructureId6(int paramStructureId6) {
      this.mStructureId6 = paramStructureId6;
   }

   public void setStructureId7(int paramStructureId7) {
      this.mStructureId7 = paramStructureId7;
   }

   public void setStructureId8(int paramStructureId8) {
      this.mStructureId8 = paramStructureId8;
   }

   public int[] getStructureElementIdArray() {
      return new int[]{this.getStructureElementId0(), this.getStructureElementId1(), this.getStructureElementId2(), this.getStructureElementId3(), this.getStructureElementId4(), this.getStructureElementId5(), this.getStructureElementId6(), this.getStructureElementId7(), this.getStructureElementId8()};
   }

   public void setStructureElementIdArray(int[] p) {
      this.setStructureElementId0(p[0]);
      this.setStructureElementId1(p[1]);
      this.setStructureElementId2(p[2]);
      this.setStructureElementId3(p[3]);
      this.setStructureElementId4(p[4]);
      this.setStructureElementId5(p[5]);
      this.setStructureElementId6(p[6]);
      this.setStructureElementId7(p[7]);
      this.setStructureElementId8(p[8]);
   }

   public void setStructureElementId0(int paramStructureElementId0) {
      this.mStructureElementId0 = paramStructureElementId0;
   }

   public void setStructureElementId1(int paramStructureElementId1) {
      this.mStructureElementId1 = paramStructureElementId1;
   }

   public void setStructureElementId2(int paramStructureElementId2) {
      this.mStructureElementId2 = paramStructureElementId2;
   }

   public void setStructureElementId3(int paramStructureElementId3) {
      this.mStructureElementId3 = paramStructureElementId3;
   }

   public void setStructureElementId4(int paramStructureElementId4) {
      this.mStructureElementId4 = paramStructureElementId4;
   }

   public void setStructureElementId5(int paramStructureElementId5) {
      this.mStructureElementId5 = paramStructureElementId5;
   }

   public void setStructureElementId6(int paramStructureElementId6) {
      this.mStructureElementId6 = paramStructureElementId6;
   }

   public void setStructureElementId7(int paramStructureElementId7) {
      this.mStructureElementId7 = paramStructureElementId7;
   }

   public void setStructureElementId8(int paramStructureElementId8) {
      this.mStructureElementId8 = paramStructureElementId8;
   }

   public void setDataType(String paramDataType) {
      this.mDataType = paramDataType;
   }

   public int getModelId() {
      return 0;
   }

   public BudgetCycleRef getBudgetCycleRef() {
      return this.mBudgetCycleRef;
   }

   public void setBudgetCycleRef(BudgetCycleRef budgetCycleRef) {
      this.mBudgetCycleRef = budgetCycleRef;
   }

   public XmlFormRef getXmlFormRef() {
      return this.mXmlFormRef;
   }

   public void setXmlFormRef(XmlFormRef xmlFormRef) {
      this.mXmlFormRef = xmlFormRef;
   }

   public EntityRef[] getSelection() {
      return this.mSelection;
   }

   public void setSelection(EntityRef[] selection) {
      this.mSelection = selection;
   }

   public String getSelectionAsText() {
      StringBuilder sb = new StringBuilder();
      if(this.getSelection() != null) {
         EntityRef[] arr$ = this.getSelection();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            EntityRef ref = arr$[i$];
            if(sb.length() > 0) {
               sb.append(",");
            }

            sb.append(ref.getNarrative());
         }
      }

      return sb.toString();
   }

   public TreeModel[] getCellPickerModel() {
      return null;
   }
}
