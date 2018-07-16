// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.FinanceCubeEditor;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.FinanceCubeEditorImpl;
import com.cedar.cp.impl.model.FinanceCubesProcessImpl;
import com.cedar.cp.util.Log;
import java.util.ArrayList;
import java.util.Set;

public class FinanceCubeEditorSessionImpl extends BusinessSessionImpl implements FinanceCubeEditorSession {

   protected FinanceCubeEditorSessionSSO mServerSessionData;
   protected FinanceCubeImpl mEditorData;
   protected FinanceCubeEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public FinanceCubeEditorSessionImpl(FinanceCubesProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get FinanceCube", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected FinanceCubeEditorSessionServer getSessionServer() throws CPException {
      return new FinanceCubeEditorSessionServer(this.getConnection());
   }

   public FinanceCubeEditor getFinanceCubeEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new FinanceCubeEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public DataTypeRef[] getAvailableDataTypeRefs() {
      try {
         EntityList e = this.getConnection().getListHelper().getDataTypesByType(0);
         ArrayList l = new ArrayList();

         for(int data = 0; data < e.getNumRows(); ++data) {
            DataTypeRefImpl i = (DataTypeRefImpl)e.getValueAt(data, "DataType");
            l.add(i);
         }

         DataTypeRef[] var6 = new DataTypeRef[l.size()];

         for(int var7 = 0; var7 < var6.length; ++var7) {
            var6[var7] = (DataTypeRef)l.get(var7);
         }

         return var6;
      } catch (Exception var5) {
         throw new RuntimeException(var5.getMessage());
      }
   }

   public EntityList getAvailableDataTypes() {
      EntityList all = this.getConnection().getListHelper().getAllDataTypes();
      Set selected = this.mEditorData.getSelectedDataTypeRefs().keySet();
      AllDataTypesELO avail = new AllDataTypesELO();

      for(int i = 0; i < all.getNumRows(); ++i) {
         DataTypeRef allER = (DataTypeRef)all.getValueAt(i, "DataType");
         int subType = ((Integer)all.getValueAt(i, "SubType")).intValue();
         Integer measureClass = (Integer)all.getValueAt(i, "MeasureClass");
         if(!selected.contains(allER)) {
            avail.add(allER, (String)all.getValueAt(i, "Description"), subType, measureClass);
         }
      }

      return avail;
   }
   
	public FinanceCubeImpl getEditorData() {
		return this.mEditorData;
	}
}
