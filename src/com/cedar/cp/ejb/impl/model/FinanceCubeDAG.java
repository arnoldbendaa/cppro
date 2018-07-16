// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAG;
import com.cedar.cp.ejb.impl.model.ModelEVO;

public class FinanceCubeDAG extends AbstractDAG {

   private int mFinanceCubeId;
   private String mName;
   private Integer mLockedByTaskId;
   private boolean mHasCells;
   private int mDataAccessLevel;
   private boolean mTemplateLocked;
   private int mVersionNum;
   private transient FinanceCubeRefImpl mFinanceCubeRef;


   public FinanceCubeDAG(DAGContext context, FinanceCubeEVO fcevo) throws Exception {
      super(context, false);
      this.mFinanceCubeId = fcevo.getFinanceCubeId();
      this.mName = fcevo.getVisId();
      this.mLockedByTaskId = fcevo.getLockedByTaskId();
      this.mHasCells = fcevo.getHasData();
      this.mTemplateLocked = fcevo.getLockedByTaskId() != null;
      this.mVersionNum = fcevo.getVersionNum();
      context.getCache().put(new FinanceCubePK(this.mFinanceCubeId), this);
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public Integer getLockedByTaskId() {
      return this.mLockedByTaskId;
   }

   public void setLockedByTaskId(Integer taskId) {
      this.mLockedByTaskId = taskId;
   }

   public boolean getHasCells() {
      return this.mHasCells;
   }

   public void setHasCells(boolean hasCells) {
      this.mHasCells = hasCells;
   }

   public int getDataAccessLevel() {
      return this.mDataAccessLevel;
   }

   public void setDataAccessLevel(int dataAccessLevel) {
      this.mDataAccessLevel = dataAccessLevel;
   }

   public boolean getTemplateLocked() {
      return this.mTemplateLocked;
   }

   public void setTemplateLocked(boolean templateLocked) {
      this.mTemplateLocked = templateLocked;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVersionNum(int versionNum) {
      this.mVersionNum = versionNum;
   }

   public FinanceCubeRefImpl getEntityRef() {
      return null;
   }

   public static FinanceCubeDAG getFinanceCube(DAGContext context, Object key) throws Exception {
      FinanceCubeDAG fcDAG = (FinanceCubeDAG)context.getCache().get(FinanceCubeDAG.class, key);
      if(fcDAG == null) {
         ModelEVO modelEVO = context.getModelAccessor().getDetails(key, "<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48>");
         new ModelDAG(context, modelEVO);
         fcDAG = (FinanceCubeDAG)context.getCache().get(FinanceCubeDAG.class, key);
         if(fcDAG == null) {
            throw new Exception("Failed to load finance cube:" + key);
         }
      }

      return fcDAG;
   }
}
