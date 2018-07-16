// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.impl.dimension.DimensionEditorSessionImpl;
import java.util.Collection;
import javax.swing.ListModel;

public class DimensionAdapter implements Dimension {

   private DimensionImpl mEditorData;
   private DimensionEditorSessionImpl mEditorSessionImpl;


   public DimensionAdapter(DimensionEditorSessionImpl e, DimensionImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected DimensionEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected DimensionImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(DimensionPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getType() {
      return this.mEditorData.getType();
   }

   public Integer getExternalSystemRef() {
      return this.mEditorData.getExternalSystemRef();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setType(int p) {
      this.mEditorData.setType(p);
   }

   public void setExternalSystemRef(Integer p) {
      this.mEditorData.setExternalSystemRef(p);
   }

   public Collection getDimensionElements() {
      return this.mEditorData.getDimensionElements();
   }

   public DimensionElement getDimensionElement(Object key) {
      return this.mEditorData.getDimensionElement(key);
   }

   public DimensionElement getDimensionElement(String visId) {
      return this.mEditorData.getDimensionElement(visId);
   }

   public ListModel getListModel() {
      return this.mEditorData.getListModel();
   }

   public DimensionRef getEntityRef() {
      return this.mEditorData.getEntityRef();
   }

   public boolean changeManagementRequestsPending() {
      return this.mEditorData.changeManagementRequestsPending();
   }

   public ModelRef getModelRef() {
      return this.mEditorData.getModelRef();
   }

   public DimensionElement getNullElement() {
      return this.mEditorData.getNullElement();
   }
}
