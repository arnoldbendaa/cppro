// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.distribution;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.api.report.distribution.DistributionEditor;
import com.cedar.cp.api.report.distribution.DistributionEditorSession;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionSSO;
import com.cedar.cp.dto.report.distribution.DistributionImpl;
import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.report.distribution.DistributionEditorImpl;
import com.cedar.cp.impl.report.distribution.DistributionsProcessImpl;
import com.cedar.cp.util.Log;

public class DistributionEditorSessionImpl extends BusinessSessionImpl implements DistributionEditorSession {

   protected DistributionEditorSessionSSO mServerSessionData;
   protected DistributionImpl mEditorData;
   protected DistributionEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public DistributionEditorSessionImpl(DistributionsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get Distribution", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected DistributionEditorSessionServer getSessionServer() throws CPException {
      return new DistributionEditorSessionServer(this.getConnection());
   }

   public DistributionEditor getDistributionEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new DistributionEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public DistributionDetails getDistributionDetailList(int modelId, int structure_element_id, EntityRef ref) {
      try {
         return this.getSessionServer().getDistributionDetailList(modelId, structure_element_id, ref);
      } catch (Exception var5) {
         throw new RuntimeException(var5.getMessage(), var5);
      }
   }
}
