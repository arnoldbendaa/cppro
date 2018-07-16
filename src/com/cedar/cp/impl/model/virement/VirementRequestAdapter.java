// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAuthPoint;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.model.virement.VirementRequest;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.virement.VirementRequestEditorSessionImpl;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class VirementRequestAdapter implements VirementRequest {

   private VirementRequestImpl mEditorData;
   private VirementRequestEditorSessionImpl mEditorSessionImpl;


   public VirementRequestAdapter(VirementRequestEditorSessionImpl e, VirementRequestImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected VirementRequestEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected VirementRequestImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(VirementRequestPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getFinanceCubeId() {
      return this.mEditorData.getFinanceCubeId();
   }

   public int getBudgetCycleId() {
      return this.mEditorData.getBudgetCycleId();
   }

   public int getRequestStatus() {
      return this.mEditorData.getRequestStatus();
   }

   public int getUserId() {
      return this.mEditorData.getUserId();
   }

   public String getReason() {
      return this.mEditorData.getReason();
   }

   public String getReference() {
      return this.mEditorData.getReference();
   }

   public Timestamp getDateSubmitted() {
      return this.mEditorData.getDateSubmitted();
   }

   public int getBudgetActivityId() {
      return this.mEditorData.getBudgetActivityId();
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

   public UserRef getOwningUserRef() {
      if(this.mEditorData.getOwningUserRef() != null) {
         try {
            UserRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getUserEntityRef(this.mEditorData.getOwningUserRef());
            this.mEditorData.setOwningUserRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setOwningUserRef(UserRef ref) {
      this.mEditorData.setOwningUserRef(ref);
   }

   public void setFinanceCubeId(int p) {
      this.mEditorData.setFinanceCubeId(p);
   }

   public void setBudgetCycleId(int p) {
      this.mEditorData.setBudgetCycleId(p);
   }

   public void setRequestStatus(int p) {
      this.mEditorData.setRequestStatus(p);
   }

   public void setUserId(int p) {
      this.mEditorData.setUserId(p);
   }

   public void setReason(String p) {
      this.mEditorData.setReason(p);
   }

   public void setReference(String p) {
      this.mEditorData.setReference(p);
   }

   public void setDateSubmitted(Timestamp p) {
      this.mEditorData.setDateSubmitted(p);
   }

   public void setBudgetActivityId(int p) {
      this.mEditorData.setBudgetActivityId(p);
   }

   public List<VirementGroup> getVirementGroups() {
      return this.mEditorData.getVirementGroups();
   }

   public Map getVirementAuthPoints() {
      return this.mEditorData.getAuthorisationPoints();
   }

   public BudgetCycleRef getBudgetCycleRef() {
      return this.mEditorData.getBudgetCycleRef();
   }

   public FinanceCubeRef getFinanceCubeRef() {
      return this.mEditorData.getFinanceCubeRef();
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }

   public int getRequestId() {
      return this.mEditorData.getRequestId();
   }

   public VirementRequestRef getRequestRef() {
      return this.mEditorData.getRequestRef();
   }

   public VirementAuthPoint queryAuthPointForLine(VirementLine line) {
      return this.mEditorData.queryAuthPointForLine(line);
   }
}
