// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.budgetinstruction;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstruction;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionEditor;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionAdapter;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BudgetInstructionEditorImpl extends BusinessEditorImpl implements BudgetInstructionEditor {

   private BudgetInstructionEditorSessionSSO mServerSessionData;
   private BudgetInstructionImpl mEditorData;
   private BudgetInstructionAdapter mEditorDataAdapter;


   public BudgetInstructionEditorImpl(BudgetInstructionEditorSessionImpl session, BudgetInstructionEditorSessionSSO serverSessionData, BudgetInstructionImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(BudgetInstructionEditorSessionSSO serverSessionData, BudgetInstructionImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
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

   public void setDocumentRef(String newDocumentRef) throws ValidationException {
      if(newDocumentRef != null) {
         newDocumentRef = StringUtils.rtrim(newDocumentRef);
      }

      this.validateDocumentRef(newDocumentRef);
      if(this.mEditorData.getDocumentRef() == null || !this.mEditorData.getDocumentRef().equals(newDocumentRef)) {
         this.setContentModified();
         this.mEditorData.setDocumentRef(newDocumentRef);
      }
   }

   public void setDocument(byte[] newDocument) throws ValidationException {
      this.validateDocument(newDocument);
      if(this.mEditorData.getDocument() == null || !this.mEditorData.getDocument().equals(newDocument)) {
         this.setContentModified();
         this.mEditorData.setDocument(newDocument);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a BudgetInstruction");
      }
   }

   public void validateDocumentRef(String newDocumentRef) throws ValidationException {
      if(newDocumentRef != null && newDocumentRef.length() > 255) {
         throw new ValidationException("length (" + newDocumentRef.length() + ") of DocumentRef must not exceed 255 on a BudgetInstruction");
      }
   }

   public void validateDocument(byte[] newDocument) throws ValidationException {}

   public BudgetInstruction getBudgetInstruction() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new BudgetInstructionAdapter((BudgetInstructionEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.getBudgetInstructionAssignments().size() < 1) {
         throw new ValidationException("Budget Instruction requires at least one assignment");
      }
   }

   public List getBudgetInstructionAssignments() {
      return this.mEditorData.getBudgetInstructionAssignments();
   }

   public BudgetInstructionAssignment getBudgetInstructionAssignmentItem(int id) {
      BudgetInstructionAssignmentPK pk = new BudgetInstructionAssignmentPK(id);
      return this.mEditorData.getBudgetInstructionAssignmentItem(pk);
   }

   public void setBudgetInstructionAssignmentItems(List l) {
      this.mEditorData.setBudgetInstructionAssignments(l);
   }

   public EntityList getAllModels() throws CPException {
      return this.getConnection().getListHelper().getAllModels();
   }
   
   public EntityList getAllModelsForLoggedUser() throws CPException {
      return this.getConnection().getListHelper().getAllModelsForLoggedUser();
   }

   public EntityList getAllBudgetCycles() throws CPException {
      return this.getConnection().getListHelper().getAllBudgetCycles();
   }

   public EntityList getAllBudgetHierarchies() {
      return this.getConnection().getListHelper().getAllBudgetHierarchies();
   }

   public EntityList getImmediateChildren(Object pk) {
      int elementId = ((StructureElementPK)pk).getStructureElementId();
      int structureId = ((StructureElementPK)pk).getStructureId();
      return this.getConnection().getListHelper().getImmediateChildren(structureId, elementId);
   }

   public boolean isModelSelected(EntityRef entityRef) {
      Iterator i = this.mEditorData.getBudgetInstructionAssignments().iterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            return false;
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(entityRef) || bia.getOwningBudgetCycleRef() != null || bia.getOwningBudgetLocationRef() != null);

      return true;
   }

   public boolean isBudgetCycleSelected(EntityRef owningModelRef, EntityRef entityRef) {
      Iterator i = this.mEditorData.getBudgetInstructionAssignments().iterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            return false;
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(owningModelRef) || bia.getOwningBudgetCycleRef() == null || !bia.getOwningBudgetCycleRef().equals(entityRef) || bia.getOwningBudgetLocationRef() != null);

      return true;
   }

   public boolean isResponsibilityAreaSelected(EntityRef owningModelRef, EntityRef entityRef) {
      Iterator i = this.mEditorData.getBudgetInstructionAssignments().iterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            return false;
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(owningModelRef) || bia.getOwningBudgetCycleRef() != null || bia.getOwningBudgetLocationRef() == null || !bia.getOwningBudgetLocationRef().equals(entityRef));

      return true;
   }

   public boolean isResponsibilityAreaAndChildrenSelected(EntityRef owningModelRef, EntityRef entityRef) {
      Iterator i = this.mEditorData.getBudgetInstructionAssignments().iterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            return false;
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(owningModelRef) || bia.getOwningBudgetCycleRef() != null || bia.getOwningBudgetLocationRef() == null || !bia.getOwningBudgetLocationRef().equals(entityRef) || !bia.getSelectChildren());

      return true;
   }

   public void addModel(EntityRef modelRef) {
      int id = 0 - (this.mEditorData.getBudgetInstructionAssignments().size() + 1);
      BudgetInstructionAssignmentImpl bia = new BudgetInstructionAssignmentImpl(new BudgetInstructionAssignmentPK(id));
      bia.setOwningModelRef((ModelRef)modelRef);
      this.mEditorData.addBudgetInstructionAssignmentItem(bia);
      this.setContentModified();
   }

   public void addBudgetCycle(EntityRef modelRef, EntityRef cycleRef) {
      int id = 0 - (this.mEditorData.getBudgetInstructionAssignments().size() + 1);
      BudgetInstructionAssignmentImpl bia = new BudgetInstructionAssignmentImpl(new BudgetInstructionAssignmentPK(id));
      bia.setOwningModelRef((ModelRef)modelRef);
      bia.setOwningBudgetCycleRef((BudgetCycleRef)cycleRef);
      this.mEditorData.addBudgetInstructionAssignmentItem(bia);
      this.setContentModified();
   }

   public void addResponsibilityArea(EntityRef modelRef, EntityRef respRef, List pathToRoot) {
      int id = 0 - (this.mEditorData.getBudgetInstructionAssignments().size() + 1);
      BudgetInstructionAssignmentImpl bia = new BudgetInstructionAssignmentImpl(new BudgetInstructionAssignmentPK(id));
      bia.setOwningModelRef((ModelRef)modelRef);
      bia.setOwningBudgetLocationRef((StructureElementRef)respRef);
      bia.setParents(pathToRoot);
      this.mEditorData.addBudgetInstructionAssignmentItem(bia);
      this.setContentModified();
   }

   public void addResponsibilityAreaPlusChildren(EntityRef modelRef, EntityRef respRef, List pathToRoot) {
      int id = 0 - (this.mEditorData.getBudgetInstructionAssignments().size() + 1);
      BudgetInstructionAssignmentImpl bia = new BudgetInstructionAssignmentImpl(new BudgetInstructionAssignmentPK(id));
      bia.setOwningModelRef((ModelRef)modelRef);
      bia.setOwningBudgetLocationRef((StructureElementRef)respRef);
      bia.setParents(pathToRoot);
      bia.setSelectChildren(true);
      this.mEditorData.addBudgetInstructionAssignmentItem(bia);
      this.setContentModified();
   }

   public void removeModel(EntityRef modelRef) {
      ListIterator i = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            throw new IllegalStateException(modelRef.getPrimaryKey() + " not found");
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(modelRef) || bia.getOwningBudgetCycleRef() != null || bia.getOwningBudgetLocationRef() != null);

      i.remove();
      this.setContentModified();
   }

   public void removeBudgetCycle(EntityRef modelRef, EntityRef cycleRef) {
      ListIterator i = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            throw new IllegalStateException(modelRef.getPrimaryKey() + " " + cycleRef.getPrimaryKey() + " not found");
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(modelRef) || bia.getOwningBudgetCycleRef() == null || !bia.getOwningBudgetCycleRef().equals(cycleRef) || bia.getOwningBudgetLocationRef() != null);

      i.remove();
      this.setContentModified();
   }

   public void removeResponsibilityArea(EntityRef modelRef, EntityRef respRef) {
      ListIterator i = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            throw new IllegalStateException(modelRef.getPrimaryKey() + " " + respRef.getPrimaryKey() + " not found");
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(modelRef) || bia.getOwningBudgetCycleRef() != null || bia.getOwningBudgetLocationRef() == null || !bia.getOwningBudgetLocationRef().equals(respRef));

      i.remove();
      this.setContentModified();
   }

   public boolean isChildSelected(EntityRef modelRef) {
      ListIterator i = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      BudgetInstructionAssignment bia;
      do {
         do {
            if(!i.hasNext()) {
               return false;
            }

            bia = (BudgetInstructionAssignment)i.next();
         } while(!bia.getOwningModelRef().equals(modelRef));
      } while(bia.getOwningBudgetCycleRef() == null && bia.getOwningBudgetLocationRef() == null);

      return true;
   }

   public boolean isChildSelected(EntityRef modelRef, EntityRef respRef) {
      ListIterator i = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      while(i.hasNext()) {
         BudgetInstructionAssignment bia = (BudgetInstructionAssignment)i.next();
         if(bia.getOwningModelRef().equals(modelRef) && bia.getOwningBudgetCycleRef() == null && bia.getOwningBudgetLocationRef() != null) {
            if(bia.getParents() == null) {
               return false;
            }

            if(bia.getParents().contains(respRef.getNarrative())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isBudgetCycleSelected(EntityRef modelRef) {
      ListIterator i = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      BudgetInstructionAssignment bia;
      do {
         if(!i.hasNext()) {
            return false;
         }

         bia = (BudgetInstructionAssignment)i.next();
      } while(!bia.getOwningModelRef().equals(modelRef) || bia.getOwningBudgetCycleRef() == null);

      return true;
   }

   public void removeBudgetInstructionAssignmentItem(Object key) {
      ListIterator iter = this.mEditorData.getBudgetInstructionAssignments().listIterator();

      BudgetInstructionAssignment item;
      do {
         if(!iter.hasNext()) {
            throw new RuntimeException("can\'t find " + key + " to remove");
         }

         item = (BudgetInstructionAssignment)iter.next();
      } while(!key.equals(item.getPrimaryKey()));

      iter.remove();
   }

   public void addModel(int paramOwningModelId) {
      ModelPK pk = new ModelPK(paramOwningModelId);
      ModelRefImpl ref = new ModelRefImpl(pk, "");
      this.addModel(ref);
   }

   public void addBudgetCycle(int paramOwningBudgetCycleId) {
      BudgetCyclePK pk = new BudgetCyclePK(paramOwningBudgetCycleId);
      BudgetCycleRefImpl ref = new BudgetCycleRefImpl(pk, "");
      this.addBudgetCycle((EntityRef)null, ref);
   }
}
