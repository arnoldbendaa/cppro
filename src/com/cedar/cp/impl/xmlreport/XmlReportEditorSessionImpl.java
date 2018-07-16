// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreport;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreport.XmlReportEditor;
import com.cedar.cp.api.xmlreport.XmlReportEditorSession;
import com.cedar.cp.dto.xmlreport.XmlReportEditorSessionSSO;
import com.cedar.cp.dto.xmlreport.XmlReportImpl;
import com.cedar.cp.ejb.api.xmlreport.XmlReportEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.xmlreport.XmlReportEditorImpl;
import com.cedar.cp.impl.xmlreport.XmlReportsProcessImpl;
import com.cedar.cp.util.Log;

public class XmlReportEditorSessionImpl extends BusinessSessionImpl implements XmlReportEditorSession {

   protected XmlReportEditorSessionSSO mServerSessionData;
   protected XmlReportImpl mEditorData;
   protected XmlReportEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public XmlReportEditorSessionImpl(XmlReportsProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get XmlReport", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected XmlReportEditorSessionServer getSessionServer() throws CPException {
      return new XmlReportEditorSessionServer(this.getConnection());
   }

   public XmlReportEditor getXmlReportEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new XmlReportEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}
}
