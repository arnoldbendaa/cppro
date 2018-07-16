// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.systemproperty;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.systemproperty.SystemPropertyEditorSession;
import com.cedar.cp.api.systemproperty.SystemPropertysProcess;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.systemproperty.SystemPropertyEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.Properties;

public class SystemPropertysProcessImpl extends BusinessProcessImpl implements SystemPropertysProcess {

   private Log mLog = new Log(this.getClass());


   public SystemPropertysProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      SystemPropertyEditorSessionServer es = new SystemPropertyEditorSessionServer(this.getConnection());

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

   public SystemPropertyEditorSession getSystemPropertyEditorSession(Object key) throws ValidationException {
      SystemPropertyEditorSessionImpl sess = new SystemPropertyEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllSystemPropertys() {
      try {
         return this.getConnection().getListHelper().getAllSystemPropertys();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllSystemPropertys", var2);
      }
   }

   public EntityList getAllSystemPropertysUncached() {
      try {
         return this.getConnection().getListHelper().getAllSystemPropertysUncached();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllSystemPropertysUncached", var2);
      }
   }

   public EntityList getAllMailProps() {
      try {
         return this.getConnection().getListHelper().getAllMailProps();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllMailProps", var2);
      }
   }

   public EntityList getSystemProperty(String param1) {
      try {
         return this.getConnection().getListHelper().getSystemProperty(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get SystemProperty", var3);
      }
   }

   public EntityList getWebSystemProperty(String param1) {
      try {
         return this.getConnection().getListHelper().getWebSystemProperty(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get WebSystemProperty", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing SystemProperty";
      return ret;
   }

   protected int getProcessID() {
      return 43;
   }

   public Properties getSystemPropertys() {
      Properties p = new Properties();
      EntityList list = this.getAllSystemPropertys();
      String key = "";
      String value = "";

      for(int i = 0; i < list.getNumRows(); ++i) {
         key = (String)list.getValueAt(i, "SystemProperty");
         value = (String)list.getValueAt(i, "Value");
         p.put(key, value);
      }

      return p;
   }
}
