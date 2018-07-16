// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.amm.AmmFinDataTypeMap;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AmmFinanceCubeMapping implements Serializable {

   private int mFinanceCubeId;
   private FinanceCubeRef mFinanceCubeRef;
   private String mDescription;
   private int mSourceFinanceCubeId;
   private FinanceCubeRef mSourceFinanceCubeRef;
   private String mSourceDescription;
   private Map<FinanceCubeRef, List<AmmFinDataTypeMap>> mDataTypeMap;


   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public FinanceCubeRef getFinanceCubeRef() {
      return this.mFinanceCubeRef;
   }

   public void setFinanceCubeRef(FinanceCubeRef financeCubeRef) {
      this.mFinanceCubeRef = financeCubeRef;
   }

   public int getSourceFinanceCubeId() {
      return this.mSourceFinanceCubeId;
   }

   public void setSourceFinanceCubeId(int sourceFinanceCubeId) {
      this.mSourceFinanceCubeId = sourceFinanceCubeId;
   }

   public FinanceCubeRef getSourceFinanceCubeRef() {
      return this.mSourceFinanceCubeRef;
   }

   public void setSourceFinanceCubeRef(FinanceCubeRef sourceFinanceCubeRef) {
      this.mSourceFinanceCubeRef = sourceFinanceCubeRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String getSourceDescription() {
      return this.mSourceDescription;
   }

   public void setSourceDescription(String sourceDescription) {
      this.mSourceDescription = sourceDescription;
   }

   public Map<FinanceCubeRef, List<AmmFinDataTypeMap>> getDataTypeMap() {
      return this.mDataTypeMap;
   }

   public void setDataTypeMap(Map<FinanceCubeRef, List<AmmFinDataTypeMap>> dataTypeMap) {
      this.mDataTypeMap = dataTypeMap;
   }
}
