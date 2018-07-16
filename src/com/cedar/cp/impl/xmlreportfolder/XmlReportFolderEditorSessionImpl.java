// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreportfolder;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditor;
import com.cedar.cp.api.xmlreportfolder.XmlReportFolderEditorSession;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderEditorSessionSSO;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderImpl;
import com.cedar.cp.ejb.api.xmlreportfolder.XmlReportFolderEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFolderEditorImpl;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFoldersProcessImpl;
import com.cedar.cp.util.Log;

public class XmlReportFolderEditorSessionImpl extends BusinessSessionImpl implements XmlReportFolderEditorSession {

   protected XmlReportFolderEditorSessionSSO mServerSessionData;
   protected XmlReportFolderImpl mEditorData;
   protected XmlReportFolderEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public XmlReportFolderEditorSessionImpl(XmlReportFoldersProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get XmlReportFolder", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected XmlReportFolderEditorSessionServer getSessionServer() throws CPException {
      return new XmlReportFolderEditorSessionServer(this.getConnection());
   }

   public XmlReportFolderEditor getXmlReportFolderEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new XmlReportFolderEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
