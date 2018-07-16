// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmCubeMap;
import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import com.cedar.cp.dto.model.amm.AmmDataTypeMapImpl;
import com.cedar.cp.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmmCubeMapImpl implements AmmCubeMap, Serializable {

   protected transient Log mLog = new Log(this.getClass());
   private Integer mTrgId;
   private String mTrgVisId;
   private String mSrcDescr;
   private Integer mSrcId;
   private String mSrcVisId;
   private String mTrgDescr;
   private Integer mAmmModelId;
   private List<AmmDataTypeMap> mChildren;


   public AmmCubeMapImpl(Integer trgId, String trgVisId, String trgDescr, Integer srcId, String srcVisId, String srcDescr, Integer ammModelId) {
      this.mTrgId = trgId;
      this.mTrgVisId = trgVisId;
      this.mTrgDescr = trgDescr;
      this.mSrcId = srcId;
      this.mSrcDescr = srcDescr;
      this.mSrcVisId = srcVisId;
      this.mAmmModelId = ammModelId;
      this.mChildren = new ArrayList();
   }

   public void addDataTypeMap(AmmDataTypeMapImpl map) {
      this.mChildren.add(map);
   }

   public Integer getTargetId() {
      return this.mTrgId;
   }

   public String getTargetVisId() {
      return this.mTrgVisId;
   }

   public String getTargetDescr() {
      return this.mTrgDescr;
   }

   public Integer getSourceId() {
      return this.mSrcId;
   }

   public String getSourceVisId() {
      return this.mSrcVisId;
   }

   public String getSourceDescr() {
      return this.mSrcDescr;
   }

   public Integer getAmmModelId() {
      return this.mAmmModelId;
   }

   public boolean isTargetRefreshNeeded() {
      Iterator i$ = this.mChildren.iterator();

      AmmDataTypeMap x;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         x = (AmmDataTypeMap)i$.next();
      } while(!x.isTargetRefreshNeeded());

      return true;
   }

   public void print() {
      this.mLog.debug("print()", "trgCubeId=" + this.getTargetId() + " trgCubeVisId=" + this.getTargetVisId() + " srcCubeId=" + this.getSourceId() + " srcCubeVisId=" + this.getSourceVisId() + " refreshNeeded=" + this.isTargetRefreshNeeded());
      Iterator i$ = this.mChildren.iterator();

      while(i$.hasNext()) {
         AmmDataTypeMap x = (AmmDataTypeMap)i$.next();
         x.print();
      }

   }

   public String toString() {
      return this.getTargetVisId() + " [" + this.getTargetDescr() + "] <-- " + this.getSourceVisId() + " [" + this.getSourceDescr() + "]";
   }

   public List<AmmDataTypeMap> getDataTypeMaps() {
      return this.mChildren;
   }
}
