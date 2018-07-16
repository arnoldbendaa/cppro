// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedModelEditorSession;
import com.cedar.cp.api.model.mapping.MappedModelsProcess;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.ModelsProcessImpl;
import com.cedar.cp.impl.model.mapping.MappedModelEditorSessionImpl;
import com.cedar.cp.util.Log;
import java.util.List;

public class MappedModelsProcessImpl extends BusinessProcessImpl implements MappedModelsProcess {

   private Log mLog = new Log(this.getClass());
   private CPConnection connection;

   public MappedModelsProcessImpl(CPConnection connection) {
      super(connection);
      this.connection = connection;
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
	   ModelsProcessImpl mpi = new ModelsProcessImpl(connection);
	   MappedModelEditorSessionServer es = new MappedModelEditorSessionServer(this.getConnection());	   
	   int modelId = es.getModelId(primaryKey);
	   mpi.deleteObject(new ModelPK(modelId));
   }

   public MappedModelEditorSession getMappedModelEditorSession(Object key) throws ValidationException {
      MappedModelEditorSessionImpl sess = new MappedModelEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllMappedModels() {
      try {
         return this.getConnection().getListHelper().getAllMappedModels();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllMappedModels", var2);
      }
   }

   public EntityList getAllMappedModelsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllMappedModelsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get getAllMappedModelsForLoggedUser", var2);
      }
   }
   
   public EntityList getMappedFinanceCubes(int param1) {
      try {
         return this.getConnection().getListHelper().getMappedFinanceCubes(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get MappedFinanceCubes", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing MappedModel";
      return ret;
   }

   protected int getProcessID() {
      return 85;
   }

   public int issueMappedModelExportTask(int mappedModelId, String mappedModelVisId, List<EntityRef> financeCubes) throws ValidationException {
      MappedModelEditorSessionServer es = new MappedModelEditorSessionServer(this.getConnection());
      return es.issueMappedModelExportTask(mappedModelId, mappedModelVisId, financeCubes);
   }

   public int issueModelImportTask(boolean safeMode, int[] mappedModelIds) throws Exception {
      MappedModelEditorSessionServer es = new MappedModelEditorSessionServer(this.getConnection());
      return es.issueModelImportTask(safeMode, mappedModelIds);
   }
}
