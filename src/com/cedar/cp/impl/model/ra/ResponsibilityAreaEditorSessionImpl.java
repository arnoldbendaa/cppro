// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.ra;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ra.ResponsibilityAreaEditor;
import com.cedar.cp.api.model.ra.ResponsibilityAreaEditorSession;
import com.cedar.cp.api.model.virement.ResponsibilityAreasEditor;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaEditorSessionSSO;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.ejb.api.model.ra.ResponsibilityAreaEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaEditorImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreasProcessImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaNodeImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreasEditorImpl;
import com.cedar.cp.util.Log;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

public class ResponsibilityAreaEditorSessionImpl extends BusinessSessionImpl implements ResponsibilityAreaEditorSession {

   protected ResponsibilityAreaEditorSessionSSO mServerSessionData;
   protected ResponsibilityAreaImpl mEditorData;
   protected ResponsibilityAreaEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ResponsibilityAreaEditorSessionImpl(ResponsibilityAreasProcessImpl process, Object key) throws ValidationException {
      super(process);
      if(key instanceof ModelPK) {
         TreeModel e = null;

         try {
            e = process.getResponsibilityAreaHierarchy(key);
         } catch (ValidationException var8) {
            throw new CPException("Error getting Hierarchy", var8);
         }

         DefaultMutableTreeNode root = (DefaultMutableTreeNode)e.getRoot();
         ResponsibilityAreaNodeImpl rootNode = (ResponsibilityAreaNodeImpl)root.getUserObject();
         key = rootNode.getRespAreaKey();
      }

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new RuntimeException("Can\'t get ResponsibilityArea", var7);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ResponsibilityAreaEditorSessionServer getSessionServer() throws CPException {
      return new ResponsibilityAreaEditorSessionServer(this.getConnection());
   }

   public ResponsibilityAreaEditor getResponsibilityAreaEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ResponsibilityAreaEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableModels() {
      try {
         return this.getSessionServer().getAvailableModels();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public EntityList getOwnershipRefs() {
      try {
         return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
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

   public ResponsibilityAreasEditor getResponsibilityAreasEditor(Object key) throws ValidationException {
      if(key instanceof ResponsibilityAreaCK) {
         key = ((ResponsibilityAreaCK)key).getModelPK();
      }

      return new ResponsibilityAreasEditorImpl(this, (ModelPK)key);
   }
}
