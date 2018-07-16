// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.amm;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.amm.AmmCubeMap;
import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import com.cedar.cp.api.model.amm.AmmMap;
import com.cedar.cp.api.model.amm.AmmModelEditorSession;
import com.cedar.cp.api.model.amm.AmmModelMap;
import com.cedar.cp.api.model.amm.AmmModelsProcess;
import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.amm.AmmModelEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class AmmModelsProcessImpl extends BusinessProcessImpl implements AmmModelsProcess {

   private Log mLog = new Log(this.getClass());


   public AmmModelsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      AmmModelEditorSessionServer es = new AmmModelEditorSessionServer(this.getConnection());

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

   public AmmModelEditorSession getAmmModelEditorSession(Object key) throws ValidationException {
      AmmModelEditorSessionImpl sess = new AmmModelEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllAmmModels() {
      try {
         return this.getConnection().getListHelper().getAllAmmModels();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllAmmModels", var2);
      }
   }
   
   public EntityList getAllAmmModelsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllAmmModelsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get getAllAmmModelsForLoggedUsers", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing AmmModel";
      return ret;
   }

   protected int getProcessID() {
      return 91;
   }

   public int issueRebuildTask(List refreshList) throws ValidationException {
      AmmModelEditorSessionServer es = new AmmModelEditorSessionServer(this.getConnection());
      return es.issueAggregatedModelTask(this.getConnection().getUserContext().getUserId(), refreshList);
   }

   public TreeModel getAmmTreeModel() throws ValidationException {
      AmmModelEditorSessionServer es = new AmmModelEditorSessionServer(this.getConnection());
      AmmMap ammMap = es.getAmmMap();
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("Aggregated Model Mappings");
      Iterator model = ammMap.getModelMaps().iterator();

      while(model.hasNext()) {
         AmmModelMap m = (AmmModelMap)model.next();
         this.addModelMap(m, root);
      }

      DefaultTreeModel model1 = new DefaultTreeModel(root);
      return model1;
   }

   private void addModelMap(AmmModelMap m, DefaultMutableTreeNode parent) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(m);
      parent.add(node);
      if(parent.getUserObject() instanceof AmmModelMap) {
         DefaultMutableTreeNode i$ = new DefaultMutableTreeNode("cube feeds to " + ((AmmModelMap)parent.getUserObject()).getVisId());
         node.add(i$);
         Iterator x = m.getCubeMaps().iterator();

         while(x.hasNext()) {
            AmmCubeMap x1 = (AmmCubeMap)x.next();
            DefaultMutableTreeNode cnode = new DefaultMutableTreeNode(x1);
            i$.add(cnode);
            Iterator i$1 = x1.getDataTypeMaps().iterator();

            while(i$1.hasNext()) {
               AmmDataTypeMap y = (AmmDataTypeMap)i$1.next();
               DefaultMutableTreeNode dnode = new DefaultMutableTreeNode(y);
               cnode.add(dnode);
            }
         }
      }

      Iterator i$2 = m.getModelMaps().iterator();

      while(i$2.hasNext()) {
         AmmModelMap x2 = (AmmModelMap)i$2.next();
         this.addModelMap(x2, node);
      }

   }
}
