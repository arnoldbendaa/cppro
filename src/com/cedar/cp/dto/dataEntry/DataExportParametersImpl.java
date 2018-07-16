// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.DataExportParameters;
import java.io.Serializable;
import javax.swing.tree.TreeModel;

public class DataExportParametersImpl implements DataExportParameters, Serializable {

   private int mModelId;
   private int mFinanceCubeId;
   private TreeModel[] mModels;


   public DataExportParametersImpl(int id) {
      this.mFinanceCubeId = id;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public TreeModel[] getModels() {
      return this.mModels;
   }

   public void setModels(TreeModel[] models) {
      this.mModels = models;
   }
}
