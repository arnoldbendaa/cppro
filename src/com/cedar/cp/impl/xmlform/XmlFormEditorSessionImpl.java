// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlFormEditor;
import com.cedar.cp.api.xmlform.XmlFormEditorSession;
import com.cedar.cp.api.xmlform.XmlFormWizardParameters;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class XmlFormEditorSessionImpl extends BusinessSessionImpl implements XmlFormEditorSession, com.cedar.cp.api.base.Destroyable {

   protected XmlFormEditorSessionSSO mServerSessionData;
   protected XmlFormImpl mEditorData;
   protected XmlFormEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public XmlFormEditorSessionImpl(XmlFormsProcessImpl process, Object key) throws ValidationException {
      super(process);
      //System.out.println("----- XmlFormEditorSessionImpl: create new XmlFormEditorSessionImpl()");
      
      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get XmlForm", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected XmlFormEditorSessionServer getSessionServer() throws CPException {
      return new XmlFormEditorSessionServer(this.getConnection());
   }

   public XmlFormEditor getXmlFormEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new XmlFormEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
 
   @SuppressWarnings("rawtypes")
   public void destroy(){
	   Iterator it = mActiveEditors.iterator();
	   while (it.hasNext()) {
		   it.next();
		   it.remove();
	   }
	   mActiveEditors = null;
   }
   
   public void terminate() { 
   }

   public Map invokeOnServer(List inputs) throws ValidationException {
      return this.getSessionServer().invokeOnServer(inputs);
   }

   public XmlFormWizardParameters getFinanceXMLFormWizardData(int financeCubeId) throws ValidationException {
      return this.getSessionServer().getFinanceXMLFormWizardData(financeCubeId);
   }
}
