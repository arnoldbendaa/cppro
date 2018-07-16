// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.udeflookup;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookupEditorSession;
import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.api.udeflookup.UdefLookupsProcess;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.udeflookup.UdefLookupEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class UdefLookupsProcessImpl extends BusinessProcessImpl implements UdefLookupsProcess {

   private Log mLog = new Log(this.getClass());


   public UdefLookupsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      UdefLookupEditorSessionServer es = new UdefLookupEditorSessionServer(this.getConnection());

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

   public UdefLookupEditorSession getUdefLookupEditorSession(Object key) throws ValidationException {
      UdefLookupEditorSessionImpl sess = new UdefLookupEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllUdefLookups() {
      try {
         return this.getConnection().getListHelper().getAllUdefLookups();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllUdefLookups", var2);
      }
   }

   public EntityList getAllUdefLookupsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllUdefLookupsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllUdefLookupsLoggedUser", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing UdefLookup";
      return ret;
   }

   protected int getProcessID() {
      return 89;
   }

   public UdefLookupEditorSession getUdefLookupEditorSessionRef(EntityRef key) throws ValidationException {
      if(key instanceof UdefLookupRef) {
         UdefLookupRef ref = (UdefLookupRef)key;
         return this.getUdefLookupEditorSession(ref.getPrimaryKey());
      } else {
         return null;
      }
   }

   public EntityList getUdefForms(Object key) {
      try {
         UdefLookupEditorSessionServer e = new UdefLookupEditorSessionServer(this.getConnection());
         return e.getUdefForms(key);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get Udeflookup Form usage", var3);
      }
   }
}
