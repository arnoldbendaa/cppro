package com.cedar.cp.impl.recalculate;

import java.util.List;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskEditor;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskEditorSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskEditorImpl;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskProcessImpl;
import com.cedar.cp.util.Log;

public class RecalculateBatchTaskEditorSessionImpl extends BusinessSessionImpl implements RecalculateBatchTaskEditorSession {

   protected RecalculateBatchTaskEditorSessionSSO mServerSessionData;
   protected RecalculateBatchTaskImpl mEditorData;
   protected RecalculateBatchTaskEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public RecalculateBatchTaskEditorSessionImpl(RecalculateBatchTaskProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException e) {
         throw e;
      } catch (Exception e) {
         throw new RuntimeException("Can\'t get RecalculateBatchTask", e);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected RecalculateBatchTaskEditorSessionServer getSessionServer() throws CPException {
      return new RecalculateBatchTaskEditorSessionServer(this.getConnection());
   }

   public RecalculateBatchTaskEditor getRecalculateBatchTaskEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new RecalculateBatchTaskEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
   
   public EntityList getAvailableModels() {
		try {
			return this.getSessionServer().getAvailableModels();
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
	}
   
   public EntityList getBudgetCyclesForModel(int modelId) {
		try {
			return this.getSessionServer().getBudgetCyclesForModel(modelId);
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
	}
   
   public EntityList getDataEntryProfile (int bcId, int modelId) {
		try {
			return this.getSessionServer().getDataEntryProfile(bcId, modelId);
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
	}

	public EntityList getHierarchiesForModel(int modelId) {
		try {
			return this.getSessionServer().getHierarchiesForModel(modelId);
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
	}

	public EntityList getWebModels() {
		try {
			return this.getSessionServer().getWebModels();
		} catch (Exception e) {
			throw new RuntimeException("unexpected exception", e);
		}
	}
}
