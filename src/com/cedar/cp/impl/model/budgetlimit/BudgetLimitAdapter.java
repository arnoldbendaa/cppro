// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.budgetlimit;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.budgetlimit.BudgetLimit;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitEditorSessionImpl;

public class BudgetLimitAdapter implements BudgetLimit {

   private BudgetLimitImpl mEditorData;
   private BudgetLimitEditorSessionImpl mEditorSessionImpl;


   public BudgetLimitAdapter(BudgetLimitEditorSessionImpl e, BudgetLimitImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected BudgetLimitEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected BudgetLimitImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(BudgetLimitPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getBudgetLocationElementId() {
      return this.mEditorData.getBudgetLocationElementId();
   }

   public int getDim0() {
      return this.mEditorData.getDim0();
   }

   public int getDim1() {
      return this.mEditorData.getDim1();
   }

   public int getDim2() {
      return this.mEditorData.getDim2();
   }

   public int getDim3() {
      return this.mEditorData.getDim3();
   }

   public int getDim4() {
      return this.mEditorData.getDim4();
   }

   public int getDim5() {
      return this.mEditorData.getDim5();
   }

   public int getDim6() {
      return this.mEditorData.getDim6();
   }

   public int getDim7() {
      return this.mEditorData.getDim7();
   }

   public int getDim8() {
      return this.mEditorData.getDim8();
   }

   public int getDim9() {
      return this.mEditorData.getDim9();
   }

   public String getDataType() {
      return this.mEditorData.getDataType();
   }

   public Long getMinValue() {
      return this.mEditorData.getMinValue();
   }

   public Long getMaxValue() {
      return this.mEditorData.getMaxValue();
   }

   public FinanceCubeRef getFinanceCubeRef() {
      if(this.mEditorData.getFinanceCubeRef() != null) {
         if(this.mEditorData.getFinanceCubeRef().getNarrative() != null && this.mEditorData.getFinanceCubeRef().getNarrative().length() > 0) {
            return this.mEditorData.getFinanceCubeRef();
         } else {
            try {
               FinanceCubeRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getFinanceCubeEntityRef(this.mEditorData.getFinanceCubeRef());
               this.mEditorData.setFinanceCubeRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public ModelRef getModelRef() {
      if(this.mEditorData.getModelRef() != null) {
         if(this.mEditorData.getModelRef().getNarrative() != null && this.mEditorData.getModelRef().getNarrative().length() > 0) {
            return this.mEditorData.getModelRef();
         } else {
            try {
               ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getModelRef());
               this.mEditorData.setModelRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setFinanceCubeRef(FinanceCubeRef ref) {
      this.mEditorData.setFinanceCubeRef(ref);
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setBudgetLocationElementId(int p) {
      this.mEditorData.setBudgetLocationElementId(p);
   }

   public void setDim0(int p) {
      this.mEditorData.setDim0(p);
   }

   public void setDim1(int p) {
      this.mEditorData.setDim1(p);
   }

   public void setDim2(int p) {
      this.mEditorData.setDim2(p);
   }

   public void setDim3(int p) {
      this.mEditorData.setDim3(p);
   }

   public void setDim4(int p) {
      this.mEditorData.setDim4(p);
   }

   public void setDim5(int p) {
      this.mEditorData.setDim5(p);
   }

   public void setDim6(int p) {
      this.mEditorData.setDim6(p);
   }

   public void setDim7(int p) {
      this.mEditorData.setDim7(p);
   }

   public void setDim8(int p) {
      this.mEditorData.setDim8(p);
   }

   public void setDim9(int p) {
      this.mEditorData.setDim9(p);
   }

   public void setDataType(String p) {
      this.mEditorData.setDataType(p);
   }

   public void setMinValue(Long p) {
      this.mEditorData.setMinValue(p);
   }

   public void setMaxValue(Long p) {
      this.mEditorData.setMaxValue(p);
   }

   public String getDim0Txt() {
      String s = this.getStructureElementTxt(this.getDim0());
      this.mEditorData.setDim0Txt(s);
      return s;
   }

   public String getDim1Txt() {
      String s = this.getStructureElementTxt(this.getDim1());
      this.mEditorData.setDim1Txt(s);
      return s;
   }

   public String getDim2Txt() {
      String s = this.getStructureElementTxt(this.getDim2());
      this.mEditorData.setDim2Txt(s);
      return s;
   }

   public String getDim3Txt() {
      String s = this.getStructureElementTxt(this.getDim3());
      this.mEditorData.setDim3Txt(s);
      return s;
   }

   public String getDim4Txt() {
      String s = this.getStructureElementTxt(this.getDim4());
      this.mEditorData.setDim4Txt(s);
      return s;
   }

   public String getDim5Txt() {
      String s = this.getStructureElementTxt(this.getDim5());
      this.mEditorData.setDim5Txt(s);
      return s;
   }

   public String getDim6Txt() {
      String s = this.getStructureElementTxt(this.getDim6());
      this.mEditorData.setDim6Txt(s);
      return s;
   }

   public String getDim7Txt() {
      String s = this.getStructureElementTxt(this.getDim7());
      this.mEditorData.setDim7Txt(s);
      return s;
   }

   public String getDim8Txt() {
      String s = this.getStructureElementTxt(this.getDim8());
      this.mEditorData.setDim8Txt(s);
      return s;
   }

   public String getDim9Txt() {
      String s = this.getStructureElementTxt(this.getDim9());
      this.mEditorData.setDim9Txt(s);
      return s;
   }

   private String getStructureElementTxt(int id) {
      StringBuffer sb = new StringBuffer();
      if(id == 0) {
         return sb.toString();
      } else {
         EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getStructureElement(id);
         sb.append(list.getValueAt(0, "VisId"));
         if(sb.length() > 0) {
            sb.append(" - ");
         }

         sb.append(list.getValueAt(0, "Description"));
         return sb.toString();
      }
   }
}
