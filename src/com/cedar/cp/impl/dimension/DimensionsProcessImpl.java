// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionEditorSession;
import com.cedar.cp.api.dimension.DimensionImportFormat;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.DimensionsProcess;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.DimensionEditorSessionImpl;
import com.cedar.cp.impl.dimension.DimensionImportFormatImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class DimensionsProcessImpl extends BusinessProcessImpl implements DimensionsProcess {

   private Log mLog = new Log(this.getClass());


   public DimensionsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      DimensionEditorSessionServer es = new DimensionEditorSessionServer(this.getConnection());

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

   public DimensionEditorSession getDimensionEditorSession(Object key) throws ValidationException {
      DimensionEditorSessionImpl sess = new DimensionEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllDimensions() {
      try {
         return this.getConnection().getListHelper().getAllDimensions();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllDimensions", var2);
      }
   }
   
   public EntityList getDimensionsForLoggedUser() {
      try {
          return this.getConnection().getListHelper().getDimensionsForLoggedUser();
       } catch (Exception var2) {
          var2.printStackTrace();
          throw new RuntimeException("can\'t get AllDimensions", var2);
       }
    }

   public EntityList getAvailableDimensions() {
      try {
         return this.getConnection().getListHelper().getAvailableDimensions();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AvailableDimensions", var2);
      }
   }

   public EntityList getImportableDimensions() {
      try {
         return this.getConnection().getListHelper().getImportableDimensions();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get ImportableDimensions", var2);
      }
   }

   public EntityList getAllDimensionsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllDimensionsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllDimensionsForModel", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing Dimension";
      return ret;
   }

   protected int getProcessID() {
      return 10;
   }

   public DimensionImportFormat getDimensionImportFormat() throws CPException {
      return new DimensionImportFormatImpl(this);
   }

   public int getDimensionId(DimensionRef dimensionRef) {
      return ((DimensionRefImpl)dimensionRef).getDimensionPK().getDimensionId();
   }
}
