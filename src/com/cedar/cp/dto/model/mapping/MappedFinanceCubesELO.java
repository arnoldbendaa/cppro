// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedFinanceCubeRef;
import com.cedar.cp.api.model.mapping.MappedModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MappedFinanceCubesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"MappedModel", "MappedCalendarYear", "MappedCalendarElement", "MappedFinanceCube", "MappedDataType", "MappedDimension", "MappedDimensionElement", "MappedHierarchy", "ExternalSystem", "MappedFinanceCube", "FinanceCube", "Model", "Model", "ExternalSystem"};
   private transient MappedModelRef mMappedModelEntityRef;
   private transient ExternalSystemRef mExternalSystemEntityRef;
   private transient MappedFinanceCubeRef mMappedFinanceCubeEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mMappedModelId;
   private transient int mSystemType;
   private transient int mModelId;
   private transient int mMappedFinanceCubeId;
   private transient String mVisId;
   private transient String mDescription;


   public MappedFinanceCubesELO() {
      super(new String[]{"MappedModel", "ExternalSystem", "MappedFinanceCube", "FinanceCube", "Model", "MappedModelId", "SystemType", "ModelId", "MappedFinanceCubeId", "VisId", "Description"});
   }

   public void add(MappedModelRef eRefMappedModel, ExternalSystemRef eRefExternalSystem, MappedFinanceCubeRef eRefMappedFinanceCube, FinanceCubeRef eRefFinanceCube, ModelRef eRefModel, int col1, int col2, int col3, int col4, String col5, String col6) {
      ArrayList l = new ArrayList();
      l.add(eRefMappedModel);
      l.add(eRefExternalSystem);
      l.add(eRefMappedFinanceCube);
      l.add(eRefFinanceCube);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(new Integer(col4));
      l.add(col5);
      l.add(col6);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mMappedModelEntityRef = (MappedModelRef)l.get(index);
      this.mExternalSystemEntityRef = (ExternalSystemRef)l.get(var4++);
      this.mMappedFinanceCubeEntityRef = (MappedFinanceCubeRef)l.get(var4++);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mMappedModelId = ((Integer)l.get(var4++)).intValue();
      this.mSystemType = ((Integer)l.get(var4++)).intValue();
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mMappedFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public MappedModelRef getMappedModelEntityRef() {
      return this.mMappedModelEntityRef;
   }

   public ExternalSystemRef getExternalSystemEntityRef() {
      return this.mExternalSystemEntityRef;
   }

   public MappedFinanceCubeRef getMappedFinanceCubeEntityRef() {
      return this.mMappedFinanceCubeEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getMappedModelId() {
      return this.mMappedModelId;
   }

   public int getSystemType() {
      return this.mSystemType;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getMappedFinanceCubeId() {
      return this.mMappedFinanceCubeId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
