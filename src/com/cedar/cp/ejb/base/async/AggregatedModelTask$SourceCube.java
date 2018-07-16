// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class AggregatedModelTask$SourceCube implements Serializable {

   private Integer mCubeId;
   private String mCubeVisId;
   private Integer mAmmModelId;
   private List<AmmDataTypeMap> mDataTypeMaps = new ArrayList();


   public AggregatedModelTask$SourceCube(Integer cubeId, String cubeVisId, Integer ammModelId) {
      this.mCubeId = cubeId;
      this.mCubeVisId = cubeVisId;
      this.mAmmModelId = ammModelId;
   }

   public void addDataTypeMap(AmmDataTypeMap map) {
      this.mDataTypeMaps.add(map);
   }

   public Integer getCubeId() {
      return this.mCubeId;
   }

   public String getCubeVisId() {
      return this.mCubeVisId;
   }

   public Integer getAmmModelId() {
      return this.mAmmModelId;
   }

   public List<AmmDataTypeMap> getDataTypeMaps() {
      return this.mDataTypeMaps;
   }

   public String getSourceDataTypeIds() {
      StringBuilder sb = new StringBuilder();

      AmmDataTypeMap d;
      for(Iterator i$ = this.mDataTypeMaps.iterator(); i$.hasNext(); sb.append(d.getSourceId())) {
         d = (AmmDataTypeMap)i$.next();
         if(sb.length() > 0) {
            sb.append(',');
         }
      }

      return sb.toString();
   }
}
