// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.udwp.WeightingProfileEditorSession;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.api.model.udwp.WeightingProfilesProcess;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.ejb.api.model.udwp.WeightingProfileEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.udwp.WeightingProfileEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import cppro.utils.DBUtils;

public class WeightingProfilesProcessImpl extends BusinessProcessImpl implements WeightingProfilesProcess {

   private Log mLog = new Log(this.getClass());


   public WeightingProfilesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      WeightingProfileEditorSessionServer es = new WeightingProfileEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public WeightingProfileEditorSession getWeightingProfileEditorSession(Object key) throws ValidationException {
      WeightingProfileEditorSessionImpl sess = new WeightingProfileEditorSessionImpl(this, key,DBUtils.userId);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllWeightingProfiles() {
      try {
         return this.getConnection().getListHelper().getAllWeightingProfiles();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllWeightingProfiles", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing WeightingProfile";
      return ret;
   }

   protected int getProcessID() {
      return 81;
   }

   public int[] getWeightingProfile(Object key) {
      WeightingProfileEditorSessionServer es = new WeightingProfileEditorSessionServer(this.getConnection());
      return es.getWeightingProfile(this.queryProfileId(key));
   }

   public Profile getStaticWeightingProfileDetail(Object key) {
      WeightingProfileEditorSessionServer es = new WeightingProfileEditorSessionServer(this.getConnection());
      return es.getWeightingProfileDetail(this.queryProfileId(key));
   }

   public Profile getStaticWeightingProfileDetail(Object key, Object calStructureElementKey) {
      WeightingProfileEditorSessionServer es = new WeightingProfileEditorSessionServer(this.getConnection());
      return es.getWeightingProfileDetail(this.queryProfileId(key), this.queryStructureElementId(calStructureElementKey));
   }

   public EntityList queryProfiles(Object modelKey, Object dataTypeKey, Object acctHierarchyKey, Object acctStructureElementKey, Object busHierarchyKey, Object busStructureElementKey, Object calHierarchyKey, Object calStructureElementKey) throws ValidationException {
      int modelId = this.queryModelId(modelKey);
      int dataTypeId = this.queryDataTypeId(dataTypeKey);
      int acctHierarchyId = this.queryHierarchyId(acctHierarchyKey);
      int acctStructureElementId = this.queryStructureElementId(acctStructureElementKey);
      int busHierarchyId = this.queryHierarchyId(busHierarchyKey);
      int busStructureElementId = this.queryStructureElementId(busStructureElementKey);
      int calHierarchyId = this.queryHierarchyId(calHierarchyKey);
      int calStructureElementId = this.queryStructureElementId(calStructureElementKey);
      WeightingProfileEditorSessionServer ss = new WeightingProfileEditorSessionServer(this.getConnection());
      return ss.queryProfiles(modelId, dataTypeId, acctHierarchyId, acctStructureElementId, busHierarchyId, busStructureElementId, calHierarchyId, calStructureElementId);
   }

   public EntityList queryProfiles(Object modelKey, int fromLevel, int toLevel, Object acctStructureElementKey, Object busStructureElementKey, String dataTypeVisId) throws ValidationException {
      int modelId = this.queryModelId(modelKey);
      int acctStructureElementId = this.queryStructureElementId(acctStructureElementKey);
      int busStructureElementId = this.queryStructureElementId(busStructureElementKey);
      WeightingProfileEditorSessionServer ss = new WeightingProfileEditorSessionServer(this.getConnection());
      return ss.queryProfiles(modelId, fromLevel, toLevel, acctStructureElementId, busStructureElementId, dataTypeVisId);
   }

   public EntityList queryDynamicWeightingFactors(Object financeCubeKey, Object[] structureElementIds, int targetElementId, String dataType, int targetFromLevel, int targetToLevel, Object calDimId) {
      int financeCubeId = this.queryFinanceCubeId(financeCubeKey);
      int[] seIds = new int[structureElementIds.length];

      for(int ss = 0; ss < structureElementIds.length; ++ss) {
         seIds[ss] = this.queryStructureElementId(structureElementIds[ss]);
      }

      WeightingProfileEditorSessionServer var11 = new WeightingProfileEditorSessionServer(this.getConnection());
      return var11.queryDynamicWeightingFactors(financeCubeId, seIds, targetElementId, dataType, targetFromLevel, targetToLevel, this.queryDimensionId(calDimId));
   }

   public Profile getProfileDetail(Object financeCubeKey, Object profileKey, Object[] addressKeys, String dataType) throws ValidationException {
      int financeCubeId = this.queryFinanceCubeId(financeCubeKey);
      int profileId = this.queryProfileId(profileKey);
      int[] address = new int[addressKeys.length];

      for(int ss = 0; ss < addressKeys.length; ss += 2) {
         address[ss] = this.queryHierarchyId(addressKeys[ss]);
         address[ss + 1] = this.queryStructureElementId(addressKeys[ss + 1]);
      }

      WeightingProfileEditorSessionServer ss1 = new WeightingProfileEditorSessionServer(this.getConnection());
      return ss1.getProfileDetail(financeCubeId, profileId, address, dataType);
   }

   private int queryFinanceCubeId(Object key) {
      if(key instanceof String) {
         key = this.getConnection().getEntityKeyFactory().getKeyFromTokens((String)key);
      }

      int financeCubeId;
      if(key instanceof FinanceCubePK) {
         financeCubeId = ((FinanceCubePK)key).getFinanceCubeId();
      } else if(key instanceof FinanceCubeRefImpl) {
         financeCubeId = ((FinanceCubeRefImpl)key).getFinanceCubePK().getFinanceCubeId();
      } else {
         if(!(key instanceof Number)) {
            throw new CPException("Unexpected key for finance cube:" + key);
         }

         financeCubeId = ((Integer)key).intValue();
      }

      return financeCubeId;
   }

   private int queryProfileId(Object key) {
      if(key instanceof String) {
         key = this.getConnection().getEntityKeyFactory().getKeyFromTokens((String)key);
      }

      int profileId;
      if(key instanceof WeightingProfilePK) {
         profileId = ((WeightingProfilePK)key).getProfileId();
      } else if(key instanceof WeightingProfileRefImpl) {
         profileId = ((WeightingProfileRefImpl)key).getWeightingProfilePK().getProfileId();
      } else {
         if(!(key instanceof Number)) {
            throw new CPException("Unexpected key for weighting profile:" + key);
         }

         profileId = ((Integer)key).intValue();
      }

      return profileId;
   }

   private int queryModelId(Object key) throws ValidationException {
      if(key instanceof String) {
         key = ModelPK.getKeyFromTokens((String)key);
      }

      int id;
      if(key instanceof ModelPK) {
         id = ((ModelPK)key).getModelId();
      } else if(key instanceof ModelRefImpl) {
         id = ((ModelRefImpl)key).getModelPK().getModelId();
      } else {
         if(!(key instanceof Integer)) {
            throw new ValidationException("Unexpected object for modelKey:" + key);
         }

         id = ((Integer)key).intValue();
      }

      return id;
   }

   private int queryHierarchyId(Object key) {
      if(key instanceof String) {
         key = HierarchyPK.getKeyFromTokens((String)key);
      }

      if(key instanceof HierarchyRef) {
         key = ((HierarchyRef)key).getPrimaryKey();
      }

      int id;
      if(key instanceof HierarchyPK) {
         id = ((HierarchyPK)key).getHierarchyId();
      } else if(key instanceof HierarchyRefImpl) {
         id = ((HierarchyRefImpl)key).getHierarchyPK().getHierarchyId();
      } else if(key instanceof Integer) {
         id = ((Integer)key).intValue();
      } else {
         if(!(key instanceof StructureElementRefImpl)) {
            throw new CPException("Unexpected object for hierarchy key:" + key);
         }

         id = ((StructureElementRefImpl)key).getStructureId();
      }

      return id;
   }

   private int queryDimensionId(Object key) {
      if(key instanceof String) {
         key = DimensionPK.getKeyFromTokens((String)key);
      }

      int id;
      if(key instanceof DimensionPK) {
         id = ((DimensionPK)key).getDimensionId();
      } else if(key instanceof DimensionRefImpl) {
         id = ((DimensionRefImpl)key).getDimensionPK().getDimensionId();
      } else {
         if(!(key instanceof Integer)) {
            throw new CPException("Unexpected object for dimension key:" + key);
         }

         id = ((Integer)key).intValue();
      }

      return id;
   }

   private int queryStructureElementId(Object key) {
      if(key instanceof String) {
         key = StructureElementPK.getKeyFromTokens((String)key);
      }

      int id;
      if(key instanceof StructureElementPK) {
         id = ((StructureElementPK)key).getStructureElementId();
      } else if(key instanceof StructureElementRefImpl) {
         id = ((StructureElementRefImpl)key).getStructureElementPK().getStructureElementId();
      } else {
         if(!(key instanceof Integer)) {
            throw new CPException("Unexpected object for structure element key:" + key);
         }

         id = ((Integer)key).intValue();
      }

      return id;
   }

   private int queryDataTypeId(Object key) throws ValidationException {
      if(key instanceof String) {
         key = DataTypePK.getKeyFromTokens((String)key);
      }

      int id;
      if(key instanceof DataTypePK) {
         id = ((DataTypePK)key).getDataTypeId();
      } else if(key instanceof DataTypeRefImpl) {
         id = ((DataTypeRefImpl)key).getDataTypePK().getDataTypeId();
      } else {
         if(!(key instanceof Integer)) {
            throw new ValidationException("Unexpected object for data type key:" + key);
         }

         id = ((Integer)key).intValue();
      }

      return id;
   }

   public WeightingProfileRef getCustomWeightingProfile() {
      return new WeightingProfileRefImpl(new WeightingProfilePK(0), "Custom");
   }
   
   public EntityList getAllWeightingProfilesForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllWeightingProfilesForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllWeightingProfiles", var2);
      }
   }
   
}
