// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.dwr;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import javax.servlet.http.HttpServletRequest;

public class CPBaseDWRRequest extends CPAction {

   protected CPConnection getCPConection(HttpServletRequest request) {
      CPConnection conn = null;

      try {
         conn = this.getCPContext(request).getCPConnection();
      } catch (Exception var4) {
         this.mLog.error("getCPConnection", "Error getting context", var4);
      }

      return conn;
   }

   protected CPContext getCPContextDWR(HttpServletRequest request) {
      CPContext conn = null;

      try {
         conn = this.getCPContext(request);
      } catch (Exception var4) {
         this.mLog.error("getCPConnection", "Error getting context", var4);
      }

      return conn;
   }

   protected UserContext getUserContext(HttpServletRequest request) {
      UserContext conn = null;

      try {
         conn = this.getCPContext(request).getUserContext();
      } catch (Exception var4) {
         this.mLog.error("getCPConnection", "Error getting context", var4);
      }

      return conn;
   }
}
