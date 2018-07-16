// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemEditorSession;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.extsys.ExternalSystemsProcess;
import com.cedar.cp.api.extsys.TransferMonitor;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.extsys.ExternalSystemEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class ExternalSystemsProcessImpl extends BusinessProcessImpl implements ExternalSystemsProcess {

   private static final int FILE_TRANSFER_BLOCK_SIZE = 10240;
   private Log mLog = new Log(this.getClass());


   public ExternalSystemsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ExternalSystemEditorSessionServer es = new ExternalSystemEditorSessionServer(this.getConnection());

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

   public ExternalSystemEditorSession getExternalSystemEditorSession(Object key) throws ValidationException {
      ExternalSystemEditorSessionImpl sess = new ExternalSystemEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllExternalSystems() {
      try {
         return this.getConnection().getListHelper().getAllExternalSystems();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllExternalSystems", var2);
      }
   }

   public EntityList getAllGenericExternalSystems() {
      try {
         return this.getConnection().getListHelper().getAllGenericExternalSystems();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllGenericExternalSystems", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ExternalSystem";
      return ret;
   }

   protected int getProcessID() {
      return 86;
   }

   public int issueExternalSystemImportTask(ExternalSystemRef externalSystemRef, URL url, TransferMonitor monitor) throws ValidationException {
      ExternalSystemEditorSessionServer ss = new ExternalSystemEditorSessionServer(this.getConnection());
      if(url != null) {
         BufferedInputStream is = null;
         URL serverUrl = null;

         try {
            is = new BufferedInputStream(url.openStream());
            byte[] e = new byte[10240];
            int totalBytesTransferred = 0;
            boolean bytesRead = false;

            int bytesRead1;
            while((bytesRead1 = is.read(e)) > 0) {
               byte[] data = new byte[bytesRead1];
               System.arraycopy(e, 0, data, 0, bytesRead1);
               if(serverUrl == null) {
                  serverUrl = ss.initiateTransfer(url, externalSystemRef, data);
               } else {
                  ss.appendToFile(serverUrl, data);
               }

               totalBytesTransferred += data.length;
               if(monitor != null && !monitor.continueTransfer(totalBytesTransferred)) {
                  throw new ValidationException("User terminated transfer.");
               }
            }
         } catch (IOException var18) {
            throw new ValidationException("Failed to transfer file:" + var18.getMessage());
         } finally {
            try {
               is.close();
            } catch (IOException var17) {
               throw new ValidationException("Failed to close input stream:" + var17.getMessage());
            }
         }

         return ss.issueExternalSystemImportTask(this.getConnection().getUserContext().getUserId(), externalSystemRef, serverUrl.toExternalForm(), 0);
      } else {
         return ss.issueExternalSystemImportTask(this.getConnection().getUserContext().getUserId(), externalSystemRef, (String)null, 0);
      }
   }
}
