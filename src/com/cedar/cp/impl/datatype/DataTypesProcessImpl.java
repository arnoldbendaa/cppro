// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.datatype;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeEditorSession;
import com.cedar.cp.api.datatype.DataTypesProcess;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.datatype.DataTypeNodeImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.datatype.DataTypeEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

public class DataTypesProcessImpl extends BusinessProcessImpl implements DataTypesProcess {

   private Log mLog = new Log(this.getClass());


   public DataTypesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      DataTypeEditorSessionServer es = new DataTypeEditorSessionServer(this.getConnection());

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

   public DataTypeEditorSession getDataTypeEditorSession(Object key) throws ValidationException {
      DataTypeEditorSessionImpl sess = new DataTypeEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllDataTypes() {
      try {
         return this.getConnection().getListHelper().getAllDataTypes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllDataTypes", var2);
      }
   }

   public EntityList getAllDataTypesWeb() {
      try {
         return this.getConnection().getListHelper().getAllDataTypesWeb();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllDataTypesWeb", var2);
      }
   }

   public EntityList getAllDataTypeForFinanceCube(int param1) {
      try {
         return this.getConnection().getListHelper().getAllDataTypeForFinanceCube(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllDataTypeForFinanceCube", var3);
      }
   }

   public EntityList getAllDataTypesForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllDataTypesForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllDataTypesForModel", var3);
      }
   }

   public EntityList getDataTypesByType(int param1) {
      try {
         return this.getConnection().getListHelper().getDataTypesByType(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DataTypesByType", var3);
      }
   }

   public EntityList getDataTypesByTypeWriteable(int param1) {
      try {
         return this.getConnection().getListHelper().getDataTypesByTypeWriteable(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DataTypesByTypeWriteable", var3);
      }
   }

   public EntityList getDataTypeDependencies(short param1) {
      try {
         return this.getConnection().getListHelper().getDataTypeDependencies(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DataTypeDependencies", var3);
      }
   }

   public EntityList getDataTypesForImpExp() {
      try {
         return this.getConnection().getListHelper().getDataTypesForImpExp();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get DataTypesForImpExp", var2);
      }
   }

   public EntityList getDataTypeDetailsForVisID(String param1) {
      try {
         return this.getConnection().getListHelper().getDataTypeDetailsForVisID(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DataTypeDetailsForVisID", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing DataType";
      return ret;
   }

   protected int getProcessID() {
      return 19;
   }

   public TreeModel getSelectionTree(Integer financeCubeId) {
      EntityList eList;
      if(financeCubeId == null) {
         eList = this.getConnection().getListHelper().getAllDataTypes();
      } else {
         eList = this.getConnection().getListHelper().getAllDataTypesForFinanceCube(financeCubeId.intValue());
      }

      return this.buildTreeModelFromList(eList);
   }

   public TreeModel getSelectionTreeFromRef(FinanceCubeRef financeCuberef) {
      if(financeCuberef == null) {
         return this.getSelectionTree((Integer)null);
      } else {
         FinanceCubeCK ck = (FinanceCubeCK)financeCuberef.getPrimaryKey();
         FinanceCubePK pk = ck.getFinanceCubePK();
         return this.getSelectionTree(Integer.valueOf(pk.getFinanceCubeId()));
      }
   }

   public TreeModel getSelectionTree(Integer financeCubeId, int[] subTypes, boolean writeable) {
      EntityList eList = this.getConnection().getListHelper().getPickerDataTypesWeb(financeCubeId.intValue(), subTypes, writeable);
      return this.buildTreeModelFromList(eList);
   }

   public TreeModel getSelectionTreeFromRef(FinanceCubeRef financeCuberef, int[] subTypes, boolean writeable) {
      FinanceCubeCK ck = (FinanceCubeCK)financeCuberef.getPrimaryKey();
      FinanceCubePK pk = ck.getFinanceCubePK();
      return this.getSelectionTree(Integer.valueOf(pk.getFinanceCubeId()), subTypes, writeable);
   }

   private TreeModel buildTreeModelFromList(EntityList eList) {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Data Types");
      HashMap subTypes = new HashMap();
      int subType = 0;
      int size = eList.getNumRows();

      for(int i = 0; i < size; ++i) {
         DataTypeNodeImpl node = new DataTypeNodeImpl(eList.getRowData(i));
         if(i == 0 || subType != node.getSubType()) {
            subType = node.getSubType();
            subTypes.put(Integer.valueOf(subType), this.getSubTypeNode(subType));
            root.add((MutableTreeNode)subTypes.get(Integer.valueOf(subType)));
         }

         DefaultMutableTreeNode child = new DefaultMutableTreeNode(node);
         ((DefaultMutableTreeNode)subTypes.get(Integer.valueOf(subType))).add(child);
      }

      return new DefaultTreeModel(root);
   }

   private DefaultMutableTreeNode getSubTypeNode(int subType) {
      switch(subType) {
      case 1:
         return new DefaultMutableTreeNode("Temp Virement");
      case 2:
         return new DefaultMutableTreeNode("Perm Virement");
      case 3:
         return new DefaultMutableTreeNode("Virtual");
      case 4:
         return new DefaultMutableTreeNode("Measure");
      default:
         return new DefaultMutableTreeNode("Financial Value");
      }
   }
}
