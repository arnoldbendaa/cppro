// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.impexp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.impexp.ImportExportHome;
import com.cedar.cp.ejb.api.impexp.ImportExportLocal;
import com.cedar.cp.ejb.api.impexp.ImportExportLocalHome;
import com.cedar.cp.ejb.api.impexp.ImportExportRemote;
import com.cedar.cp.util.Log;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ImportExportServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ImportExportRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ImportExportLocalHome";
   protected ImportExportRemote mRemote;
   protected ImportExportLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public ImportExportServer(CPConnection conn_) {
      super(conn_);
   }

   public ImportExportServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ImportExportRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            ImportExportHome e = (ImportExportHome)this.getHome(jndiName, ImportExportHome.class);
            this.mRemote = e.create();
         } catch (CreateException var3) {
            this.removeFromCache(jndiName);
            var3.printStackTrace();
            throw new CPException("getRemote " + jndiName + " CreateException", var3);
         } catch (RemoteException var4) {
            this.removeFromCache(jndiName);
            var4.printStackTrace();
            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
         }
      }

      return this.mRemote;
   }

   private ImportExportLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            ImportExportLocalHome e = (ImportExportLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public int issueImportTask(int financeCubeId, DataExtract dataExtract) throws ValidationException, CPException {
      if(this.isRemoteConnection()) {
         try {
            return this.getRemote().issueImportTask(financeCubeId, dataExtract);
         } catch (CreateException var4) {
            throw new CPException(var4.getMessage(), var4);
         } catch (RemoteException var5) {
            throw new CPException(var5.getMessage(), var5);
         }
      } else {
         return this.getLocal().issueImportTask(financeCubeId, dataExtract);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ImportExportRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ImportExportLocalHome";
   }
}
