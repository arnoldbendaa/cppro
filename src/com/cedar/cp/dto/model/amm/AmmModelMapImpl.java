// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmCubeMap;
import com.cedar.cp.api.model.amm.AmmModelMap;
import com.cedar.cp.dto.model.amm.AmmCubeMapImpl;
import com.cedar.cp.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmmModelMapImpl implements AmmModelMap, Serializable {

   protected transient Log mLog = new Log(this.getClass());
   private Integer mId;
   private String mVisId;
   private String mDescr;
   private Integer mParentId;
   private Integer mAmmModelId;
   private List<AmmModelMap> mChildren;
   private List<AmmCubeMap> mCubeMaps;


   public AmmModelMapImpl(Integer id, String visId, String descr, Integer parentId, Integer ammModelId) {
      this.mId = id;
      this.mVisId = visId;
      this.mDescr = descr;
      this.mChildren = new ArrayList();
      this.mCubeMaps = new ArrayList();
      this.mParentId = parentId;
      this.mAmmModelId = ammModelId;
   }

   public Integer getParentId() {
      return this.mParentId;
   }

   public Integer getAmmModelId() {
      return this.mAmmModelId;
   }

   public void addModelMap(AmmModelMapImpl mm) {
      this.mChildren.add(mm);
   }

   public void addFinanceCubeMap(AmmCubeMapImpl map) {
      this.mCubeMaps.add(map);
   }

   public List<AmmModelMap> getModelMaps() {
      return this.mChildren;
   }

   public Integer getId() {
      return this.mId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescr() {
      return this.mDescr;
   }

   public boolean isTargetRefreshNeeded() {
      Iterator i$ = this.mCubeMaps.iterator();

      AmmCubeMap x;
      do {
         if(!i$.hasNext()) {
            i$ = this.mChildren.iterator();

            AmmModelMap x1;
            do {
               if(!i$.hasNext()) {
                  return false;
               }

               x1 = (AmmModelMap)i$.next();
            } while(!x1.isTargetRefreshNeeded());

            return true;
         }

         x = (AmmCubeMap)i$.next();
      } while(!x.isTargetRefreshNeeded());

      return true;
   }

   public void setModelMaps(List<AmmModelMap> modelMaps) {
      this.mChildren = modelMaps;
   }

   public void print() {
      this.mLog.debug("print()", "modelId=" + this.getId() + " visId=" + this.getVisId() + " upd=" + this.isTargetRefreshNeeded() + " parent=" + this.getParentId());
      Iterator i$ = this.mCubeMaps.iterator();

      while(i$.hasNext()) {
         AmmCubeMap x = (AmmCubeMap)i$.next();
         x.print();
      }

      i$ = this.mChildren.iterator();

      while(i$.hasNext()) {
         AmmModelMap x1 = (AmmModelMap)i$.next();
         x1.print();
      }

   }

   public List<AmmCubeMap> getCubeMaps() {
      return this.mCubeMaps;
   }

   public String toString() {
      return this.getVisId() + " [" + this.getDescr() + "]";
   }
}
