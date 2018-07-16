// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.HierarchyEditorSessionImpl;
import javax.swing.tree.TreeModel;

public class HierarchyAdapter implements Hierarchy {

   private HierarchyImpl mEditorData;
   private HierarchyEditorSessionImpl mEditorSessionImpl;


   public HierarchyAdapter(HierarchyEditorSessionImpl e, HierarchyImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected HierarchyEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected HierarchyImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(HierarchyPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getDimensionId() {
      return this.mEditorData.getDimensionId();
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public DimensionRef getDimensionRef() {
      if(this.mEditorData.getDimensionRef() != null) {
         if(this.mEditorData.getDimensionRef().getNarrative() != null && this.mEditorData.getDimensionRef().getNarrative().length() > 0) {
            return this.mEditorData.getDimensionRef();
         } else {
            try {
               DimensionRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getDimensionEntityRef(this.mEditorData.getDimensionRef());
               this.mEditorData.setDimensionRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setDimensionRef(DimensionRef ref) {
      this.mEditorData.setDimensionRef(ref);
   }

   public void setDimensionId(int p) {
      this.mEditorData.setDimensionId(p);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public TreeModel getTreeModel() {
      return this.mEditorData.getTreeModel();
   }

   public HierarchyNode findElement(Object key) {
      return this.mEditorData.findElement(key);
   }

   public HierarchyNode getRoot() {
      return this.mEditorData.getRoot();
   }

   public Integer getExternalSystemRef() {
      return this.mEditorData.getExternalSystemRef();
   }

   public ModelRef getModel() {
      return this.mEditorData.getModel();
   }

   public boolean isSubmitChangeManagementRequest() {
      return this.mEditorData.isSubmitChangeManagementRequest();
   }

   public boolean isChangeManagementRequestsPending() {
      return this.mEditorData.isChangeManagementRequestsPending();
   }

   public HierarchyNode findElement(String visId) {
      return this.mEditorData.findElement(visId);
   }

   public boolean isNew() {
      return this.mEditorData.isNew();
   }
}
