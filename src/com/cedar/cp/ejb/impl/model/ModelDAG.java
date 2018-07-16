// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelDAG extends AbstractDAG implements Serializable {

   private int mModelId;
   private String mName;
   private DimensionDAG mAccount;
   private DimensionDAG mCalendar;
   private List mBusinessDimensions;
   private transient List mDimensions;
   private transient ModelRefImpl mModelRef;


   public ModelDAG(DAGContext context, ModelEVO mevo) throws Exception {
      super(context, false);
      this.mModelId = mevo.getModelId();
      this.mName = mevo.getVisId();
      this.mAccount = context.getDimensionDAG(new DimensionPK(mevo.getAccountId()));
      this.mBusinessDimensions = new ArrayList();
      Iterator dimIter = mevo.getModelDimensionRels().iterator();

      while(dimIter.hasNext()) {
         ModelDimensionRelEVO mdrevo = (ModelDimensionRelEVO)dimIter.next();
         DimensionDAG dag = context.getDimensionDAG(new DimensionPK(mdrevo.getDimensionId()));
         this.mBusinessDimensions.add(dag);
      }

      this.mCalendar = context.getDimensionDAG(new DimensionPK(mevo.getCalendarId()));
      context.getCache().put(new ModelPK(this.mModelId), this);
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int value) {
      this.mModelId = value;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String value) {
      this.mName = value;
   }

   public DimensionDAG getAccount() {
      return this.mAccount;
   }

   public void setAccount(DimensionDAG value) {
      this.mAccount = value;
   }

   public DimensionDAG getCalendar() {
      return this.mCalendar;
   }

   public void setCalendar(DimensionDAG value) {
      this.mCalendar = value;
   }

   public ModelRefImpl getEntityRef() {
      if(this.mModelRef == null) {
         this.mModelRef = new ModelRefImpl(new ModelPK(this.mModelId), this.mName);
      }

      return this.mModelRef;
   }

   public List getBusinessDimensions() {
      return this.mBusinessDimensions;
   }

   public synchronized void setBusinessDimensions(List value) {
      this.mBusinessDimensions = value;
   }

   public int getNumDimensions() {
      int count = 0;
      if(this.mAccount != null) {
         ++count;
      }

      if(this.mCalendar != null) {
         ++count;
      }

      count += this.mBusinessDimensions.size();
      return count;
   }

   public List getDimensions() {
      if(this.mDimensions == null) {
         this.mDimensions.addAll(this.mBusinessDimensions);
         this.mDimensions.add(this.mAccount);
         this.mDimensions.add(this.mCalendar);
      }

      return this.mDimensions;
   }
}
