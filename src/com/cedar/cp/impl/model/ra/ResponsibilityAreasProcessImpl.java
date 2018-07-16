// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.ra;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.api.model.ModelEditor;
import com.cedar.cp.api.model.ModelEditorSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ra.ResponsibilityAreaEditorSession;
import com.cedar.cp.api.model.ra.ResponsibilityAreasProcess;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNode;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.ra.ResponsibilityAreaEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaEditorSessionImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaNodeImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaProxyNode;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import com.cedar.cp.util.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class ResponsibilityAreasProcessImpl extends BusinessProcessImpl implements ResponsibilityAreasProcess {

   private Log mLog = new Log(this.getClass());


   public ResponsibilityAreasProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ResponsibilityAreaEditorSessionServer es = new ResponsibilityAreaEditorSessionServer(this.getConnection());

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

   public ResponsibilityAreaEditorSession getResponsibilityAreaEditorSession(Object key) throws ValidationException {
      ResponsibilityAreaEditorSessionImpl sess = new ResponsibilityAreaEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllResponsibilityAreas() {
      try {
         return this.getConnection().getListHelper().getAllResponsibilityAreas();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllResponsibilityAreas", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ResponsibilityArea";
      return ret;
   }

   protected int getProcessID() {
      return 25;
   }

   public TreeModel getResponsibilityAreaHierarchy(EntityRef modelRef) throws ValidationException {
      return this.getResponsibilityAreaHierarchy(modelRef.getPrimaryKey());
   }

   public TreeModel getResponsibilityAreaHierarchy(Object modelKey) throws ValidationException {
      ModelEditorSession modelSession = this.getConnection().getModelsProcess().getModelEditorSession(modelKey);
      ModelEditor modelEditor = modelSession.getModelEditor();
      Model model = modelEditor.getModel();
      this.getConnection().getModelsProcess().terminateSession(modelSession);
      HierarchyRefImpl hRef = (HierarchyRefImpl)model.getBudgetHierarchyRef();
      if(hRef == null) {
         throw new ValidationException("Model has no budget hierarchy defined");
      } else {
         EntityList rootElem = this.getConnection().getListHelper().getRespAreaImmediateChildren(hRef.getHierarchyPK().getHierarchyId(), 0);
         ResponsibilityAreaNodeImpl raNode = new ResponsibilityAreaNodeImpl(this.getConnection(), rootElem.getRowData(0));
         raNode.setModelId(((ModelPK)model.getPrimaryKey()).getModelId());
         return new DefaultTreeModel(new OnDemandMutableTreeNode(raNode, ResponsibilityAreaProxyNode.CLASS_NAME));
      }
   }

   public Object queryRARootKey(Object key) throws ValidationException {
      if(key instanceof ModelRef) {
         key = ((ModelRef)key).getPrimaryKey();
      }

      if(key instanceof ModelPK) {
         TreeModel treeModel = this.getResponsibilityAreaHierarchy(key);
         DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
         ResponsibilityAreaNode rootNode = (ResponsibilityAreaNode)root.getUserObject();
         return rootNode.getRespAreaKey();
      } else {
         return key;
      }
   }
}
