// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.udwp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.udwp.WeightingProfileEditorSessionHome;
import com.cedar.cp.ejb.api.model.udwp.WeightingProfileEditorSessionLocal;
import com.cedar.cp.ejb.api.model.udwp.WeightingProfileEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.udwp.WeightingProfileEditorSessionRemote;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class WeightingProfileEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/WeightingProfileEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/WeightingProfileEditorSessionLocalHome";
   protected WeightingProfileEditorSessionRemote mRemote;
   protected WeightingProfileEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static WeightingProfileEditorSessionSEJB server = new WeightingProfileEditorSessionSEJB();

   public WeightingProfileEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public WeightingProfileEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private WeightingProfileEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            WeightingProfileEditorSessionHome e = (WeightingProfileEditorSessionHome)this.getHome(jndiName, WeightingProfileEditorSessionHome.class);
//            this.mRemote = e.create();
//         } catch (CreateException var3) {
//            this.removeFromCache(jndiName);
//            var3.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " CreateException", var3);
//         } catch (RemoteException var4) {
//            this.removeFromCache(jndiName);
//            var4.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//         }
//      }
//
//      return this.mRemote;
	   return this.server;
   }

   private WeightingProfileEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            WeightingProfileEditorSessionLocalHome e = (WeightingProfileEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return this.server;
   }

   public void removeSession() throws CPException {}

   public void delete(Object primaryKey_) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(this.getUserId(), primaryKey_);
         } else {
            this.getLocal().delete(this.getUserId(), primaryKey_);
         }

         if(timer != null) {
            timer.logDebug("delete", primaryKey_.toString());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public EntityList getAvailableModels() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      EntityList ret = this.getConnection().getListHelper().getAllModels();
      if(timer != null) {
         timer.logDebug("getModelList", "");
      }

      return ret;
   }

   public EntityList getOwnershipRefs(Object pk_) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getOwnershipData(this.getUserId(), pk_);
         } else {
            e = this.getLocal().getOwnershipData(this.getUserId(), pk_);
         }

         if(timer != null) {
            timer.logDebug("getOwnershipRefs", pk_ != null?pk_.toString():"null");
         }

         return e;
      } catch (Exception var4) {
         throw new CPException("unable to getOwnershipRefs(" + pk_ + ") from server " + var4.getMessage(), var4);
      }
   }

   public WeightingProfileEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingProfileEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getNewItemData(this.getUserId());
         } else {
            e = this.getLocal().getNewItemData(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("getNewItemData", "");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public WeightingProfileEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingProfileEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getItemData(this.getUserId(), key);
         } else {
            e = this.getLocal().getItemData(this.getUserId(), key);
         }

         if(timer != null) {
            timer.logDebug("getItemData", key.toString());
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public WeightingProfileCK insert(WeightingProfileImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingProfileCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new WeightingProfileEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new WeightingProfileEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public WeightingProfileCK copy(WeightingProfileImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         WeightingProfileCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new WeightingProfileEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new WeightingProfileEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(WeightingProfileImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new WeightingProfileEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new WeightingProfileEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public Object[][] queryWeightingInfo(ModelRef modelRef) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Object[][] e = (Object[][])null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryWeightingInfo(this.getUserId(), modelRef);
         } else {
            e = this.getLocal().queryWeightingInfo(this.getUserId(), modelRef);
         }

         if(timer != null) {
            timer.logDebug("queryWeightingInfo");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public EntityList queryProfiles(int modelId, int fromLevel, int toLevel, int acctStructureElementId, int busStructureElementId, String dataTypeId) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryProfiles(modelId, fromLevel, toLevel, acctStructureElementId, busStructureElementId, dataTypeId);
         } else {
            e = this.getLocal().queryProfiles(modelId, fromLevel, toLevel, acctStructureElementId, busStructureElementId, dataTypeId);
         }

         if(timer != null) {
            timer.logDebug("queryProfiles");
         }

         return e;
      } catch (Exception var9) {
         throw this.unravelFatalException(var9);
      }
   }

   public EntityList queryProfiles(int modelId, int dataTypeId, int acctStructureId, int acctStructureElementId, int busStructureId, int busStructureElementId, int calStructureId, int calStructureElementId) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryProfiles(modelId, dataTypeId, acctStructureId, acctStructureElementId, busStructureId, busStructureElementId, calStructureId, calStructureElementId);
         } else {
            e = this.getLocal().queryProfiles(modelId, dataTypeId, acctStructureId, acctStructureElementId, busStructureId, busStructureElementId, calStructureId, calStructureElementId);
         }

         if(timer != null) {
            timer.logDebug("queryProfiles( budget transfers version )");
         }

         return e;
      } catch (Exception var11) {
         throw this.unravelFatalException(var11);
      }
   }

   public int[] getWeightingProfile(int profileId) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int[] e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getWeightingProfile(profileId);
         } else {
            e = this.getLocal().getWeightingProfile(profileId);
         }

         if(timer != null) {
            timer.logDebug("getWeightingProfile");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelFatalException(var4);
      }
   }

   public Profile getWeightingProfileDetail(int profileId) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Profile e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getWeightingProfileDetail(profileId);
         } else {
            e = this.getLocal().getWeightingProfileDetail(profileId);
         }

         if(timer != null) {
            timer.logDebug("getStaticWeightingProfileDetail");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelFatalException(var4);
      }
   }

   public Profile getWeightingProfileDetail(int profileId, int calendar_structure_element_id) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Profile e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getWeightingProfileDetail(profileId, calendar_structure_element_id);
         } else {
            e = this.getLocal().getWeightingProfileDetail(profileId, calendar_structure_element_id);
         }

         if(timer != null) {
            timer.logDebug("getStaticWeightingProfileDetail - for calendar element");
         }

         return e;
      } catch (Exception var5) {
         throw this.unravelFatalException(var5);
      }
   }

   public EntityList queryDynamicWeightingFactors(int financeCubeId, int[] structureElementIds, int targetElementId, String dataType, int targetFromLevel, int targetToLevel, int calDimId) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryDynamicWeightingFactors(financeCubeId, structureElementIds, targetElementId, dataType, targetFromLevel, targetToLevel, calDimId);
         } else {
            e = this.getLocal().queryDynamicWeightingFactors(financeCubeId, structureElementIds, targetElementId, dataType, targetFromLevel, targetToLevel, calDimId);
         }

         if(timer != null) {
            timer.logDebug("queryDynamicWeightingFactors");
         }

         return e;
      } catch (Exception var10) {
         throw this.unravelFatalException(var10);
      }
   }

   public Profile getProfileDetail(int financeCubeId, int profileId, int[] address, String dataType) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Profile e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getProfileDetail(financeCubeId, profileId, address, dataType);
         } else {
            e = this.getLocal().getProfileDetail(financeCubeId, profileId, address, dataType);
         }

         if(timer != null) {
            timer.logDebug("getProfileDetail( virement version )");
         }

         return e;
      } catch (Exception var7) {
         throw this.unravelFatalException(var7);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/WeightingProfileEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/WeightingProfileEditorSessionLocalHome";
   }
}
