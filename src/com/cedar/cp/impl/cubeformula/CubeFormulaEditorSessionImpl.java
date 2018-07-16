// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cubeformula;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormulaEditor;
import com.cedar.cp.api.cubeformula.CubeFormulaEditorSession;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionSSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.cubeformula.CubeFormulaEditorImpl;
import com.cedar.cp.impl.cubeformula.CubeFormulasProcessImpl;
import com.cedar.cp.util.Log;

public class CubeFormulaEditorSessionImpl extends BusinessSessionImpl implements CubeFormulaEditorSession {

   protected CubeFormulaEditorSessionSSO mServerSessionData;
   protected CubeFormulaImpl mEditorData;
   protected CubeFormulaEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public CubeFormulaEditorSessionImpl(CubeFormulasProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get CubeFormula", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected CubeFormulaEditorSessionServer getSessionServer() throws CPException {
      return new CubeFormulaEditorSessionServer(this.getConnection());
   }

   public CubeFormulaEditor getCubeFormulaEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new CubeFormulaEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableFinanceCubes() {
      try {
         return this.getSessionServer().getAvailableFinanceCubes();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exception", var2);
      }
   }

   public EntityList getOwnershipRefs() {
      try {
         return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exception", var2);
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

   public void testCompileFormula(int financeCubeId, int cubeFormulaId, String formulaText, int formulaType) throws ValidationException {
      this.getSessionServer().testCompilerFormula(financeCubeId, cubeFormulaId, formulaText, formulaType);
   }
}
