// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.HierarchyEditor;
import com.cedar.cp.api.dimension.HierarchyEditorSession;
import com.cedar.cp.dto.dimension.DimensionElementListModel;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl;
import com.cedar.cp.impl.dimension.HierarchysProcessImpl;
import com.cedar.cp.util.Log;
import java.util.Collections;
import java.util.List;
import javax.swing.ListModel;

public class HierarchyEditorSessionImpl extends BusinessSessionImpl implements HierarchyEditorSession {

   private DimensionElementListModel mDimensionElements;
   protected HierarchyEditorSessionSSO mServerSessionData;
   private HierarchyEditorSessionServer mEditorSessionServer = new HierarchyEditorSessionServer(this.getConnection());
   protected HierarchyImpl mEditorData;
   protected HierarchyEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public HierarchyEditorSessionImpl(HierarchysProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get Hierarchy", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected HierarchyEditorSessionServer getSessionServer() throws CPException {
      return this.mEditorSessionServer;
   }

   public HierarchyEditor getHierarchyEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new HierarchyEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableDimensions() {
      try {
         return this.getSessionServer().getAvailableDimensions();
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

   public void terminate() {
      try {
         if(this.mEditorSessionServer != null) {
            this.mEditorSessionServer.removeSession();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public EntityList getAvailableDimensionsForInsert(int dimensionType) {
      return this.getSessionServer().getAvailableDimensionsForInsert(dimensionType);
   }

   public void setEditorData(HierarchyImpl hierarchy) {
      this.mEditorData = hierarchy;
      this.mServerSessionData.setEditorData(hierarchy);
      if(this.mClientEditor != null) {
         this.mClientEditor.setHierarchy(hierarchy);
      }

   }

   public ListModel getDimensionElements() {
      if(this.mDimensionElements == null) {
         this.mDimensionElements = new DimensionElementListModel(Collections.EMPTY_LIST);
      }

      return this.mDimensionElements;
   }

   public void setDimensionElements(List<DimensionElement> dimensionElements) {
      if(this.mDimensionElements == null) {
         this.mDimensionElements = new DimensionElementListModel(dimensionElements);
      } else {
         this.mDimensionElements.setDimensionElements(dimensionElements);
      }

   }
}
