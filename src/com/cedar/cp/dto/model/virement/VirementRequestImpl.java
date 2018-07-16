// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAuthPoint;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.model.virement.VirementRequest;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import com.cedar.cp.dto.model.virement.VirementGroupImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.model.virement.VirementRequestRefImpl;
import com.cedar.cp.dto.user.UserRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VirementRequestImpl implements VirementRequest, Serializable, Cloneable {

   private String mBudgetCycleVisId;
   private String mFinanceCubeVisId;
   private List<VirementGroup> mVirementGroups = new ArrayList();
   private Map<Object, VirementAuthPoint> mAuthorisationPoints = new HashMap();
   private Object mPrimaryKey;
   private int mFinanceCubeId;
   private int mBudgetCycleId;
   private int mRequestStatus;
   private int mUserId;
   private String mReason;
   private String mReference;
   private Timestamp mDateSubmitted;
   private int mBudgetActivityId;
   private int mVersionNum;
   private ModelRef mModelRef;
   private UserRef mOwningUserRef;


   public VirementRequestImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mFinanceCubeId = 0;
      this.mBudgetCycleId = 0;
      this.mRequestStatus = 0;
      this.mUserId = 0;
      this.mReason = "";
      this.mReference = "";
      this.mDateSubmitted = null;
      this.mBudgetActivityId = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (VirementRequestPK)paramKey;
   }

   public void setPrimaryKey(VirementRequestCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getRequestStatus() {
      return this.mRequestStatus;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getReason() {
      return this.mReason;
   }

   public String getReference() {
      return this.mReference;
   }

   public Timestamp getDateSubmitted() {
      return this.mDateSubmitted;
   }

   public int getBudgetActivityId() {
      return this.mBudgetActivityId;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public UserRef getOwningUserRef() {
      return this.mOwningUserRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setOwningUserRef(UserRef ref) {
      this.mOwningUserRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setFinanceCubeId(int paramFinanceCubeId) {
      this.mFinanceCubeId = paramFinanceCubeId;
   }

   public void setBudgetCycleId(int paramBudgetCycleId) {
      this.mBudgetCycleId = paramBudgetCycleId;
   }

   public void setRequestStatus(int paramRequestStatus) {
      this.mRequestStatus = paramRequestStatus;
   }

   public void setUserId(int paramUserId) {
      this.mUserId = paramUserId;
   }

   public void setReason(String paramReason) {
      this.mReason = paramReason;
   }

   public void setReference(String paramReference) {
      this.mReference = paramReference;
   }

   public void setDateSubmitted(Timestamp paramDateSubmitted) {
      this.mDateSubmitted = paramDateSubmitted;
   }

   public void setBudgetActivityId(int paramBudgetActivityId) {
      this.mBudgetActivityId = paramBudgetActivityId;
   }

   public List<VirementGroup> getVirementGroups() {
      return this.mVirementGroups;
   }

   public void setVirementGroups(List virementGroups) {
      this.mVirementGroups = virementGroups;
   }

   public VirementGroupImpl findGroup(Object key) {
      Iterator i = this.mVirementGroups.iterator();

      VirementGroupImpl group;
      do {
         if(!i.hasNext()) {
            return null;
         }

         group = (VirementGroupImpl)i.next();
      } while(key == null || group.getKey() == null || !group.getKey().equals(key));

      return group;
   }

   public void removeGroup(VirementGroupImpl group) {
      this.mVirementGroups.remove(group);
   }

   private void validateModelRef() throws ValidationException {
      if(this.mModelRef == null) {
         throw new ValidationException("A target model must be defined");
      }
   }

   private void validateFinanceCubeRef() throws ValidationException {
      if(this.mFinanceCubeId <= 0) {
         throw new ValidationException("A target finance cube must be defined");
      }
   }

   private void validateBudgetCycleRef() throws ValidationException {
      if(this.mBudgetCycleId <= 0) {
         throw new ValidationException("A budget cycle must be defined");
      }
   }

   private void validateReason() throws ValidationException {
      if(this.mReason == null || this.mReason.trim().length() == 0) {
         throw new ValidationException("A reason for the transfer must be supplied");
      }
   }

   public void validate() throws ValidationException {
      this.validateModelRef();
      this.validateFinanceCubeRef();
      this.validateBudgetCycleRef();
      this.validateReason();
      if(this.mVirementGroups != null && !this.mVirementGroups.isEmpty()) {
         Iterator i = this.mVirementGroups.iterator();

         while(i.hasNext()) {
            VirementGroupImpl group = (VirementGroupImpl)i.next();
            group.validate(this);
         }

      } else {
         throw new ValidationException("No virement details present");
      }
   }

   public int getModelId() {
      return ((ModelRefImpl)this.mModelRef).getModelPK().getModelId();
   }

   public int getRequestId() {
      if(this.mPrimaryKey == null) {
         return -1;
      } else if(this.mPrimaryKey instanceof VirementRequestPK) {
         return ((VirementRequestPK)this.mPrimaryKey).getRequestId();
      } else if(this.mPrimaryKey instanceof VirementRequestCK) {
         return ((VirementRequestCK)this.mPrimaryKey).getVirementRequestPK().getRequestId();
      } else {
         throw new IllegalStateException("Unexpected primary key class in VirementRequestImpl");
      }
   }

   public Map getAuthorisationPoints() {
      return this.mAuthorisationPoints;
   }

   public void setAuthorisationPoints(Map authorisationPoints) {
      this.mAuthorisationPoints = authorisationPoints;
   }

   public List getAllLines() {
      ArrayList l = new ArrayList();
      Iterator gIter = this.mVirementGroups.iterator();

      while(gIter.hasNext()) {
         VirementGroupImpl group = (VirementGroupImpl)gIter.next();
         l.addAll(group.getRows());
      }

      return l;
   }

   public List getLinesForBudgetLocation(int seId) {
      ArrayList l = new ArrayList();
      Iterator ggIter = this.mVirementGroups.iterator();

      while(ggIter.hasNext()) {
         VirementGroupImpl group = (VirementGroupImpl)ggIter.next();
         l.addAll(group.getLinesForBudgetLocation(seId));
      }

      return l;
   }

   public VirementAuthPointImpl addToAuthPoint(StructureElementRefImpl seRef, Collection lines) {
      VirementAuthPointImpl authPoint = null;

      VirementLineImpl line;
      for(Iterator lIter = lines.iterator(); lIter.hasNext(); authPoint = this.addToAuthPoint(seRef, line)) {
         line = (VirementLineImpl)lIter.next();
      }

      return authPoint;
   }

   public VirementAuthPointImpl addToAuthPoint(StructureElementRefImpl seRef, VirementLineImpl line) {
      VirementAuthPointImpl existingAuthPoint = (VirementAuthPointImpl)this.queryAuthPointForLine(line);
      if(existingAuthPoint != null) {
         existingAuthPoint.getLines().remove(line);
         if(existingAuthPoint.getLines().isEmpty()) {
            this.mAuthorisationPoints.remove(existingAuthPoint.getRAElement());
         }
      }

      VirementAuthPointImpl authPoint = this.getAuthPoint(seRef);
      if(authPoint == null) {
         HashSet lines = new HashSet();
         lines.add(line);
         authPoint = new VirementAuthPointImpl(this.getNewAuthPointKey(), seRef, (String)null, lines, new HashSet(), (UserRefImpl)null, 0, false);
         this.mAuthorisationPoints.put(authPoint.getRAElement(), authPoint);
      } else if(!authPoint.getLines().contains(line)) {
         authPoint.getLines().add(line);
      }

      return authPoint;
   }

   private VirementAuthPointPK getNewAuthPointKey() {
      int newKeyId = -1;
      Iterator apIter = this.mAuthorisationPoints.values().iterator();

      while(apIter.hasNext()) {
         VirementAuthPointImpl authPoint = (VirementAuthPointImpl)apIter.next();
         if(authPoint.getPK().getAuthPointId() < newKeyId) {
            newKeyId = authPoint.getPK().getAuthPointId() - 1;
         }
      }

      return new VirementAuthPointPK(newKeyId);
   }

   public Set getAllAuthPointSERefs() {
      return this.mAuthorisationPoints.keySet();
   }

   public VirementAuthPoint queryAuthPointForLine(VirementLine line) {
      Iterator aIter = this.mAuthorisationPoints.values().iterator();

      VirementAuthPointImpl authPoint;
      do {
         if(!aIter.hasNext()) {
            return null;
         }

         authPoint = (VirementAuthPointImpl)aIter.next();
      } while(!authPoint.getLines().contains(line));

      return authPoint;
   }

   public Set queryAuthPointsForGroup(VirementGroupImpl group) {
      HashSet result = new HashSet();
      Iterator lIter = group.getRows().iterator();

      while(lIter.hasNext()) {
         VirementLineImpl line = (VirementLineImpl)lIter.next();
         VirementAuthPointImpl authPoint = (VirementAuthPointImpl)this.queryAuthPointForLine(line);
         if(authPoint != null) {
            result.add(authPoint);
         }
      }

      return result;
   }

   public VirementAuthPointImpl getAuthPoint(StructureElementRefImpl seRef) {
      return (VirementAuthPointImpl)this.mAuthorisationPoints.get(seRef);
   }

   public void removeAuthPoint(VirementAuthPointImpl authPoint) {
      this.mAuthorisationPoints.remove(authPoint.getRAElement());
   }

   public VirementLineImpl getLineById(int lineId) {
      Iterator gIter = this.mVirementGroups.iterator();

      while(gIter.hasNext()) {
         VirementGroupImpl group = (VirementGroupImpl)gIter.next();
         Iterator lIter = group.getRows().iterator();

         while(lIter.hasNext()) {
            VirementLineImpl line = (VirementLineImpl)lIter.next();
            if(line.getPK() != null && line.getPK().getRequestLineId() == lineId) {
               return line;
            }
         }
      }

      return null;
   }

   public Set getAllAvailableAuthorisers() {
      HashSet result = new HashSet();
      Iterator apIter = this.getAuthorisationPoints().values().iterator();

      while(apIter.hasNext()) {
         VirementAuthPointImpl authPoint = (VirementAuthPointImpl)apIter.next();
         result.addAll(authPoint.getAvailableAuthorisers());
      }

      return result;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("VirementRequest");
      sb.append("\nprimaryKey:" + this.mPrimaryKey != null?this.mPrimaryKey.toString():"null");
      sb.append("\nfinanceCubeId:");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append("\nbudgetCycleId:");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append("\nrequestStatus:");
      sb.append(String.valueOf(this.mRequestStatus));
      sb.append("\nuserId:");
      sb.append(this.mUserId);
      sb.append("\nreason:");
      sb.append(this.mReason);
      sb.append("\nreference:");
      sb.append(this.mReference);
      sb.append("\nversionNum:");
      sb.append(this.mVersionNum);
      sb.append("\nmodelRef:" + this.mModelRef != null?this.mModelRef.toString():"null");
      sb.append("\nowningUserRef:" + this.mOwningUserRef != null?this.mOwningUserRef.toString():"null");
      Iterator apIter = this.mVirementGroups.iterator();

      while(apIter.hasNext()) {
         VirementGroupImpl authPoint = (VirementGroupImpl)apIter.next();
         sb.append(authPoint.toString());
      }

      apIter = this.mAuthorisationPoints.values().iterator();

      while(apIter.hasNext()) {
         VirementAuthPointImpl authPoint1 = (VirementAuthPointImpl)apIter.next();
         sb.append(authPoint1.toString());
      }

      return sb.toString();
   }

   public Map getVirementAuthPoints() {
      return this.mAuthorisationPoints;
   }

   public int getNextNewGroupNo() {
      int newGroupNo = -1;
      Iterator gIter = this.mVirementGroups.iterator();

      while(gIter.hasNext()) {
         VirementGroupImpl group = (VirementGroupImpl)gIter.next();
         if(group.getPK() != null && group.getPK().getRequestGroupId() <= newGroupNo) {
            newGroupNo = group.getPK().getRequestGroupId() - 1;
         }
      }

      return newGroupNo;
   }

   public int getGroupIdx(VirementGroupImpl targetGroup) {
      return this.mVirementGroups.indexOf(targetGroup);
   }

   public BudgetCycleRef getBudgetCycleRef() {
      return new BudgetCycleRefImpl(new BudgetCyclePK(this.mBudgetCycleId), this.mBudgetCycleVisId);
   }

   public FinanceCubeRef getFinanceCubeRef() {
      return new FinanceCubeRefImpl(new FinanceCubePK(this.mFinanceCubeId), this.mFinanceCubeVisId);
   }

   public void setBudgetCycleVisId(String budgetCycleVisId) {
      this.mBudgetCycleVisId = budgetCycleVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public VirementAuthPointImpl findAuthPoint(Object key) {
      Iterator apIter = this.mAuthorisationPoints.values().iterator();

      VirementAuthPointImpl virementAuthPoint;
      do {
         if(!apIter.hasNext()) {
            return null;
         }

         virementAuthPoint = (VirementAuthPointImpl)apIter.next();
      } while(!virementAuthPoint.getKey().equals(key));

      return virementAuthPoint;
   }

   public boolean allAuthPointAuthorised() {
      Iterator apIter = this.mAuthorisationPoints.values().iterator();

      VirementAuthPointImpl authPoint;
      do {
         if(!apIter.hasNext()) {
            return true;
         }

         authPoint = (VirementAuthPointImpl)apIter.next();
      } while(authPoint.getStatus() == 1);

      return false;
   }

   public VirementRequestRef getRequestRef() {
      return new VirementRequestRefImpl((VirementRequestCK)this.mPrimaryKey, String.valueOf(this.mReference));
   }
}
