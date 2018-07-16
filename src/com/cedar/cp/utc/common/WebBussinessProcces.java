// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.BusinessSession;

public class WebBussinessProcces {

   public static final String ATTRIBUTE_ID = "cpWebProcess";
   private BusinessProcess mProcess;
   private BusinessSession mSession;
   private BusinessEditor mEditor;


   public WebBussinessProcces() {}

   public WebBussinessProcces(BusinessProcess process, BusinessSession session, BusinessEditor editor) {
      this.mProcess = process;
      this.mSession = session;
      this.mEditor = editor;
   }

   public BusinessProcess getProcess() {
      return this.mProcess;
   }

   public void setProcess(BusinessProcess process) {
      this.mProcess = process;
   }

   public BusinessSession getSession() {
      return this.mSession;
   }

   public void setSession(BusinessSession session) {
      this.mSession = session;
   }

   public BusinessEditor getEditor() {
      return this.mEditor;
   }

   public void setEditor(BusinessEditor editor) {
      this.mEditor = editor;
   }
}
