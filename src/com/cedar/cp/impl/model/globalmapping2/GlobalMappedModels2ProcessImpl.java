package com.cedar.cp.impl.model.globalmapping2;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2EditorSession;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModels2Process;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.ModelsProcessImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModel2EditorSessionImpl;
import com.cedar.cp.util.Log;
import java.util.List;

public class GlobalMappedModels2ProcessImpl extends BusinessProcessImpl implements GlobalMappedModels2Process {

   private Log mLog = new Log(this.getClass());
   private CPConnection connection;

   public GlobalMappedModels2ProcessImpl(CPConnection connection) {
      super(connection);
      this.connection = connection;
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
	   ModelsProcessImpl mpi = new ModelsProcessImpl(connection);	   
	   GlobalMappedModel2EditorSessionServer es = new GlobalMappedModel2EditorSessionServer(this.getConnection());	   
	   int modelId = es.getModelId(primaryKey);
	   mpi.deleteObject(new ModelPK(modelId));
   }

   public GlobalMappedModel2EditorSession getMappedModelEditorSession(Object key) throws ValidationException {
      GlobalMappedModel2EditorSessionImpl sess = new GlobalMappedModel2EditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllGlobalMappedModels2() {
      try {
         return this.getConnection().getListHelper().getAllGlobalMappedModels2();
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("can\'t get AllGlobalMappedModels2", e);
      }
   }
   
   public EntityList getAllGlobalMappedModelsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllGlobalMappedModelsForLoggedUser();
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("can\'t get getAllGlobalMappedModelsForLoggedUser", e);
      }
   }

   public EntityList getMappedFinanceCubes(int param1) {
      try {
         return this.getConnection().getListHelper().getMappedFinanceCubes(param1);
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("can\'t get MappedFinanceCubes", e);
      }
   }

   public String getProcessName() {
      String ret = "Processing MappedModel";
      return ret;
   }

   protected int getProcessID() {
      return 100;
   }

   public int issueMappedModelExportTask(int mappedModelId, String mappedModelVisId, List<EntityRef> financeCubes) throws ValidationException {
	  GlobalMappedModel2EditorSessionServer es = new GlobalMappedModel2EditorSessionServer(this.getConnection());
      return es.issueMappedModelExportTask(mappedModelId, mappedModelVisId, financeCubes);
   }

   public int issueModelImportTask(boolean safeMode, int[] mappedModelIds) throws Exception {
	  GlobalMappedModel2EditorSessionServer es = new GlobalMappedModel2EditorSessionServer(this.getConnection());
      return es.issueModelImportTask(safeMode, mappedModelIds);
   }

   public boolean isGlobalMappedModel(int modelId) {
       try {
           GlobalMappedModel2EditorSessionServer es = new GlobalMappedModel2EditorSessionServer(this.getConnection());
           return es.isGlobalMappedModel(modelId);
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException("can\'t check GlobalMappedModel", e);
       }
   }
}
